package com.example.khanmaruf.diucomlainbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FlashScreen extends AppCompatActivity {

    private static int Splah_Time_Out = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_screen);

        hideNavigationBar();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent homeIntent = new Intent(FlashScreen.this , LoginActivity.class );
               startActivity(homeIntent);
               finish();

           }
       },Splah_Time_Out);
    }

      //Eliminate Navigation Bar

    private void hideNavigationBar(){
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}
