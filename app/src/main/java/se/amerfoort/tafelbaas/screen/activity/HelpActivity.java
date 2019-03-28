package se.amerfoort.tafelbaas.screen.activity;

import android.os.Bundle;

import se.amerfoort.tafelbaas.R;

public class HelpActivity extends BaseActivity {
    @Override
    public void initialiseView() {

    }

    @Override
    public void showNextScreen() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Help");
        super.onCreate(savedInstanceState);
    }
}
