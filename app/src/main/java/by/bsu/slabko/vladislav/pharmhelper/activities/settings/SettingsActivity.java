package by.bsu.slabko.vladislav.pharmhelper.activities.settings;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import by.bsu.slabko.vladislav.pharmhelper.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

     /* @Override
    public void onCreatePreference(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.settings, s);

    }*/
}
