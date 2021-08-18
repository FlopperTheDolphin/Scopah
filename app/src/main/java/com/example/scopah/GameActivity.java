package com.example.scopah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        ArrayList<String> names = intent.getStringArrayListExtra("names");
        ArrayList<String> colors = intent.getStringArrayListExtra("colors");

        int size = names.size();

        if (size == 2)
            setContentView(R.layout.activity_game_two);
        else if (size == 3)
            setContentView(R.layout.activity_game_three);
        else if (size == 4)
            setContentView(R.layout.activity_game_four);

        TextView name = (TextView) findViewById(R.id.name_one);
        name.setText(names.get(0));
        Button button_minus = (Button) findViewById(R.id.button_one_minus);
        Button button_plus = (Button) findViewById(R.id.button_one_plus);
        button_minus.setBackgroundColor(Color.parseColor(colors.get(0)));
        button_plus.setBackgroundColor(Color.parseColor(colors.get(0)));

        name = (TextView) findViewById(R.id.name_two);
        name.setText(names.get(1));
        button_minus = (Button) findViewById(R.id.button_two_minus);
        button_plus = (Button) findViewById(R.id.button_two_plus);
        button_minus.setBackgroundColor(Color.parseColor(colors.get(1)));
        button_plus.setBackgroundColor(Color.parseColor(colors.get(1)));

        if (size >= 3) {
            name = (TextView) findViewById(R.id.name_three);
            name.setText(names.get(2));
            button_minus = (Button) findViewById(R.id.button_three_minus);
            button_plus = (Button) findViewById(R.id.button_three_plus);
            button_minus.setBackgroundColor(Color.parseColor(colors.get(2)));
            button_plus.setBackgroundColor(Color.parseColor(colors.get(2)));
        }

        if (size == 4) {
            name = (TextView) findViewById(R.id.name_four);
            name.setText(names.get(3));
            button_minus = (Button) findViewById(R.id.button_four_minus);
            button_plus = (Button) findViewById(R.id.button_four_plus);
            button_minus.setBackgroundColor(Color.parseColor(colors.get(3)));
            button_plus.setBackgroundColor(Color.parseColor(colors.get(3)));
        }

    }
}