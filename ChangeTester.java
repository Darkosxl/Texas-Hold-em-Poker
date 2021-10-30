import java.util.ArrayList;

public class ChangeTester {
  
  public static void main(String[] args) {
    boolean cont = true;
    Common c = new Common();
    
    AI ai1 = new AI(300,100,100,100,60);
    AI ai2 = new AI(300,100,100,100,60);
    AI ai3 = new AI(300,100,100,100,60);
    Player p1 = new Player(300,100,100,100,60,"devil");
    Player[] players = new Player[4];
    ArrayList<Player> playersInGame;
    int playingCount = 0;
    boolean stop = false;
    players[0] = p1;
    players[1] = (AI) ai1;
    players[2] = (AI) ai2;
    players[3] = (AI) ai3;
    
    Game game = new Game(players);
    while (!stop) {
      if (game.playersAtDesk() > 1) {
        
        for (int i = 1; i < players.length; i++) {
          ((AI)game.playerArray[i]).setRaiseCount(0);
        }
        
        playersInGame = new ArrayList<Player>();
        for(int i = 0; i < players.length; i++){
          if(players[i].playing){
            playersInGame.add(players[i]);
          }
        }
        if(game.bbIndex >= playersInGame.size()){
          game.bbIndex=0;
        }
        if(game.sbIndex >= playersInGame.size()){
          game.sbIndex=0;
        }
        game.payBlinds(playersInGame,game.bigBlind, game.smallBlind, game.bbIndex, game.sbIndex);
        Common.println("Small blind betted by player " + (game.sbIndex + 1));
        Common.println("Big blind betted by player " + (game.bbIndex + 1));
        game.bbIndex++;
        game.sbIndex++;
        
        //game.handleBlinds();
        
        while(cont) {
          playingCount = 0;
          //Prints player's cards
          
          //prints cards on table
          int cardNo = game.comCards.index;
          c.println("Cards on table: ");
          for(int i = 0; i<cardNo; i++){
            try {
              c.println(game.comCards.display()[i].toString());
            }
            catch (Exception e) {}
          }
          
          //Loops through players to make them play.
          for(int i = 0; i < game.playerArray.length; i++){
            if(i==0 && game.playerArray[i].playing){
              c.println("Your cards: " + game.playerArray[0].getHand()[0].toString() + ", " + game.playerArray[0].getHand()[1].toString());
              playingCount++;
              ArrayList<Chip> playerPot = game.playerArray[i].play(game.pot);
              game.pot.addToPot(playerPot, game.playerArray[i]);
              Common.println("Budget:" + game.playerArray[i].getBudget());
              game.playNumber +=1;
              game.nextRound();
              if(game.gameEnded) {
                cont = false;  
                break;
              }
            }
            else{
              if(game.playerArray[i].playing){
                playingCount++;
                //Common.println("Highest POT: " + game.pot.potValue(game.pot.getHighestPot()));
                //Common.println("Get pot of ai: "+game.pot.potValue(game.pot.getPotOf(game.playerArray[i])) );
                ArrayList<Chip> aiPot = ((AI)game.playerArray[i]).play(game.comCards.display(), game.pot.potValue(game.pot.getHighestPot()) - game.pot.potValue(game.pot.getPotOf(game.playerArray[i])));
                //Common.println("Pot of AI:" +  game.pot.potValue(aiPot));
                game.pot.addToPot(aiPot, game.playerArray[i]);
                game.playNumber +=1;
                game.nextRound();
                if(game.gameEnded){
                  cont = false;
                  break;
                }
              }
            }
            //Advances to next round if applicable
            
          }
          
        }
      }
      else {
        stop = true;
      }
      game.resetGame();
      cont = true;
      
      
    }
  }
  
  
  
  
}