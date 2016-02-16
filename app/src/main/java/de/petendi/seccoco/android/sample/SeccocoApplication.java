package de.petendi.seccoco.android.sample;

import android.app.Application;

import de.petendi.seccoco.android.Seccoco;
import de.petendi.seccoco.android.SeccocoFactory;

public class SeccocoApplication extends Application {

    private Seccoco seccoco;

    @Override
    public void onCreate() {
        super.onCreate();
        byte [] randomBytesForLegacy = {0x34,0x04,0x02,0x21,0x55,0x6e,0x02,0x7e,0x36,0x5a,0x6e,0x7d,0x11,0x1f,0x7a };
        seccoco = SeccocoFactory.createWithLegacySupport(this,randomBytesForLegacy);
        seccoco.unlock();
    }

    public Seccoco getSeccoco() {
        return seccoco;
    }
}
