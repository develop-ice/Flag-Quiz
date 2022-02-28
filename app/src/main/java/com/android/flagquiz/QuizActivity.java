package com.android.flagquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.flagquiz.db.FlagsDAO;
import com.android.flagquiz.db.FlagsDatabase;
import com.android.flagquiz.db.FlagsModel;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    // UI
    private TextView tv1, tv2, tv3, tvQuestion;
    private ImageView imgFlag;
    private Button btn1, btn2, btn3, btn4;
    private FloatingActionButton fabNext;
    private Button[] buttonsArr = {btn1, btn2, btn3, btn4};
    // DB
    private FlagsDatabase database;
    private ArrayList<FlagsModel> questionsList;
    // Points
    private int correct = 0, wrong = 0, empty = 0, question = 0;
    private FlagsModel correctFlag;
    private ArrayList<FlagsModel> wrongOptList;
    private HashSet<FlagsModel> mixOpt = new HashSet<>();
    private ArrayList<FlagsModel> options = new ArrayList<>();
    private boolean btnControl = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // UI
        initViews();
        // DB
        setupDB();
        // Questions
        loadQuestions();
    }

    @SuppressLint("SetTextI18n")
    private void loadQuestions() {
        tvQuestion.setText(getString(R.string.question) + (question + 1));
        correctFlag = questionsList.get(question);
        imgFlag.setImageResource(getResources().getIdentifier(correctFlag.getFlag_image(), "drawable", getPackageName()));
        wrongOptList = new FlagsDAO().getRandomTreeOptions(database, correctFlag.getFlag_id());

        mixOpt.clear();
        mixOpt.add(correctFlag);
        mixOpt.add(wrongOptList.get(0));
        mixOpt.add(wrongOptList.get(1));
        mixOpt.add(wrongOptList.get(2));

        options.clear();
        options.addAll(mixOpt);

        // UI
        btn1.setText(options.get(0).getFlag_name());
        btn2.setText(options.get(1).getFlag_name());
        btn3.setText(options.get(2).getFlag_name());
        btn4.setText(options.get(3).getFlag_name());


    }

    private void setupDB() {
        database = new FlagsDatabase(QuizActivity.this);
        questionsList = new FlagsDAO().getRandomTenQuestions(database);
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

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        fabNext.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                answerControl(btn1);
                break;
            case R.id.btn_2:
                answerControl(btn2);
                break;
            case R.id.btn_3:
                answerControl(btn3);
                break;
            case R.id.btn_4:
                answerControl(btn4);
                break;
            case R.id.fab_next:
                question++;
                if (!btnControl && question < 10) {
                    empty++;
                    tv2.setText(getResources().getString(R.string.empty) + empty);
                    loadQuestions();
                } else if (btnControl && question < 10) {
                    loadQuestions();
                    for (Button btn : buttonsArr) {
                        btn.setClickable(true);
                        btn.setBackgroundColor(getResources().getColor(R.color.myPeach));
                    }
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    intent.putExtra("empty", empty);
                    startActivity(intent);
                    finish();
                }
                btnControl = false;
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void answerControl(Button button) {
        String btnText = button.getText().toString();
        String correctAnswer = correctFlag.getFlag_name();

        if (btnText.equals(correctAnswer)) {
            correct++;
            button.setBackgroundColor(Color.GREEN);
        } else {
            wrong++;
            button.setBackgroundColor(Color.RED);
            for (Button btn : buttonsArr) {
                if (btn.getText().toString().equals(correctAnswer)) {
                    btn.setBackgroundColor(Color.GREEN);
                }
            }
        }

        // Disable
        for (Button btn : buttonsArr) {
            btn.setClickable(false);
        }
        // Text
        tv1.setText(getString(R.string.correct) + correct);
        tv3.setText(getString(R.string.wrong) + wrong);
        // Empty control
        btnControl = true;
    }
}
