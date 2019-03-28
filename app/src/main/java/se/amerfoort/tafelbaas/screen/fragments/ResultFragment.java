package se.amerfoort.tafelbaas.screen.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.DialogUtitlity;
import se.amerfoort.tafelbaas.Utils.TafelBossPreference;
import se.amerfoort.tafelbaas.modal.response.ChallengeResponse;
import se.amerfoort.tafelbaas.rest.ApiClient;
import se.amerfoort.tafelbaas.rest.ApiInterface;
import se.amerfoort.tafelbaas.screen.activity.AddScoreActivity;
import se.amerfoort.tafelbaas.screen.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment implements Callback<List<ChallengeResponse>> {
    private View v;
    private ListView listView;
    private ImageView imgAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_result, container, false);
        return v;
    }

    @Override
    public void onResume() {
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(
                "My Result");
        super.onResume();
    }

    private void getChallenges() {
        TafelBossPreference.getInstance().saveStringData(TafelBossPreference.USER_ID, "u-5e8161cb-412d-43d7-a3f9-1aa92aabc088");
        DialogUtitlity.showLoading(getActivity());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ChallengeResponse>> callClient = apiService.getChallenges(ApiClient.getHeaders());
        callClient.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ChallengeResponse>> call, Response<List<ChallengeResponse>> response) {
        DialogUtitlity.hideLoading();
        int statusCode = response.code();
        if (statusCode == 200) {
            if (response.body() instanceof List) {
                List<ChallengeResponse> challengeResponses = response.body();
                List<ChallengeResponse> dataToShow = new ArrayList<>();
                for (ChallengeResponse challengeResponse1 : challengeResponses) {
                    if (challengeResponse1.getDone().equalsIgnoreCase("0") && challengeResponse1.getCanceled().equalsIgnoreCase("0")) {
                        dataToShow.add(challengeResponse1);
                    }
                }
                GroupAdapter groupAdapter = new GroupAdapter(getActivity(), R.layout.row_group, dataToShow);
                listView.setAdapter(groupAdapter);
            }
        } else {

        }
    }

    @Override
    public void onFailure(Call<List<ChallengeResponse>> call, Throwable t) {
        DialogUtitlity.hideLoading();
        t.printStackTrace();
    }

    public class GroupAdapter extends ArrayAdapter<ChallengeResponse> {
        public GroupAdapter(Context context, int layoutResourceId, List<ChallengeResponse> data) {
            super(context, layoutResourceId, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            QuestionHolder holder = null;
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.row_challange, parent, false);
                holder = new QuestionHolder();
                holder.txtNameBold = (TextView) row.findViewById(R.id.txtNameBold);
                holder.txtGroup = (TextView) row.findViewById(R.id.txtGroupName);
                holder.btnGreen = (Button) row.findViewById(R.id.btnGreen);
                holder.btnRed = (Button) row.findViewById(R.id.btnRed);
                row.setTag(holder);
            } else {
                holder = (QuestionHolder) row.getTag();
            }

            final ChallengeResponse challenge = getItem(position);
            if (challenge.getAccepted().equalsIgnoreCase("1")) {
                holder.btnGreen.setText("Add score");
                holder.btnRed.setText("Cancel");
                holder.btnGreen.setEnabled(true);
                holder.btnGreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent registrationIntent = new Intent(getActivity(), AddScoreActivity.class);
                        startActivity(registrationIntent);
                    }
                });
            } else {
                String userId = TafelBossPreference.getInstance().getStringData(TafelBossPreference.USER_ID);
                if (challenge.getUser_id().equalsIgnoreCase(userId)) {
                    holder.btnGreen.setText("Waiting on opponent");
                    holder.btnGreen.setEnabled(false);
                    holder.btnRed.setText("Cancel");
                } else {
                    holder.btnGreen.setText("Accept");
                    holder.btnRed.setText("Decline");
                    holder.btnGreen.setEnabled(true);
                }
            }
            //holder.txtName.setText();
            return row;
        }


        class QuestionHolder {
            TextView txtName;
            TextView txtNameBold;
            TextView txtGroup;
            Button btnGreen;
            Button btnRed;
        }
    }
}