package by.bsu.slabko.vladislav.pharmhelper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MedicineEntity.class}, version = 2, exportSchema = false)
public abstract class DatabaseController extends RoomDatabase {
    private static DatabaseController db;

    public abstract MedicineDao medicineDao();
}
