package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators;

import java.util.Comparator;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;


public class LocationComparator implements Comparator<SearchItem> {

    public int compare(SearchItem item1, SearchItem item2) {
        double dist1 = Math.pow(item1.latitude - Constants.myLatitude, 2) + Math.pow(item1.longitude - Constants.myLongitude, 2);
        double dist2 = Math.pow(item2.latitude - Constants.myLatitude, 2) + Math.pow(item2.longitude - Constants.myLongitude, 2);
        if (dist1 > dist2)
            return 1;
        else
            return -1;
    }
}
