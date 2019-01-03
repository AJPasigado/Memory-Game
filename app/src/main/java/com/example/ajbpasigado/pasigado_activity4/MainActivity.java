package com.example.ajbpasigado.pasigado_activity4;

import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View v){
        Intent intent = new Intent(this, GameStart.class);
        startActivity(intent);
    }

    public void options(View v){
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    public void quit(View v){
        QuitDialog alert = new QuitDialog();
        alert.showDialog(this);
    }

    @Override
    public void onBackPressed() {
        QuitDialog alert = new QuitDialog();
        alert.showDialog(this);
    }

    @Override
    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onResume();
    }
}
