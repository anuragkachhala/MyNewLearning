package se.amerfoort.tafelbaas.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.TafelBossPreference;

import static se.amerfoort.tafelbaas.Utils.TafelBossPreference.USER_ID;

public class SplashActivity extends BaseActivity {

    private static final long NAVIGATION_TIME_DURATION = 2000;
    private Handler navigationHandler = new Handler();
    private Runnable navigationRunnable = new Runnable() {
        @Override
        public void run() {
            showNextScreen();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        TafelBossPreference.setmContext(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initialiseView() {
        navigationHandler.postDelayed(navigationRunnable, NAVIGATION_TIME_DURATION);
    }

    @Override
    public void showNextScreen() {
        if (TafelBossPreference.getInstance().getStringData(USER_ID).isEmpty()) {
            Intent registrationIntent = new Intent(this, RegistrationActivity.class);
            startActivity(registrationIntent);
        } else {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }
        finish();
    }
}
