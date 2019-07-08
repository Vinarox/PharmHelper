package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects;

import java.util.ArrayList;
import java.util.Objects;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.MinskInfo;
import by.bsu.slabko.vladislav.pharmhelper.oflineDatabase.OflineMedicineEntity;

public class SearchItem {
    int pharmId;
    public String pharmName;
    public String address;
    public String district;
    public String phone;
    public double fullPrice;
    public double longitude;
    public double latitude;

    public ArrayList<ItemSlot> expandedList;
    public static SearchItem instance;

    public SearchItem(int pharmId){
        this.pharmId = pharmId;
    }

    public SearchItem(int pharmId, String pharmacy, String address, String district, String phone,
                       double latitude,double longitude) {
        this.fullPrice  = .0f;
        this.expandedList = new ArrayList<>();
        this.instance = this;
        
        this.pharmId = pharmId;
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

    public void addInfo(String pharmName, double price, int quantity, String manuf){
        this.fullPrice += price;
        expandedList.add(new ItemSlot(pharmName, price, quantity, manuf));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchItem that = (SearchItem) o;
        return pharmId == that.pharmId;
    }
}