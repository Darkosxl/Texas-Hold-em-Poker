//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

//Class definition: Main frame view controller
public class HoldemFrame extends JFrame {

    //Different components of the frame
    private HoldemMenu menu;
    private HoldemRaise raiser;
    private HoldemJoin connector;
    private HoldemCreator creator;

    private int screen;
    private int menuchooser;
    private boolean firsttime;

    public HoldemFrame() {
        //Set simple settings, prepare panels
        menuchooser = 1;
        firsttime = true;
        screen = 1;
        this.setSize(1920, 1080);
        menu = new HoldemMenu(screen, 5, "Warro");
        menu.addMouseListener(new HoldemListener());
        this.add(menu);
        raiser = new HoldemRaise(menu.getP1Budget(), 100);
        connector = new HoldemJoin();
        creator = new HoldemCreator();
        connector.addWindowListener(new ButtonListener());
        creator.addWindowListener(new ButtonListener());
        raiser.addWindowListener(new ButtonListener());
  /*
  menu.setLayout(new GridLayout(4,1));
  JPanel fill1 = new JPanel();
  JPanel fill2 = new JPanel();
  JPanel fill3 = new JPanel();
  JPanel fill4 = new JPanel();
  

  JPanel fill5 = new JPanel();
  JPanel fill6 = new JPanel();
  JPanel fill7 = new JPanel();
  JPanel fill8 = new JPanel();
  JPanel fill9 = new JPanel();
  JPanel fill10 = new JPanel();
  
  fill1.setVisible(false);
  fill3.setVisible(false);
  fill5.setVisible(false);
  fill7.setVisible(false);
  fill8.setVisible(false);
  fill10.setVisible(false);
  
  fill2.add(fill5);
  fill2.add(fill6);
  fill2.add(fill7);
  
  fill4.add(fill8);
  fill4.add(fill9);
  fill4.add(fill10);
  
  
  
  fill2.setLayout(new GridLayout(1,3));
  fill4.setLayout(new GridLayout(1,3));
  
  JTextArea portbox = new JTextArea();
  JTextArea adressbox = new JTextArea();
  fill6.add(adressbox);
  fill10.add(portbox);
  
            
  adressbox.setBackground(menu.getBackground());
  portbox.setBackground(menu.getBackground());
//textarea.setVisible(false);
  menu.add(fill1);
  menu.add(fill2);
  menu.add(fill3);
  menu.add(fill4);
  */
    }

    class ButtonListener implements WindowListener {
        public void windowDeactivated(WindowEvent we) {
        }

        public void windowActivated(WindowEvent we) {
        }

        public void windowDeiconified(WindowEvent we) {
        }

        public void windowIconified(WindowEvent we) {
        }

        public void windowOpened(WindowEvent we) {
        }

        public void windowClosing(WindowEvent we) {
        }

        //If the windows is closed for the frame
        public void windowClosed(WindowEvent we) {
            //If raiser is closed, set raised amount as the last inputted amount and raise it automatically. Play the round for all players

            if (we.getSource() == raiser) {
                menu.setRaisedAmount(raiser.returnRaise());
                menu.setAction("raise");
                if (raiser.getDecision())
                    menu.playOneRound();
                HoldemFrame.this.repaint();
            }
            //If the server connection window is closed, connect to the server and close the window
            //NOT IN USE AS SERVER CONNECTION IS NOT ESTABLISHED
            if (we.getSource() == connector) {
                if (connector.ifEnabled()) {
                    final int HOLDEM_JOIN = connector.getPort();
                    final String NAME = connector.getName();
//TODO : figure out how to get the number of players expected from the main server
                    //Oh and, do the server room please??
//cnmenu = new ClientMenu = (6,,HOLDEM_JOIN,NAME);
                    HoldemFrame.this.remove(menu);

                    menuchooser = 3;
                }

            }

            //If server creater is closed, just create a server.
            if (we.getSource() == creator) {
                System.out.println("Uh???");
                System.out.println("Uh???");
                System.out.println("Uh???");
                if (creator.ifEnabled()) {
                    try {

                        final int PLAYER_AMOUNT = creator.getPlayers();
                        int playernum = 1;
                        final int HOLDEM_PORT = creator.getPort();
                        final String NAME = creator.getName();

                        ServerSocket server = new ServerSocket(HOLDEM_PORT);
                        System.out.println("Waiting for players to join");

                        menuchooser = 2;
                        //svmenu.setScreen(7);
                        //HoldemFrame.this.repaint();
                        //Now 'fore you rage and regret brother
                        //realize that you need to open the server from the game object
                        // otherwise the while loop screws over the game itself real bad
                        // you do you, just do this, game.openBeacon(port,playeramount,name(??));
                        //then repaint this screwer, you can even probably get rid of servermenu and clientmenu
                        //rather clientgame and servergame
                        while (true && playernum <= PLAYER_AMOUNT) {
                            try (Socket s = server.accept()) {
                                System.out.println("Player " + playernum + " connected.");
                                playernum++;
                            }
                            playernum++;
                            System.out.println("in finite loop");
                        }
                        System.out.println("I'm done with the server");

                    } catch (IOException ioe) {
                        System.out.println("The port is not available");
                    }
                }
            }
        }
    }

    //Mouse Menu Setting Listener
    class HoldemListener implements MouseListener {
        public void mouseExited(MouseEvent me) {
        }

        public void mouseEntered(MouseEvent me) {
            // if(screen <5)
            menu.checkPos(me.getX(), me.getY());
            HoldemFrame.this.repaint();
        }

        //Depending on the x and y-pos clicked, switch into requested menus.
        public void mouseClicked(MouseEvent me) {
            // if(screen < 5)
            System.out.println("Mouse pressed: " + me.getX() + " y: " + me.getY());
            if (menuchooser == 1)
                menu.pressOption(me.getX(), me.getY());

            screen = menu.getScreen();

            //Raise menu
            if (menu.holdemraise()) {
                raiser.setVisible(true);
            }
            //Join server menu
            else if (menu.holdemjoin()) {
                connector = new HoldemJoin();
                connector.addWindowListener(new ButtonListener());
                connector.setVisible(true);
            }
            //Create server menu
            else if (menu.holdemcreate()) {
                creator = new HoldemCreator();
                creator.addWindowListener(new ButtonListener());
                creator.setVisible(true);
            }
            //Catch*all
            else {
                raiser.setVisible(false);
                connector.setVisible(false);
                creator.setVisible(false);
            }
            raiser.setMax(menu.getP1Budget());
            raiser.setMin(100);
            HoldemFrame.this.repaint();
        }

        public void mousePressed(MouseEvent me) {
        }

        public void mouseReleased(MouseEvent me) {
        }
    }

}
