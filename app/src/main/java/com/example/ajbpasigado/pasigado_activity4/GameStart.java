package com.example.ajbpasigado.pasigado_activity4;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameStart extends AppCompatActivity {
    GameManager gameManager = new GameManager();
    String[][] board;
    int[] lastIndex = null;
    Button lastButton;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        initializeBoard();
        startTimer();

    }

    public void initializeBoard(){
        board = gameManager.initalizeBoard();
        changeBoardAll(board, false);
    }

    public void startTimer(){
        final TextView tv = findViewById(R.id.tv_timer);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("00:0" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                changeBoardAll(gameManager.defaultBoard(), true);
            }
        }.start();
    }

    public void changeBoardAll(String[][] board, Boolean activate){
        Button[][] btns = new Button[][]{
                { findViewById(R.id.btn_00), findViewById(R.id.btn_01), findViewById(R.id.btn_02), findViewById(R.id.btn_03)},
                { findViewById(R.id.btn_10), findViewById(R.id.btn_11), findViewById(R.id.btn_12), findViewById(R.id.btn_13)},
                { findViewById(R.id.btn_20), findViewById(R.id.btn_21), findViewById(R.id.btn_22), findViewById(R.id.btn_23)},
                { findViewById(R.id.btn_30), findViewById(R.id.btn_31), findViewById(R.id.btn_32), findViewById(R.id.btn_33)},
        };

        for (int i = 0; i< 4; i++){
            for (int j = 0; j< 4; j++){
                btns[i][j].setText(board[i][j]);
                btns[i][j].setEnabled(activate);
            }
        }
    }

    public void clickButton(View v){
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
            countDownTimer = null;
        }

        Button currentButton = findViewById(v.getId());
        currentButton.setEnabled(false);

        String id = getResources().getResourceName(v.getId());
        int r = Integer.parseInt(id.split("_")[2].split("")[1]);
        int c = Integer.parseInt(id.split("_")[2].split("")[2]);
        currentButton.setText(board[r][c]);

        if (lastButton != null){
            if (gameManager.checker(lastIndex, new int[] {r, c})){
                gameManager.correct ++;
                TextView correct = findViewById(R.id.tv_correct);
                correct.setText(Integer.toString(gameManager.getCorrect()));

                if (gameManager.correct == 8){
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(this, "You Won!", this);
                }
            } else {
                TextView tv = findViewById(R.id.tv_timer);
                resetTimer(currentButton, lastButton, tv);
                gameManager.incorrect++;
                TextView incorrect = findViewById(R.id.tv_incorrect);
                incorrect.setText(Integer.toString(gameManager.getIncorrect()));
            }
            lastIndex = null;
            lastButton = null;
        } else  if (lastButton == null){
            lastIndex = new int[]{r, c};
            lastButton = currentButton;
        }
    }

    public void resetTimer(final Button first, final Button second, final TextView tv){
        final Button one = first;
        final Button two = second;
        countDownTimer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("00:0" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                one.setEnabled(true);
                two.setEnabled(true);
                one.setText("???");
                two.setText("???");
            }
        }.start();
    }

    public void newGame(View v){
        newGameProcess();
    }

    public void newGameProcess(){
        TextView incorrect = findViewById(R.id.tv_incorrect);
        incorrect.setText("0");

        TextView correct = findViewById(R.id.tv_correct);
        correct.setText("0");

        board = null;
        gameManager = new GameManager();
        lastIndex = null;
        lastButton = null;
        if (countDownTimer != null) countDownTimer.cancel();
        countDownTimer = null;

        initializeBoard();
        startTimer();
    }
}
