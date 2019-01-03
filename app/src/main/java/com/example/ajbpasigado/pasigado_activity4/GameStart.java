package com.example.ajbpasigado.pasigado_activity4;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class GameStart extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefs";

    GameManager gameManager = new GameManager();
    String[][] board;
    int[] lastIndex = null;
    Button lastButton;
    CountDownTimer countDownTimer;
    CountDownTimer startTimer;
    Timer stopwatch = new Timer();

    int seconds = 0;
    boolean countDownRunning = false;

    int GAME_TIME = 3000;
    int BOARD_SIZE = 5;
    int GAME_MODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        loadPref();

        initializeBoard();
        startTimer();
    }

    @Override
    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onResume();
    }

    private void loadPref(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        GAME_TIME = prefs.getInt("time", 3000);
        BOARD_SIZE = prefs.getInt("size", 4);
        GAME_MODE = prefs.getInt("sym", 0);
    }

    public void startStopwatch() {
        final TextView tv = findViewById(R.id.tv_timer);
        stopwatch = new Timer();
        stopwatch.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(formatSeconds(seconds));
                        seconds++;
                    }
                });
            }
        }, 0, 1000);
    }

    private String formatSeconds(int seconds){
        String sec = String.format("%02d", seconds % 60);
        String min = String.format("%02d", seconds / 60);
        return min + ":" + sec;
    }

    public void initializeBoard(){
        board = gameManager.initalizeBoard(GAME_MODE, BOARD_SIZE);
        changeBoardAll(board, false);
    }

    public void startTimer(){
        final TextView tv = findViewById(R.id.tv_timer);
        startTimer = new CountDownTimer(GAME_TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("00:0" + (millisUntilFinished + 1000) / 1000);
            }

            public void onFinish() {
                changeBoardAll(gameManager.defaultBoard(), true);
                startStopwatch();
            }
        }.start();
    }

    public void changeBoardAll(String[][] board, Boolean activate){
        Button[][] btns = new Button[][]{
                { findViewById(R.id.btn_00), findViewById(R.id.btn_01), findViewById(R.id.btn_02), findViewById(R.id.btn_03), findViewById(R.id.btn_04) , findViewById(R.id.btn_05)},
                { findViewById(R.id.btn_10), findViewById(R.id.btn_11), findViewById(R.id.btn_12), findViewById(R.id.btn_13), findViewById(R.id.btn_14) , findViewById(R.id.btn_15)},
                { findViewById(R.id.btn_20), findViewById(R.id.btn_21), findViewById(R.id.btn_22), findViewById(R.id.btn_23), findViewById(R.id.btn_24) , findViewById(R.id.btn_25)},
                { findViewById(R.id.btn_30), findViewById(R.id.btn_31), findViewById(R.id.btn_32), findViewById(R.id.btn_33), findViewById(R.id.btn_34) , findViewById(R.id.btn_35)},
                { findViewById(R.id.btn_40), findViewById(R.id.btn_41), findViewById(R.id.btn_42), findViewById(R.id.btn_43), findViewById(R.id.btn_44) , findViewById(R.id.btn_45)},
                { findViewById(R.id.btn_50), findViewById(R.id.btn_51), findViewById(R.id.btn_52), findViewById(R.id.btn_53), findViewById(R.id.btn_54) , findViewById(R.id.btn_55)},
        };

        for (int i = 0; i< BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++){
                if (!(BOARD_SIZE % 2 != 0 && (i == (BOARD_SIZE/2) && j == (BOARD_SIZE/2)))) {
                    btns[i][j].setText(board[i][j]);
                    btns[i][j].setEnabled(activate);
                } else {
                    btns[i][j].setText("۞");
                }
            }
        }

        if (BOARD_SIZE < btns.length){
            for (int i = BOARD_SIZE; i<btns.length; i++){
                for (int j = 0; j<btns.length; j++){
                    if (i == 4) findViewById(R.id.linear_4).setVisibility(View.GONE);
                    if (i == 5) findViewById(R.id.linear_5).setVisibility(View.GONE);
                    btns[i][j].setVisibility(View.GONE);
                }
            }
        }

        btns[0][BOARD_SIZE-1].setBackground(getDrawable(R.drawable.ripple_tr));
        btns[BOARD_SIZE-1][0].setBackground(getDrawable(R.drawable.ripple_bl));
        btns[BOARD_SIZE-1][BOARD_SIZE-1].setBackground(getDrawable(R.drawable.ripple_br));
    }

    public void clickButton(View v){
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
        countDownTimer = null;

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

                if (gameManager.correct == ((BOARD_SIZE * BOARD_SIZE) / 2)){
                    stopwatch.cancel();
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(this, "Congratulations!", seconds, this);
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
        countDownTimer = new CountDownTimer(GAME_TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownRunning = true;
            }

            public void onFinish() {
                countDownRunning = false;
                one.setEnabled(true);
                one.setText("???");
                two.setEnabled(true);
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
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
        startTimer.cancel();
        stopwatch.cancel();
        countDownTimer = null;
        seconds = 0;

        initializeBoard();
        startTimer();
    }
}
