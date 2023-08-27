package name.boyle.chris.sgtpuzzles;

import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.Tap;

import android.os.SystemClock;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static android.view.KeyEvent.KEYCODE_1;
import static android.view.KeyEvent.KEYCODE_2;
import static android.view.KeyEvent.KEYCODE_3;
import static android.view.KeyEvent.KEYCODE_D;
import static android.view.KeyEvent.KEYCODE_DPAD_CENTER;
import static android.view.KeyEvent.KEYCODE_DPAD_DOWN;
import static android.view.KeyEvent.KEYCODE_DPAD_LEFT;
import static android.view.KeyEvent.KEYCODE_DPAD_RIGHT;
import static android.view.KeyEvent.KEYCODE_DPAD_UP;
import static android.view.KeyEvent.KEYCODE_G;
import static android.view.KeyEvent.KEYCODE_L;
import static android.view.KeyEvent.KEYCODE_SPACE;
import static android.view.KeyEvent.KEYCODE_V;
import static android.view.KeyEvent.KEYCODE_Z;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

import name.boyle.chris.sgtpuzzles.backend.BLACKBOX;
import name.boyle.chris.sgtpuzzles.backend.BRIDGES;
import name.boyle.chris.sgtpuzzles.backend.BackendName;
import name.boyle.chris.sgtpuzzles.backend.CUBE;
import name.boyle.chris.sgtpuzzles.backend.DOMINOSA;
import name.boyle.chris.sgtpuzzles.backend.FIFTEEN;
import name.boyle.chris.sgtpuzzles.backend.FILLING;
import name.boyle.chris.sgtpuzzles.backend.FLIP;
import name.boyle.chris.sgtpuzzles.backend.FLOOD;
import name.boyle.chris.sgtpuzzles.backend.GALAXIES;
import name.boyle.chris.sgtpuzzles.backend.GUESS;
import name.boyle.chris.sgtpuzzles.backend.INERTIA;
import name.boyle.chris.sgtpuzzles.backend.KEEN;
import name.boyle.chris.sgtpuzzles.backend.LIGHTUP;
import name.boyle.chris.sgtpuzzles.backend.LOOPY;
import name.boyle.chris.sgtpuzzles.backend.MAGNETS;
import name.boyle.chris.sgtpuzzles.backend.MAP;
import name.boyle.chris.sgtpuzzles.backend.MINES;
import name.boyle.chris.sgtpuzzles.backend.MOSAIC;
import name.boyle.chris.sgtpuzzles.backend.NET;
import name.boyle.chris.sgtpuzzles.backend.NETSLIDE;
import name.boyle.chris.sgtpuzzles.backend.PALISADE;
import name.boyle.chris.sgtpuzzles.backend.PATTERN;
import name.boyle.chris.sgtpuzzles.backend.PEARL;
import name.boyle.chris.sgtpuzzles.backend.PEGS;
import name.boyle.chris.sgtpuzzles.backend.RANGE;
import name.boyle.chris.sgtpuzzles.backend.RECT;
import name.boyle.chris.sgtpuzzles.backend.SAMEGAME;
import name.boyle.chris.sgtpuzzles.backend.SIGNPOST;
import name.boyle.chris.sgtpuzzles.backend.SINGLES;
import name.boyle.chris.sgtpuzzles.backend.SIXTEEN;
import name.boyle.chris.sgtpuzzles.backend.SLANT;
import name.boyle.chris.sgtpuzzles.backend.SOLO;
import name.boyle.chris.sgtpuzzles.backend.TENTS;
import name.boyle.chris.sgtpuzzles.backend.TOWERS;
import name.boyle.chris.sgtpuzzles.backend.TRACKS;
import name.boyle.chris.sgtpuzzles.backend.TWIDDLE;
import name.boyle.chris.sgtpuzzles.backend.UNDEAD;
import name.boyle.chris.sgtpuzzles.backend.UNEQUAL;
import name.boyle.chris.sgtpuzzles.backend.UNRULY;
import name.boyle.chris.sgtpuzzles.backend.UNTANGLE;

@RunWith(Parameterized.class)
public class GamePlayParameterizedTest {

	private static final Set<BackendName> _usedBackends = new LinkedHashSet<>();
	private static final Set<Object[]> _params = new LinkedHashSet<>();

	private final BackendName _backend;
	private final String _gameID;
	private final ViewAction[] _viewActions;

	private static void addExamples() {
		addExample(NET.INSTANCE, "1x2:42", new GeneralClickAction(Tap.SINGLE,
				squareProportions(0, -0.25), Press.FINGER, 0, 0));
		addExample(BLACKBOX.INSTANCE, "w3h3m1M1:38727296",
				KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER);
		addExample(BRIDGES.INSTANCE, "3x3m2:3a2c1b",
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT);
		addExample(CUBE.INSTANCE, "c4x4:0C56,0", KEYCODE_DPAD_DOWN, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_LEFT);
		addExample(DOMINOSA.INSTANCE, "1:011100",
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER);
		addExample(FIFTEEN.INSTANCE, "2x2:1,2,0,3", KEYCODE_DPAD_LEFT);
		addExample(FILLING.INSTANCE, "2x1:02", KEYCODE_DPAD_UP, KEYCODE_2);
		addExample(FLIP.INSTANCE, "2x2:edb7,d", KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(FLOOD.INSTANCE, "2x2:1212,6", KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(GALAXIES.INSTANCE, "3x3:co",
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(GUESS.INSTANCE, "c2p2g2Bm:c2ab",
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(INERTIA.INSTANCE, "3x3:wbbSbbgms", KEYCODE_DPAD_DOWN);
		addExample(KEEN.INSTANCE, "3:_baa_3a,m6s1m3a3",
				KEYCODE_DPAD_UP, KEYCODE_1, KEYCODE_DPAD_RIGHT, KEYCODE_3, KEYCODE_DPAD_RIGHT,
				KEYCODE_2, KEYCODE_DPAD_DOWN, KEYCODE_1, KEYCODE_DPAD_LEFT, KEYCODE_2,
				KEYCODE_DPAD_LEFT, KEYCODE_3, KEYCODE_DPAD_DOWN, KEYCODE_2, KEYCODE_DPAD_RIGHT,
				KEYCODE_1, KEYCODE_DPAD_RIGHT, KEYCODE_3);
		addExample(LIGHTUP.INSTANCE, "2x2:a0b", KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER);
		addExample(LOOPY.INSTANCE, "3x3t0:02a2a1c",
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_DPAD_UP,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER);
		addExample(MAGNETS.INSTANCE, "3x2:111,21,111,12,TTTBBB",
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(MAP.INSTANCE, "3x2n5:afa,3120a", KEYCODE_DPAD_UP,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(MINES.INSTANCE, "4x3:2,0,m5d9",
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER);
		addExample(MOSAIC.INSTANCE, "3x3:a4a4a6c", KEYCODE_DPAD_UP,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(NET.INSTANCE, "1x2:12", KEYCODE_DPAD_UP, KEYCODE_D);
		addExample(NETSLIDE.INSTANCE, "2x2:ch116", KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER);
		addExample(PALISADE.INSTANCE, "2x3n3:d33", KEYCODE_L, KEYCODE_L,
				KEYCODE_DPAD_DOWN, KEYCODE_L, KEYCODE_L,
				KEYCODE_DPAD_DOWN, KEYCODE_L, KEYCODE_L);
		addExample(PATTERN.INSTANCE, "1x2:2/1/1", KEYCODE_DPAD_UP,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER);
		addExample(PEARL.INSTANCE, "5x5:dBaWaBgWaBeB", KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER);
		addExample(PEGS.INSTANCE, "4x4:PHPPHPPOPOPOPOPO", KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_DOWN);
		addExample(RANGE.INSTANCE, "3x2:b2_4b",
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(RECT.INSTANCE, "2x2:2a2a", KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(SAMEGAME.INSTANCE, "2x2c3s2:1,1,3,3", KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER);
		addExample(SIGNPOST.INSTANCE, "3x2:1ccfcg6a",
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER);
		addExample(SINGLES.INSTANCE, "2x2:1121", KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER);
		addExample(SIXTEEN.INSTANCE, "2x2:1,4,3,2", KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER);
		addExample(SLANT.INSTANCE, "2x2:1c1d", KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_LEFT, KEYCODE_DPAD_CENTER);
		addExample(SOLO.INSTANCE, "2j:1c,b__", KEYCODE_DPAD_RIGHT, KEYCODE_2,
				KEYCODE_DPAD_DOWN, KEYCODE_1, KEYCODE_DPAD_LEFT, KEYCODE_2);
		addExample(TENTS.INSTANCE, "4x4:baj_,1,1,0,1,1,0,2,0", KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_LEFT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER);
		addExample(TOWERS.INSTANCE, "3:1/2/3/2/2/1/1/2/2/3/2/1",
				KEYCODE_DPAD_UP, KEYCODE_3, KEYCODE_DPAD_RIGHT, KEYCODE_2, KEYCODE_DPAD_RIGHT,
				KEYCODE_1, KEYCODE_DPAD_DOWN, KEYCODE_2, KEYCODE_DPAD_LEFT, KEYCODE_3,
				KEYCODE_DPAD_LEFT, KEYCODE_1, KEYCODE_DPAD_DOWN, KEYCODE_2, KEYCODE_DPAD_RIGHT,
				KEYCODE_1, KEYCODE_DPAD_RIGHT, KEYCODE_3);
		addExample(TRACKS.INSTANCE, "4x4:Cm9a,3,2,S3,4,S3,4,3,2", KEYCODE_DPAD_UP, KEYCODE_DPAD_UP,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER);
		addExample(TWIDDLE.INSTANCE, "2x2n2:4,3,2,1",
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_CENTER);
		addExample(UNDEAD.INSTANCE, "3x3:1,2,2,dLaRLL,2,0,2,2,2,0,1,0,0,2,1,2",
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_Z, KEYCODE_DPAD_UP, KEYCODE_Z,
				KEYCODE_DPAD_RIGHT, KEYCODE_G, KEYCODE_DPAD_RIGHT, KEYCODE_V, KEYCODE_DPAD_DOWN,
				KEYCODE_V);
		addExample(UNEQUAL.INSTANCE, "3:0D,0,0,0,0,0,0R,0,0U,",
				KEYCODE_DPAD_UP, KEYCODE_3, KEYCODE_DPAD_RIGHT, KEYCODE_2, KEYCODE_DPAD_RIGHT,
				KEYCODE_1, KEYCODE_DPAD_DOWN, KEYCODE_2, KEYCODE_DPAD_LEFT, KEYCODE_3,
				KEYCODE_DPAD_LEFT, KEYCODE_1, KEYCODE_DPAD_DOWN, KEYCODE_2, KEYCODE_DPAD_RIGHT,
				KEYCODE_1, KEYCODE_DPAD_RIGHT, KEYCODE_3);
		addExample(UNRULY.INSTANCE, "6x6:BCCAHgBCga", KEYCODE_DPAD_UP,
				KEYCODE_SPACE, KEYCODE_DPAD_DOWN, KEYCODE_SPACE, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN,
				KEYCODE_SPACE, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT,
				KEYCODE_SPACE, KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_SPACE, KEYCODE_DPAD_UP,
				KEYCODE_SPACE, KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_DPAD_RIGHT, KEYCODE_SPACE,
				KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN, KEYCODE_SPACE, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_DOWN, KEYCODE_SPACE, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP,
				KEYCODE_SPACE, KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP,
				KEYCODE_DPAD_CENTER, KEYCODE_DPAD_UP, KEYCODE_SPACE, KEYCODE_DPAD_UP, KEYCODE_SPACE,
				KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_DOWN, KEYCODE_SPACE, KEYCODE_DPAD_DOWN,
				KEYCODE_DPAD_DOWN, KEYCODE_SPACE, KEYCODE_DPAD_DOWN, KEYCODE_DPAD_DOWN,
				KEYCODE_SPACE, KEYCODE_DPAD_RIGHT, KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_UP, KEYCODE_SPACE, KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER,
				KEYCODE_DPAD_UP, KEYCODE_DPAD_CENTER);
		addExample(UNTANGLE.INSTANCE, "4:0-1,0-2,0-3,1-2,1-3,2-3", new GeneralSwipeAction(Swipe.FAST,
				squareProportions(0, -0.42), squareProportions(0, 0.25), Press.FINGER));
	}

	private static void addExample(final BackendName backend, final String gameID, final int... keystrokes) {
		final ArrayList<ViewAction> actions = new ArrayList<>();
		for (int key : keystrokes) {
			actions.add(pressKey(key));
		}
		addExample(backend, gameID, actions.toArray(new ViewAction[0]));
	}

	private static void addExample(final BackendName backend, final String gameID, final ViewAction... actions) {
		_usedBackends.add(backend);
		_params.add(new Object[]{backend, gameID, actions});
	}

	@Parameters(name = "{0}:{1}")
	public static Iterable<Object[]> data() {
		addExamples();
		for (final BackendName backend : BackendName.getAll()) {
			if (!_usedBackends.contains(backend)) {
				addExample(backend, null, KEYCODE_DPAD_CENTER);  // testGameCompletion will fail appropriately
			}
		}
		return _params;
	}

	public GamePlayParameterizedTest(final BackendName backend, final String gameID, final ViewAction... viewActions) {
		_backend = backend;
		_gameID = gameID;
		_viewActions = viewActions;
	}

	@Test
	public void testGameCompletion() {
		assertNotNull("Missing test for " + _backend, _gameID);
		final Uri uri = Uri.parse("sgtpuzzles:" + _backend + ":" + _gameID);
		final Intent intent = new Intent(Intent.ACTION_VIEW, uri, ApplicationProvider.getApplicationContext(), GamePlay.class);
		try(ActivityScenario<GamePlay> ignored = ActivityScenario.launch(intent)) {
			if (_viewActions[0] instanceof GeneralSwipeAction) {
				SystemClock.sleep(500);  // Untangle seems to need this (multiple layout passes?)
			}
			onView(withText(R.string.starting)).check(doesNotExist());
			assertCompleted(false);
			onView(withId(R.id.game_view)).perform(_viewActions);
			assertCompleted(true);
		}
	}

	private void assertCompleted(final boolean isCompleted) {
		// Note that flood and mines copy the status bar so there is score info before/after "COMPLETED".
		final Matcher<View> titleMatcher = withText(containsString(getApplicationContext().getString(R.string.COMPLETED)));
		if (isCompleted) {
			onView(titleMatcher).inRoot(isDialog()).check(matches(isDisplayed()));
		} else {
			onView(titleMatcher).check(doesNotExist());
		}
	}

	@SuppressWarnings("SameParameterValue")
	private static CoordinatesProvider squareProportions(final double xProp, final double yProp) {
		return view -> {
			final int[] screenPos = new int[2];
			view.getLocationOnScreen(screenPos);
			final int squareSz = Math.min(view.getWidth(), view.getHeight());
			final float screenX = (float) (screenPos[0] + (0.5 * view.getWidth()) + xProp * squareSz);
			final float screenY = (float) (screenPos[1] + (0.5 * view.getHeight()) + yProp * squareSz);
			return new float[]{screenX, screenY};
		};
	}
}
