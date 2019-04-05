package by.bsu.slabko.vladislav.pharmhelper.oflineDatabase;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


import java.util.List;

public class OflineMyContentProvider extends ContentProvider {
    static OflineMyContentProvider instance;
    private  OflineDatabaseController db;
    private OflineMedicineDao medicineDao;


    @Override
    public boolean onCreate() {
        instance = this;
        db = Room.databaseBuilder(getContext(), OflineDatabaseController.class,
                "ofline_medecine_table")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        medicineDao = db.OflineMedicineDao();
        return false;
    }
    public static OflineMyContentProvider getInstance() {
        return instance;
    }

    public OflineDatabaseController getDatabase() {
        return db;
    }

    public void addItemToDatabase(String str, long id){
        String[] items = str.split("\t");
        if(items.length == 11) {
            OflineMedicineEntity OflineMedicineEntity = new OflineMedicineEntity();
            //OflineMedicineEntity.med_id = id;
            OflineMedicineEntity.medicine = items[1];

            OflineMedicineEntity.manufacturer = items[2];

            OflineMedicineEntity.price= Float.parseFloat(items[3]);

            OflineMedicineEntity.instock = Integer.parseInt(items[4]);

            OflineMedicineEntity.pharmacy = items[5];

            OflineMedicineEntity.district = items[6];

            OflineMedicineEntity.address = items[7];

            OflineMedicineEntity.phone = items[8];

            OflineMedicineEntity.latitude = Float.parseFloat(items[9]);

            OflineMedicineEntity.longitude = Float.parseFloat(items[10]);

            medicineDao.insert(OflineMedicineEntity);
        }
    }

    public List<OflineMedicineEntity> getItemByID(int ID){
        return medicineDao.getByID(ID);
    }

    public List<OflineMedicineEntity> getItemByName(String name){
        //List<OflineMedicineEntity> it = medicineDao.getAll();
         List<OflineMedicineEntity> items = medicineDao.getByName(name);
        // if(items.size() > 0) {
             //10118250
            // getDataFromFirebaseDatabase(items.get(0).med_id);
         //}
        return items;
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
