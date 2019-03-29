package by.bsu.slabko.vladislav.pharmhelper.activities;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;


import by.bsu.slabko.vladislav.pharmhelper.PreStartSettings;
import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.settings.SettingsActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.home.HomeFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;
import by.bsu.slabko.vladislav.pharmhelper.fragment.userList.UserListFragment;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class HomeActivity extends PreStartSettings {
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private CharSequence title;
    private final String TITLE_TAG = "settingsActivityTitle";
    private SettingsActivity settingsFragment;
    private FragmentManager transaction;
    private Fragment fragmentHome;
    private Fragment fragmentSearch;
    private Fragment fragmentUserList;
    public static Context homeContext;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.DNAppTheme);
        super.onCreate(savedInstanceState);
        homeContext = this;
        //final Intent intn = new Intent();
        //intn.setClass(this, ContentProvider.class);
        //startActivity(intn);

        super.onResume();
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);

       // SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
       // int searchBarId = searchView.getContext().getResources().getIdentifier("android:id/search_bar", null, null);
        //LinearLayout searchBar = (LinearLayout) searchView.findViewById(searchBarId);
        //searchBar.setLayoutTransition(new LayoutTransition());

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec(Constants.FRAGMENT_HOME);
        spec.setContent(R.id.fr1);
        spec.setIndicator("Главная", getResources().getDrawable(R.drawable.ic_home));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(Constants.FRAGMENT_SEARCH);
        spec.setContent(R.id.fr2);
        spec.setIndicator("Комплексный поиск", getResources().getDrawable(R.drawable.ic_search));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(Constants.FRAGMENT_USER_LIST);
        spec.setContent(R.id.fr3);
        spec.setIndicator("Мой список", getResources().getDrawable(R.drawable.ic_user_list));
        tabHost.addTab(spec);

        tabHost.setOnTabChangedListener(new TabListener());

        navigationView = findViewById(R.id.nav_view);
        TextView mSlideshowTextView;
        mSlideshowTextView = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_city));
        mSlideshowTextView.setGravity(Gravity.CENTER_VERTICAL);
        mSlideshowTextView.setTypeface(null, Typeface.BOLD);
        mSlideshowTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        mSlideshowTextView.setTextSize(22);
        mSlideshowTextView.setText("Минск");

        fragmentHome = HomeFragment.newInstance(this);
        fragmentSearch = PharmacySearchFragment.newInstance();
        fragmentUserList = UserListFragment.newInstance();

        transaction = getFragmentManager();
        transaction.beginTransaction()
                .replace(R.id.change_frame, fragmentHome)
                .commit();

        mDrawerLayout = findViewById(R.id.drawer_layout);

        final Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_settings:
                                if (savedInstanceState != null) {
                                    title = savedInstanceState.getCharSequence(TITLE_TAG);
                                }
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        if(Constants.lines.size() == 0)
            Constants.lines.add(new SearchLine(homeContext));
    }


    class TabListener implements TabHost.OnTabChangeListener {
        public void onTabChanged(String tabId) {
            transaction = getFragmentManager();
            switch (tabId){
                case Constants.FRAGMENT_HOME:
                    transaction.beginTransaction()
                            .replace(R.id.change_frame, fragmentHome)
                            .commit();
                    break;
                case Constants.FRAGMENT_SEARCH:
                    transaction.beginTransaction()
                            .replace(R.id.change_frame, fragmentSearch)
                            .commit();
                    break;
                case Constants.FRAGMENT_USER_LIST:
                    transaction.beginTransaction()
                            .replace(R.id.change_frame, fragmentUserList)
                            .commit();
                    break;
            }
        }
    }



    public void myImageClick(View view) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        final Intent intent = new Intent();
        //intent.setClass(AppListActivity.this, ProfileActivity.class);
        //startActivity(intent);
        //mDrawerLayout.closeDrawers();
        /*if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }*/
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, title);
    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title = savedInstanceState.getCharSequence(TITLE_TAG);
    }
}
