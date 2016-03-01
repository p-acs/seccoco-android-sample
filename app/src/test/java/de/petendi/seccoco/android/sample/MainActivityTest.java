package de.petendi.seccoco.android.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void testOnCreate() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
        final String testSecret = "this is some secret";
        mainActivity.setSecret(testSecret);
        mainActivity.onDestroy();
        MainActivity restoredMainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
        assertEquals(testSecret, restoredMainActivity.getSecret());
    }

}