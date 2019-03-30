package by.bsu.slabko.vladislav.pharmhelper.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedicineDao {
    @Query("SELECT * FROM MedicineEntity")
    List<MedicineEntity> getAll();

    @Query("SELECT * FROM MedicineEntity WHERE med_id = :id")
    List<MedicineEntity> getByID(long id);

    @Query("SELECT * FROM MedicineEntity WHERE medicine like :med")
    List<MedicineEntity> getByName(String med);

    @Insert
    void insert(MedicineEntity employee);

    @Update
    void update(MedicineEntity employee);

    @Delete
    void delete(MedicineEntity employee);
}
