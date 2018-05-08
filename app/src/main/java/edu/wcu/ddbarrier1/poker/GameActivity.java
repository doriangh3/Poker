package edu.wcu.ddbarrier1.poker;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import edu.wcu.ddbarrier1.poker.R;

/**
 * @Author Dorian Barrier
 *
 * Class to manage the fragments being placed
 */
public class GameActivity extends FragmentActivity {

    /**Fragment Manager*/
    FragmentManager fragmentManager;

    /***Fragment transaction*/
    FragmentTransaction fragmentTransaction;

    /**Current fragment being used*/
    android.support.v4.app.Fragment currentFragment;

    /**Second fragment being used for larger screens*/
    android.support.v4.app.Fragment currentFragment2;

    /**
     * OnCreate determines what fragment to place based on screen size or the intent
     * that invoked this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int fragType = getIntent().getIntExtra("fragType", -1);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        fragmentManager = getSupportFragmentManager();

        switch(screenSize){
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                addGame(new GameFragment());
                addGame2(new WinsFragment());
                break;
            default:
                if (fragType == 1) {
                    addGame(new GameFragment());
                } else if (fragType == 2) {
                    addGame(new WinsFragment());
                }
        }



        setContentView(R.layout.activity_game);

    }//end onCreate()

    /**
     * adds the passed fragment to the current game holder
     * Sets the current fragment
     * @param fragment - the fragment to add
     */
    public void addGame(android.support.v4.app.Fragment fragment) {
        currentFragment = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.game_holder, fragment);
        fragmentTransaction.commit();

    }

    /**
     * Used for larger screens to add a second fragment to the screen
     *
     * @param fragment - the fragment to add
     */
    public void addGame2(android.support.v4.app.Fragment fragment) {
        currentFragment2 = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.game_holder2, fragment);
        fragmentTransaction.commit();

    }

}
