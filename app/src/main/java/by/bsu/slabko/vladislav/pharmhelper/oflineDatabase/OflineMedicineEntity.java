package by.bsu.slabko.vladislav.pharmhelper.oflineDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class OflineMedicineEntity {
    @PrimaryKey(autoGenerate = true)
    public long med_id;

    @ColumnInfo(name = "medicine")
    public String medicine;

    @ColumnInfo(name = "manufacturer")
    public String manufacturer;
    @ColumnInfo(name = "price")
    public float price;
    @ColumnInfo(name = "instock")
    public int instock;
    @ColumnInfo(name = "pharmacy")
    public String pharmacy;
    @ColumnInfo(name = "address")
    public String district;
    @ColumnInfo(name = "district")
    public String address;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "latitude")
    public float latitude;
    @ColumnInfo(name = "longitude")
    public float longitude;
}
