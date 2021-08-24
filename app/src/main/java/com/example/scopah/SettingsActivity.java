package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    public static final String SETTINGS_PREFERENCES_KEY = "com.example.scopah.SETTINGS_PREFERENCES_KEY";
    public static final String NAPOLA_KEY = "NAPOLA";
    public static final String SEVEN_COINS_KEY = "SEVEN_COINS";
    public static final String TWO_SPADES_KEY = "TWO_SPADES";
    public static final String TEN_COINS_KEY = "TEN COINS";
    public static final String EIGHT_CUPS_KEY = "EIGHT CUPS";
    public static final String MAIN_SEED_KEY = "MAIN_SEED";

    private RadioButton radio_coins;
    private RadioButton radio_spades;
    private RadioButton radio_cups;
    private RadioButton radio_batons;

    private boolean checked;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(SETTINGS_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();


        // main seed
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


        // napola
        SwitchCompat switch_compat = (SwitchCompat) findViewById(R.id.switch_napola);
        checked = preferences.getBoolean(NAPOLA_KEY, true);
        switch_compat.setChecked(checked);
        switch_compat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(NAPOLA_KEY, !checked);
                editor.commit();
            }
        });

        // seven of coins
        switch_compat = (SwitchCompat) findViewById(R.id.switch_seven_coins);
        checked = preferences.getBoolean(SEVEN_COINS_KEY, true);
        switch_compat.setChecked(checked);
        switch_compat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(SEVEN_COINS_KEY, !checked);
                editor.commit();
            }
        });

        // two of spades
        switch_compat = (SwitchCompat) findViewById(R.id.switch_two_spades);
        checked = preferences.getBoolean(TWO_SPADES_KEY, true);
        switch_compat.setChecked(checked);
        switch_compat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(TWO_SPADES_KEY, !checked);
                editor.commit();
            }
        });

        // ten of coins
        switch_compat = (SwitchCompat) findViewById(R.id.switch_ten_coins);
        checked = preferences.getBoolean(TEN_COINS_KEY, true);
        switch_compat.setChecked(checked);
        switch_compat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(TEN_COINS_KEY, !checked);
                editor.commit();
            }
        });

        // eight of cups
        switch_compat = (SwitchCompat) findViewById(R.id.switch_eight_cups);
        checked = preferences.getBoolean(EIGHT_CUPS_KEY, true);
        switch_compat.setChecked(checked);
        switch_compat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(EIGHT_CUPS_KEY, !checked);
                editor.commit();
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