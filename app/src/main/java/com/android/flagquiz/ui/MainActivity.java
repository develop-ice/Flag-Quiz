package com.android.flagquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.flagquiz.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Splash Screen
        setTheme(R.style.AppTheme);
        // onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play(View view) {
        startActivity(new Intent(MainActivity.this, QuizActivity.class));
        finish();
    }
}
