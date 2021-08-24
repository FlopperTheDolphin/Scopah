package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    private static final String SETTINGS_PREFERENCES_KEY = "com.example.scopah.SETTINGS_PREFERENCES_KEY";
    private static final String NAPOLA_KEY = "NAPOLA";
    private static final String MAIN_SEED_KEY = "MAIN_SEED";

    private RadioButton radio_coins;
    private RadioButton radio_spades;
    private RadioButton radio_cups;
    private RadioButton radio_batons;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(SETTINGS_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();

        final SwitchCompat switch_napola = (SwitchCompat) findViewById(R.id.switch_napola);
        switch_napola.setChecked(preferences.getBoolean(NAPOLA_KEY, true));

        switch_napola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean napola = preferences.getBoolean(NAPOLA_KEY, true);
                napola = !napola;

                editor.putBoolean(NAPOLA_KEY, napola);
                editor.commit();
            }
        });

        radio_coins = (RadioButton) findViewById(R.id.radio_coins);
        radio_spades = (RadioButton) findViewById(R.id.radio_spades);
        radio_cups = (RadioButton) findViewById(R.id.radio_cups);
        radio_batons = (RadioButton) findViewById(R.id.radio_batons);

        radioRefresh();

        radio_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(MAIN_SEED_KEY, "COINS");
                editor.commit();
                radioRefresh();
            }
        });

        radio_spades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(MAIN_SEED_KEY, "SPADES");
                editor.commit();
                radioRefresh();
            }
        });

        radio_cups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(MAIN_SEED_KEY, "CUPS");
                editor.commit();
                radioRefresh();
            }
        });

        radio_batons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(MAIN_SEED_KEY, "BATONS");
                editor.commit();
                radioRefresh();
            }
        });

    }

    private void radioRefresh() {
        String mainSeed = preferences.getString(MAIN_SEED_KEY, "COINS");

        switch(mainSeed) {
            case "COINS":
                radio_coins.setChecked(true);
                break;
            case "SPADES":
                radio_spades.setChecked(true);
                break;
            case "CUPS":
                radio_cups.setChecked(true);
                break;
            case "BATONS":
                radio_batons.setChecked(true);
                break;
        }
    }
}