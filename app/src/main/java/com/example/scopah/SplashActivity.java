package com.example.scopah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

public class SplashActivity extends AppCompatActivity{
    private static final long DELAY = 1000L;   // ms

    private class LoadingDelayTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            SystemClock.sleep(DELAY);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            goAhead();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new LoadingDelayTask().execute();
    }

    private void goAhead() {
        final Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}