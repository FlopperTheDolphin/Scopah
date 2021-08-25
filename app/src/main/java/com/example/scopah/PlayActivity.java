package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    private static final String RED = "#F44336";
    private static final String BLUE = "#2196F3";
    private static final String GREEN = "#4CAF50";
    private static final String YELLOW = "#FFEB3B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        final Button settings = (Button) findViewById(R.id.button_start);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();

        CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox1);
        if (checkbox.isChecked()) {
            EditText text = (EditText) findViewById(R.id.text1);

            names.add(text.getText().toString());
            colors.add(RED);
        }

        checkbox = (CheckBox) findViewById(R.id.checkBox2);
        if (checkbox.isChecked()) {
            EditText text = (EditText) findViewById(R.id.text2);

            names.add(text.getText().toString());
            colors.add(BLUE);
        }

        checkbox = (CheckBox) findViewById(R.id.checkBox3);
        if (checkbox.isChecked()) {
            EditText text = (EditText) findViewById(R.id.text3);

            names.add(text.getText().toString());
            colors.add(GREEN);
        }

        checkbox = (CheckBox) findViewById(R.id.checkBox4);
        if (checkbox.isChecked()) {
            EditText text = (EditText) findViewById(R.id.text4);

            names.add(text.getText().toString());
            colors.add(YELLOW);
        }

        if (names.size() < 2) {
            Toast.makeText(this, R.string.minimum_players, Toast.LENGTH_SHORT).show();
            return;
        }

        final Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("names", names);
        intent.putExtra("colors", colors);

        startActivity(intent);
        finish();
    }
}