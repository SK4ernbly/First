package my.android.notes.app.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import my.android.notes.app.R;

/**
 * Created by KolomoetsS on 09.04.2014.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);


    }

}
