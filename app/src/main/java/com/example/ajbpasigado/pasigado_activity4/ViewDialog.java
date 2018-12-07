package com.example.ajbpasigado.pasigado_activity4;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewDialog {
    public void showDialog(final Activity activity, String msg, int time, final GameStart gameStart){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.game_finish);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialog.findViewById(R.id.tv_msg);
        text.setText(msg);

        TextView details = dialog.findViewById(R.id.tv_details);
        details.setText(((time/60) > 0) ?
                String.format("You finished the game in %02d minute(s) and %02d second(s).", time/60, time%60) :
                String.format("You finished the game in %02d second(s).", time%60));

        Button play_again = dialog.findViewById(R.id.btn_play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStart.newGameProcess();
                dialog.dismiss();
            }
        });

        Button main_menu = dialog.findViewById(R.id.btn_main_menu);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
