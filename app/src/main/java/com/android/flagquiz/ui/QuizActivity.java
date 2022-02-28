package com.android.flagquiz.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.flagquiz.R;
import com.android.flagquiz.model.FlagsModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    // UI
    private TextView tv1, tv2, tv3, tvQuestion;
    private ImageView imgFlag;
    private Button btn1, btn2, btn3, btn4;
    // Points
    private ArrayList<FlagsModel> questionsList = new ArrayList<>();
    private int correct = 0, wrong = 0, empty = 0, question = 0;
    private FlagsModel correctFlag;
    private HashSet<FlagsModel> mixOpt = new HashSet<>();
    private ArrayList<FlagsModel> options = new ArrayList<>();
    private boolean btnControl = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // UI
        initViews();
        // Load CSV data
        loadCSV();
        // Questions
        loadQuestions();
    }

    private void loadCSV() {
        try {
            AssetManager manager = getAssets();
            InputStream in = manager.open("data.csv");

            ArrayList<FlagsModel> flags = parse(in);
            for (FlagsModel flag : flags) {
                questionsList.add(new FlagsModel(flag.getFlag_id(), flag.getFlag_name(), flag.getFlag_image()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Simple CSV Parser */
    private static final int COL_ID = 0;
    private static final int COL_NAME = 1;
    private static final int COL_IMAGE = 2;

    private ArrayList<FlagsModel> parse(InputStream in) throws IOException {
        ArrayList<FlagsModel> results = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
            String[] tokens = nextLine.split(",");
            if (tokens.length != 3) {
                Log.w("CSVParser", "Skipping Bad CSV Row");
                continue;
            }
            //Add new parsed result
            FlagsModel current = new FlagsModel();
            current.setFlag_id(Integer.parseInt(tokens[COL_ID]));
            current.setFlag_name(tokens[COL_NAME]);
            current.setFlag_image(tokens[COL_IMAGE]);
            results.add(current);
        }
        in.close();
        return results;
    }

    @SuppressLint("SetTextI18n")
    private void loadQuestions() {
        tvQuestion.setText(getString(R.string.question) + (question + 1));
        correctFlag = questionsList.get(question);
        imgFlag.setImageResource(getResources().getIdentifier(correctFlag.getFlag_image(), "drawable", getPackageName()));
        ArrayList<FlagsModel> wrongOptList = getRandomTreeOptions(correctFlag.getFlag_id());

        mixOpt.clear();
        mixOpt.add(correctFlag);
        assert wrongOptList != null;
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

    private ArrayList<FlagsModel> getRandomTreeOptions(int flag_id) {
        ArrayList<FlagsModel> result = new ArrayList<>();
        ArrayList<FlagsModel> shuffle = questionsList;
        Collections.shuffle(shuffle);
        int questionLimit = 3;
        for (FlagsModel flag : shuffle) {
            if (questionLimit >= 0 && flag.getFlag_id() != flag_id) {
                result.add(flag);
                questionLimit--;
            }
        }
        return result;
    }

    @SuppressLint("SetTextI18n")
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
        FloatingActionButton fabNext = findViewById(R.id.fab_next);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        fabNext.setOnClickListener(this);

        tv1.setText(getString(R.string.correct) + 0);
        tv2.setText(getString(R.string.empty) + 0);
        tv3.setText(getString(R.string.wrong) + 0);
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
                    reset();

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

    private void reset() {
        btn1.setBackgroundColor(getResources().getColor(R.color.myPeach));
        btn2.setBackgroundColor(getResources().getColor(R.color.myPeach));
        btn3.setBackgroundColor(getResources().getColor(R.color.myPeach));
        btn4.setBackgroundColor(getResources().getColor(R.color.myPeach));
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
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

            if (btn1.getText().toString().equals(correctAnswer)) {
                btn1.setBackgroundColor(Color.GREEN);
            }
            if (btn2.getText().toString().equals(correctAnswer)) {
                btn2.setBackgroundColor(Color.GREEN);
            }
            if (btn3.getText().toString().equals(correctAnswer)) {
                btn3.setBackgroundColor(Color.GREEN);
            }
            if (btn4.getText().toString().equals(correctAnswer)) {
                btn4.setBackgroundColor(Color.GREEN);
            }
        }

        // Disable all buttons
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
        // Text
        tv1.setText(getString(R.string.correct) + correct);
        tv3.setText(getString(R.string.wrong) + wrong);
        // Empty control
        btnControl = true;
    }
}
