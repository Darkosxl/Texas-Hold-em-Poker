//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020
import java.util.*;
//Class Definition: Keeps a track of the cards opened at the middle of the desk.
public class CommunityCards{

  Card[] communityCards;
  int index = 0;
  
  public CommunityCards(){
   communityCards = new Card[5];
  }

  //Add a card to the array, increment available card #
  public void add(Card card){
    communityCards[index]= card;
    index ++;
  }

  //Return what cards we have for the community
  public Card[] display(){
    return communityCards;
  }

  //Reset the community cards for the next game.
  public void reset() {
    for (int i = 0; i < 5; i++) {
      communityCards[i] = null;
    }
    index = 0;
  }

}