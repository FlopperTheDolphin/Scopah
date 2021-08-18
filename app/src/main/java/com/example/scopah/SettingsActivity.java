package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFERENCES_KEY = "com.example.scopah.PREFERENCES_KEY";
    private static final String NAPOLA_KEY = "NAPOLA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

        final SwitchCompat switch_napola = (SwitchCompat) findViewById(R.id.switch_napola);
        switch_napola.setChecked(preferences.getBoolean(NAPOLA_KEY, true));

        switch_napola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean napola = preferences.getBoolean(NAPOLA_KEY, true);
                napola = !napola;

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(NAPOLA_KEY, napola);
                editor.commit();
            }
        });
    }
}