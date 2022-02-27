package com.android.flagquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Splash Screen
        setTheme(R.style.AppTheme);
        // onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
