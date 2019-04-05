package by.bsu.slabko.vladislav.pharmhelper.searchResults;

import android.util.Log;


import java.util.HashMap;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;

public class SearchInfo {
    private static SearchInfo instance;
    public SearchInfo(){
        this.instance = this;
    }

    public static SearchInfo getInstance() {
        return instance;
    }

}
