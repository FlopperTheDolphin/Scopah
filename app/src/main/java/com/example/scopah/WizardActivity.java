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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class WizardActivity extends AppCompatActivity {
    private ArrayList<String> names;
    private ArrayList<String> colors;
    private ArrayList<Integer> scores;

    private int questionIndex;
    private boolean napola;
    private int bonus;
    private ArrayList<String> questions;

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

        preferences = getSharedPreferences(SettingsActivity.SETTINGS_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();

        final String who_has = getString(R.string.who_has) + " ";

        intent.putExtra("index", 0); // question index
        questions = new ArrayList<>();

        questions.add(who_has + getString(R.string.more_coins));
        questions.add(who_has + getString(R.string.prime));
        questions.add(who_has + getString(R.string.more_cards));

        if (preferences.getBoolean(SettingsActivity.NAPOLA_KEY, true))
            questions.add(who_has + getString(R.string.napola_question));

        if (preferences.getBoolean(SettingsActivity.SEVEN_COINS_KEY, true))
            questions.add(who_has + getString(R.string.seven_coins));

        if (preferences.getBoolean(SettingsActivity.TWO_SPADES_KEY, false))
            questions.add(who_has + getString(R.string.two_spades));

        if (preferences.getBoolean(SettingsActivity.TEN_COINS_KEY, false))
            questions.add(who_has + getString(R.string.ten_coins));

        if (preferences.getBoolean(SettingsActivity.EIGHT_CUPS_KEY, false))
            questions.add(who_has + getString(R.string.eight_cups));

        if (questionIndex == 0) {
            String mainSeed = preferences.getString(SettingsActivity.MAIN_SEED_KEY, "COINS");

            switch (mainSeed) {
                case "SPADES":
                    questions.set(0, who_has + getString(R.string.more_spades));
                    break;
                case "CUPS":
                    questions.set(0, who_has + getString(R.string.more_cups));
                    break;
                case "BATONS":
                    questions.set(0, who_has + getString(R.string.more_batons));
                    break;
            }
        }

        TextView currentQuestion = (TextView) findViewById(R.id.question);
        currentQuestion.setText(questions.get(questionIndex));

        napola = preferences.getBoolean(SettingsActivity.NAPOLA_KEY, true)
                    && questionIndex == 3;

        if (!napola) {
            LinearLayoutCompat row = (LinearLayoutCompat) findViewById(R.id.row_napola);
            row.setVisibility(View.INVISIBLE);
        }

        // player one
        Button button = (Button) findViewById(R.id.button_one);
        button.setBackgroundColor(Color.parseColor(colors.get(0)));
        button.setText(names.get(0));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (napola) {
                    if (!checkBonus())
                        return;
                } else {
                    bonus = 1;
                }
                scores.set(0, scores.get(0) + bonus);
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
                if (napola) {
                    if (!checkBonus())
                        return;
                } else {
                    bonus = 1;
                }
                scores.set(1, scores.get(1) + bonus);
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
                    if (napola) {
                        if (!checkBonus())
                            return;
                    } else {
                        bonus = 1;
                    }
                    scores.set(2, scores.get(2) + bonus);
                    next();
                }
            });
        } else {
            Button p3 = (Button) findViewById(R.id.button_three);
            p3.setVisibility(View.INVISIBLE);
        }

        // player four
        if (size == 4) {
            button = (Button) findViewById(R.id.button_four);
            button.setBackgroundColor(Color.parseColor(colors.get(3)));
            button.setText(names.get(3));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (napola) {
                        if (!checkBonus())
                            return;
                    } else {
                        bonus = 1;
                    }
                    scores.set(3, scores.get(3) + bonus);
                    next();
                }
            });
        } else {
            Button p4 = (Button) findViewById(R.id.button_four);
            p4.setVisibility(View.INVISIBLE);
        }

        // skip
        button = (Button) findViewById(R.id.button_skip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

    private boolean checkBonus() {
        EditText napola_text = (EditText) findViewById(R.id.editTextNumber);
        bonus = Integer.parseInt(napola_text.getText().toString());

        if (bonus >= 3 && bonus <= 10)
            return true;
        else
            Toast.makeText(this, R.string.napola_bounds, Toast.LENGTH_SHORT).show();

        return false;
    }

    private void next() {
        final Intent intent;
        questionIndex += 1;

        if (questionIndex < questions.size()) {
            intent = new Intent(this, WizardActivity.class);
            intent.putExtra("index", questionIndex);
        } else {
            intent = new Intent(this, LeaderboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        intent.putExtra("names", names);
        intent.putExtra("colors", colors);
        intent.putExtra("scores", scores);
        intent.putExtra("sum", 0);

        startActivity(intent);
    }
}