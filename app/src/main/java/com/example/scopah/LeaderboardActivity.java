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

import com.example.scopah.utils.LocalData;
import com.example.scopah.utils.LocalDataDB;
import com.example.scopah.utils.MatchData;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {
    private static final String NAMES_KEY = "NAMES";
    private static final String COLORS_KEY = "COLORS";
    private static final String SCORES_KEY = "SCORES";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String LEADERBOARD_PREFERENCES_KEY = "com.example.scopah.SETTINGS_PREFERENCES_KEY";
    private static final String SCORE_ONE_KEY = "SCORE_ONE";
    private static final String SCORE_TWO_KEY = "SCORE_TWO";
    private static final String SCORE_THREE_KEY = "SCORE_THREE";
    private static final String SCORE_FOUR_KEY = "SCORE_FOUR";

    private ArrayList<String> names;
    private ArrayList<String> colors;
    private ArrayList<Integer> scores;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList(NAMES_KEY, names);
        savedInstanceState.putStringArrayList(COLORS_KEY, colors);
        savedInstanceState.putIntegerArrayList(SCORES_KEY, scores);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        names = savedInstanceState.getStringArrayList(NAMES_KEY);
        colors = savedInstanceState.getStringArrayList(COLORS_KEY);
        scores = savedInstanceState.getIntegerArrayList(SCORES_KEY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("names");
        colors = intent.getStringArrayListExtra("colors");

        int size = names.size();

        preferences = getSharedPreferences(LEADERBOARD_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();


        if (intent.hasExtra("scores")) {
            scores = intent.getIntegerArrayListExtra("scores");
            updateScores();
        } else {
            scores = new ArrayList<>();

            for (int i = 0; i < size; i++)
                scores.add(new Integer(0));
        }

        // write scores in SavedPreferences
        editor.putInt(SCORE_ONE_KEY, scores.get(0).intValue());
        editor.putInt(SCORE_TWO_KEY, scores.get(1).intValue());

        if (size >= 3)
            editor.putInt(SCORE_THREE_KEY, scores.get(2).intValue());

        if (size == 4)
            editor.putInt(SCORE_FOUR_KEY, scores.get(3).intValue());

        editor.commit();

        // player one
        TextView name = (TextView) findViewById(R.id.text1);
        name.setBackgroundColor(Color.parseColor(colors.get(0)));
        name.setText(names.get(0));
        TextView score = (TextView) findViewById(R.id.score1);
        score.setText(scores.get(0).toString());

        // player two
        name = (TextView) findViewById(R.id.text2);
        name.setBackgroundColor(Color.parseColor(colors.get(1)));
        name.setText(names.get(1));
        score = (TextView) findViewById(R.id.score2);
        score.setText(scores.get(1).toString());

        // player three
        if (size >= 3) {
            name = (TextView) findViewById(R.id.text3);
            name.setBackgroundColor(Color.parseColor(colors.get(2)));
            name.setText(names.get(2));
            score = (TextView) findViewById(R.id.score3);
            score.setText(scores.get(2).toString());
        } else {
            LinearLayoutCompat row = (LinearLayoutCompat) findViewById(R.id.row3);
            row.setVisibility(View.INVISIBLE);
        }

        // player 4
        if (size == 4) {
            name = (TextView) findViewById(R.id.text4);
            name.setBackgroundColor(Color.parseColor(colors.get(3)));
            name.setText(names.get(3));
            score = (TextView) findViewById(R.id.score4);
            score.setText(scores.get(3).toString());
        } else {
            LinearLayoutCompat row = (LinearLayoutCompat) findViewById(R.id.row4);
            row.setVisibility(View.INVISIBLE);
        }

        Button button = (Button) findViewById(R.id.button_new);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        button = (Button) findViewById(R.id.button_end);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end();
            }
        });
    }

    private void end() {
        LocalData dao = new LocalDataDB();

        dao.open();
        MatchData match = new MatchData(names, colors, scores, true, System.currentTimeMillis());
        dao.insertData(match);
        dao.close();

        finish();
    }

    private void updateScores() {
        scores.set(0, scores.get(0) + preferences.getInt(SCORE_ONE_KEY, 0));
        scores.set(1, scores.get(1) + preferences.getInt(SCORE_TWO_KEY, 0));

        int size = scores.size();

        if (size >= 3)
            scores.set(2, scores.get(2) + preferences.getInt(SCORE_THREE_KEY, 0));

        if (size == 4)
            scores.set(3, scores.get(3) + preferences.getInt(SCORE_FOUR_KEY, 0));
    }

    private void newGame() {

        final Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("names", names);
        intent.putExtra("colors", colors);

        startActivity(intent);
    }
}