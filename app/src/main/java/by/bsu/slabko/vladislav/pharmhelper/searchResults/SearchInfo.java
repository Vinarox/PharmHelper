package by.bsu.slabko.vladislav.pharmhelper.searchResults;

import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;

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

    public void addNewInfo(GenericTypeIndicator<HashMap<String, List<String>>> info){
        System.out.println();
        Log.d("SearchInfo", "added");
    }

    public void clearAllInfo(){

    }

}
