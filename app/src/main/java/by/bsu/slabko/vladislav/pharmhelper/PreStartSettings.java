package by.bsu.slabko.vladislav.pharmhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;

import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class PreStartSettings extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener  {

    private static boolean loadedFromSharedPreferences = false;
    private boolean showWelcomePage = true;


    @Override
    protected void onResume() {
        super.onResume();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        new Constants(size.x, size.y);
        loadSettingsFromSharedPreferences();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
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
}
