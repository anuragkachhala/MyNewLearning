package se.amerfoort.tafelbaas.screen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.DialogUtitlity;
import se.amerfoort.tafelbaas.modal.response.GroupIdResponse;
import se.amerfoort.tafelbaas.rest.ApiClient;
import se.amerfoort.tafelbaas.rest.ApiInterface;


public class AddGroupActivity extends BaseActivity implements Callback<GroupIdResponse> {
    private EditText editName;
    private EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_new_group));
        super.onCreate(savedInstanceState);
    }

    private void createGroup() {
        DialogUtitlity.showLoading(AddGroupActivity.this);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GroupIdResponse> callClient = apiService.creatGroups(ApiClient.getGroupHeaders(), editName.getText().toString(), editDescription.getText().toString());
        callClient.enqueue(this);
    }

    @Override
    public void initialiseView() {
        editName = (EditText) findViewById(R.id.edtGroupName);
        editDescription = (EditText) findViewById(R.id.edtMessage);
        Button btnAdd = (Button) findViewById(R.id.btnContinue);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
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
