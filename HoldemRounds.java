//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020
public class HoldemRounds {
    private Player[] players;
    private String[] names;
    private Game game;
    private int whosplayin;
    private int rounds;
    //Class Description: Keeps the track of who is playing next, when the amountofplayers and the array of names are given.
    public HoldemRounds(int amountoplayers, String[] names) {
        //In first round, first player is playing.
        rounds = 1;
        whosplayin = 0;
        names = names;
        players = new Player[amountoplayers];
        for (int i = 0; i < players.length; i++) {
            //All players start with the same chip #
            players[i] = new Player(300, 100, 100, 100, 50, names[i]);
        }
        //Create a new game.
        game = new Game(players);
    }

    //When a player is ready, set that player as ready to play the game.
    public void ready(int playernum, boolean ready) {
        players[playernum].setReady(ready);
    }

    //Check if all is ready to start the game
    public boolean startgame(int playernum) {
        boolean everybodyready = true;
        for (int i = 0; i < players.length; i++)
            //Check for all players, if they are all ready
            everybodyready = players[i].ifReady();
        //If they are, and the request is made from the first player, we are ready to go
        if (playernum == 1 && everybodyready) {
            return true;
        } else {
            System.out.println("You can not start the game, not everybody is ready!!");
            return false;
        }
    }

    //Removes a user from server
    public void leaveServer(int playernum) {
        players[playernum].setInServer(false);
        players[playernum].playing = false;
    }


    //Next 3 methods work as same:
    // 1) Call intended method
    // 2) Modify who's playing next
    // 3) If back to start, increment the # of rounds.
    public void raise(int playernum, int raise, int bet) {
        players[playernum].raise(raise, bet);
        if (whosplayin == players.length - 1) {
            whosplayin = 0;
            rounds++;
        } else
            whosplayin++;
    }

    public void fold(int playernum) {
        players[playernum].fold();

        if (whosplayin == players.length - 1) {
            whosplayin = 0;
            rounds++;
        } else
            whosplayin++;
    }

    public void call(int playernum) {
        if (whosplayin == players.length - 1) {
            whosplayin = 0;
            rounds++;
        } else
            whosplayin++;
    }

    //Checking method. You must play at least 2 rounds to check.
    public void check(int playernum) {
        if (rounds >= 2) {
            if (whosplayin == players.length - 1) {
                whosplayin = 0;
                rounds++;
            } else
                whosplayin++;
        } else {
            System.out.println("TO EARLY TO CHECK, YOU MUST WAIT FOR AT LEAST THE SECOND ROUND OF BETTING");
        }
    }
}
