package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends ActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener,FeedbackFragment.OnFragmentInteractionListener,NavigationDrawerFragment.NavigationDrawerCallbacks,View.OnClickListener,PollsFragment.OnFragmentInteractionListener,NoticesFragment.OnFragmentInteractionListener{
    NavigationDrawerFragment navDrawer = new NavigationDrawerFragment();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    boolean isFabOpen;
    boolean backPressed=false;
    private FloatingActionButton fab,suggest,complaint;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    @Override
    public void onBackPressed() {
       // do something on back.
        if(backPressed==false) {
            Toast.makeText(HomeActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
            backPressed=true;
        }
        else {
            animateFAB();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private CharSequence mTitle;
    public void animateFAB(){
        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            suggest.startAnimation(fab_close);
            complaint.startAnimation(fab_close);
            suggest.setClickable(false);
            complaint.setClickable(false);
            isFabOpen = false;
            Log.d("fab", "close");

        } else {

            fab.startAnimation(rotate_forward);
            suggest.startAnimation(fab_open);
            complaint.startAnimation(fab_open);
            suggest.setClickable(true);
            complaint.setClickable(true);
            isFabOpen = true;
            Log.d("fab", "open");

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

     //   NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        isFabOpen=false;
        fab = (FloatingActionButton)findViewById(R.id.fab);
        suggest = (FloatingActionButton)findViewById(R.id.suggest);
        complaint = (FloatingActionButton)findViewById(R.id.complaint);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        suggest.setOnClickListener(this);
        complaint.setOnClickListener(this);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = "Home";

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            case 1:
            fragmentManager.beginTransaction()
                    .replace(R.id.container, NoticesFragment.newInstance("str1","str2"))
                    .commit();
                mTitle = "Notices";
                break;

            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PollsFragment.newInstance("str1", "str2"))
                        .commit();
                mTitle = "Polls";
                break;

            case 3:
                Intent i = new Intent(HomeActivity.this,ComplaintActivity.class);
                startActivity(i);
                break;

            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FeedbackFragment.newInstance("str1", "str2"))
                        .commit();
                mTitle = "Feedback";
                break;
        }
    }

    public void onSectionAttached(int number) {

        mTitle = navDrawer.categories[number-1];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.suggest:
                animateFAB();
                Intent sugggestIntent = new Intent(getApplicationContext(),SuggestionsActivity.class);
                startActivity(sugggestIntent);
                break;
            case R.id.complaint:
                animateFAB();
                Intent complaintIntent = new Intent(HomeActivity.this,Complaint1Activity.class);
                startActivity(complaintIntent);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
