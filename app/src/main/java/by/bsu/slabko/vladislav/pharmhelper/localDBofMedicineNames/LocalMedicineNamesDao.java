package by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface LocalMedicineNamesDao {
    @Query("SELECT * FROM LocalMedicineNamesEntity")
    List<LocalMedicineNamesEntity> getAll();

    @Query("SELECT * FROM LocalMedicineNamesEntity WHERE med_id = :id")
    List<LocalMedicineNamesEntity> getByID(long id);

    @Query("SELECT * FROM LocalMedicineNamesEntity WHERE medicine = :med ")
    LocalMedicineNamesEntity getByName(String med);

    @Query("SELECT * FROM LocalMedicineNamesEntity WHERE medicine like :med")
    List<LocalMedicineNamesEntity> getAllByName(String med);

    @Insert
    void insert(LocalMedicineNamesEntity employee);

    @Update
    void update(LocalMedicineNamesEntity employee);

    @Delete
    void delete(LocalMedicineNamesEntity employee);
}
