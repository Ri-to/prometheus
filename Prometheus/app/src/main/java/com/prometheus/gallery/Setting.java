package com.prometheus.gallery;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Setting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

        public static final String skey="sub";
        private ListPreference listPreference;
        private SharedPreferences prefs ;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
            listPreference=(ListPreference)getPreferenceScreen().findPreference(skey);
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            Log.e("Zawgyi",""+prefs.getBoolean("zawgyi",false));
            Log.e("lang",listPreference.getEntry().toString());
        }
        @Override
        protected void onResume(){
            super.onResume();
            listPreference.setSummary(listPreference.getEntry().toString());
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }
        @Override
        protected void onPause(){
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key){
            if(key.equals(skey)){
                listPreference.setSummary(listPreference.getEntry().toString());
            }
        }

}
