package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects;

import java.util.ArrayList;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.oflineDatabase.OflineMedicineEntity;

public class SearchItem {
    public String pharmName;
    public String address;
    public String district;
    public String phone;
    public float fullPrice;
    public float longitude;
    public float latitude;

    public ArrayList<ItemSlot> expandedList;
    public static SearchItem instance;

    public SearchItem(String pharmacy, String address, String district, String phone,
                       float latitude,float longitude) {
        this.fullPrice  = .0f;
        this.expandedList = new ArrayList<>();
        this.instance = this;
        this.pharmName = pharmacy;
        this.address = address;
        this.district = district;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static SearchItem getInstance(){
        return instance;
    }

    public void addInfo(String pharmName, float price, int quantity, String manuf){
        this.fullPrice += price;
        expandedList.add(new ItemSlot(pharmName, price, quantity, manuf));
    }



    /*public boolean equals(Object o) {
        SearchItem obj = (SearchItem) o;
     return false;
    }*/
}
