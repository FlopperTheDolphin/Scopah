package com.example.scopah;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scopah.utils.AppContext;
import com.example.scopah.utils.LocalData;
import com.example.scopah.utils.LocalDataDB;
import com.example.scopah.utils.MatchData;
import com.example.scopah.utils.ScopahContentResolver;

import java.util.ArrayList;
import java.util.Calendar;

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

    private boolean completed;
    private long id;

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

        completed = intent.getBooleanExtra("completed", true);
        id = intent.getLongExtra("id", 0);

        if (intent.hasExtra("scores")) {
            scores = intent.getIntegerArrayListExtra("scores");

            if (intent.hasExtra("sum"))
                updateScores();
        } else {
            scores = new ArrayList<>();

            for (int i = 0; i < size; i++)
                scores.add(new Integer(0));
        }

        if (intent.hasExtra("completed")) {
            if (!completed) {
                LinearLayoutCompat row = (LinearLayoutCompat) findViewById(R.id.row_buttons);
                row.setVisibility(View.INVISIBLE);
            } else  {
                Button b = (Button) findViewById(R.id.button_new);
                b.setVisibility(View.INVISIBLE);
                b = (Button) findViewById(R.id.button_save);
                b.setVisibility(View.INVISIBLE);
                b = (Button) findViewById(R.id.button_end);
                b.setVisibility(View.INVISIBLE);
            }
        } else {
            Button resume = (Button) findViewById(R.id.button_resume);
            resume.setVisibility(View.INVISIBLE);
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

        Button button = (Button) findViewById(R.id.button_new);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        button = (Button) findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end(false);
            }
        });

        button = (Button) findViewById(R.id.button_end);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end(true);
            }
        });

        button = (Button) findViewById(R.id.button_resume);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData dao = new LocalDataDB();
                dao.open();

                dao.deleteData(id);

                dao.close();

                Intent intent = new Intent(AppContext.getAppContext(), LeaderboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.putExtra("names", names);
                intent.putExtra("colors", colors);
                intent.putExtra("scores", scores);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();

        if (!intent.hasExtra("completed")) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.without_saving)

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LeaderboardActivity.super.onBackPressed();
                        }
                    })

                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })

                    .show();
        } else
            super.onBackPressed();
    }

    private void end(boolean completed) {
        LocalData dao = new ScopahContentResolver();

        dao.open();
        MatchData match = new MatchData(names, colors, scores, completed,
                Calendar.getInstance().getTimeInMillis());
        dao.insertData(match);
        dao.close();

        if (completed)
            Toast.makeText(this, R.string.match_concluded, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.match_saved, Toast.LENGTH_SHORT).show();

        finish();
    }

    private void updateScores() {
        scores.set(0, scores.get(0) + preferences.getInt(SCORE_ONE_KEY, 0));
        editor.putInt(SCORE_ONE_KEY, 0);
        scores.set(1, scores.get(1) + preferences.getInt(SCORE_TWO_KEY, 0));
        editor.putInt(SCORE_TWO_KEY, 0);

        int size = scores.size();

        if (size >= 3) {
            scores.set(2, scores.get(2) + preferences.getInt(SCORE_THREE_KEY, 0));
            editor.putInt(SCORE_THREE_KEY, 0);
        }

        if (size == 4) {
            scores.set(3, scores.get(3) + preferences.getInt(SCORE_FOUR_KEY, 0));
            editor.putInt(SCORE_FOUR_KEY, 0);
        }
        editor.commit();
    }

    private void newGame() {
        // write scores in SavedPreferences
        editor.putInt(SCORE_ONE_KEY, scores.get(0).intValue());
        editor.putInt(SCORE_TWO_KEY, scores.get(1).intValue());

        if (names.size() >= 3)
            editor.putInt(SCORE_THREE_KEY, scores.get(2).intValue());

        if (names.size() == 4)
            editor.putInt(SCORE_FOUR_KEY, scores.get(3).intValue());

        editor.commit();

        final Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("names", names);
        intent.putExtra("colors", colors);

        startActivity(intent);
    }
}