package com.android.flagquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    // UI
    private TextView tvCorrect, tvWrong, tvEmpty, tvSuccessRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // UI
        initViews();

    }

    private void initViews() {
        tvCorrect = findViewById(R.id.tv_correct);
        tvWrong = findViewById(R.id.tv_wrong);
        tvEmpty = findViewById(R.id.tv_empty);
        tvSuccessRate = findViewById(R.id.tv_success_rate);
    }

    public void playAgain(View view) {
        startActivity(new Intent(ResultActivity.this, QuizActivity.class));
    }

    public void quitGame(View view) {
        finish();
    }

}
