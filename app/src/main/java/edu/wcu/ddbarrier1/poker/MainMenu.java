package edu.wcu.ddbarrier1.poker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @Author - Dorian Barrier
 * Implements the main menu for the poker app
 *
 */
public class MainMenu extends Activity implements View.OnClickListener{

    /**The three main menu buttons*/
    Button b1,b2,b3;

    /**
     * OnCreate for MainMenu
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        b1 = (Button) findViewById(R.id.playerNameBtn);
        b2 = (Button) findViewById(R.id.playBtn);
        b3 = (Button) findViewById(R.id.viewHandBtn);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
    }

    /**
     * Handles button implementation
     * Takes the user to the respective screen
     * @param v - the button clicked
     */
    @Override
    public void onClick(View v) {

        Intent nextActivity = null;
        switch(v.getId()) {
            case R.id.playerNameBtn:
                nextActivity = new Intent(this, PlayerNameActivity.class);
                break;
            case R.id.playBtn:
                nextActivity = new Intent(this, GameActivity.class);
                nextActivity.putExtra("fragType", 1);
                break;
            case R.id.viewHandBtn:
                nextActivity = new Intent(this, GameActivity.class);
                nextActivity.putExtra("fragType", 2);
                break;

        }

        this.startActivity(nextActivity);

    }//end onClick()

}//end MainMenu
