//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Class Explanation: Backbone of the game.
public class Game {

    public Player[] playerArray;
    public Deck deck = new Deck();
    public CommunityCards comCards = new CommunityCards();
    public Pot pot;
    private boolean firstRound = true;
    private int cardsOnTable = 0;
    public int playNumber = 0;
    public boolean firstTime = true;
    public boolean gameEnded = false;

    public int bbIndex = 1;
    public int sbIndex = 0;
    final int bigBlind = 100;
    final int smallBlind = 50;


    //my variables - Cem
    private int currentround = 1;
    private ArrayList<Card> comcards = new ArrayList<Card>();
    private String winners = "";

    public Game(Player[] players) {
        playerArray = players;
        pot = new Pot(players);
        deck.dealHands(players);
    }


    //Method to pay the blind bets at te beginning of the game
    public void payBlinds(ArrayList<Player> playersInGame, int bigBlind, int smallBlind, int bigIndex, int smallIndex) {
        pot.addToPot(playersInGame.get(bigIndex).spend(bigBlind), playersInGame.get(bigIndex));
        pot.addToPot(playersInGame.get(smallIndex).spend(smallBlind), playersInGame.get(smallIndex));
    }
  
/*  public void handleBlinds() {

    boolean small = false;
    boolean big = false;
    
    while (!small && !big) {
      
      bbIndex++;
      sbIndex++;
      if (bbIndex >= playerArray.length) {
        bbIndex = 0;
        if (playerArray[bbIndex].playing) big = true;
      }
      if (sbIndex >= playerArray.length) {
        sbIndex = 0;
        if (playerArray[sbIndex].playing) small = true;
      }
      if (bbIndex != sbIndex && playerArray[bbIndex].playing && playerArray[sbIndex].playing) {
        break;
      }
    }
  }
 */

    //Returns the # of players
    public int playersAtDesk() {
        int count = 0;
        for (int i = 0; i < playerArray.length; i++) {
            if (playerArray[i].playing) {
                count++;
            }
        }
        return count;
    }

    //Reset the player points
    public void zeroPoints() {
        for (int i = 0; i < playerArray.length; i++) {
            playerArray[i].points = 0;
        }
    }

    //Fully reset the game state
    public void resetGame() {
        cardsOnTable = 0;
        firstTime = true;
        firstRound = true;
        gameEnded = false;
        playNumber = 0;
        resetPlayers();
        pot.resetPot();
        comcards = new ArrayList<Card>();
        comCards.reset();
        gameEnded = false;
        deck = new Deck();
        for (int i = 0; i < playerArray.length; i++) {
            playerArray[i].allIn = false;
            if (playerArray[i].getBudget() > 0) {
                playerArray[i].playing = true;
            } else {
                playerArray[i].playing = false;
            }
        }
        deck.dealHands(playerArray);
    }

    //Print a player's chips
    public void printChips(Player player) {
        Common.println(player.getName() + ":");
        for (int i = 0; i < player.chips.length; i++) {
            Common.println("Chips with value " + player.chips[i].value + ": " + player.chips[i].getNumber());
        }
    }

    //Print all chips for all players
    public void printAllChips(Player[] players) {
        for (Player p : players) {
            printChips(p);
        }
    }


    //Round controller
    public void nextRound() {
        int minPlayNumber = 0;
        for (int i = 0; i < playerArray.length; i++) {
            if (playerArray[i].playing) {
                minPlayNumber += 1;
            }
        }
        if (pot.advance() && cardsOnTable <= 5 && playNumber >= minPlayNumber - 1) {// minPlayNumber = # of those who played
            playNumber = 0;
            if (firstRound && cardsOnTable < 5) {
                Card card1 = deck.dealCard();
                Card card2 = deck.dealCard();
                Card card3 = deck.dealCard();
                comCards.add(card1);
                comCards.add(card2);
                comCards.add(card3);
                comcards.add(card1);
                comcards.add(card2);
                comcards.add(card3);

                cardsOnTable = cardsOnTable + 3;
                firstRound = false;
            } else if (pot.advance() && cardsOnTable == 5 && firstTime) {//Below is for last round when there are already 5 cards
                endGameCheck();
                printAllBudget();
                firstTime = false;
                gameEnded = true;

            } else if (cardsOnTable < 5) {
                Card card4 = deck.dealCard();
                comCards.add(card4);
                comcards.add(card4);
                cardsOnTable = cardsOnTable + 1;
            }
        }


    }

    //MY METHODS TO IMITATE NEXTROUND FOR GUI - Cem "Darkwarro"
    public void dealCommunityCard() {
        if (currentround == 1) {
            comcards.add(deck.dealCard());
            comcards.add(deck.dealCard());
            comcards.add(deck.dealCard());
        } else if (currentround < 4)
            comcards.add(deck.dealCard());
    }

    //Go on to the next round
    public void advanceRounds() {
        currentround++;
    }

    //Get common cards
    public ArrayList<Card> getComCards() {
        return comcards;
    }
    //end of my methods - cem


    //Check if end of game is reached
    public void endGameCheck() {
        ArrayList<Integer> playingPlayersIndex = new ArrayList<Integer>();//Stores index of players that are still playing
        for (int i = 0; i < playerArray.length; i++) {
            if (playerArray[i].playing) {
                playerArray[i].checkHand(comCards.display());//Checking hands on players to calculate their points
                playingPlayersIndex.add(i);//Adds players index to array if they are playing for future reference
            }
        }
        ArrayList<Player> winnerArrayList = new ArrayList<Player>();
        for (int i = 0; i < playingPlayersIndex.size(); i++) {//Adding the players that are playing to the winnerArrayList
            winnerArrayList.add(playerArray[playingPlayersIndex.get(i)]);
        }

        Player[] winnerArray = new Player[winnerArrayList.size()];//transferring arraylist to array
        for (int i = 0; i < winnerArray.length; i++) {
            winnerArray[i] = winnerArrayList.get(i);
        }

        //Splitting the award
        int numberOfWinners = winnerArray.length;
        for (int i = 0; i < winnerArray.length; i++) {
            for (int j = 0; j < winnerArray.length; j++) {
                try {
                    if (winnerArray[i].points < winnerArray[j].points) {
                        winnerArray[i] = null;
                        numberOfWinners = numberOfWinners - 1;//Decreases number of winners if there is a null player meaning they have less points
                    }
                } catch (Exception exc) {
                    System.out.println("Exceptionladım");
                }
            }
        }
        winnerArray = getHighHand(comCards.display(), winnerArray);
        winners = "";
        //Runs through winners (already knowing how many of them won), and splits pot to all winners
        for (int i = 0; i < winnerArray.length; i++) {
            winnerArray[i].hasWon(true);
            winnerArray[i].profit(pot.splitPot(winnerArray.length));
            Common.println("Someone profited");
            winners += winnerArray[i].getName() + " ";

        }

    }

    //Get the highest hand given the common cards
    public Player[] getHighHand(Card[] comCards, Player[] winning) {

        ArrayList<Player> notNullPlayers = new ArrayList<Player>();

        for (int i = 0; i < winning.length; i++) {
            if (winning[i] != null) {
                notNullPlayers.add(winning[i]);
                Card[] cards = winning[i].uniteCards(comCards);
                for (int j = 0; j < cards.length; j++) {
                    winning[i].totalCardVal += cards[j].getValue();
                }
            }
        }
        int max = notNullPlayers.get(0).totalCardVal;
        for (int i = 1; i < notNullPlayers.size(); i++) {
            if (notNullPlayers.get(i).totalCardVal > max) {
                max = notNullPlayers.get(i).totalCardVal;
            }
        }
        for (int i = 0; i < notNullPlayers.size(); i++) {
            if (notNullPlayers.get(i).totalCardVal < max) {
                notNullPlayers.remove(i);
            }
        }
        Player[] winningPlayers = new Player[notNullPlayers.size()];
        for (int i = 0; i < winningPlayers.length; i++) {
            winningPlayers[i] = notNullPlayers.get(i);
        }
        System.out.println("Winning players count: " + winningPlayers.length);
        return winningPlayers;
    }

    //Printing budgets for all players
    public void printAllBudget() {
        for (int i = 0; i < playerArray.length; i++) {
            playerArray[i].calculateBudget();
            Common.println("Budget of player " + (i + 1) + ": " + playerArray[i].getBudget());
        }
    }

    //Resetting player status'es

    public void resetPlayers() {
        for (int i = 0; i < playerArray.length; i++) {
            playerArray[i].resetHand();
            playerArray[i].hasWon(false);
        }
    }

    //Common card picture getter
    public void getComCardPictures(ArrayList<BufferedImage> ccards) throws IOException {

        for (int i = 0; i < comcards.size(); i++) {
            BufferedImage c = (BufferedImage) comcards.get(i).getImg();
            ccards.add(c);
        }
    }
    public Player getHuman() {
        Player human = null;
        for (Player p : playerArray) {
            if (p.isAI == false) human = p;
        }
        return human;
    }

    //More getter methods

    public String getWinner() {
        return winners;
    }

    public int getTotalPot() {
        return pot.totalPot();
    }
}