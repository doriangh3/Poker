package edu.wcu.ddbarrier1.poker;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Dorian Barrier
 *
 * Game class is used to be the main class of the Poker! project.
 * This class contains the code necessary to fill a deck of cards,
 * make a players hand and a computers hand, initialize both of
 * the hand and deck sizes. This class also has several helper
 * methods for the go() method.
 *
 */
public class Game {

    //An Array of Cards.
    public Card pack[];
    //Two hands, a player and a computer.
    public Hand player,computer;
    //The size of the deck, 52.
    public static final int     PACK_SIZE  = 52;
    //The size of both of the hands, 5.
    public static final int     HAND_SIZE  = 5;
    //A counter for the index of the current spot in the deck.
    private int index;
    //A Scanner to accept input from the user.
    Scanner input = new Scanner(System.in);

    /**
     *
     * The default constructor for the Game class.
     * Initializes all the fields and adds the cards
     * to the deck with the method initPack().
     *
     */
    public Game(){

        pack = new Card[PACK_SIZE];
        player = new Hand(HAND_SIZE);
        computer = new Hand(HAND_SIZE);
        index =0;

        initPack();


    }//end method

    /**
     *
     * This method is used to add all of the cards to the deck, traversing
     * a for each array for all of the suits and face combinations and
     * adding them to the pack.
     *
     */
    public void initPack(){

        int count=0;
        for (Suit suit :  Suit.values()) {

            for (Face face : Face.values()) {
                pack[count++] = new Card(face,suit);
            }//end for
        }//end for

    }//end method

    /**
     *
     * This method distributes cards to each hand (player and computer)
     * from the pack by going through a loop until each hand has
     * HAND_SIZE amount of cards.
     *
     * If the counter in the deck is getting close to the end the
     * deck is shuffled and the counter is reset to 0;
     *
     */
    public void deal(){

        for(int i=0; i<(HAND_SIZE);i++){

            if(index>PACK_SIZE-HAND_SIZE){
                index=0;
                shuffle();
            }//end if

            this.player.setCard(i, pack[index++]);

            if(index>PACK_SIZE-HAND_SIZE){
                index=0;
                shuffle();
            }//end if

            this.computer.setCard(i,pack[index++]);
            index++;

        }//end for
    }//end method

    /**
     * Helper method for shuffle that swaps two cards
     * in the pack around.
     */
    private void swap(int one, int two){
        Card tmp = pack[one];
        pack[one] = pack[two];
        pack[two] = tmp;
    }//end method

    /**
     * The Fisher-Yates Shuffle algorithm.
     * This is used to shuffle the deck so that every time a
     * shuffle is called all of the cards will be rearranged.
     *
     */
    public void shuffle() {

        Random r = new Random();
        for (int i = 0; i<PACK_SIZE; i++) {
            int index = r.nextInt(PACK_SIZE);
            swap(i,index);
        }//end for
    }//end method

    /**
     * This method displays the hand of the player that is taken as the
     * parameter, 1 is the player, 2 is the computer.
     *//**
     * @param - the number of the player. 1 for the player, 2 for the computer.
     */
    public void displayHand(int playerNum) {
        if(playerNum == 1){
            System.out.println("Player: " + player.toString());
        }//end if
        else if(playerNum == 2){
            System.out.println("Computer: "+computer.toString());

        }//end else if
        else{
            System.out.println("Invalid player");
        }//end else
    }//end method

    /**
     *
     * This method is used to replace the cards the user no longer wants.
     *
     * @param - the player who no longer wants a card
     * @param - at a certain index.
     *
     */
    public void dealOne(int player, int index){
        if(player == 1){
            this.player.setCard(index,pack[this.index++]);
        }
        else if(player == 2){
            this.computer.setCard(index,pack[this.index++]);
        }
        else{
            System.out.println("Invalid player");{
            }
        }
    }


    /**
     *
     * This method determines who the winner is by checking their hand rank.
     * The player with the higher hand rank wins.
     *
     * (This does not include a tie condition for when
     * the user and computer both tie with the same thing and
     * the highest card wins out of the win condition.)
     *
     */
    public void printWinner(){
        if( player.getRank()>computer.getRank()){
            System.out.println("Player wins with " + player.getHandType());
        }//end if
        else if(player.getRank()<computer.getRank()){
            System.out.println("Computer wins with " + computer.getHandType());
        }//end else if
        else if(player.getRank() == computer.getRank() && player.getRank()>1){
            System.out.println("Tie!");
        }//end else if
        else{
            if(player.highCard().rank()>computer.highCard().rank()){
                System.out.println("Player wins with " + player.highCard());
            }//end if
            else if(player.highCard().rank()<computer.highCard().rank()){
                System.out.println("Computer wins with " + computer.highCard());
            }//end else if
        }//end else
    }//end method

    /**
     * A helper method for go() that asks the user if they would like
     * to discard any cards from their hand, if they do how many
     * and also checks for any bad input the user might enter.
     */
    private void discard(){
        System.out.println("How many cards would you like to discard?");
        int i = input.nextInt();

        if(i<0 || i>HAND_SIZE){
            System.out.println("INVALID INPUT");
            discard();
        }//end if

        if(i>0){System.out.println("Which " + i + " would you like to swap?");
        }for(int count=0; count<i;count++){
            int i2 = input.nextInt();

            if(i2<0 || i2>HAND_SIZE-1){
                System.out.println("INVALID INPUT, Try again!");
                count--;
            }//end if
            else{
                dealOne(1,i2);
            }//end else

        }//end for
    }//end method


    /**
     * REQUIRED TO DETERMINE A WINNER:
     *
     * A Helper method for go()
     * Calls both player and computers getHandType methods
     * to determine the ranking of their hands.
     */
    private void handType(){
        player.getHandType();
        computer.getHandType();
    }//end method

    /**
     * REQUIRED FOR HAND CHECKS:
     *
     * A helper method for go()
     * Calls both sort methods to make sure that the
     * hand is sorted before and checks start.
     */
    public void newSort(){
        player.sort();
        computer.sort();
    }//end method

    /**
     *
     * A helper method for go() that prompts the user
     * if they would like to play again, also contains checks
     * to see if any invalid inputer is entered.
     *
     */
    private void goAgain(){
        System.out.println("Would you like to play again?");
        System.out.println("yes = 1, no = 0");
        int i = input.nextInt();

        if(i == 1){
            go();
        }//end if
        else if (i == 0){
            System.exit(0);
        }//end else if
        else{
            System.out.println("INVALID INPUT.");
            goAgain();
        }//end else

    }//end method

    /**
     * go(), the method that drives the program, calling all its helper methods
     * to shuffle the deck, deal the hands, display the hands, sort the hands, determines what
     * the user wants to do with their cards and if they want to play again.
     */
    public void go(){

        shuffle();
        deal();
        newSort();
        displayHand(1);
        discard();
        newSort();
        handType();
        displayHand(2);
        displayHand(1);
        printWinner();
        goAgain();

    }//end go()

    public void go2(){

        shuffle();
        deal();

    }//end go2()

    /**
     * A main method for the required for the program to run. Using an instance of
     * Game the main method calls the driving method go() in order to run the program.
     */
    public static void main(String[]args){

        Game game = new Game();

        game.go();

    }// end main

}//end class
