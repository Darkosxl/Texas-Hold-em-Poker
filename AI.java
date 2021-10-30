//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

/**
 * Class Definition: AI Object, inherits behaivours and methods from Player class.
 */


import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class AI extends Player {

    int communityCardCount = 0;
    int anaskm = 0;
    int coef = 1758;
    public String action = "";
    public int raiseCount = 0;
    private boolean canChange = true;


    //Constructor. Gets amount of chips and the AI name, passes it onto the super class
    public AI(int r, int blu, int g, int bla, int w, String name) {
        super(r, blu, g, bla, w, name);
        isAI = true;
    }

    //Analyse current card selections.
    public int analyze(Card[] communityCards) {
        //System.out.println("AI: hmmmm, you chose poorly.");
        int strength = 0; // 0-100

        //Count currently available community cards
        for (int i = 0; i < communityCards.length; i++) {
            if (communityCards[i] != null) {
                communityCardCount++;
            }
        }

        //Create a new collection with hand cards and community cards
        Card[] cardCollection = uniteAICards(communityCards, communityCardCount);

        int coef = 0;

        //Depending on how many cards are open in the middle
        //For none open, goto zeroCard method
        if (communityCardCount == 0) {
            coef = 100 / 4;
            strength = coef * zeroCard(cardCollection);
        }
        //For three open, goto threeCard method
        else if (communityCardCount == 3) {
            coef = 100 / 7;
            strength = coef * threeCard(cardCollection);
        }
        //For four open, goto fourCard method
        else if (communityCardCount == 4) {
            coef = 100 / 8;
            strength = coef * fourCard(cardCollection);
        }
        //For five open, goto fiveCard method
        else if (communityCardCount == 5) {
            coef = 100 / 9;
            strength = coef * fiveCard(communityCards);
        }

        //return how strong your card is.
        communityCardCount = 0;
        return strength;
    }

    //Combine what AI has on hand with available community cards and create a new array
    public Card[] uniteAICards(Card[] communityCards, int communityCardCount) {
        //System.out.println("Community cards length @AI: " + communityCards.length);
        Card[] cardCollection = new Card[2 + communityCardCount];
        cardCollection[0] = getHand()[0];
        cardCollection[1] = getHand()[1];

        for (int i = 2; i < 2 + communityCardCount; i++) {
            cardCollection[i] = communityCards[i - 2];
        }
        return sort(cardCollection);
    }


    /**
     * ALL CALCULATIONS FOR THE METHODS DEPEND ON CARD SUITS AND VALUES AND THEIR POSSIBILITES FOR A RESULT.
     */
    public int zeroCard(Card[] cards) {
        int str = 0;
        if (cards[0].getValue() == cards[1].getValue()) str += 4; //pair
        if (cards[0].getSuit() >= 11 || cards[1].getSuit() >= 11) str += 3; //face card
        if (cards[0].getValue() - cards[1].getValue() <= 4 && cards[0].getValue() - cards[1].getValue() > 0)
            str += 2; //consecutive possible straight
        if (cards[0].getSuit() == cards[1].getSuit()) str += 1; //same suit possible flush

        return str;
    }

    public int threeCard(Card[] cards) {
        int str = 0;
        //4 of a kind
        for (int i = 0; i < 2; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue() && cards[i + 1].getValue() == cards[i + 2].getValue() && cards[i + 2].getValue() == cards[i + 3].getValue()) {
                str += 7;
            }
        }

        //5 same suit
        if (cards[0].getSuit() == cards[1].getSuit() && cards[1].getSuit() == cards[2].getSuit()
                && cards[2].getSuit() == cards[3].getSuit() && cards[3].getSuit() == cards[4].getSuit()) {
            str += 6;
        }
        //4 same suit
        for (int i = 0; i < 2; i++) {
            if (cards[i].getSuit() == cards[i + 1].getSuit() && cards[i + 1].getSuit() == cards[i + 2].getSuit() && cards[i + 2].getSuit() == cards[i + 3].getSuit()) {
                str += 5;
            }
        }
        //3 of a kind
        for (int i = 0; i < 3; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue() && cards[i + 1].getValue() == cards[i + 2].getValue()) {
                str += 4;
            }
        }
        //2 pair
        int pair = 0;
        for (int i = 0; i < cards.length - 1; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue()) {
                pair += 1;
                if (cards[i].getValue() >= 10) {
                    str += 1;
                }
            }
        }

        if (pair == 2) {
            str += 3;
        }
        //consecutive
        int difference = cards[cards.length - 1].getValue() - cards[0].getValue();
        if (difference <= 5 && difference > 0) {
            str += 2;
        }

        //pair
        if (pair == 1) {
            str += 1;
        }
        //3 same suit
        for (int i = 0; i < 3; i++) {
            if (cards[i].getSuit() == cards[i + 1].getSuit() && cards[i + 1].getSuit() == cards[i + 2].getSuit()) {
                str += 1;
            }
        }
        return str;
    }

    public int fourCard(Card[] cards) {
        int str = 0;

        //4 of a kind
        for (int i = 0; i < 3; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue() && cards[i + 1].getValue() == cards[i + 2].getValue() && cards[i + 2].getValue() == cards[i + 3].getValue()) {
                str += 8;
            }
        }

        //5 same suit
        if (cards[0].getSuit() == cards[1].getSuit() && cards[1].getSuit() == cards[2].getSuit()
                && cards[2].getSuit() == cards[3].getSuit() && cards[3].getSuit() == cards[4].getSuit() && cards[4].getSuit() == cards[5].getSuit()) {
            str += 7;
        }

        //3 of a kind
        for (int i = 0; i < 4; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue() && cards[i + 1].getValue() == cards[i + 2].getValue()) {
                str += 6;
            }
        }

        //2 pair
        int pair = 0;
        for (int i = 0; i < cards.length - 1; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue()) {
                pair += 1;
            }
        }

        if (pair == 2) {
            str += 5;
        }

        //consecutive vol 1
        int difference = cards[cards.length - 1].getValue() - cards[0].getValue();
        if (difference <= 4 && difference > 0) {
            str += 4;
        }

        //4 same suit
        for (int i = 0; i < 3; i++) {
            if (cards[i].getSuit() == cards[i + 1].getSuit() && cards[i + 1].getSuit() == cards[i + 2].getSuit() && cards[i + 2].getSuit() == cards[i + 3].getSuit()) {
                str += 3;
            }
        }

        //consecutive vol 2
        difference = cards[cards.length - 1].getValue() - cards[0].getValue();
        if (difference == 5) {
            str += 2;
        }

        //pair
        if (pair == 1) {
            str += 1;
        }

        return str;
    }

    public int fiveCard(Card[] cards) {
        //System.out.println("Cards length @AI.fiveCard: " + cards.length);
        checkHand(cards);
        int str = points;
        return str;
    }

    /**
     * Deciding algorithm
     * @param strength strength of the current hand
     * @param requiredSpend how much can be spent in this hand
     * @return how much chips to throw in and the action to be taken from AI
     */
    public ArrayList<Chip> decide(int strength, int requiredSpend) {// strength between 0-100, getHighestPot() - getPotOf(AI)
        ArrayList<Chip> chips = new ArrayList<Chip>();
        boolean validPlay = false;
        if (getBudget() == 0) {
            validPlay = true;
        }
        while (!validPlay) {
            //System.out.println("Strength: " + strength );
            //For high-strength hands
            if (strength >= 55 && raiseCount <= 2) {
                //System.out.println("Raise count: " + raiseCount);
                int spend = Math.round(strength * getBudget() / coef);
                //If we have more than requiredspend, raise.
                if (spend + requiredSpend <= getBudget()) {
                    if (canChange)
                        action = "Raised "+Math.round((spend / 10) * 10)+"$";
                    System.out.println("AI raised: " + Math.round(spend / 10) * 10);
                    chips = raise(Math.round(spend / 10) * 10, requiredSpend);
                    raiseCount++;
                    validPlay = true;
                }
                //If we have just enough, go all in.
                else if (requiredSpend <= getBudget()) {
                    if (canChange)
                        action = "All in";
                    allIn = true;
                    chips = raise(getBudget() - requiredSpend, requiredSpend);
                    raiseCount++;
                    System.out.println("AI all in, raised: " + (getBudget() - requiredSpend));
                    validPlay = true;
                }
                //Go all in if requiredSpend is smaller than the budget.
                else if (requiredSpend > getBudget()) {
                    if (canChange)
                        action = "All in";
                    allIn = true;
                    chips = call(getBudget());
                    System.out.println("AI all in, called: " + getBudget());
                    validPlay = true;
                }
            }
            //For mid -strength hands.
            else if (strength <= 25 && requiredSpend >= (getBudget() / 4)) {

                Random rng = new Random();
                double random = rng.nextDouble();

                if (random < 0.7) {
                    if (canChange) {
                        action = "Folded"
                        ;
                        canChange = false;
                    }
                    fold();
                    System.out.println("AI folded");
                    validPlay = true;
                } else {
                    if (canChange)
                        action = "Called";
                    chips = call(requiredSpend);
                    System.out.println("AI called: " + requiredSpend);
                    validPlay = true;
                }

            }
            //For low strength hands
            else {
                //System.out.println("Can't decide what to do");
                if (check(requiredSpend)) {
                    if (canChange)
                        action = "Checked";
                    System.out.println("AI checked");
                    validPlay = true;
                } else {
                    if (requiredSpend < getBudget()) {
                        if (canChange)
                            action = "Called "+requiredSpend+"$";
                        chips = call(requiredSpend);
                        System.out.println("AI called: " + requiredSpend);
                        validPlay = true;
                    } else if (requiredSpend >= getBudget()) {
                        action = "All in";
                        allIn = true;
                        chips = call(getBudget());
                        System.out.println("AI is all in with: " + getBudget());
                        validPlay = true;
                    }
                }
            }
        }
        return chips;
    }

    //Play the chips for the AI
    public ArrayList<Chip> play(Card[] communityCards, int requiredSpend) {
        ArrayList<Chip> chips = new ArrayList<Chip>();
        chips = decide(analyze(communityCards), requiredSpend);
        return chips;
    }


    //Getter and setter methods, pretty self explainatory.
    public String getAction() {
        return action;
    }

    public void setChange(boolean b) {
        canChange = b;
    }

    public void setRaiseCount(int set) {
        raiseCount = set;
    }

}