package edu.wcu.ddbarrier1.poker;
import java.util.Arrays;

import edu.wcu.ddbarrier1.poker.Card;
import edu.wcu.ddbarrier1.poker.WinHand;


/**
 *
 *@author Dorian Barrier
 *
 *Hand class, contains methods used to manipulate hands created within the
 *game class. Checks the win conditions, has variables for the size of the current hand, and
 *for the rank of the hand.
 *
 */
public class Hand{

    //A field that stores how many cards are in the hand
    private final int CARDS_IN_HAND;
    //An array of cards called hand
    public Card[] hand;
    //A variable for the rank of the hand
    private int rank;

    /**
     * The default constructor that sets the base hand size to 5.
     */
    public Hand(){

        this(5);

    }//end default constructor

    /**
     * A constructor taking in the amount of cards in the hand,
     * setting the number of CARDS_IN_HAND, then using that value to
     * initialize the hand array to that size, sets rank to zero for later
     * user.
     *
     * @param numCards the number of cards in the hand
     */
    public Hand(int numCards){
        rank=0;
        CARDS_IN_HAND = numCards;
        hand = new Card [CARDS_IN_HAND];

    }


    /**
     * The sort method is used to sort all of the cards in the hand numerically
     * by their suit.
     */
    public void sort() {

        Arrays.sort(hand, (Card u1, Card u2) -> u1.compareTo(u2));

    }//end method

    /**
     * A method used to set one of the cards at a certain place in
     * the hand.
     * @param index, the index of the card to be changed
     * 		  newCard, the new card that will be put into
     * 		  the old cards place.
     */
    public void setCard(int index, Card newCard){

        hand[index] = newCard;

    }//end method

    /**
     * A getter method to get the rank.
     * @return rank, the rank of the hand
     */
    public int getRank(){
        return rank;
    }

    /**
     * A method that using helper methods to determine
     * the winning hand, also sets the rank of the hand.
     * @return the name of the winning hand.
     */
    public String getHandType() {


        if(royalFlush()==1 ){
            rank = 10;
            return WinHand.RFLUSH.getName();
        }//end if
        if(straightFlush()==1){
            rank = 9;
            return WinHand.SFLUSH.getName();
        }//end if
        if(fourOfAKind()==1){
            rank =8;
            return WinHand.FOURKIND.getName();
        }//end if
        if(fullHouse()==1){
            rank=7;
            return WinHand.FHOUSE.getName();
        }//end if
        if(threeOfAKind()==1){
            rank=6;
            return WinHand.THREEKIND.getName();
        }//end if
        if(straight()==1){
            rank =5;
            return " "+WinHand.STRAIGHT.getName();
        }//end if
        if(flush()==1){
            rank =4;
            return WinHand.FLUSH.getName();
        }//end if
        if(twoPair()==1){
            rank =3;
            return WinHand.TWOPAIR.getName();
        }//end if
        if(pair()==1){
            rank =2;
            return " "+WinHand.PAIR.getName();
        }
        rank =1;
        return "No Win";
    }//end method

    /**
     * Finds the high card of the hand.
     * (In this case it will be the last card
     * in the hand because of invoking the sort method.)
     * @return the value of the highest card in the hand.
     */
    public Card highCard(){
        return hand[hand.length-1];
    }//end method

    /**
     * This is a helper method used to see if the hand contains a pair.
     * @return 1 if the hand contains a pair
     * 			0 otherwise
     */
    private int pair(){
        sort();
        for(int i=1; i < hand.length; i++){

            if (hand[i].getName() == hand[i-1].getName()){

                return 1;

            }//end if
        }//end for

        return 0;

    }//end method

    /**
     * A helper method used to see if the hand has two pairs.
     * @return 1 if the hand has two pairs, 0 otherwise.
     */
    private int twoPair(){
        int numPairs = 0;
        sort();
        for(int i=1; i < hand.length; i++){

            if (hand[i].getName() == hand[i-1].getName()){

                numPairs ++;

            }//end if
        }//end for

        if(numPairs == 2)
            return 1;
        else
            return 0;

    }//end method

    /**
     * A helper method used to see if the hand has a
     * three of a kind. ONLY IF THE HAND IS SORTED.
     * @return 1 if there is a three of a kind, 0 otherwise
     */
    private int threeOfAKind(){
        sort();
        for(int i=0; i < hand.length-3; i++){

            if (hand[i].rank() == hand[i+2].rank() ){

                return 1;

            }//end if
        }//end for

        return 0;

    }//end method

    /**
     * A helper method used to see if the hand has a
     * four of a kind. ONLY IF THE HAND IS SORTED.
     * @return 1 if there is a four of a kind, 0 otherwise
     */
    private int fourOfAKind(){
        sort();
        for(int i=0; i < hand.length-3; i++){
            if (hand[i].rank() == hand[i+3].rank() ){
                return 1;
            }//end if

        }//end for

        return 0;

    }//end method

    /**
     * A helper method used to see if the hand has a
     * full house.
     * @return 1 if there is a full house, 0 otherwise.
     */
    private int fullHouse(){
        sort();
        int target =0;

        for(int i=0; i<hand.length-1; i++){

            if(hand[i].rank()==hand[i+1].rank()){
                target++;
            }//end if

        }//end for

        if (target == 3){
            return 1;
        }//end if

        return 0;

    }//end method

    /**
     * A helper method used to see if the hand has a
     * straight.
     * @return 1 if there is a straight, 0 if otherwise.
     */
    private int straight(){
        sort();
        for(int i=0; i < hand.length-2; i++){

            if(hand[i].rank() != (hand[i+1].rank()-1)){
                return 0;
            }//end if

        }//end for

        return 1;

    }//end method

    /**
     * A helper method that check to see if the
     * hand has a flush.
     * @return 1 if there is a flush, 0 otherwise
     */
    private int flush(){
        for(int i=0; i<hand.length-2;i++){

            if(hand[i].getSuit() != hand[i+1].getSuit()){
                return 0;
            }//end if

        }//end for

        return 1;

    }//end method

    /**
     * A helper method that uses both straight and flush
     * to see if the hand contains a straight flush.
     * @return 1 if the hand has a straight flush.
     */
    private int straightFlush(){
        if(straight() ==1 && flush()==1){

            return 1;

        }//end if

        return 0;

    }//end method

    /**
     * A helper method that checks the only possible win condition of a royal flush
     * between all four suits.
     * @return 1 if the hand has a royal flush, 0 otherwise.
     */
    private int royalFlush(){
        int target =0;
        if (hand[0].rank() == 1 && hand[1].rank() == 10 && hand[2].rank() == 11 && hand[3].rank() == 12 && hand[4].rank() == 13){

            for(int i =0; i<hand.length-2;i++){
                if(hand[i].getSuit() == hand[i+1].getSuit()){
                    target++;
                }//end if
            }//end for

            if (target == hand.length-2){

                return 1;

            }//end if
        }//end if

        return 0;

    }//end method

    /**
     * A method used to format all of the cards in the hand
     * into a proper string to be printed out later.
     */
    public String toString(){
        String str = " ";
        for(int i=0; i<hand.length;i++){
            str += i + ": " + hand[i].getName() + " "+ hand[i].getSuit() + "\n";
        }//end for
        return str;
    }//end method

}//end class
