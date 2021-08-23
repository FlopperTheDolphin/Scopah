package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {
    private static final String NAMES_KEY = "NAMES";
    private static final String COLORS_KEY = "COLORS";
    private static final String SCORES_KEY = "SCORES";

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

        if (intent.hasExtra("scores")) {
            scores = intent.getIntegerArrayListExtra("scores");
        } else {
            scores = new ArrayList<>();

            for (int i = 0; i < size; i++)
                scores.add(new Integer(0));
        }


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

        Button buttonNew = (Button) findViewById(R.id.button_new);
        buttonNew .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    private void newGame() {

        final Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("names", names);
        intent.putExtra("colors", colors);

        startActivity(intent);
    }
}