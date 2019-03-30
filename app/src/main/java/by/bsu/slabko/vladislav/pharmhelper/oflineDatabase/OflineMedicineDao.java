package by.bsu.slabko.vladislav.pharmhelper.oflineDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.database.MedicineEntity;

@Dao
public interface OflineMedicineDao {
    @Query("SELECT * FROM OflineMedicineEntity")
    List<OflineMedicineEntity> getAll();

    @Query("SELECT * FROM OflineMedicineEntity WHERE med_id = :id")
    List<OflineMedicineEntity> getByID(long id);

    @Query("SELECT * FROM OflineMedicineEntity WHERE medicine like :med ")
    List<OflineMedicineEntity> getByName(String med);

    @Insert
    void insert(OflineMedicineEntity employee);

    @Update
    void update(OflineMedicineEntity employee);

    @Delete
    void delete(OflineMedicineEntity employee);
}
