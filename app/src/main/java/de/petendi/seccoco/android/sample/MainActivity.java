package de.petendi.seccoco.android.sample;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.petendi.seccoco.android.Seccoco;

public class MainActivity extends AppCompatActivity {
    private final String PREF_KEY_SECRET = "PREF_KEY_SECRET";
    private final String APP_PREFS = "app-prefs";
    private final String NOT_SET = "NOT_SET";

    private Seccoco seccoco;
    private String secret = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeccocoApplication seccocoApplication = (SeccocoApplication) getApplication();
        seccoco = seccocoApplication.getSeccoco();
        secret = seccoco.io().getSharedPreferences(APP_PREFS,MODE_PRIVATE).getString(PREF_KEY_SECRET,NOT_SET);
    }

    void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persist();

    }

    private void persist() {
        SharedPreferences preferences = seccoco.io().getSharedPreferences(APP_PREFS,MODE_PRIVATE);
        preferences.edit().putString(PREF_KEY_SECRET, secret).commit();

    }
}
