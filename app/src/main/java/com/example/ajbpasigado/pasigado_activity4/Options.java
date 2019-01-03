package com.example.ajbpasigado.pasigado_activity4;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Options extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefs";
    SharedPreferences.Editor editor;

    RadioButton time_3s;
    RadioButton time_5s;
    RadioButton time_7s;

    RadioButton size_4 ;
    RadioButton size_5 ;
    RadioButton size_6 ;

    RadioButton symbol_alpha;
    RadioButton symbol_num;
    RadioButton symbol_sym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        time_3s = findViewById(R.id.rdbtn_3s);
        time_5s = findViewById(R.id.rdbtn_5s);
        time_7s = findViewById(R.id.rdbtn_7s);

        size_4 = findViewById(R.id.rdbtn_4x4);
        size_5 = findViewById(R.id.rdbtn_5x5);
        size_6 = findViewById(R.id.rdbtn_6x6);


        symbol_alpha = findViewById(R.id.rdbtn_alpha);
        symbol_num = findViewById(R.id.rdbtn_num);
        symbol_sym = findViewById(R.id.rdbtn_sym);

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        RadioGroup rg = findViewById(R.id.radioGroup_time);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.rdbtn_3s:
                        editor.putInt("time", 3000);
                        editor.apply();
                        break;
                    case R.id.rdbtn_5s:
                        editor.putInt("time", 5000);
                        editor.apply();
                        break;
                    case R.id.rdbtn_7s:
                        editor.putInt("time", 7000);
                        editor.apply();
                        break;
                }
            }
        });

        RadioGroup rgs = findViewById(R.id.radioGroup_size);
        rgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.rdbtn_4x4:
                        editor.putInt("size", 4);
                        editor.apply();
                        break;
                    case R.id.rdbtn_5x5:
                        editor.putInt("size", 5);
                        editor.apply();
                        break;
                    case R.id.rdbtn_6x6:
                        editor.putInt("size", 6);
                        editor.apply();
                        break;
                }
            }
        });

        RadioGroup rgsy = findViewById(R.id.radioGroup_symbol);
        rgsy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.rdbtn_alpha:
                        editor.putInt("sym", 0);
                        editor.apply();
                        break;
                    case R.id.rdbtn_num:
                        editor.putInt("sym", 1);
                        editor.apply();
                        break;
                    case R.id.rdbtn_sym:
                        editor.putInt("sym", 2);
                        editor.apply();
                        break;
                }
            }
        });

       loadPref();
    }

    private void loadPref(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int time = prefs.getInt("time", 3000);
        int size = prefs.getInt("size", 4);
        int sym = prefs.getInt("sym", 0);

        if (time == 3000) time_3s.setChecked(true);
        else if (time == 5000) time_5s.setChecked(true);
        else time_7s.setChecked(true);

        if (size == 4) size_4.setChecked(true);
        else if (size == 5) size_5.setChecked(true);
        else size_6.setChecked(true);

        if (sym == 0) symbol_alpha.setChecked(true);
        else if (sym == 1) symbol_num.setChecked(true);
        else symbol_sym.setChecked(true);
    }

    @Override
    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onResume();
    }

    public void mainMenu(View v){
        this.finish();
    }

    public void reset(View v){
        time_3s.setChecked(true);
        size_4.setChecked(true);
        symbol_alpha.setChecked(true);

        editor.putInt("size", 4);
        editor.putInt("sym", 0);
        editor.putInt("time", 3000);
        editor.apply();
    }


}
