package by.bsu.slabko.vladislav.pharmhelper.database;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;

public class MyContentProvider extends ContentProvider {
    static MyContentProvider instance;
    private  DatabaseController db;
    private MedicineDao medicineDao;

    @Override
    public boolean onCreate() {
        //super.onCreate(savedInstance);
        instance = this;
        db = Room.databaseBuilder(getContext(), DatabaseController.class,
                "medecine_table")
                .allowMainThreadQueries()
                .build();

        medicineDao = db.medicineDao();
        return false;
    }
    public static MyContentProvider getInstance() {
        return instance;
    }

    public DatabaseController getDatabase() {
        return db;
    }

    public void addItemToDatabase(String str){
        String[] items = str.split("\t");
        if(items.length == 2) {
            MedicineEntity medicineEntity = new MedicineEntity();
            medicineEntity.med_id = Integer.parseInt(items[0]);
            medicineEntity.medicine = items[1];

            medicineDao.insert(medicineEntity);
        }
    }

    public List<MedicineEntity> getItemByID(int ID){
        return medicineDao.getByID(ID);
    }

    public List<MedicineEntity> getItemByName(String name){
        return medicineDao.getByName(name);
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
