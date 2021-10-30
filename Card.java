//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020


import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

//Class definition: Card object class
public class Card implements Comparable<Card> {
  
  private int suit; // Numerical suit values 1-4
  private int value; // Numerical card values 2-14 (J --> 11, Q --> 12, K --> 13, A --> 14)
  
  //Constructor with two integer parameters, s is for suit, v is for value
  public Card(int s, int v) {
    suit = s;
    value = v;
  }
  
  //Method that returns the value
  public int getValue() {
    return value;
  }
  
  //method that returns the suit
  public int getSuit() {
    return suit;
  }
  
  //Compares this card object to another card object
  public int compareTo(Card c) {
    int result = this.getValue() - c.getValue();
    
    if (result == 0) {
      result = this.getSuit() - c.getSuit(); //if the cards have the same value
    }
    return result;
  }
  
  //This method returns the card value as a string
  public String toString() {
    String resultCard = "";
    String cardSuit = "";
    String cardValue = "";
    
    if (suit == 1) {
      cardSuit = "clubs";
    }
    else if (suit == 2) {
      cardSuit = "diamonds";
    }
    else if (suit == 3) {
      cardSuit = "hearts";
    }
    else {
      cardSuit = "spades";
    }
    if (value > 1 && value < 11) {
      cardValue = Integer.toString(value);
    }
    else if (value == 14) {
      cardValue = "Ace";
    }
    else if (value == 11) {
      cardValue = "Jack";
    }
    else if (value == 12) {
      cardValue = "Queen";
    }
    else if (value == 13) {
      cardValue = "King";
    }
    resultCard = cardValue + " of " + cardSuit;
    return resultCard;
  }
  
  //returns true if the cards in comparison have the same suit
  public boolean sameSuit(Card c) {
    return this.getSuit() == c.getSuit();
  }
  
  //returns true if the cards in comparison have the same value
  public boolean sameValue(Card c) {
    return this.getValue() == c.getValue();
  }
  
  //returns the difference between the values of the cards in comparison
  public int valueDiff (Card c) {
    return this.getValue() - c.getValue();
  }

  //returns the file name of the card
  public Image getImg() throws IOException {
    String img = "";
    String cardName = toString();
    
    //switch statement that compares the string values of the cards 
    switch (cardName) {
      case "Ace of spades": 
        img = "SA";
        break;
      case "Ace of diamonds":
        img = "DA";
        break;
      case "Ace of hearts":
        img = "HA";
        break;
      case "Ace of clubs":
        img = "CA";
        break;
        
      case "2 of spades": 
        img = "S2";
        break;
      case "2 of diamonds":
        img = "D2";
        break;
      case "2 of hearts":
        img = "H2";
        break;
      case "2 of clubs":
        img = "C2";
        break;
        
      case "3 of spades": 
        img = "S3";
        break;
      case "3 of diamonds":
        img = "D3";
        break;
      case "3 of hearts":
        img = "H3";
        break;
      case "3 of clubs":
        img = "C3";
        break;
      case "4 of spades": 
        img = "S4";
        break;
      case "4 of diamonds":
        img = "D4";
        break;
      case "4 of hearts":
        img = "H4";
        break;
      case "4 of clubs":
        img = "C4";
        break;
      case "5 of spades": 
        img = "S5";
        break;
      case "5 of diamonds":
        img = "D5";
        break;
      case "5 of hearts":
        img = "H5";
        break;
      case "5 of clubs":
        img = "C5";
        break;
      case "6 of spades": 
        img = "S6";
        break;
      case "6 of diamonds":
        img = "D6";
        break;
      case "6 of hearts":
        img = "H6";
        break;
      case "6 of clubs":
        img = "C6";
        break;
      case "7 of spades": 
        img = "S7";
        break;
      case "7 of diamonds":
        img = "D7";
        break;
      case "7 of hearts":
        img = "H7";
        break;
      case "7 of clubs":
        img = "C7";
        break;
      case "8 of spades": 
        img = "S8";
        break;
      case "8 of diamonds":
        img = "D8";
        break;
      case "8 of hearts":
        img = "H8";
        break;
      case "8 of clubs":
        img = "C8";
        break;
      case "9 of spades": 
        img = "S9";
        break;
      case "9 of diamonds":
        img = "D9";
        break;
      case "9 of hearts":
        img = "H9";
        break;
      case "9 of clubs":
        img = "C9";
        break;
      case "10 of spades": 
        img = "S10";
        break;
      case "10 of diamonds":
        img = "D10";
        break;
      case "10 of hearts":
        img = "H10";
        break;
      case "10 of clubs":
        img = "C10";
        break;
      case "Jack of spades": 
        img = "SJ";
        break;
      case "Jack of diamonds":
        img = "DJ";
        break;
      case "Jack of hearts":
        img = "HJ";
        break;
      case "Jack of clubs":
        img = "CJ";
        break;
      case "Queen of spades": 
        img = "SQ";
        break;
      case "Queen of diamonds":
        img = "DQ";
        break;
      case "Queen of hearts":
        img = "HQ";
        break;
      case "Queen of clubs":
        img = "CQ";
        break;
      case "King of spades": 
        img = "SK";
        break;
      case "King of diamonds":
        img = "DK";
        break;
      case "King of hearts":
        img = "HK";
        break;
      case "King of clubs":
        img = "CK";
        break;
      default: 
        img = null;
        
    }
    return ImageIO.read(new File(img + ".png"));
  }
  
  
}