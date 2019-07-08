package by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {LocalMedicineNamesEntity.class}, version = 1, exportSchema = false)
public abstract class LocalMedicineNameController extends RoomDatabase {
    private static LocalMedicineNameController db;

    public abstract LocalMedicineNamesDao LocalMedicineNamesDao();
}

