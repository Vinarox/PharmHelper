package by.bsu.slabko.vladislav.pharmhelper;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncAppsInfo;
import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncCreateDatabase;
import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncCreateOflineDatabase;
import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncDB;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.database.MyContentProvider;
import by.bsu.slabko.vladislav.pharmhelper.searchResults.SearchInfo;

public class PreStartSettings extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener  {

    private static boolean loadedFromSharedPreferences = false;
    private boolean showWelcomePage = true;
    private long downloadID;

    @Override
    protected void onResume() {
        super.onResume();
        boolean setTrue = false;
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        new Constants(size.x, size.y);
        new SearchInfo();
        loadSettingsFromSharedPreferences();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDownloaded = sharedPreferences.getBoolean("IS_DOWNLOADED", false);
        if(!isDownloaded) {
            boolean isDBFull = sharedPreferences.getBoolean("IS_DB_FULL", false);

            try {
                File dir = getExternalFilesDir(null);
                Log.d("FIle", Environment
                        .getExternalStorageDirectory().toString() + "/" + Constants.DATA_FILE_NAME);
                BufferedReader br = new BufferedReader(new FileReader(new File(dir, "/storage/emulated/0/Download/" + Constants.DATA_FILE_NAME)));

            } catch (FileNotFoundException e) {
                AsyncAppsInfo faviconTask = new AsyncAppsInfo();
                faviconTask.execute(getApplicationContext());
                try {
                    faviconTask.get(2, TimeUnit.MINUTES);
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ////////////////////////////// Waiting for downloading file
            boolean isDownloadedEnd;
            if (!isDBFull) {
                        //AsyncCreateDatabase createDatabase = new AsyncCreateDatabase();
                       // createDatabase.execute(getExternalFilesDir(null));

                        sharedPreferences
                                .edit().
                                putBoolean("IS_DB_FULL", true)
                                .apply();
            }

///////////////
            ////////////////
            ///////////
            boolean isOflineDBFull = sharedPreferences.getBoolean("IS_OFLINE_DB_FULL", false);
            try {
                File dir = getExternalFilesDir(null);
                Log.d("FIle", Environment
                        .getExternalStorageDirectory().toString() + "/" + Constants.DATA_ALL);
                BufferedReader br = new BufferedReader(new FileReader(new File(dir, "/storage/emulated/0/Download/" + Constants.DATA_ALL)));


            } catch (FileNotFoundException e) {
                AsyncDB faviconDB = new AsyncDB();
                faviconDB.execute(getApplicationContext());
                try {
                    downloadID = faviconDB.get(2, TimeUnit.MINUTES);
                    setTrue = true;
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!isOflineDBFull) {
                        //AsyncCreateOflineDatabase createDatabase = new AsyncCreateOflineDatabase();
                        //createDatabase.execute(getExternalFilesDir(null));
                        if(setTrue) {
                            sharedPreferences
                                    .edit().
                                    putBoolean("IS_OFLINE_DB_FULL", true)
                                    .apply();
                        }

            }

            sharedPreferences
                    .edit().
                    putBoolean("IS_DOWNLOADED", true)
                    .apply();
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Log.d("creation", "Completed");
                Toast.makeText(PreStartSettings.this, "Первый запуск. Поиск скоро будет доступен.", Toast.LENGTH_SHORT).show();
                AsyncCreateOflineDatabase createDatabase = new AsyncCreateOflineDatabase();
                createDatabase.execute(getExternalFilesDir(null));
                /*try {
                    createDatabase.get();
                    Toast.makeText(PreStartSettings.this, "Database creating completed!", Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (sharedPreferences == null)
            return;
        switch (key) {
            default:
                //onSortChancged(sharedPreferences, key);
                break;
        }
    }

    private void loadSettingsFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private static final int READ_REQUEST_CODE = 42;
    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("PharmData/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

            }
        }
    }
}
