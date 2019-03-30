package by.bsu.slabko.vladislav.pharmhelper.constants;

import android.app.Fragment;
import android.content.Context;

import java.util.ArrayList;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;

public class Constants {
    public static final String YANDEX_MAP_KEY = "c9043558-54e4-4794-adc8-c052e65ecdc4";
    public static final String FIREBASE_DATA_URL =
            "https://firebasestorage.googleapis.com/v0/b/pharm-222921.appspot.com" +
                    "/o/SmallIDMedicine_utf-8.csv?alt=media&token=d26b74d8-e8a0-4e30-a04e-1ccd1a6bc527";
   /* public static final String FIREBASE_DATA_URL =
            "https://firebasestorage.googleapis.com/v0/b/pharm-222921.appspot.com/o/dialog.txt?alt=media&token=235ce7d5-8e7d-4f99-b33e-52f64dcde61f";*/
   public static final String FIREBASE_ALL_DATA =
           "https://firebasestorage.googleapis.com/v0/b/pharm-222921.appspot.com/o/SmallData_utf-8.csv?alt=media&token=d433d767-504c-4710-9eb4-fc729c2e3b9b";
    public static ArrayList<SearchLine> lines = new ArrayList<>();
   public static ArrayList<SearchItem> searchRes = new ArrayList<>();

    public static String PHARM_DATA_PATH = "";
    public static final String DATA_FILE_NAME = "PharmData.csv";
 public static final String DATA_ALL = "SmallData_utf-8.csv";

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
