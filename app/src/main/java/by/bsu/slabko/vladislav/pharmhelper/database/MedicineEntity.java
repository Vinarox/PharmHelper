package by.bsu.slabko.vladislav.pharmhelper.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MedicineEntity {
    @PrimaryKey
    public int med_id;

    @ColumnInfo(name = "MEDICINE")
    public String medicine;
}
