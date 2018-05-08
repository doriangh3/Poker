package edu.wcu.ddbarrier1.poker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @Author - Dorian Barrier
 *
 * Implements the functionality for the name selection screen
 *
 */
public class PlayerNameActivity extends Activity{

    /**Static instance of the player's name*/
    public static String playerName = null;

    /**text view to enter player name*/
    TextView tv1;

    /**Edit text to enter player name*/
    EditText et1;

    /**Button to confirm player name*/
    Button b1;

    /**
     * OnCreate initializes button, edit text, and edit text for activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        tv1 = (TextView) findViewById(R.id.enterName);
        et1 = (EditText) findViewById(R.id.editName);
        b1 = (Button) findViewById(R.id.playerNameBtn);


    }//end onCreate()

    /**
     * Sets the players name when ok is pressed
     * Alert dialog if no space of alpha character is set
     *
     * @param view
     */
    public void okPressed(View view){

        int id = view.getId();

        if(id == R.id.okNameBtn){
            String tempName = et1.getText().toString();
            if(checkLettersAndSpaces(tempName)) {
                playerName = tempName;
                Intent backHome = new Intent(this, MainMenu.class);
                this.startActivity(backHome);
            }
            else{
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Error!");
                alertDialog.setMessage("The Entered Name Should Only Include Letters and Spaces!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                dialogInterface.dismiss();
                            }
                        });

                alertDialog.show();
            }
        }

    }//end okPressed()

    /**
     * Helper to check if the entered name only contains letters and spaces
     * @param s - name being checked
     * @return - true if does contain, false if not
     */
    public static boolean checkLettersAndSpaces(String s){
        for(int i=0; i < s.length(); i++){
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ') {
                continue;
            }
            return false;
        }
        return true;
    }//end checkLettersAndSpaces()


}//end PlayerNameActivity
