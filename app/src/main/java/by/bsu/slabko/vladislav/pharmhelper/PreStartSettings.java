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
import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncDBConnection;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;
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
        AsyncDBConnection createDatabase = new AsyncDBConnection();
        createDatabase.execute();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        new Constants(size.x, size.y);
        if(Constants.lines.size() == 0) {
            Constants.lines.add(new SearchLine(this, getLayoutInflater()));
           // Constants.lines.add(new SearchLine(this, getLayoutInflater()));
            //Constants.lines.add(new SearchLine(this, getLayoutInflater()));
        }
        new SearchInfo();
        loadSettingsFromSharedPreferences();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


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
