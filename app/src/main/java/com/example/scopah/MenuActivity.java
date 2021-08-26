package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        final Button settings = (Button) findViewById(R.id.button_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings();
            }
        });

        final Button play = (Button) findViewById(R.id.button_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        final Button resume = (Button) findViewById(R.id.button_resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resume();
            }
        });

        final Button stats = (Button) findViewById(R.id.button_stats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stats();
            }
        });
    }

    private void settings() {
        final Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void play() {
        final Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    private void resume() {
        final Intent intent = new Intent(this, ViewMatchesActivity.class);
        intent.putExtra("completed", false);
        startActivity(intent);
    }

    private void stats() {
        final Intent intent = new Intent(this, ViewMatchesActivity.class);
        intent.putExtra("completed", true);
        startActivity(intent);
    }
}