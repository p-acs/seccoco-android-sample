package de.petendi.seccoco.android.sample;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import de.petendi.seccoco.android.Seccoco;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ServerMessengerTest {

    private final String server = "FILLME";
    private final String cert = "FILLME";

    @Ignore("replace FILLME with the real values of your setup")
    @Test
    public void testSendMessage() throws Exception {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
        Seccoco seccoco = ((SeccocoApplication)mainActivity.getApplication()).getSeccoco();
        ServerMessenger serverMessenger = new ServerMessenger(seccoco,server,cert);
        String message = "hello";
        String response = serverMessenger.sendMessage(message);
        assertEquals("Echo: " + message,response);
    }
}