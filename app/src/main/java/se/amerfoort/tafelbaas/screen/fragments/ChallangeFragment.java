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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.DialogUtitlity;
import se.amerfoort.tafelbaas.Utils.TafelBossPreference;
import se.amerfoort.tafelbaas.modal.response.ChallengeResponse;
import se.amerfoort.tafelbaas.rest.ApiClient;
import se.amerfoort.tafelbaas.rest.ApiInterface;
import se.amerfoort.tafelbaas.screen.activity.AddScoreActivity;
import se.amerfoort.tafelbaas.screen.activity.HomeActivity;

import static se.amerfoort.tafelbaas.screen.activity.AddScoreActivity.GROUP_ID;
import static se.amerfoort.tafelbaas.screen.activity.AddScoreActivity.OPONENT_NAME;
import static se.amerfoort.tafelbaas.screen.activity.AddScoreActivity.USER_ID;

public class ChallangeFragment extends Fragment implements Callback<List<ChallengeResponse>> {
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
        v = inflater.inflate(R.layout.fragment_mychallange, container, false);
        listView = (ListView) v.findViewById(R.id.listChallange);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(
                getString(R.string.challenges));
        getChallenges();
    }

    private void getChallenges() {
        //TafelBossPreference.getInstance().saveStringData(TafelBossPreference.USER_ID, "u-5e8161cb-412d-43d7-a3f9-1aa92aabc088");
        DialogUtitlity.showLoading(getActivity());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ChallengeResponse>> callClient = apiService.getChallenges(ApiClient.getHeaders());
        callClient.enqueue(this);
    }

    private void chnageChallaenge(String type, String id) {
        //TafelBossPreference.getInstance().saveStringData(TafelBossPreference.USER_ID, "u-5e8161cb-412d-43d7-a3f9-1aa92aabc088");
        DialogUtitlity.showLoading(getActivity());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> callClient = apiService.changeChallengeState(ApiClient.getHeaders(), type, id);
        callClient.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                DialogUtitlity.hideLoading();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body() instanceof String) {
                        String resultResponse = response.body();
                        if (resultResponse.equalsIgnoreCase("{\"result\":\"succes\"}")) {
                            getChallenges();
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_success_alert_title), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                DialogUtitlity.hideLoading();
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
            }
        });
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
                holder.txtGroup = (TextView) row.findViewById(R.id.txtGroup);
                holder.btnGreen = (Button) row.findViewById(R.id.btnGreen);
                holder.btnRed = (Button) row.findViewById(R.id.btnRed);
                row.setTag(holder);
            } else {
                holder = (QuestionHolder) row.getTag();
            }

            final ChallengeResponse challenge = getItem(position);
            if (challenge.getAccepted().equalsIgnoreCase("1")) {
                holder.btnGreen.setText(getString(R.string.my_challenges_button_add_scores_title));
                holder.btnRed.setText(getString(R.string.my_challenges_button_cancel_challenge_title));
                holder.btnGreen.setEnabled(true);
                holder.btnGreen.setAlpha(1.0f);
                holder.btnGreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent registrationIntent = new Intent(getActivity(), AddScoreActivity.class);
                        registrationIntent.putExtra(AddScoreActivity.USER_NAME, challenge.getUser().getName());
                        registrationIntent.putExtra(OPONENT_NAME, challenge.getOpponent().getName());
                        registrationIntent.putExtra(USER_ID, challenge.getUser_id());
                        registrationIntent.putExtra(GROUP_ID, challenge.getGroup_id());
                        startActivity(registrationIntent);
                    }
                });
                holder.btnRed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chnageChallaenge("cancel", challenge.getChallenge_id());
                    }
                });
            } else {
                String userId = TafelBossPreference.getInstance().getStringData(TafelBossPreference.USER_ID);
                if (challenge.getUser_id().equalsIgnoreCase(userId)) {
                    holder.btnGreen.setText(getString(R.string.my_challenges_button_waiting_on_opponent_title));
                    holder.btnGreen.setEnabled(false);
                    holder.btnGreen.setAlpha(0.7f);
                    holder.btnRed.setText(getString(R.string.my_challenges_button_cancel_challenge_title));
                    holder.btnGreen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    holder.btnRed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chnageChallaenge("cancel", challenge.getChallenge_id());
                        }
                    });
                } else {
                    holder.btnGreen.setText(getString(R.string.my_challenges_button_accept_challenge_title));
                    holder.btnRed.setText(getString(R.string.my_challenges_button_decline_challenge_title));
                    holder.btnGreen.setEnabled(true);
                    holder.btnGreen.setAlpha(1.0f);
                    holder.btnGreen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chnageChallaenge("accept", challenge.getChallenge_id());
                        }
                    });
                    holder.btnRed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chnageChallaenge("cancel", challenge.getChallenge_id());
                        }
                    });
                }
            }
            holder.txtNameBold.setText(challenge.getUser().getName() + " vs " + challenge.getOpponent().getName());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(challenge.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            holder.txtGroup.setText(challenge.getGroup().getName() + " : " + sdf.format(calendar.getTime()));
            return row;
        }


        class QuestionHolder {
            TextView txtNameBold;
            TextView txtGroup;
            Button btnGreen;
            Button btnRed;
        }
    }
}