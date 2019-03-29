package by.bsu.slabko.vladislav.pharmhelper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;

@Database(entities = {MedicineEntity.class}, version = 1)
public abstract class DatabaseController extends RoomDatabase {
    private static DatabaseController db;

    public abstract MedicineDao medicineDao();
}
