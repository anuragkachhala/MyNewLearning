package se.amerfoort.tafelbaas.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import se.amerfoort.tafelbaas.R;

import static se.amerfoort.tafelbaas.Utils.TafelBossPreference.USER_NAME;

public class RegistrationActivity extends BaseActivity {
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registration);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initialiseView() {
        edtName = (EditText) findViewById(R.id.edtName);
        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextScreen();
            }
        });
    }

    @Override
    public void showNextScreen() {
        Intent registrationIntent = new Intent(this, RegistrationQRActivity.class);
        registrationIntent.putExtra(USER_NAME, edtName.getText().toString());
        startActivity(registrationIntent);
    }


}
