package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.oflineDatabase.OflineMedicineEntity;

public class SearchItem {
    public String name;
    public String pharmName;
    public float price;
    public static SearchItem instance;

    public SearchItem(OflineMedicineEntity item) {
        this.instance = this;
        this.name = item.medicine;
        this.pharmName = item.pharmacy;
        this.price = item.price;
    }

    public static SearchItem getInstance(){
        return instance;
    }

    public static void addInfo(){
        SearchResultActivity.notifyAllData();
    }
}
