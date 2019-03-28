package se.amerfoort.tafelbaas.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.DialogUtitlity;
import se.amerfoort.tafelbaas.modal.response.GroupIdResponse;
import se.amerfoort.tafelbaas.rest.ApiClient;
import se.amerfoort.tafelbaas.rest.ApiInterface;

/**
 * Created by skushwa1 on 10/31/2017.
 */
public class AddScoreActivity extends BaseActivity implements Callback<GroupIdResponse> {
    public static final String USER_NAME = "USER_NAME";
    public static final String OPONENT_NAME = "OPONENT_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String GROUP_ID = "GROUP_ID";
    private EditText edtYourScore;
    private EditText edtOpponentScore;
    private String userId;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_fillscore);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_score_title));
        super.onCreate(savedInstanceState);
    }

    private void parseData() {
        Bundle extras = getIntent().getExtras();
        TextView txtYourScore = (TextView) findViewById(R.id.txtYourScore);
        TextView txtOpponentScore = (TextView) findViewById(R.id.txtOpponentScore);
        if (extras != null) {
            txtYourScore.setText(extras.getString(USER_NAME));
            txtOpponentScore.setText(extras.getString(OPONENT_NAME));
//            txtYourScore.setText(getString(R.string.your_score)+"(" + extras.getString(USER_NAME) + ")");
//            txtOpponentScore.setText(getString(R.string.oponent_score)+"(" + extras.getString(OPONENT_NAME) + ")");
            userId = extras.getString(userId);
            groupId = extras.getString(groupId);
        }
    }

    @Override
    public void initialiseView() {
        edtYourScore = (EditText) findViewById(R.id.edtYourScore);
        edtOpponentScore = (EditText) findViewById(R.id.edtOpponentScore);
        parseData();
        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postScore();
            }
        });
        Button btnReadGame = (Button) findViewById(R.id.btnReadGame);
        btnReadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(AddScoreActivity.this, HelpActivity.class);
                startActivity(registrationIntent);
            }
        });
    }

    private void postScore() {
        DialogUtitlity.showLoading(this);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> callClient = apiService.postScore(ApiClient.getHeaders(), edtYourScore.getText().toString(), userId, edtOpponentScore.getText().toString(), groupId);
        callClient.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                DialogUtitlity.hideLoading();
                Toast.makeText(getApplicationContext(), getString(R.string.sending_scores_success_alert_title), Toast.LENGTH_LONG).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                DialogUtitlity.hideLoading();
                Toast.makeText(getApplicationContext(), getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showNextScreen() {

    }

    @Override
    public void onResponse(Call<GroupIdResponse> call, Response<GroupIdResponse> response) {
        DialogUtitlity.hideLoading();
        int statusCode = response.code();
        finish();
    }

    @Override
    public void onFailure(Call<GroupIdResponse> call, Throwable t) {
        DialogUtitlity.hideLoading();
        finish();
    }
}
