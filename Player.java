//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020


import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
//Class Definition: Player object and related methods
public class Player implements Comparable {

    private Chip red;
    private Chip blue;
    private Chip green;
    private Chip black;
    private Chip white;
    private int budget;
    public boolean isAI;
    private BufferedImage border;
    public int points = 0;
    private String name = "";
    public boolean playing = true;
    public boolean validPlay = false;
    private Card[] hand = new Card[2];
    public Chip[] chips = new Chip[5];
    public int totalCardVal = 0;
    // Scanner scanner = new Scanner(System.in);
    boolean allIn = false;

    private boolean ready;
    private boolean inserver;
    private boolean myround;
    private boolean hasWon;
    private boolean canCheck;

    //Contructor, creates an array list of card objects
    public Player(int w, int r, int blu, int g, int bla, String name) {
        border = null;
        this.name = name;
        ready = false;
        myround = false;
        hasWon = false;
        white = new Chip(w, 10);//300 ---> 3,000 USD
        red = new Chip(r, 20);//100 ---> 2,000 USD
        blue = new Chip(blu, 50);//100 ---> 5,000 USD
        green = new Chip(g, 100); //100 ---> 10,000 USD
        black = new Chip(bla, 500); //50 ---> 25,000 USD
        chips[0] = white;
        chips[1] = red;
        chips[2] = blue;
        chips[3] = green;
        chips[4] = black;
        calculateBudget();
        isAI = false;
    }

    //Set if player has won or not
    public void hasWon(boolean b) {
        hasWon = b;
    }


    //COMPARATOR: Compares with players' budgets
    public int compareTo(Object o) {
        if (o == null)
            return 1;
        else {
            Player p = (Player) o;
            p.calculateBudget();
            this.calculateBudget();
            if (p.getBudget() > this.getBudget())
                return -1;
            else if (p.getBudget() < this.getBudget())
                return 1;
            else
                return 0;
        }
    }



    //Calculates available player budgets from chips from on hand
    public void calculateBudget() {

        int total = 0;

        for (int i = 0; i < chips.length; i++) {
            total += chips[i].totalValue();
        }
        budget = total;
    }

    //For Cem GUI
    public void getHandPicture(ArrayList<BufferedImage> hand) throws IOException {
        Card[] playerCards = getHand();
        hand.add((BufferedImage) playerCards[0].getImg());
        hand.add((BufferedImage) playerCards[1].getImg());
    }


    //Calculate the amount of profit and add it back to the chips
    public void profit(int pot) {

        int amount = pot;

        while (amount >= white.value) {
            if (amount >= black.value) {
                black.addChips(1);
                amount = amount - black.value;
            } else if (amount >= green.value) {
                green.addChips(1);
                amount = amount - green.value;
            } else if (amount >= blue.value) {

                blue.addChips(1);
                amount = amount - blue.value;
            } else if (amount >= red.value) {

                red.addChips(1);
                amount = amount - red.value;
            } else if (amount >= white.value) {

                white.addChips(1);
                amount -= white.value;
            }
        }
    }

    //@param is the amount put on the table by the prew player
    public boolean check(int raised) {
        if (raised == 0) {
            Common.println("Checked");
            return true;
        }
        Common.println("You can't check");
        return false;
    }

    //Folding method
    public void fold() {
        Common.println("I concede to you... Well played...");
        playing = false;
    }

    //Raise the bet, and return the amount in chips raised.
    public ArrayList<Chip> raise(int raised, int betted) {
        ArrayList<Chip> bet = call(betted);
        ArrayList<Chip> raise = spend(raised);
        for (int i = 0; i < bet.size(); i++) {
            Chip bettedChip = bet.get(i);
            for (int j = 0; j < raise.size(); j++) {
                Chip raisedChip = raise.get(j);
                if (bettedChip.value == raisedChip.value) {
                    raisedChip.addChips(bettedChip.getNumber());
                }
            }
        }
        return raise;
    }

    //Create a new pot, spend it with amount requested to spend, and pass that pot.
    public ArrayList<Chip> spend(int spendAmount) {

        ArrayList<Chip> pot = new ArrayList<Chip>();
        pot.add(new Chip(0, 10));
        pot.add(new Chip(0, 20));
        pot.add(new Chip(0, 50));
        pot.add(new Chip(0, 100));
        pot.add(new Chip(0, 500));
        int amount = spendAmount;
        calculateBudget();
        if (spendAmount > 0) {
            if (changeNeeded(spendAmount, chips)) {
                change(spendAmount);

            }
        }

        calculateBudget();
        if (amount <= budget) {

            while (amount != 0) {

                // System.out.println("Stuck at 198");

                if (amount >= black.value && black.getNumber() > 0) {
                    black.removeChips(1);
                    amount = amount - black.value;
                    pot.get(4).addChips(1);
                } else if (amount >= green.value && green.getNumber() > 0) {

                    green.removeChips(1);
                    amount = amount - green.value;
                    pot.get(3).addChips(1);
                } else if (amount >= blue.value && blue.getNumber() > 0) {

                    blue.removeChips(1);
                    amount = amount - blue.value;
                    pot.get(2).addChips(1);
                } else if (amount >= red.value && red.getNumber() > 0) {

                    red.removeChips(1);
                    amount = amount - red.value;
                    pot.get(1).addChips(1);
                } else if (amount >= white.value && white.getNumber() > 0) {
                    white.removeChips(1);
                    amount -= white.value;
                    pot.get(0).addChips(1);
                }
            }
        }
        calculateBudget();
        revertChips(chips);
        return pot;
    }

    //Get the change of bet from chips.
    public void change(int bet) {
        calculateBudget();
        int budget = getBudget();
        int numberNeeded = budget / 10;
        for (int i = 0; i < chips.length; i++) {
            chips[i].removeChips(chips[i].getNumber());
        }

        chips[0].addChips(numberNeeded);


    }

    //Check if the game needs to return back any change in chips due to a betç
    public boolean changeNeeded(int bet, Chip[] arr) {

        Chip[] temp = copyChipsArr(arr);
        int extra = bet;
        Chip maxChip = findMaxChip(temp, extra);
        int maxChipVal = 0;
        try {
            maxChipVal = maxChip.value;
        } catch (Exception exc) {
            return true;
        }

        while (maxChipVal <= extra && extra > 0 && maxChip != null) {

            maxChipVal = maxChip.value;

            if (extra - maxChipVal == 0) {
                return false;
            }
            extra -= maxChipVal;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == maxChip) {
                    temp[i].removeChips(1);
                }
            }
            maxChip = findMaxChip(temp, extra);
        }

        return true;

    }


    //Get the chip with the greatest number
    public Chip greatestChip() {

        Chip greatest = null;

        for (int i = 4; i > -1; i--) {
            if (chips[i].getNumber() > 0) {
                greatest = chips[i];
                return greatest;
            }
        }
        return greatest;
    }

    //Get the chip with max value
    public Chip findMaxChip(Chip[] arr, int bet) {
        for (int i = 4; i > -1; i--) {
            if (arr[i].value <= bet && arr[i].getNumber() > 0) {
                //Common.println("Value " + arr[i].value + " is smaller than or equal to " + bet);
                return arr[i];
            }
        }
        Chip c = null;
        return c;
    }

    //Compare chip values and get chip with value bigger than @param value
    public Chip findChipWithValueBiggerThan(int value, Chip[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].value > value && arr[i].getNumber() > 0) {
                return chips[i];
            }
        }
        return null;
    }

    //An utility method used to copy a chips arr into a new array
    public Chip[] copyChipsArr(Chip[] toCopy) {

        Chip[] temp = new Chip[5];

        for (int i = 0; i < toCopy.length; i++) {
            temp[i] = new Chip(toCopy[i].getNumber(), toCopy[i].value);
        }

        return temp;
    }

    //Next two method modifies chips and in/decrease their value and add num number of chips/remove one chip
    public void increaseChipWithValue(int value, int num) {
        for (int i = 0; i < chips.length; i++) {
            if (chips[i].value == value) {
                chips[i].addChips(num);
            }
        }
    }

    public void decreaseChipWithValue(int value) {
        for (int i = 0; i < chips.length; i++) {
            if (chips[i].value == value) {
                chips[i].removeChips(1);
            }
        }
    }



    public void revertChips(Chip[] chips) {

        for (int i = 0; i < chips.length - 1; i++) {
            int cost = chips[i].getNumber() * chips[i].value;

            for (int j = i + 1; j < chips.length; j++) {
                while (cost >= chips[j].value) {
                    if (chips[j].value % chips[i].value > 0) {
                        chips[0].addChips(1);
                    }
                    int numberNeeded = chips[j].value / chips[i].value;
                    if (chips[j].value % chips[i].value > 0) {
                        numberNeeded += 1;
                    }
                    chips[i].removeChips(numberNeeded);
                    chips[j].addChips(1);
                    cost = chips[i].getNumber() * chips[i].value;
                }
            }

        }


    }


    //Sort the cards on hand
    public Card[] sort(Card[] cards) {
        Arrays.sort(cards);
        return cards;
    }

    //Unite the cards from the community deck for algorithm checks
    public Card[] uniteCards(Card[] communityCards) {
        //System.out.println("Community cards length @Player.uniteCards: " + communityCards.length);
        Card[] cardCollection = new Card[7];
        cardCollection[0] = getHand()[0];
        cardCollection[1] = getHand()[1];
        for (int i = 2; i < communityCards.length + 2; i++) {
            cardCollection[i] = communityCards[i - 2];
        }
        cardCollection = sort(cardCollection);
        return cardCollection;
    }

    public int pair(Card[] communityCards) {
        Card[] cards = uniteCards(communityCards);
        Card tempCard;
        int i = 1;
        int nPairs = 0;

        while (i < cards.length) {
            tempCard = cards[i - 1];
            if (tempCard.sameValue(cards[i])) {
                i++;
                nPairs++;
            }
            i++;
        }
        return nPairs;
    }

    //Two Pair Checks
    public int twoPairs(Card[] communityCards) {
        if (pair(communityCards) == 2) {
            return 1;
        } else {
            return 0;
        }
    }

    //Three of a kind check
    public int threeKind(Card[] communityCards) {
        Card[] cards = uniteCards(communityCards);
        if (pair(communityCards) == 0) {
            return 0;
        } else if (cards[0].sameValue(cards[2]) || cards[1].sameValue(cards[3]) || cards[2].sameValue(cards[4])
                || cards[3].sameValue(cards[5]) || cards[4].sameValue(cards[6])) {
            return 1;
        } else {
            return 0;
        }
    }

    //Four of a kind check
    public int fourKind(Card[] communityCards) {
        Card[] cards = uniteCards(communityCards);
        if (pair(communityCards) == 2 && threeKind(communityCards) == 1) {
            if (cards[0].sameValue(cards[3]) || cards[1].sameValue(cards[4]) || cards[2].sameValue(cards[5]) || cards[3].sameValue(cards[6])) {
                return 1;
            }
        }
        return 0;
    }


    public int straight(Card[] communityCards) {
        int anaskm = 0;
        Card[] cards = uniteCards(communityCards);
        for (int i = 1; i < 4; i++) {
            for (int j = i; j < 4 + i; j++) {
                if (cards[j - 1].valueDiff(cards[j]) == -1) {
                    anaskm++;
                }
            }
            anaskm = 0;
        }

        if (anaskm == 4) {
            return 1;
        } else {
            return 0;
        }

    }


    //Flushing cards.
    public int flush(Card[] communityCards) {
        Card[] cards = uniteCards(communityCards);
        if (twoPairs(communityCards) == 1) {
            return 0;
        } else {
            int i = 1;
            int count = 1;
            for (Card card : cards) {
                if (card.sameSuit(cards[i])) {
                    count++;
                }
                if (i < cards.length - 1) {
                    i++;
                }
            }
            if (count == 5) {
                return 1;
            }
            return 0;
        }
    }


//Check for fullHouse, straightFlush and royalFlush
    public int fullHouse(Card[] communityCards) {
        if (pair(communityCards) == 2 && threeKind(communityCards) == 1 && fourKind(communityCards) == 0) {
            return 1;
        } else return 0;
    }

    public int straightFlush(Card[] communityCards) {
        if (straight(communityCards) == 1 && flush(communityCards) == 1) {
            return 1;
        } else return 0;
    }

    public int royalFlush(Card[] communityCards) {
        Card[] cards = uniteCards(communityCards);
        if (straightFlush(communityCards) == 1 && cards[2].getValue() == 11) {
            return 1;
        } else {
            return 0;
        }
    }

    //Check all hand for all possible ends
    public String checkHand(Card[] communityCards) {
        //System.out.println("Community cards length @Player.checkHand: " + communityCards.length);
        points = 0;
        String result;
        if (royalFlush(communityCards) == 1) {
            points = 9;
            result = "Royal Flush";
        } else if (straightFlush(communityCards) == 1) {
            points = 8;
            result = "Straight Flush";
        } else if (fourKind(communityCards) == 1) {
            points = 7;
            result = "Four of a Kind";
        } else if (fullHouse(communityCards) == 1) {
            points = 6;
            result = "Full House";
        } else if (flush(communityCards) == 1) {
            points = 5;
            result = "Flush";
        } else if (straight(communityCards) == 1) {
            points = 4;
            result = "Straight";
        } else if (threeKind(communityCards) == 1) {
            points = 3;
            result = "Three of a Kind";
        } else if (twoPairs(communityCards) == 1) {
            points = 2;
            result = "Two Pairs";
        } else if (pair(communityCards) == 1) {
            points = 1;
            result = "A pair";
        } else {
            result = "No Pair";
        }

        return result;
    }

    //Play the game according to what has been called from the GUI
    public ArrayList<Chip> play(Pot pot, String action, int necessarynum, boolean player) {
        ArrayList<Chip> chips = new ArrayList<Chip>();
        int potAmount = pot.potValue(pot.getHighestPot()) - pot.potValue(pot.getPotOf(this));
        if (allIn) {
            validPlay = true;
        }
        while (!validPlay) {
            System.out.println("Do you want to raise, fold, call, or check? ");

            if (action.equals("raise")) {
                System.out.println("How much do you want to raise? ");
                System.out.println("Raisenum: " + necessarynum + " potAmount: " + potAmount);
                int raiseAmount = necessarynum - potAmount;
                if (raiseAmount + potAmount <= this.budget) {
                    chips = raise(raiseAmount, potAmount);
                    if (getBudget() == 0) {
                        allIn = true;
                    }
                    validPlay = true;
                }

            } else if (action.equals("fold")) {
                fold();
                validPlay = true;
            } else if (action.equals("call")) {
                if (potAmount < this.budget) {
                    chips = call(potAmount);
                    validPlay = true;
                } else if (potAmount >= this.budget) {
                    chips = call(this.budget);
                    allIn = true;
                    validPlay = true;
                }
            } else if (action.equals("check")) {
                if (player || check(potAmount))
                    validPlay = true;
            }
        }
        validPlay = false;
        return chips;
    }

    public void resetHand() {
        for (int i = 0; i < hand.length; i++) {
            hand[i] = null;
        }
    }

    public boolean canCheck(Pot pot) {
        int potAmount = pot.potValue(pot.getHighestPot()) - pot.potValue(pot.getPotOf(this));
        return check(potAmount);
    }

    //Getter and setter methods
    public ArrayList<Chip> call(int raised) {
        return spend(raised);
    }

    public void setBorder(BufferedImage b) {
        border = b;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getBorder() {
        return border;
    }

    public boolean ifWon() {
        return hasWon;
    }

    public boolean ifReady() {
        return ready;
    }

    public void setReady(boolean b) {
        ready = b;
    }

    public void setInServer(boolean b) {
        inserver = b;
    }

    public boolean ifMyRound() {
        return myround;
    }

    public void setMyRound(boolean b) {
        myround = b;
    }

    public void getChipPhotos(BufferedImage[] chipos, int playernum) throws IOException {
        //Something needs change, when a chip is missing the further one immediately makes up for it
        // TODO: specific places in the array for specific chips so they don't mix up in the gui

        int placement = playernum * 5;
        int counter = 0;
        if (chips[0].getNumber() > 0 && playing) {
            chipos[placement + counter] = chips[0].getImg();
        } else
            chipos[placement + counter] = null;
        counter++;
        if (chips[1].getNumber() > 0 && playing) {
            chipos[placement + counter] = chips[1].getImg();
        } else
            chipos[placement + counter] = null;
        counter++;
        if (chips[2].getNumber() > 0 && playing) {
            chipos[placement + counter] = chips[2].getImg();
        } else
            chipos[placement + counter] = null;
        counter++;
        if (chips[3].getNumber() > 0 && playing) {
            chipos[placement + counter] = chips[3].getImg();
        } else
            chipos[placement + counter] = null;
        counter++;
        if (chips[4].getNumber() > 0 && playing) {
            chipos[placement + counter] = chips[4].getImg();
        } else
            chipos[placement + counter] = null;
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getBudget() {
        return budget;
    }

    public Card[] getHand() {
        return hand;
    }

}