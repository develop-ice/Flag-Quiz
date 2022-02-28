package com.android.flagquiz.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.flagquiz.R;

public class ResultActivity extends AppCompatActivity {
    // UI
    private TextView tvCorrect, tvWrong, tvEmpty, tvSuccessRate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // UI
        initViews();
        // get data
        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);
        int empty = getIntent().getIntExtra("empty", 0);
        // set data
        tvCorrect.setText(getString(R.string.correct) + correct);
        tvWrong.setText(getString(R.string.wrong) + wrong);
        tvEmpty.setText(getString(R.string.empty) + empty);
        tvSuccessRate.setText(getString(R.string.success) + (correct * 10) + "%");

    }

    private void initViews() {
        tvCorrect = findViewById(R.id.tv_correct);
        tvWrong = findViewById(R.id.tv_wrong);
        tvEmpty = findViewById(R.id.tv_empty);
        tvSuccessRate = findViewById(R.id.tv_success_rate);
    }

    public void playAgain(View view) {
        startActivity(new Intent(ResultActivity.this, QuizActivity.class));
        finish();
    }

    public void quitGame(View view) {
        finish();
    }

}
