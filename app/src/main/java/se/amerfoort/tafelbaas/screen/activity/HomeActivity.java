package se.amerfoort.tafelbaas.screen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import se.amerfoort.tafelbaas.R;
import se.amerfoort.tafelbaas.screen.fragments.ChallangeFragment;
import se.amerfoort.tafelbaas.screen.fragments.GroupsFragment;
import se.amerfoort.tafelbaas.screen.fragments.ResultFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAB_GROUP = "TAB_GROUP";
    private static final String TAB_GAMES = "TAB_GAMES";
    private static final String TAB_CHALLENGES = "TAB_CHALLENGES";
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initialiseView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(
                mTabHost.newTabSpec(TAB_GROUP).setIndicator(getString(R.string.overview), getResources().getDrawable(R.drawable.overview_selector)),
                GroupsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec(TAB_CHALLENGES).setIndicator(getString(R.string.challenges), getResources().getDrawable(R.drawable.challenge_selector)),
                ChallangeFragment.class, null);
//        mTabHost.addTab(
//                mTabHost.newTabSpec(TAB_GAMES).setIndicator(getString(R.string.games), getResources().getDrawable(R.drawable.result_selector)),
//                ResultFragment.class, null);
    }

    @Override
    public void showNextScreen() {

    }

    @Override
    public void onClick(View v) {

    }
}
