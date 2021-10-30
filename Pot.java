//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

import java.util.ArrayList;

public class Pot {

    private Player[] players;
    private Chip[] pot = {new Chip(0, 10), new Chip(0, 20), new Chip(0, 50), new Chip(0, 100), new Chip(0, 500)};
    private ArrayList<ArrayList<Chip>> individualPots;

    public Pot(Player[] players) {
        this.players = players;
        individualPots = new ArrayList<ArrayList<Chip>>();
        setUpPotList();
    }

    //If game requires splitting the pot, this will calculate the amount to give to each user
    public int splitPot(int toSplit) {
        int total = totalPot();
        int eachSplit = Math.round(total / toSplit);
        System.out.println("Each split is " + eachSplit);
        return eachSplit;

    }

    //returns pot value
    public int potValue(ArrayList<Chip> pot) {
        int p = 0;
        for (int i = 0; i < pot.size(); i++) {
            p += pot.get(i).totalValue();
        }
        return p;
    }


    //Just a checker method.
    public boolean advance() {
        for (int i = 0; i < individualPots.size(); i++) {
            for (int j = 0; j < individualPots.size(); j++) {
                if (potValue(individualPots.get(i)) != potValue(individualPots.get(j)) && players[i].playing && players[j].playing && !players[i].allIn && !players[j].allIn) {
                    return false;
                }

            }
        }
        return true;
    }

    //Returns the highest pot put in.
    public ArrayList<Chip> getHighestPot() {
        ArrayList<Chip> highest = individualPots.get(0);

        for (int i = 0; i < individualPots.size(); i++) {
            if (potValue(individualPots.get(i)) > potValue(highest)) {
                highest = individualPots.get(i);
            }
        }
        return highest;
    }

    //Get back the pot put in from a specific player.
    public ArrayList<Chip> getPotOf(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (player == players[i]) {
                //System.out.println("aligned index in getPotOf method: " + i);
                return individualPots.get(i);
            }
        }
        Common.println("No such player dude");
        return null;
    }

    //initialize individual pot lists for each player and initialize those lists with empty chips as well.
    public void setUpPotList() {
        for (int i = 0; i < players.length; i++) {
            ArrayList<Chip> indPot = new ArrayList<Chip>();
            indPot.add(new Chip(0, 10));
            indPot.add(new Chip(0, 20));
            indPot.add(new Chip(0, 50));
            indPot.add(new Chip(0, 100));
            indPot.add(new Chip(0, 500));
            individualPots.add(indPot);
        }

    }

    //Given the individual pot and to be updated one, update it from the list.
    public void individualPotFiller(ArrayList<Chip> individualPot, ArrayList<Chip> update) {
        try {
            for (int i = 0; i < individualPot.size(); i++) {
                individualPot.get(i).addChips(update.get(i).getNumber());
                //System.out.println("indPotFiller'daki update.get(i).getNumber() = " + update.get(i).getNumber());
            }
        } catch (Exception exc) {
            Common.println("No chips potted");
        }
    }

    //Add a given pot to player's pot.
    public void addToPot(ArrayList<Chip> playerPot, Player player) {
        //Get the player pot
        for (int i = 0; i < players.length; i++) {
            if (player == players[i]) {
                individualPotFiller(individualPots.get(i), playerPot);
            }
        }
        //For all values, update the #Chips
        for (int i = 0; i < playerPot.size(); i++) {
            for (int j = 0; j < pot.length; j++) {
                if (pot[j].value == playerPot.get(i).value) {
                    pot[j].addChips(playerPot.get(i).getNumber());
                }
            }
        }
    }

    //Return total pot
    public int totalPot() {
        int total = 0;
        for (int i = 0; i < pot.length; i++) {
            total += pot[i].totalValue();
        }
        return total;
    }

    //resets all individual pots in the main pot.
    public void resetPot() {
        for (int i = 0; i < pot.length; i++) {
            pot[i].removeChips(pot[i].getNumber());
        }
        for (int i = 0; i < individualPots.size(); i++) {
            for (int j = 0; j < individualPots.get(i).size(); j++) {
                individualPots.get(i).get(j).removeChips(individualPots.get(i).get(j).getNumber());
            }
        }
    }

}