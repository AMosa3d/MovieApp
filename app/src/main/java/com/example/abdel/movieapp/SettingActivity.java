package com.example.abdel.movieapp;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_setting);
        addPreferencesFromResource(R.xml.acitvity_settings);

        Preference mPreference = findPreference(getString(R.string.pref_key));


        if (mPreference == null)
            return;
        mPreference.setOnPreferenceChangeListener(this);

        //get the current value or the default
        onPreferenceChange(mPreference, PreferenceManager.getDefaultSharedPreferences(
                mPreference.getContext()).getString(mPreference.getKey(),getString(R.string.pref_default_value))
        );
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();

        if (preference instanceof ListPreference)
        {
            ListPreference pref = (ListPreference)preference;
            preference.setSummary(
                    pref.getEntries()[pref.findIndexOfValue(value)]
            );
        }

        return true;
    }
}
