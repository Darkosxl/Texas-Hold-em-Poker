//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

/*
Class Definition: Deck object is being used to keep a track of the playing cards that are available to the players.
 */

public class Deck {
  
  private Card[] cards; //Array of cards to keep track of it.
  private int top; //index of the top card
  
  public Deck() {
    int numCards = 0; //the index of the cards array
    Card card;
    cards = new Card[52];
    
    //initiates the deck
    //First for loop is used for 4 suits
    for (int i = 1; i < 5; i++) {
      //Second for loop is used for 13 cards, with A = 14, 2 = 2 etc.
      for (int j = 2; j < 15; j++) {
        card = new Card(i,j); //creates 52 card objects and stores them in the card array, cards
        cards[numCards] = card;
        numCards++;
      }
    }
    top = 0; //top card at index zero when the deck is first created
    shuffleDeck(); //Shuffle the deck to make sure it's not ordered
  }

  //Getter method that returns the topmost card's index.
  public int getTop() {
    return top;
  }
  
  //Method that shuffles the "Deck", a.k.a. the Cards array.
  public void shuffleDeck() {
    Card tempCard; //this is a temporary card object

    //1000 is being used as an arbitary number to make sure that the deck is well-mixed.
    for (int i = 1; i < 1000; i++) {
      int rndm = (int) (Math.random() * 52); //generate a random number valid within the deck
      tempCard = cards[0]; //replace the top card with a random card in the deck 999 times
      cards[0] = cards[rndm]; 
      cards[rndm] = tempCard;
    }
  }

  //Method that distributes cards to each players within the passed array
  public void dealHands(Player[] players) {
    //Do two rounds of distribution
    for (int i = 0; i < 2; i++) {
      for (Player player: players) {
        //For each player in the array, if playing, give them a card.
        if (player.playing) {
          player.getHand()[i] = dealCard();
        }
      }
    }
  }
  
  //Method that returns the topmost card of the array. Note that a card that is given is NOT removed
  //from the array, thus there's a need to keep track of the topcard.
  public Card dealCard() 
  {
    //If we're exceeding the array, generate an empty card (Signifies the end of game)
    if (top > 51) {
      top = 0;
      return new Card(0,0);
    }
    //Else, give them the topmost card.
    else {
      top++;
      return cards[top - 1];
    }
  }
}