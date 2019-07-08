package by.bsu.slabko.vladislav.pharmhelper.constants;

import com.yandex.mapkit.geometry.Point;

import java.sql.Connection;
import java.util.ArrayList;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SimpleExpandableListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;

public class Constants {
    public static Connection connection = null;
    public static final String YANDEX_MAP_KEY = "c9043558-54e4-4794-adc8-c052e65ecdc4";
    public static final String FIREBASE_DATA_URL =
            "https://firebasestorage.googleapis.com/v0/b/pharm-222921.appspot.com" +
                    "/o/SmallIDMedicine_utf-8.csv?alt=media&token=d26b74d8-e8a0-4e30-a04e-1ccd1a6bc527";
   public static final String FIREBASE_ALL_DATA =
           "https://firebasestorage.googleapis.com/v0/b/pharm-222921.appspot.com/o/SmallData_utf-8.csv?alt=media&token=d433d767-504c-4710-9eb4-fc729c2e3b9b";
    public static ArrayList<SearchLine> lines = new ArrayList<>();
   public static ArrayList<SearchItem> searchRes = new ArrayList<>();

    public static String PHARM_DATA_PATH = "";
    public static final String DATA_FILE_NAME = "PharmData.csv";
    public static final String DATA_ALL = "SmallData.csv";

    public static final String FRAGMENT_HOME = "home";
    public static final String FRAGMENT_SEARCH = "search";
    public static final String FRAGMENT_USER_LIST = "userList";

    public static boolean canScrolled = true;

    public static ArrayList<SimpleExpandableListAdapter> adapters = new ArrayList<>();

    public static double myLatitude = 0;
    public static double myLongitude = 0;

    public static int screenWidth;
    public static int screenHight;

    public static Point loacotionPoint = new Point(53.900706, 27.559335);
    public static double minPrice = .0f;
    public static double maxPrice = .0f;

    public final static String APP_THEME = "app_theme";
    public final static String HAS_LOCAL_DB = "hasLocalDB";

    public final static double LOW_LEFT_LAT = 53.826974;
    public final static double LOW_LEFT_LON = 27.346493;
    public final static double UP_RIGHT_LAT = 53.993788;
    public final static double UP_RIGHT_LON = 27.789071;

    public static String isDialogFinished  = null;


    public Constants(int x, int y){
        screenWidth = x;
        screenHight = y;
    }
}
