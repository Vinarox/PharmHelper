package by.bsu.slabko.vladislav.pharmhelper.activities;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncGetDataFromDB;
import by.bsu.slabko.vladislav.pharmhelper.PreStartSettings;
import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.settings.SettingsActivity;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.CitiesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.MedecinesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Medecines;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.home.HomeFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.userList.UserListFragment;
import com.crashlytics.android.Crashlytics;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames.LocalMedicineNamesEntity;
import by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames.LocalMedicineNamesProvider;
import io.fabric.sdk.android.Fabric;

public class HomeActivity extends PreStartSettings {
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private CharSequence title;
    private final String TITLE_TAG = "settingsActivityTitle";
    private FragmentManager transaction;
    private Fragment fragmentHome;
    private Fragment fragmentSearch;
    private Fragment fragmentUserList;
    public static Context homeContext;
    public static LayoutInflater homeInflater;

    private TabHost.TabSpec spec1;
    private TabHost.TabSpec spec2;
    private TabHost.TabSpec spec3;
    private TabHost tabHost;

    SuggestionAdapter<String> adapter;
    List<LocalMedicineNamesEntity>[] items;
    List<String> items_str;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.DNAppTheme);
        super.onCreate(savedInstanceState);
        AppCenter.start(getApplication(), "f484689c-80ff-42d3-9aa9-c395b757cd82",
                Analytics.class, Crashes.class);
        homeContext = this;
        homeInflater = getLayoutInflater();

        super.onResume();
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();





        spec1 = tabHost.newTabSpec(Constants.FRAGMENT_HOME);
        spec1.setContent(R.id.fr1);
        spec1.setIndicator(drawTab(1, true));
        tabHost.addTab(spec1);


        spec2 = tabHost.newTabSpec(Constants.FRAGMENT_SEARCH);
        spec2.setContent(R.id.fr2);
        spec2.setIndicator(drawTab(2, false));
        tabHost.addTab(spec2);


        spec3 = tabHost.newTabSpec(Constants.FRAGMENT_USER_LIST);
        spec3.setContent(R.id.fr3);
        spec3.setIndicator(drawTab(3, false));
        tabHost.addTab(spec3);

        tabHost.setOnTabChangedListener(new TabListener());


        navigationView = findViewById(R.id.nav_view);
        //navigationView.setBackgroundColor(R.color.white);
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MedecinesController medecinesController = new MedecinesController();
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);


        MenuItem searchItem = menu.findItem(R.id.search_icon);
        SearchView searchView =
                (SearchView) searchItem.getActionView();
        searchView.setBackground(getResources().getDrawable(R.drawable.circle));
        LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);
        searchBar.setLayoutTransition(new LayoutTransition());

       SearchView.SearchAutoComplete searchSrcTextView = (SearchView.SearchAutoComplete)
                searchView.findViewById(R.id.search_src_text);
        searchSrcTextView.setThreshold(3);
        items = new List[]{new ArrayList<>()};
        //items.add("1");
        //items.add("11");
        //items.add("101");
        //items.add("22");
        adapter = new SuggestionAdapter<>
                (this, android.R.layout.simple_dropdown_item_1line, items_str);
        searchSrcTextView.setAdapter(adapter);
        searchSrcTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callSearch(items[0].get(position).medicine, String.valueOf(items[0].get(position).med_id));
                return;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() > 0)
                    callSearch(query, null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length() > 2) {
                    LocalMedicineNamesProvider provider = LocalMedicineNamesProvider.getInstance();
                    items[0] = provider.getAllItemsByName("%" + newText + "%");
                    items_str = itemsToString(items[0]);
                    adapter.setItems(items_str);
                    adapter.notifyDataSetChanged();
                }

                return true;
            }



        });

        return super.onCreateOptionsMenu(menu);
    }

    public void callSearch(String query, String id) {
        query = query.trim();
        Constants.searchRes.clear();
        //homeContext.startService(new Intent(homeContext, CleverCloudDataService.class)
        //      .putExtra("name", query));
        AsyncGetDataFromDB createDatabase = new AsyncGetDataFromDB();
        if(id == null)
            createDatabase.execute(query, null);
        else
            createDatabase.execute(query, id);
        try {
            createDatabase.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

                /*for(int i = 0; i < result.size(); i++) {
                    Constants.searchRes.add(new SearchItem(result.get(i)));
                    if(ReservResultActivity.getInstance() != null) {
                        ReservResultActivity.notifyAllData();
                    }
                }*/
        final Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SearchResultActivity.class);
        startActivity(intent);
    }

    class TabListener implements TabHost.OnTabChangeListener {
        public void onTabChanged(String tabId) {
            transaction = getFragmentManager();
            switch (tabId){
                case Constants.FRAGMENT_HOME:
                    setTabColors(1);
                    transaction.beginTransaction()
                            .replace(R.id.change_frame, fragmentHome)
                            .commit();
                    break;
                case Constants.FRAGMENT_SEARCH:
                    setTabColors(2);
                    transaction.beginTransaction()
                            .replace(R.id.change_frame, fragmentSearch)
                            .commit();
                    break;
                case Constants.FRAGMENT_USER_LIST:
                    setTabColors(3);
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
        super.changeTheme(true);
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

    private View drawTab(int tab, boolean needDraw){
        View tabWidget = homeInflater.inflate(R.layout.tab_widjet_view, null, false);
        Drawable coloredIcon = coloredIcon = getResources().getDrawable(R.drawable.ic_home);
        ImageView icon = tabWidget.findViewById(R.id.widget_icon);
        switch (tab){
            case 2:
                coloredIcon = getResources().getDrawable(R.drawable.ic_search);
                break;
            case 3:
                coloredIcon = getResources().getDrawable(R.drawable.ic_user_list);
                break;
        }
        if(needDraw)
            DrawableCompat.setTint(coloredIcon, getResources().getColor(R.color.colorPrimary));
        icon.setImageDrawable(coloredIcon);
        return tabWidget;
    }

    private void setTabColors(int coloredTabID){
        TabWidget tabWidget = tabHost.getTabWidget();
        View parentView;
        ImageView imageView;
        Drawable coloredIcon;
        int a = 0, b = 0, c = 0;
        if(coloredTabID == 1)
            a = 1;
        else if(coloredTabID == 2)
            b = 1;
        else
            c = 1;

        parentView = tabWidget.getChildTabViewAt(0);
        imageView = parentView.findViewById(R.id.widget_icon);
        coloredIcon = getResources().getDrawable(R.drawable.ic_home);
        DrawableCompat.setTint(coloredIcon, getResources().getColor(chooseColor(a)));
        imageView.setImageDrawable(coloredIcon);

        parentView = tabWidget.getChildTabViewAt(1);
        imageView = parentView.findViewById(R.id.widget_icon);
        coloredIcon = getResources().getDrawable(R.drawable.ic_search);
        DrawableCompat.setTint(coloredIcon, getResources().getColor(chooseColor(b)));
        imageView.setImageDrawable(coloredIcon);

        parentView = tabWidget.getChildTabViewAt(2);
        imageView = parentView.findViewById(R.id.widget_icon);
        coloredIcon = getResources().getDrawable(R.drawable.ic_user_list);
        DrawableCompat.setTint(coloredIcon, getResources().getColor(chooseColor(c)));
        imageView.setImageDrawable(coloredIcon);
    }
    private int chooseColor(int color){
        return color == 1 ? R.color.colorPrimary : R.color.tab_grey;
    }


    private List<String> itemsToString(List<LocalMedicineNamesEntity> list){
        List<String> result = new ArrayList<>();
        for(LocalMedicineNamesEntity obj: list){
            result.add(obj.medicine);
        }
        return result;
    }

    static boolean currentLocationChecker = false;
    static Point point;
    static public Point getLocation(){
        MapKitFactory.initialize(homeContext);
        MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                point = location.getPosition();
                currentLocationChecker = true;
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
            }
        });
        while (!currentLocationChecker){}
        currentLocationChecker = true;
        return point;
    }
}
