package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Scanner;

public class WizardActivity extends AppCompatActivity {
    private ArrayList<String> names;
    private ArrayList<String> colors;
    private ArrayList<Integer> scores;

    private int questionIndex;

    private String[] questions;

    private static final String SETTINGS_PREFERENCES_KEY = "com.example.scopah.SETTINGS_PREFERENCES_KEY";
    private static final String NAPOLA_KEY = "NAPOLA";
    private static final String MAIN_SEED_KEY = "MAIN_SEED";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("names");
        colors = intent.getStringArrayListExtra("colors");
        scores = intent.getIntegerArrayListExtra("scores");

        questionIndex = intent.getIntExtra("index", 0);
        int size = names.size();

        preferences = getSharedPreferences(SETTINGS_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();

        final String who_has = getString(R.string.who_has) + " ";

        intent.putExtra("index", 0); // question index
        questions = new String[] {
                who_has + "more coins?",
                who_has + "the 7 of coins?",
                who_has + "the highest prime (the majority of sevens)?",
                who_has + "more cards?",
                who_has + "napola (1, 2, 3 of coins)?",
                who_has + "the two of spades?",
                who_has + "the 10 of coins?",
                who_has + "the 8 of cups?"
        };

        if (questionIndex == 0) {
            String mainSeed = preferences.getString(MAIN_SEED_KEY, "COINS");

            switch (mainSeed) {
                case "SPADES":
                    questions[0] = who_has + "more spades?";
                    break;
                case "CUPS":
                    questions[0] = who_has + "more cups?";
                    break;
                case "BATONS":
                    questions[0] = who_has + "more batons?";
                    break;
            }
        }

        TextView currentQuestion = (TextView) findViewById(R.id.question);
        currentQuestion.setText(questions[questionIndex]);

        // player one
        Button button = (Button) findViewById(R.id.button_one);
        button.setBackgroundColor(Color.parseColor(colors.get(0)));
        button.setText(names.get(0));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scores.set(0, scores.get(0) + 1);
                next();
            }
        });

        // player two
        button = (Button) findViewById(R.id.button_two);
        button.setBackgroundColor(Color.parseColor(colors.get(1)));
        button.setText(names.get(1));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scores.set(1, scores.get(1) + 1);
                next();
            }
        });

        // player three
        if (size >= 3) {
            button = (Button) findViewById(R.id.button_three);
            button.setBackgroundColor(Color.parseColor(colors.get(2)));
            button.setText(names.get(2));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scores.set(2, scores.get(2) + 1);
                    next();
                }
            });
        } else {
            LinearLayoutCompat row = (LinearLayoutCompat) findViewById(R.id.row3);
            row.setVisibility(View.INVISIBLE);
        }

        // player four
        if (size == 4) {
            button = (Button) findViewById(R.id.button_four);
            button.setBackgroundColor(Color.parseColor(colors.get(3)));
            button.setText(names.get(3));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scores.set(3, scores.get(3) + 1);
                    next();
                }
            });
        } else {
            LinearLayoutCompat row = (LinearLayoutCompat) findViewById(R.id.row4);
            row.setVisibility(View.INVISIBLE);
        }
    }

    private void next() {
        final Intent intent;
        questionIndex += 1;

        if (questionIndex < questions.length) {
            intent = new Intent(this, WizardActivity.class);
            intent.putExtra("index", questionIndex);
        } else {
            intent = new Intent(this, LeaderboardActivity.class);
        }

        intent.putExtra("names", names);
        intent.putExtra("colors", colors);
        intent.putExtra("scores", scores);

        startActivity(intent);
    }
}