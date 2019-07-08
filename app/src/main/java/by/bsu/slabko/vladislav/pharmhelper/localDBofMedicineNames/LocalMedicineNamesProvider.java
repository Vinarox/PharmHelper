package by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Medecines;


public class LocalMedicineNamesProvider extends ContentProvider {
    static LocalMedicineNamesProvider instance;
    private LocalMedicineNameController db;
    private LocalMedicineNamesDao medicineDao;


    @Override
    public boolean onCreate() {
        instance = this;
        db = Room.databaseBuilder(getContext(), LocalMedicineNameController.class,
                "local_medicine_names_table")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        medicineDao = db.LocalMedicineNamesDao();
        return false;
    }
    public static LocalMedicineNamesProvider getInstance() {
        return instance;
    }

    public LocalMedicineNameController getDatabase() {
        return db;
    }

    public void addItemsToDatabase(ArrayList<Medecines> list){
        for(Medecines obj: list) {
            LocalMedicineNamesEntity LocalMedicineNamesEntity = new LocalMedicineNamesEntity();
            LocalMedicineNamesEntity.med_id = obj.getIdMedicine();
            LocalMedicineNamesEntity.medicine = obj.getMedecine();
            medicineDao.insert(LocalMedicineNamesEntity);
        }
    }

    public List<LocalMedicineNamesEntity> getAll(){
        return medicineDao.getAll();
    }

    public LocalMedicineNamesEntity getItemByName(String name){
        return medicineDao.getByName(name);
    }

    public List<LocalMedicineNamesEntity> getAllItemsByName(String name){
        return medicineDao.getAllByName(name);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }
    @Override
    public String getType( Uri uri) {
        return null;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }
    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}

