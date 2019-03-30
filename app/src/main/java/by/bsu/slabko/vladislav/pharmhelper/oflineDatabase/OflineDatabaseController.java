package by.bsu.slabko.vladislav.pharmhelper.oflineDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.bsu.slabko.vladislav.pharmhelper.database.MedicineDao;
import by.bsu.slabko.vladislav.pharmhelper.database.MedicineEntity;

@Database(entities = {OflineMedicineEntity.class}, version = 3, exportSchema = false)
public abstract class OflineDatabaseController extends RoomDatabase {
    private static OflineDatabaseController db;

    public abstract OflineMedicineDao OflineMedicineDao();
}
