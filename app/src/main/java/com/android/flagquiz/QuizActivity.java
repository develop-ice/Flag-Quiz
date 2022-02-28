package com.android.flagquiz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {
    // UI
    private TextView tv1, tv2, tv3, tvQuestion;
    private ImageView imgFlag;
    private Button btn1, btn2, btn3, btn4;
    private FloatingActionButton fabNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // UI
        initViews();

    }

    private void initViews() {
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        tvQuestion = findViewById(R.id.tv_question);
        imgFlag = findViewById(R.id.img_flag);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        fabNext = findViewById(R.id.fab_next);
    }

}
