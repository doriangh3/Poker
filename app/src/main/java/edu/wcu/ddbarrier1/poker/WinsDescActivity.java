package edu.wcu.ddbarrier1.poker;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Author - Dorian Barrier
 * Class to implement the Wins Activity
 * Shows an example of each type of win and a description
 */
public class WinsDescActivity extends Activity {

    /**Name of the hand type*/
    TextView nameHand;

    /**Description of the hand type*/
    TextView description;

    /**The images of the cards*/
    ImageView v1,v2,v3,v4,v5;

    /**An array containing the descriptions of each hand type*/
    String[] DESCRIPTION = {"All cards are the same suit, run in sequence A, K, Q, j, 10",
            "All cards are the same suit and run in sequence",
            "Four cards  of the same rank","A pair and a three of a kind",
            "All cards are the same suit.","All cards run in sequence.",
            "Three cards of the same rank","Two pairs of cards",
            "Two of the same cards with a rank of 10 or more"};

    /**
     * onCreate sets all the correct TextViews and ImageViews
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wins_desc);

        nameHand = (TextView) findViewById(R.id.nameHand2);
        nameHand.setText(PlayerNameActivity.playerName+"'s Hand");

        v1 = (ImageView) findViewById(R.id.card12);
        v2 = (ImageView) findViewById(R.id.card22);
        v3 = (ImageView) findViewById(R.id.card32);
        v4 = (ImageView) findViewById(R.id.card42);
        v5 = (ImageView) findViewById(R.id.card52);

        description = (TextView) findViewById(R.id.winDescriptions);

        int fragType = getIntent().getIntExtra("fragType", -1);

        displayWins(fragType);

    }//end onCreate()

    /**
     * Display the correct type of hand
     * @param display - int representing which type of hand to set
     */
    public void displayWins(int display){

        switch (display){
            case 0:
                v1.setImageResource(R.drawable.diamonds_of_10);
                v2.setImageResource(R.drawable.king_of_diamonds);
                v3.setImageResource(R.drawable.queen_of_diamonds);
                v4.setImageResource(R.drawable.jack_of_diamonds);
                v5.setImageResource(R.drawable.ace_of_diamonds);

                description.setText(DESCRIPTION[0]);
                break;
            case 1:
                v1.setImageResource(R.drawable.clubs_of_10);
                v2.setImageResource(R.drawable.clubs_of_9);
                v3.setImageResource(R.drawable.clubs_of_8);
                v4.setImageResource(R.drawable.clubs_of_7);
                v5.setImageResource(R.drawable.clubs_of_6);

                description.setText(DESCRIPTION[1]);
                break;
            case 2:
                v1.setImageResource(R.drawable.clubs_of_10);
                v2.setImageResource(R.drawable.king_of_diamonds);
                v3.setImageResource(R.drawable.king_of_clubs);
                v4.setImageResource(R.drawable.king_of_hearts);
                v5.setImageResource(R.drawable.king_of_spades);
                description.setText(DESCRIPTION[2]);
                break;
            case 3:
                v1.setImageResource(R.drawable.ace_of_diamonds);
                v2.setImageResource(R.drawable.ace_of_spades);
                v3.setImageResource(R.drawable.ace_of_clubs);
                v4.setImageResource(R.drawable.king_of_diamonds);
                v5.setImageResource(R.drawable.king_of_spades);
                description.setText(DESCRIPTION[3]);
                break;
            case 4:
                v1.setImageResource(R.drawable.ace_of_spades);
                v2.setImageResource(R.drawable.king_of_spades);
                v3.setImageResource(R.drawable.queen_of_spades);
                v4.setImageResource(R.drawable.jack_of_spades);
                v5.setImageResource(R.drawable.spades_of_10);
                description.setText(DESCRIPTION[4]);
                break;
            case 5:
                v1.setImageResource(R.drawable.clubs_of_2);
                v2.setImageResource(R.drawable.hearts_of_3);
                v3.setImageResource(R.drawable.clubs_of_4);
                v4.setImageResource(R.drawable.spades_of_5);
                v5.setImageResource(R.drawable.clubs_of_6);
                description.setText(DESCRIPTION[5]);
                break;
            case 6:
                v1.setImageResource(R.drawable.ace_of_diamonds);
                v2.setImageResource(R.drawable.ace_of_spades);
                v3.setImageResource(R.drawable.ace_of_clubs);
                v4.setImageResource(R.drawable.queen_of_spades);
                v5.setImageResource(R.drawable.jack_of_diamonds);
                description.setText(DESCRIPTION[6]);
                break;
            case 7:
                v1.setImageResource(R.drawable.ace_of_diamonds);
                v2.setImageResource(R.drawable.ace_of_clubs);
                v3.setImageResource(R.drawable.diamonds_of_3);
                v4.setImageResource(R.drawable.diamonds_of_4);
                v5.setImageResource(R.drawable.spades_of_3);
                description.setText(DESCRIPTION[7]);
                break;
            case 8:
                v1.setImageResource(R.drawable.ace_of_diamonds);
                v2.setImageResource(R.drawable.ace_of_spades);
                v3.setImageResource(R.drawable.queen_of_diamonds);
                v4.setImageResource(R.drawable.king_of_clubs);
                v5.setImageResource(R.drawable.clubs_of_7);
                description.setText(DESCRIPTION[8]);
                break;
            default:
        }

    }//end displayWins()

}
