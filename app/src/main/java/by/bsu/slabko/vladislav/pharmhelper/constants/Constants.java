package by.bsu.slabko.vladislav.pharmhelper.constants;

import android.app.Fragment;
import android.content.Context;

public class Constants {
    public static Context appContext;
    public static Fragment currentFragment = null;
    public static String currentFragmentName = "";

    public static final String FRAGMENT_HOME = "home";
    public static final String FRAGMENT_SEARCH = "search";
    public static final String FRAGMENT_USER_LIST = "userList";

    public static final String fragmentDesktopName = "nav_desk";
    public static final String fragmentListName = "nav_list";
    public static final String fragmentGridName = "nav_grid";
    public static final String fragmentSettingsName = "nav_settings";

    public static final String MODEL_DEFAULT = "default";
    public static final String MODEL_TIGHT = "tight";
    public static final String SORT_DEFAULT = "no_sort";

    public static final String KEY_THEME = "pref_theme";
    public static final String KEY_MODEL = "pref_model";
    public static final String KEY_SORT = "sort";
    public static final String KEY_SHOW_WELCOME_PAGE_ON_NEXT_LOAD = "pref_show_welcome_page_on_next_load";

    public static final String THEME_LIGHT = "AppTheme";
    public static final String THEME_DARK = "MyTheme";

    public static int screenWidth;
    public static int screenHight;

    public static final String SETTINGS_THEME = "list_theme";
    public static final String SETTINGS_SORT = "sort";

    public static String MODEL_CURRENT = "default";
    public static String SORT_CURRENT = "no_sort";
    public static boolean SHOW_WELCOME_PAGE = true;

    public static final String SETTINGS_MODEL = "list_maquette";

    public static final String NO_SORT = "no_sort";
    public static final String DATE_SORT = "date_sort";
    public static final String AZ_SORT = "AZ_sort";
    public static final String ZA_SORT = "ZA_sort";


    public Constants(int x, int y){
        screenWidth = x;
        screenHight = y;
    }
}
