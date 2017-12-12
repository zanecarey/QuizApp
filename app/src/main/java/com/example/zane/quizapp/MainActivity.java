package com.example.zane.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;
    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            //{"Country", "right answer", "choice 1", "c2", "c3", "categoryTag"}
            {"China", "Beijing", "Jakarata", "Manila", "Hong Kong", "1"},
            {"India", "New Delhi", "Dubai", "Mumbai", "Abu Dhabi", "1"},
            {"Indonesia", "Jakarata", "Bandung", "Manila", "Makassar", "1"},
            {"Japan", "Tokyo", "Hiroshima", "Kyoto", "Osaka", "1"},
            {"Thailand", "Bangkok", "Pattaya", "Hat Yai", "Chiang Mai", "1"},
            {"Brazil", "Brasilia", "Rio de Janeiro", "Salvador", "Sau Paulo", "2"},
            {"Canada", "Ottawa", "Toronto", "Montreal", "Quebec", "2"},
            {"Cuba", "Havana", "Santiago", "Cienfugeos", "Trinidad", "2"},
            {"Mexico", "Mexico City", "Toluca", "Acapulco", "Puebla", "2"},
            {"United States", "Washington D.C.", "New York", "Los Angeles", "Seattle", "2"},
            {"France", "Paris", "Nice", "Lyon", "Bordeaux", "3"},
            {"Germany", "Berlin", "Hamburg", "Frankfurt", "Munich", "3"},
            {"Italy", "Rome", "Florence", "Venice", "Naples", "3"},
            {"Spain", "Madrid", "Seville", "Barcelona", "Granada", "3"},
            {"United Kingdom", "London", "Dublin", "Manchester", "Birmingham", "3"},
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = (TextView)findViewById(R.id.countLabel);
        questionLabel = (TextView)findViewById(R.id.questionLabel);
        answerBtn1 = (Button)findViewById(R.id.answerBtn1);
        answerBtn2 = (Button)findViewById(R.id.answerBtn2);
        answerBtn3 = (Button)findViewById(R.id.answerBtn3);
        answerBtn4 = (Button)findViewById(R.id.answerBtn4);


        //receive quizCategory from startActivty
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);

        Log.v("CATEGORY_TAG", quizCategory + "");


        //quizArray from quizData
        for(int i = 0; i < quizData.length; i++) {

            if(quizCategory == Integer.parseInt(quizData[i][5]) || quizCategory == 0){
                ArrayList<String> tmpArray = new ArrayList<>();
                tmpArray.add(quizData[i][0]); //country
                tmpArray.add(quizData[i][1]); //correct answer
                tmpArray.add(quizData[i][2]); //choice1
                tmpArray.add(quizData[i][3]); //choice 2
                tmpArray.add(quizData[i][4]); //choice 3

                quizArray.add(tmpArray);
            }

        }
        showNextQuiz();
    }

    public void showNextQuiz() {
        //update quizCountLAbel
        countLabel.setText("Q" + quizCount);
        //rand number for next quiz q
        Random rand = new Random();
        int randomNum = rand.nextInt(quizArray.size());

        //pick quiz q
        ArrayList<String> quiz = quizArray.get(randomNum);
        rightAnswer = quiz.get(1);

        //set question label
        questionLabel.setText(quiz.get(0));
        //remove country and shuffle
        quiz.remove(0);
        Collections.shuffle(quiz);

        //set choices
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        //remove quiz from array
        quizArray.remove(randomNum);
    }

    public void checkAnswer(View view) {
        //pushed button
        Button answerBtn = (Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if(btnText.equals(rightAnswer)) {
            //correct
            alertTitle = "Correct";
            rightAnswerCount++;
        }else {
            //wrong
            alertTitle = "Wrong";
        }

        //Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer: " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(quizCount == QUIZ_COUNT) {
                    //show result
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
