package edu.wcu.ddbarrier1.poker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Class to implement the splash screen
 */
public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent menuIntent;
                if(PlayerNameActivity.playerName == null){
                    menuIntent= new Intent(SplashActivity.this, PlayerNameActivity.class);
                }else
                    menuIntent = new Intent(SplashActivity.this, MainMenu.class);
                startActivity(menuIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
