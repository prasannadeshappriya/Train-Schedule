package com.example.prasanna.trainshadule.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.prasanna.trainshadule.DAO.TrainStationDAO;
import com.example.prasanna.trainshadule.Fragments.FeedBackFragment;
import com.example.prasanna.trainshadule.Fragments.TrainScheduleFragment;
import com.example.prasanna.trainshadule.R;
import com.example.prasanna.trainshadule.ServerRequest.Request;
import com.example.prasanna.trainshadule.Utilities.Constants;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TrainStationDAO trainStationDAO = new TrainStationDAO(this);
        if (trainStationDAO.getTotalCount()>0){
            Log.i(Constants.TAG, "Database already has updated data");
            showTrainScheduleFragment showTrainScheduleFragment = new showTrainScheduleFragment(this,this);
            showTrainScheduleFragment.execute();
        }else {
            pd = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            TrainScheduleFragment trainScheduleFragment = new TrainScheduleFragment();
            trainScheduleFragment.synchronize(this, pd, this);
            refreshHome(trainScheduleFragment);
        }
    }

    public class showTrainScheduleFragment extends AsyncTask<Void,Void,Void>{
        private TrainScheduleFragment trainScheduleFragment;
        private Context context;
        private HomeActivity activity;
        public showTrainScheduleFragment(Context context, HomeActivity activity){
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Request request = new Request(context,null, activity);
            request.getLines();
            return null;
        }

        @Override
        protected void onPreExecute() {
            trainScheduleFragment = new TrainScheduleFragment();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frmMain, trainScheduleFragment,Constants.FRAGMENT_TRAIN_SCHEDULE);
            toolbar.setTitle("Train Schedule");
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_train_schedule) {
            TrainScheduleFragment trainScheduleFragment = new TrainScheduleFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frmMain, trainScheduleFragment,Constants.FRAGMENT_TRAIN_SCHEDULE);
            toolbar.setTitle("Train Schedule");
            transaction.commit();
        }else if(id == R.id.nav_feedback){
            FeedBackFragment feedBackFragment = new FeedBackFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frmMain,feedBackFragment,Constants.FRAGMENT_FEEDBACK);
            toolbar.setTitle("FeedBack");
            transaction.commit();
        }else if(id==R.id.nav_schedule_history){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void refreshHome(TrainScheduleFragment trainScheduleFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frmMain);
        if(currentFragment==null) {
            if(trainScheduleFragment==null){
                trainScheduleFragment = new TrainScheduleFragment();
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frmMain, trainScheduleFragment, Constants.FRAGMENT_TRAIN_SCHEDULE);
            toolbar.setTitle("Train Schedule");
            transaction.commit();
        }else{
            if (currentFragment.getTag().equals(Constants.FRAGMENT_TRAIN_SCHEDULE)) {
                TrainScheduleFragment fragment = (TrainScheduleFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_TRAIN_SCHEDULE);
                fragment.updateDAO();
            }
        }
    }
}