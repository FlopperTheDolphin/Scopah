package com.example.scopah;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
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

        refreshScores();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("names");
        colors = intent.getStringArrayListExtra("colors");
        scores = new ArrayList<>();

        int size = names.size();

        if (size == 2)
            setContentView(R.layout.activity_game_two);
        else if (size == 3)
            setContentView(R.layout.activity_game_three);
        else if (size == 4)
            setContentView(R.layout.activity_game_four);

        // player one
        TextView name = (TextView) findViewById(R.id.name_one);
        name.setText(names.get(0));
        Button button_minus = (Button) findViewById(R.id.button_one_minus);
        Button button_plus = (Button) findViewById(R.id.button_one_plus);
        button_minus.setBackgroundColor(Color.parseColor(colors.get(0)));
        button_plus.setBackgroundColor(Color.parseColor(colors.get(0)));
        scores.add(new Integer(0));

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = scores.get(0);
                i -= 1;
                if (i >= 0) {
                    scores.set(0, i);
                    refreshScores();
                }
            }
        });

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = scores.get(0);
                i += 1;
                scores.set(0, i);
                refreshScores();
            }
        });


        // player two
        name = (TextView) findViewById(R.id.name_two);
        name.setText(names.get(1));
        button_minus = (Button) findViewById(R.id.button_two_minus);
        button_plus = (Button) findViewById(R.id.button_two_plus);
        button_minus.setBackgroundColor(Color.parseColor(colors.get(1)));
        button_plus.setBackgroundColor(Color.parseColor(colors.get(1)));
        scores.add(new Integer(0));

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = scores.get(1);
                i -= 1;
                if (i >= 0) {
                    scores.set(1, i);
                    refreshScores();
                }
            }
        });

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = scores.get(1);
                i += 1;
                scores.set(1, i);
                refreshScores();
            }
        });


        // player three
        if (size >= 3) {
            name = (TextView) findViewById(R.id.name_three);
            name.setText(names.get(2));
            button_minus = (Button) findViewById(R.id.button_three_minus);
            button_plus = (Button) findViewById(R.id.button_three_plus);
            button_minus.setBackgroundColor(Color.parseColor(colors.get(2)));
            button_plus.setBackgroundColor(Color.parseColor(colors.get(2)));
            scores.add(new Integer(0));

            button_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer i = scores.get(2);
                    i -= 1;
                    if (i >= 0) {
                        scores.set(2, i);
                        refreshScores();
                    }
                }
            });

            button_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer i = scores.get(2);
                    i += 1;
                    scores.set(2, i);
                    refreshScores();
                }
            });
        }

        // player four
        if (size == 4) {
            name = (TextView) findViewById(R.id.name_four);
            name.setText(names.get(3));
            button_minus = (Button) findViewById(R.id.button_four_minus);
            button_plus = (Button) findViewById(R.id.button_four_plus);
            button_minus.setBackgroundColor(Color.parseColor(colors.get(3)));
            button_plus.setBackgroundColor(Color.parseColor(colors.get(3)));
            scores.add(new Integer(0));

            button_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer i = scores.get(3);
                    i -= 1;
                    if (i >= 0) {
                        scores.set(3, i);
                        refreshScores();
                    }
                }
            });

            button_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer i = scores.get(3);
                    i += 1;
                    scores.set(3, i);
                    refreshScores();
                }
            });
        }

        final Button end = (Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end();
            }
        });

    }

    private void refreshScores() {
        TextView score = (TextView) findViewById(R.id.score_one);
        score.setText(scores.get(0).toString());

        score = (TextView) findViewById(R.id.score_two);
        score.setText(scores.get(1).toString());

        int size = scores.size();

        if (size >= 3) {
            score = (TextView) findViewById(R.id.score_three);
            score.setText(scores.get(2).toString());
        }

        if (size >= 4) {
            score = (TextView) findViewById(R.id.score_four);
            score.setText(scores.get(3).toString());
        }
    }

    private void end() {
        final Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("names", names);
        intent.putExtra("colors", colors);
        intent.putExtra("scores", scores);

        startActivity(intent);
    }
}