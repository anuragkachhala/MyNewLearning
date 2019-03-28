package se.amerfoort.tafelbaas.screen.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.Utils.DialogUtitlity;
import se.amerfoort.tafelbaas.modal.request.JoinRequest;
import se.amerfoort.tafelbaas.modal.response.ChallengeIdResponse;
import se.amerfoort.tafelbaas.modal.response.GroupResponse;
import se.amerfoort.tafelbaas.modal.response.User;
import se.amerfoort.tafelbaas.rest.ApiClient;
import se.amerfoort.tafelbaas.rest.ApiInterface;
import se.amerfoort.tafelbaas.screen.activity.AddGroupActivity;
import se.amerfoort.tafelbaas.screen.activity.HomeActivity;

public class GroupsFragment extends Fragment {
    private View v;
    private ListView listView;
    private ImageView imgAdd;
    private AlertDialog dialogJoinGroup;
    private AlertDialog dialogChallengeUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_group, container, false);
        setHasOptionsMenu(true);
        listView = (ListView) v.findViewById(R.id.listGroup);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
        //inflater.inflate(R.menu.add_menu, menu);
        menu.add("Add")
                .setIcon(R.drawable.ic_add)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent registrationIntent = new Intent(getActivity(), AddGroupActivity.class);
        startActivity(registrationIntent);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(
                getString(R.string.mygroup));
        getGroups();
    }

    private void getGroups() {
        DialogUtitlity.showLoading(getActivity());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<GroupResponse>> callClient = apiService.getGroups(ApiClient.getHeaders());
        callClient.enqueue(new Callback<List<GroupResponse>>() {
            @Override
            public void onResponse(Call<List<GroupResponse>> call, Response<List<GroupResponse>> response) {
                DialogUtitlity.hideLoading();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body() instanceof List) {
                        List<GroupResponse> groupResponse = response.body();
                        GroupAdapter groupAdapter = new GroupAdapter(getActivity(), R.layout.row_group, groupResponse);
                        listView.setAdapter(groupAdapter);
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
                DialogUtitlity.hideLoading();
            }
        });
    }

    private void joinGroups(String groupId) {
        DialogUtitlity.showLoading(getActivity());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setGroup(groupId);
        String join = "group:" + groupId;
        Call<String> callClient = apiService.joinGroup(ApiClient.getGroupHeaders(), groupId);
        callClient.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                DialogUtitlity.hideLoading();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body() instanceof String) {
                        String resultResponse = response.body();
                        if (resultResponse.contains("{\"result\":\"succes\"}")) {
                            getGroups();
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

    private void challengeUser(User user) {
        DialogUtitlity.showLoading(getActivity());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ChallengeIdResponse> callClient = apiService.challenge(ApiClient.getHeaders(), user.getUser_id(), user.getGroupScores().getGroup().getHash(), "0");
        callClient.enqueue(new Callback<ChallengeIdResponse>() {
            @Override
            public void onResponse(Call<ChallengeIdResponse> call, Response<ChallengeIdResponse> response) {
                DialogUtitlity.hideLoading();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body() instanceof ChallengeIdResponse) {
                        ChallengeIdResponse challengeIdResponse = response.body();
                        if (challengeIdResponse.getChallenge_id() != null) {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_success_alert_title), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_failed_alert_title), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sending_scores_success_alert_title), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChallengeIdResponse> call, Throwable t) {
                DialogUtitlity.hideLoading();
            }
        });
    }

    private void showJoinGroupDialog(final GroupResponse groupResponse) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_group_join, null);
        dialogBuilder.setView(dialogView);
        TextView txtSeler = (TextView) dialogView.findViewById(R.id.txtSeler);
        TextView txtDate = (TextView) dialogView.findViewById(R.id.txtDate);
        TextView txtGroupName2 = (TextView) dialogView.findViewById(R.id.txtGroupName2);
        TextView txtGroupName = (TextView) dialogView.findViewById(R.id.txtGroupName);

        ImageView imgClose = (ImageView) dialogView.findViewById(R.id.imgClose);
        Button btnJoinGroup = (Button) dialogView.findViewById(R.id.btnJoinGroup);

        txtGroupName.setText(groupResponse.getName());
        txtGroupName2.setText(groupResponse.getDescription());
        txtSeler.setText("Spelers : " + groupResponse.getUsers().size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(groupResponse.getTimestamp());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDate.setText(sdf.format(calendar.getTime()));
        txtDate.setVisibility(View.GONE);
        dialogJoinGroup = dialogBuilder.create();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogJoinGroup.dismiss();
            }
        });
        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroups(groupResponse.getHash());
                dialogJoinGroup.dismiss();
            }
        });
        dialogJoinGroup.show();
    }

    private void showChallengeUserDialog(final User user) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_challenge_user, null);
        dialogBuilder.setView(dialogView);

        TextView txtGroupName = (TextView) dialogView.findViewById(R.id.txtGroupName);
        TextView txtGames = (TextView) dialogView.findViewById(R.id.txtGames);
        TextView txtDate = (TextView) dialogView.findViewById(R.id.txtDate);
        TextView txtWon = (TextView) dialogView.findViewById(R.id.txtWon);
        TextView txtLost = (TextView) dialogView.findViewById(R.id.txtLost);
        TextView txtDraw = (TextView) dialogView.findViewById(R.id.txtDraw);

        ImageView imgClose = (ImageView) dialogView.findViewById(R.id.imgClose);
        Button btnJoinGroup = (Button) dialogView.findViewById(R.id.btnJoinGroup);

        txtGroupName.setText(user.getDisplay_name());
        txtGames.setText(getString(R.string.total_games) + " " + user.getMatches_won() + user.getMatches_lost());
        int total = user.getMatches_won() + user.getMatches_lost();
        txtWon.setText("-"+getString(R.string.won) + " " + total);
        if (user.getGroupScores().getGroup().getName() != null) {
            txtDraw.setText(getString(R.string.group) + " " + user.getGroupScores().getGroup().getName());
        } else {
            txtDraw.setVisibility(View.GONE);
        }
        txtLost.setText("-"+getString(R.string.lost) + " " + user.getMatches_lost());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(user.getTimestamp());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDate.setText(sdf.format(calendar.getTime()));
        txtDate.setVisibility(View.GONE);

        dialogJoinGroup = dialogBuilder.create();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogJoinGroup.dismiss();
            }
        });
        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeUser(user);
                dialogJoinGroup.dismiss();
            }
        });
        dialogJoinGroup.show();
    }

    public class GroupAdapter extends ArrayAdapter<GroupResponse> {
        public GroupAdapter(Context context, int layoutResourceId, List<GroupResponse> data) {
            super(context, layoutResourceId, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            QuestionHolder holder = null;
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.row_group, parent, false);
                holder = new QuestionHolder();
                holder.layoutUser = (LinearLayout) row.findViewById(R.id.layoutUser);
                holder.txtGroup = (TextView) row.findViewById(R.id.txtGroupName);
                holder.imgInfo = (ImageView) row.findViewById(R.id.imgInfo);
                row.setTag(holder);
            } else {
                holder = (QuestionHolder) row.getTag();
            }

            final GroupResponse groupResponse = getItem(position);
            holder.txtGroup.setText(groupResponse.getName());
            holder.layoutUser.removeAllViews();
            for (final User user : groupResponse.getUsers()) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_user, null);
                TextView txtUser = (TextView) view.findViewById(R.id.txtUserName);
                TextView txtCount = (TextView) view.findViewById(R.id.txtCount);
                txtCount.setText(user.getMatches_won() + "");
                txtUser.setText(user.getDisplay_name());
                holder.layoutUser.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showChallengeUserDialog(user);
                    }
                });
            }
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showJoinGroupDialog(groupResponse);
                }
            });
            return row;
        }


        class QuestionHolder {
            TextView txtGroup;
            LinearLayout layoutUser;
            ImageView imgInfo;
        }
    }
}