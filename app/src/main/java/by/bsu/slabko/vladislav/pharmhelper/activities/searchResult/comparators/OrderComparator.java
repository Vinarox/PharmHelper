package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators;

import java.util.Comparator;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;

public class OrderComparator implements Comparator<SearchItem> {

    public int compare(SearchItem item1, SearchItem item2) {
        if(item1.fullPrice > item2.fullPrice)
            return 1;
        else
            return -1;
    }
}