package com.swarajbara.mycoronachecker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_HIGH = "keyHigh";

    private TextView textViewHighscore;

    private int highs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonStartQuiz = findViewById(R.id.button_start);
        Button buttonContact = findViewById(R.id.button_contact);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        loadHigh();

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });


        buttonContact.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                contactInfo();
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    private void contactInfo()
    {
        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int sco = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (sco > highs) {
                    updateHighscore(sco);
                }
            }
        }
    }

    private void loadHigh(){
        SharedPreferences pre = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        highs = pre.getInt(KEY_HIGH, 0);
        textViewHighscore.setText("Highscore: " + highs);
    }

    private void updateHighscore(int highScoNew) {
        highs = highScoNew;
        textViewHighscore.setText("Highscore: " + highs);

        SharedPreferences pre = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(KEY_HIGH, highs);
        editor.apply();
    }

}