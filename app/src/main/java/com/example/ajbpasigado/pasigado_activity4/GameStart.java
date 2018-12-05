package com.example.ajbpasigado.pasigado_activity4;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameStart extends AppCompatActivity {
    GameManager gameManager = new GameManager();
    String[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        initializeBoard();
        startTimer();
    }

    public void initializeBoard(){
        board = gameManager.initalizeBoard();
        changeBoardAll(board);
    }

    public void startTimer(){
        final TextView tv = findViewById(R.id.tv_timer);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("00:0" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                changeBoardAll(gameManager.defaultBoard());
            }
        }.start();
    }

    public void changeBoardAll(String[][] board){
        Button[][] btns = new Button[][]{
                { findViewById(R.id.btn_00), findViewById(R.id.btn_01), findViewById(R.id.btn_02), findViewById(R.id.btn_03)},
                { findViewById(R.id.btn_10), findViewById(R.id.btn_11), findViewById(R.id.btn_12), findViewById(R.id.btn_13)},
                { findViewById(R.id.btn_20), findViewById(R.id.btn_21), findViewById(R.id.btn_22), findViewById(R.id.btn_23)},
                { findViewById(R.id.btn_30), findViewById(R.id.btn_31), findViewById(R.id.btn_32), findViewById(R.id.btn_33)},
        };

        for (int i = 0; i< 4; i++){
            for (int j = 0; j< 4; j++){
                btns[i][j].setText(board[i][j]);
            }
        }
    }

    int[] lastIndex = null;
    Button lastButton;

    public void clickButton(View v){
        Button btn = findViewById(v.getId());
        String id = getResources().getResourceName(v.getId());
        int r = Integer.parseInt(id.split("_")[2].split("")[1]);
        int c = Integer.parseInt(id.split("_")[2].split("")[2]);
        btn.setText(board[r][c]);

        if (lastIndex != null) {
            if (gameManager.checker(lastIndex, new int[] {r, c})){
                gameManager.correct ++;
                TextView correct = findViewById(R.id.tv_correct);
                correct.setText(Integer.toString(gameManager.getCorrect()));
                btn.setEnabled(false);
                lastButton.setEnabled(false);
            } else {
                resetTimer(btn, lastButton);
                gameManager.incorrect++;
                TextView incorrect = findViewById(R.id.tv_incorrect);
                incorrect.setText(Integer.toString(gameManager.getIncorrect()));
            }
            lastIndex = null;
            lastButton = null;
        } else {
            lastIndex = new int[]{r, c};
            lastButton = btn;
            Toast.makeText(this, "new", Toast.LENGTH_LONG).show();
        }
    }

    public void resetTimer(final Button first, final Button second){
        final TextView tv = findViewById(R.id.tv_timer);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("00:0" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                first.setText("???");
                second.setText("???");
            }
        }.start();
    }
}
