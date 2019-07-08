package by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LocalMedicineNamesEntity {
    @PrimaryKey(autoGenerate = false)
    public long med_id;

    @ColumnInfo(name = "medicine")
    public String medicine;
}
