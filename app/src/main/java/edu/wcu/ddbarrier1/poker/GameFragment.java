package edu.wcu.ddbarrier1.poker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GameFragment extends Fragment implements View.OnClickListener{

    /**Player's name + hand*/
    TextView nameHand;

    /**Shows number of hands played*/
    TextView handsPlayedText;

    /**Shows how much bank a player has connected*/
    TextView bankText;

    /**Image views representing the cards*/
    ImageView v1,v2,v3,v4,v5;

    /**Buttons representing the holds/throws, menu, deal, wins buttons*/
    Button b1,b2,b3,b4,b5,b6,b7,b8;

    /**Game object so that we can add functionality to the activity*/
    Game game;

    /**Count of the number of hands played*/
    int handsPlayed;

    /**Keeps track of how much bank a player has*/
    int bank;

    /**Number of throws*/
    int throwCnt = 0;

    /**false if cards have not been dealed yet. True if so*/
    boolean dealed;

    /**
     * Constructor to initialized some default values
     */
    public GameFragment(){
        dealed = false;
        bank = 0;
    }

    /**
     * onCreate sets the default values for the imageViews and text Views
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        nameHand = (TextView) view.findViewById(R.id.nameHand);
        nameHand.setText(PlayerNameActivity.playerName+"'s Hand");

        handsPlayedText = (TextView) view.findViewById(R.id.handsPlayed);
        bankText = (TextView) view.findViewById(R.id.bank);

        v1 = (ImageView) view.findViewById(R.id.card1);
        v2 = (ImageView) view.findViewById(R.id.card2);
        v3 = (ImageView) view.findViewById(R.id.card3);
        v4 = (ImageView) view.findViewById(R.id.card4);
        v5 = (ImageView) view.findViewById(R.id.card5);

        b1 = (Button) view.findViewById(R.id.hold1);
        b2 = (Button) view.findViewById(R.id.hold2);
        b3 = (Button) view.findViewById(R.id.hold3);
        b4 = (Button) view.findViewById(R.id.hold4);
        b5 = (Button) view.findViewById(R.id.hold5);
        b6 = (Button) view.findViewById(R.id.menuBtn);
        b7 = (Button) view.findViewById(R.id.dealBtn);
        b8 = (Button) view.findViewById(R.id.winsBtn);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);

        disableEnableButtons(2);//disable hold buttons

        game = new Game();
        //startGame();

        return view;
    }


    /**
     * Handles button clicks
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        Intent nextActivity = null;
        switch(v.getId()){
            case R.id.dealBtn:
                pressedDeal();
                break;
            case R.id.hold1:
                toggleText(b1);
                break;
            case R.id.hold2:
                toggleText(b2);
                break;
            case R.id.hold3:
                toggleText(b3);
                break;
            case R.id.hold4:
                toggleText(b4);
                break;
            case R.id.hold5:
                toggleText(b5);
                break;
            case R.id.winsBtn:
                nextActivity = new Intent(getActivity(), GameActivity.class);
                nextActivity.putExtra("fragType", 2);
                this.startActivity(nextActivity);
                break;
            case R.id.menuBtn:
                nextActivity = new Intent(getActivity(), MainMenu.class);
                this.startActivity(nextActivity);
                break;
            default:
        }

        //reset throw/hold count here
    }


    /**
     * Helper to disable or enable throw/hold buttons
     * @param enable - if 1 enable buttons. If 2 disable buttons
     */
    public void disableEnableButtons(int enable){
        if(enable == 1) {
            b1.setEnabled(true);
            b2.setEnabled(true);
            b3.setEnabled(true);
            b4.setEnabled(true);
            b5.setEnabled(true);
        }
        else if(enable == 2){
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(false);
            b5.setEnabled(false);
        }
    }//end disableEnableButtons

    /**
     * Reset all buttons to say hold
     * @param btn - which button to reset
     */
    public void resetButtonText(Button btn){
        if(btn.getText().toString().equals("Throw")) {
            btn.setText("Hold");
            throwCnt--;
        }
    }//end resetButtonText

    /**
     * Helper to help with toggling text
     * @param btn - which button to toggle
     */
    public void toggleText(Button btn){

        if(btn.getText().toString().equals("Hold")) {
            btn.setText("Throw");
            throwCnt++;
        }
        else if(btn.getText().toString().equals("Throw")) {
            btn.setText("Hold");
            throwCnt--;
        }

    }//end toggleText

    /**
     * Implements deal button functionality which calls functions in the game class
     * to start a game of poker.
     * Also resets the game once a player chooses to throw or hold cards
     */
    public void pressedDeal(){

        //reset throw/hold count here
        if(!dealed){
            handsPlayed++;
            handsPlayedText.setText("Hands Played "+handsPlayed);
            game.shuffle();
            game.deal();
            game.newSort();
            for(int i = 0; i < game.player.hand.length; i++){
                displayCard(i);
            }
            dealed = true;
            disableEnableButtons(1);//enable buttons
        }else if(dealed){
            replaceCards();
            checkWins();
            dealed = false;
            disableEnableButtons(2);
            resetButtonText(b1);
            resetButtonText(b2);
            resetButtonText(b3);
            resetButtonText(b4);
            resetButtonText(b5);
        }
    }//end pressedDeal()

    /**
     * Helper to check if the winner has received
     * any wins.
     * Increment bank if a win has occurred
     */
    public void checkWins(){
        String wins = game.player.getHandType();
        int rank = game.player.getRank();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        String alert = "";
        if(rank == 1){
            alert += "You Received No Wins!";
        }else if(rank == 2){
            alert+="You Win With a Pair!\nReward $5";
            bank+=5;
        }else if(rank == 3){
            alert+="You Win With a 2 Pair!\nReward $10";
            bank+=10;
        }else if(rank == 4){
            alert+="You Win With a 3 of a Kind!\nReward $20";
            bank+=20;
        }else if(rank == 5){
            alert+="You Win With a Straight!\nReward $25";
            bank+=25;
        }else if(rank == 6){
            alert+="You Win With a Flush!\nReward $30";
            bank+=30;
        }else if(rank == 7){
            alert+="You Win With a Full House!\nReward $50";
            bank+=50;
        }else if(rank == 8){
            alert+="You Win With a 4 of a Kind!\nReward $100";
            bank+=100;
        }else if(rank == 9){
            alert+="You Win With a Straight Flush!\nReward $250";
            bank+=250;
        }else if(rank == 10){
            alert+="You Win With a Royal Flush!\nReward $1000";
            bank+=1000;
        }

        bankText.setText("Bank: "+bank);
        alertDialog.setMessage(alert);
        alertDialog.setTitle("WINS!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        setCardImageHelper(0,R.drawable.back);
                        setCardImageHelper(1,R.drawable.back);
                        setCardImageHelper(2,R.drawable.back);
                        setCardImageHelper(3,R.drawable.back);
                        setCardImageHelper(4,R.drawable.back);
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.show();

    }//end checkWins()

    /**
     * Checks which cards the user decides to throw and replaces them
     */
    public void replaceCards(){
        if(b1.getText().toString().equals("Throw")) {
            game.dealOne(1, 0);
            displayCard(0);
        }
        if(b2.getText().toString().equals("Throw")) {
            game.dealOne(1, 1);
            displayCard(1);
        }
        if(b3.getText().toString().equals("Throw")) {
            game.dealOne(1, 2);
            displayCard(2);
        }
        if(b4.getText().toString().equals("Throw")) {
            game.dealOne(1, 3);
            displayCard(3);
        }
        if(b5.getText().toString().equals("Throw")) {
            game.dealOne(1, 4);
            displayCard(4);
        }
    }

    /**
     * Helper to determine which cards to display
     * @param index - Index of card whose image view is to be set
     */
    public void displayCard(int index){
        //ace
        if(game.player.hand[index].getName().equals(Face.ACE)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
                setCardImageHelper(index,R.drawable.ace_of_clubs);
        }else if(game.player.hand[index].getName().equals(Face.ACE)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.ace_of_spades);
        }else if(game.player.hand[index].getName().equals(Face.ACE)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.ace_of_hearts);
        }else if(game.player.hand[index].getName().equals(Face.ACE)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.ace_of_diamonds);
        }
        //King
        else if(game.player.hand[index].getName().equals(Face.KING)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.king_of_clubs);
        }else if(game.player.hand[index].getName().equals(Face.KING)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.king_of_spades);
        }else if(game.player.hand[index].getName().equals(Face.KING)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.king_of_hearts);
        }else if(game.player.hand[index].getName().equals(Face.KING)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.king_of_diamonds);
        }
        //queen
        else if(game.player.hand[index].getName().equals(Face.QUEEN)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.queen_of_clubs);
        }else if(game.player.hand[index].getName().equals(Face.QUEEN)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.queen_of_spades);
        }else if(game.player.hand[index].getName().equals(Face.QUEEN)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.queen_of_hearts);
        }else if(game.player.hand[index].getName().equals(Face.QUEEN)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.queen_of_diamonds);
        }
        //jack
        else if(game.player.hand[index].getName().equals(Face.JACK)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.jack_of_clubs);
        }else if(game.player.hand[index].getName().equals(Face.JACK)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.jack_of_spades);
        }else if(game.player.hand[index].getName().equals(Face.JACK)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.jack_of_hearts);
        }else if(game.player.hand[index].getName().equals(Face.JACK)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.jack_of_diamonds);
        }
        //Ten
        else if(game.player.hand[index].getName().equals(Face.TEN)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_10);
        }else if(game.player.hand[index].getName().equals(Face.TEN)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_10);
        }else if(game.player.hand[index].getName().equals(Face.TEN)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_10);
        }else if(game.player.hand[index].getName().equals(Face.TEN)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_10);
        }
        //Nine
        else if(game.player.hand[index].getName().equals(Face.NINE)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_9);
        }else if(game.player.hand[index].getName().equals(Face.NINE)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_9);
        }else if(game.player.hand[index].getName().equals(Face.NINE)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_9);
        }else if(game.player.hand[index].getName().equals(Face.NINE)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_9);
        }
        //Eight
        else if(game.player.hand[index].getName().equals(Face.EIGHT)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_8);
        }else if(game.player.hand[index].getName().equals(Face.EIGHT)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_8);
        }else if(game.player.hand[index].getName().equals(Face.EIGHT)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_8);
        }else if(game.player.hand[index].getName().equals(Face.EIGHT)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_8);
        }
        //Seven
        else if(game.player.hand[index].getName().equals(Face.SEVEN)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_7);
        }else if(game.player.hand[index].getName().equals(Face.SEVEN)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_7);
        }else if(game.player.hand[index].getName().equals(Face.SEVEN)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_7);
        }else if(game.player.hand[index].getName().equals(Face.SEVEN)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_7);
        }
        //Six
        else if(game.player.hand[index].getName().equals(Face.SIX)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_6);
        }else if(game.player.hand[index].getName().equals(Face.SIX)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_6);
        }else if(game.player.hand[index].getName().equals(Face.SIX)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_6);
        }else if(game.player.hand[index].getName().equals(Face.SIX)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_6);
        }
        //Five
        else if(game.player.hand[index].getName().equals(Face.FIVE)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_5);
        }else if(game.player.hand[index].getName().equals(Face.FIVE)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_5);
        }else if(game.player.hand[index].getName().equals(Face.FIVE)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_5);
        }else if(game.player.hand[index].getName().equals(Face.FIVE)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_5);
        }
        //Four
        else if(game.player.hand[index].getName().equals(Face.FOUR)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_4);
        }else if(game.player.hand[index].getName().equals(Face.FOUR)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_4);
        }else if(game.player.hand[index].getName().equals(Face.FOUR)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_4);
        }else if(game.player.hand[index].getName().equals(Face.FOUR)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_4);
        }
        //Three
        else if(game.player.hand[index].getName().equals(Face.THREE)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_3);
        }else if(game.player.hand[index].getName().equals(Face.THREE)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_3);
        }else if(game.player.hand[index].getName().equals(Face.THREE)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_3);
        }else if(game.player.hand[index].getName().equals(Face.THREE)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_3);
        }
        //Two
        else if(game.player.hand[index].getName().equals(Face.DUECE)
                && game.player.hand[index].getSuit().equals(Suit.CLUBS)){
            setCardImageHelper(index,R.drawable.clubs_of_2);
        }else if(game.player.hand[index].getName().equals(Face.DUECE)
                && game.player.hand[index].getSuit().equals(Suit.SPADES)){
            setCardImageHelper(index,R.drawable.spades_of_2);
        }else if(game.player.hand[index].getName().equals(Face.DUECE)
                && game.player.hand[index].getSuit().equals(Suit.HEARTS)){
            setCardImageHelper(index,R.drawable.hearts_of_2);
        }else if(game.player.hand[index].getName().equals(Face.DUECE)
                && game.player.hand[index].getSuit().equals(Suit.DIAMONDS)){
            setCardImageHelper(index,R.drawable.diamonds_of_2);
        }
    }//end displayCard()

    /**
     * Sets the card image base on index
     * @param index - index of card in hand
     * @param imageSource - image to be placed
     */
    public void setCardImageHelper(int index, int imageSource){
        index++;
        switch(index){
            case 1:
                v1.setImageResource(imageSource);
                break;
            case 2:
                v2.setImageResource(imageSource);
                break;
            case 3:
                v3.setImageResource(imageSource);
                break;
            case 4:
                v4.setImageResource(imageSource);
                break;
            case 5:
                v5.setImageResource(imageSource);
                break;
            default:
        }
    }//end setCardImageHelper()

}//end GameFragment
