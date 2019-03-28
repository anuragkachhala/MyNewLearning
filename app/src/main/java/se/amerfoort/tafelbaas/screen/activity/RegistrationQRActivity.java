package se.amerfoort.tafelbaas.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.DialogUtitlity;
import se.amerfoort.tafelbaas.Utils.TafelBossPreference;
import se.amerfoort.tafelbaas.modal.request.UserNameRequest;
import se.amerfoort.tafelbaas.modal.response.UserIdResponse;
import se.amerfoort.tafelbaas.rest.ApiClient;
import se.amerfoort.tafelbaas.rest.ApiInterface;

import static se.amerfoort.tafelbaas.Utils.TafelBossPreference.QR_CODE;
import static se.amerfoort.tafelbaas.Utils.TafelBossPreference.USER_ID;
import static se.amerfoort.tafelbaas.Utils.TafelBossPreference.USER_NAME;

public class RegistrationQRActivity extends BaseActivity implements Callback<UserIdResponse> {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registration_qr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QRCode Scan");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initialiseView() {
        Button btnScanQrCode = (Button) findViewById(R.id.btnScanQrCode);
        btnScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readQRCode();
            }
        });
        parseData();
    }

    private void parseData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString(USER_NAME);
        }
    }

    private void readQRCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan QR code on the table tennis table.");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    public void showNextScreen() {
        Intent registrationIntent = new Intent(this, HomeActivity.class);
        startActivity(registrationIntent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, getString(R.string.fail_scan), Toast.LENGTH_LONG).show();
            } else {
                String qrCodes = result.getContents();
                qrCodes = qrCodes.substring(28, qrCodes.lastIndexOf('/'));
                TafelBossPreference.getInstance().saveStringData(QR_CODE, qrCodes);
                getUserId();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getUserId() {
        DialogUtitlity.showLoading(RegistrationQRActivity.this);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        UserNameRequest userNameRequest = new UserNameRequest();
        userNameRequest.setDisplay_name(userName);
        Call<UserIdResponse> callClient = apiService.registerUser(userNameRequest);
        callClient.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserIdResponse> call, Response<UserIdResponse> response) {
        DialogUtitlity.hideLoading();
        int statusCode = response.code();
        if (statusCode == 200) {
            if (response.body() instanceof UserIdResponse) {
                UserIdResponse userIdResponse = response.body();
                TafelBossPreference.getInstance().saveStringData(USER_ID, userIdResponse.getUser_id());
                showNextScreen();
            } else {
                Toast.makeText(this, getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<UserIdResponse> call, Throwable t) {
        DialogUtitlity.hideLoading();
        TafelBossPreference.getInstance().saveStringData(USER_ID, "u-fe094312-182d-4ee3-a85b-cadf1b4c9594");
        showNextScreen();
    }
}
