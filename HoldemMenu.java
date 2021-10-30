//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Font;
import java.awt.Color;

//Class Description: Main Menu view controller
public class HoldemMenu extends JPanel {
    //private File mainmenupath;
    //private File gamemenupath;
    //private File[] mainmenuf;
    //private File[] gamemenuf;
    private BufferedImage[] mainmenu;
    private BufferedImage[] gamemenu;
    private BufferedImage[] multimenu;
    private BufferedImage[] servermenu;
    private BufferedImage[] createmenu;
    private BufferedImage[] joinmenu;
    private BufferedImage[] pokertable;
    private BufferedImage[] pokermoney;
    private BufferedImage[] pokerchips;

    private BufferedImage[] chipos;
    private ArrayList<BufferedImage> hands;
    private ArrayList<BufferedImage> comcards;
    private ArrayList<BufferedImage> currentmoniesp1;
    private ArrayList<BufferedImage> currenttotalmonies;
    private BufferedImage[] musicstuff;
    private BufferedImage[] borders;
    private BufferedImage[] cc;
   
  
    private boolean firstact = false;
    private boolean leftgame = false;
    private boolean promptendfeatures = false;
    private int ds;
    BufferedImage menubackground;
    BufferedImage title;
    Image scaled;


    private int screen;
    private int sp;
    private int xTitle;
    private int playeramount;
    private boolean singleplayer;
    private boolean openholdemraise;
    private boolean openholdemjoin;
    private boolean openholdemcreate;
    private BufferedImage winbox1;
    private BufferedImage closedcard;
    private BufferedImage winbox2;
    private BufferedImage winbox3;
    private BufferedImage potframe;
    private BufferedImage pot;
    private Game game;
    private boolean start;
    private Player[] players;
    private Player p1;
    private String action;
    private String smoney;
    private int raise;
    private String p1name;
    private boolean canCheck;
    private boolean musicon;

    public HoldemMenu(int screen, int playeramount, String p1name) {
        this.p1name = p1name;
        borders = new BufferedImage[5];
        canCheck = false;
        

        //Pull the background images
        try {
            borders[4] = ImageIO.read(new File("bronzeborder.png"));
            borders[3] = ImageIO.read(new File("silverborder.png"));
            borders[2] = ImageIO.read(new File("goldborder.png"));
            borders[1] = ImageIO.read(new File("diamondborder.png"));
            borders[0] = ImageIO.read(new File("royalframe.png"));
            winbox1 = ImageIO.read(new File("winnerbox1.png"));
            winbox2 = ImageIO.read(new File("winnerbox2.png"));
            winbox3 = ImageIO.read(new File("winnerbox3.png"));
            closedcard = ImageIO.read(new File("back.png"));
            pot = ImageIO.read(new File("pot.png"));
            potframe = ImageIO.read(new File("potframe.png"));
        } catch (IOException ie) {
            System.out.println("Some problems with the winboxes");
        }
        ds = 0;
        start = true;
        //Render images
        mainmenu = new BufferedImage[6];
        gamemenu = new BufferedImage[6];
        multimenu = new BufferedImage[10];
        servermenu = new BufferedImage[14];
        createmenu = new BufferedImage[10];
        joinmenu = new BufferedImage[10];
        pokertable = new BufferedImage[30];
        pokermoney = new BufferedImage[20];
        musicstuff = new BufferedImage[2];

        p1 = new Player(300, 100, 100, 100, 50, p1name);

        chipos = new BufferedImage[30];
        comcards = new ArrayList<BufferedImage>();
        currentmoniesp1 = new ArrayList<BufferedImage>();
        currenttotalmonies = new ArrayList<BufferedImage>();
        singleplayer = true;

        //Render pathways for image files for menus
        File mainmenupath = new File("a)main menu");
        File gamemenupath = new File("b)game menu");
        File multipath = new File("c)multi menu 2");
        File joinpath = new File("d)Join server");
        File serverpath = new File("f)Server room");
        File tablepath = new File("poker table");
        File settingspath = new File("settings menu");
        File createpath = new File("e)Create server");
        File moneypath = new File("pokermoney");
        File musicpath = new File("music");
        
        File[] mainmenuf = mainmenupath.listFiles();
        File[] gamemenuf = gamemenupath.listFiles();
        File[] multif = multipath.listFiles();
        File[] joinf = joinpath.listFiles();
        File[] serverf = serverpath.listFiles();
        File[] tablef = tablepath.listFiles();
        File[] settingsf = settingspath.listFiles();
        File[] createf = createpath.listFiles();
        File[] moneyf = moneypath.listFiles();
        File[] musicf = musicpath.listFiles();
        
        Arrays.sort(mainmenuf);
        Arrays.sort(gamemenuf);
        Arrays.sort(multif);
        Arrays.sort(joinf);
        Arrays.sort(serverf);
        Arrays.sort(tablef);
        //Arrays.sort(settingsf);
        Arrays.sort(createf);
        Arrays.sort(moneyf);
        Arrays.sort(musicf);
        
        this.screen = screen;
        //screen 1: Main menu
        //screen 2: singleplayer
        //screen 3: multiplayer
        //screen 4: join/create server
        //screen 5: join server
        //screen 6: create server
        //screen 7: server room
        xTitle = 850;

        //Background filler
        menubackground = null;
        try {
            for (int i = 0; i < mainmenuf.length; i++) {
                if (mainmenuf[i].isFile()) {
                    mainmenu[i] = ImageIO.read(mainmenuf[i]);
                }
            }
            for (int i = 0; i < gamemenuf.length; i++) {
                if (gamemenuf[i].isFile())
                    gamemenu[i] = ImageIO.read(gamemenuf[i]);
            }
            for (int i = 0; i < multif.length; i++) {
                if (multif[i].isFile())
                    multimenu[i] = ImageIO.read(multif[i]);
            }
            for (int i = 0; i < joinf.length; i++) {
                if (joinf[i].isFile())
                    joinmenu[i] = ImageIO.read(joinf[i]);
            }
            for (int i = 0; i < serverf.length; i++) {
                if (serverf[i].isFile())
                    servermenu[i] = ImageIO.read(serverf[i]);
            }
            for (int i = 0; i < tablef.length; i++) {
                if (tablef[i].isFile())
                    pokertable[i] = ImageIO.read(tablef[i]);
            }
            for (int i = 0; i < createf.length; i++) {
                if (createf[i].isFile())
                    createmenu[i] = ImageIO.read(createf[i]);
            }
            for (int i = 0; i < moneyf.length; i++) {
                if (moneyf[i].isFile())
                    pokermoney[i] = ImageIO.read(moneyf[i]);
            }
            for(int i = 0; i < musicf.length; i++)  {
                if (musicf[i].isFile())
                    musicstuff[i] = ImageIO.read(musicf[i]);
            }


            sp = 1200;
        } catch (IOException e) {

        }
        this.setUpGame();
    }
    //Paints texts and images
    public void paintComponent(Graphics g) {

        //main menu
        if (screen == 1) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 1:
                        x = 900;
                        y = 260;
                        break; //King's Casino title text
                    case 2:
                        x = sp;
                        y = 430;
                        break; // Sinlgeplayer text
                    case 3:
                        y = 520;
                        break; // multiplayer text
                    case 4:
                        x = sp - 50;
                        y = 590;
                        break; // settings text
                    case 5:
                        x = sp - 50;
                        y = 575;
                        break;// quit text
                    case 6: 
                        x = sp - 350;
                        y = 620;
                        break;// change music text
                    case 7:
                        x = sp - 350;
                        y = 690;
                        break;// turn music on/off text
                }
                if (i != 4 && i < 6)
                    g.drawImage(mainmenu[i], x, y, null);
                else if (i == 6)
                    g.drawImage(musicstuff[0],x,y,null);
                else if (i == 7)
                    g.drawImage(musicstuff[1],x,y,null);
            }
        }
        //singleplayer menu
        else if (screen == 2) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < gamemenu.length; i++) {
                switch (i) {
                    case 1:
                        x = sp - 100;
                        y = 260;
                        break; //Games title text
                    case 2:
                        x = sp - 140;
                        y = 430;
                        break; // Texas Holdem text
                    case 3:
                        x = sp - 126;
                        y = 500;
                        break; // Blackjack text
                    case 4:
                        x = sp - 150;
                        y = 595;
                        break; // 1984 tetris text
                    case 5:
                        x = sp - 150;
                        y = 660;
                        break;// main menu text
                }
                g.drawImage(gamemenu[i], x, y, null);
            }
        }
        //multiplayer menu
        else if (screen == 3) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < gamemenu.length; i++) {
                switch (i) {
                    case 1:
                        x = sp - 100;
                        y = 260;
                        break; //Games title text
                    case 2:
                        x = sp - 140;
                        y = 430;
                        break; // Texas Holdem text
                    case 3:
                        x = sp - 126;
                        y = 500;
                        break; // Blackjack text
                    case 5:
                        x = sp - 150;
                        y = 660;
                        break;// main menu text
                }
                if (i != 4)
                    g.drawImage(gamemenu[i], x, y, null);
            }
            Graphics2D g2 = (Graphics2D) g;
        }
        //join or create a server screen
        else if (screen == 4) {
            int x = 0;
            int y = 0;
            for (int d = 0; d < multimenu.length; d++) {
                switch (d) {
                    case 1:
                        x = 600;
                        y = 300;
                        break;
                    case 2:
                        x = 600;
                        y = 420;
                        break;
                    case 3:
                        x = 600;
                        y = 700;
                        break;
                }
                g.drawImage(multimenu[d], x, y, null);
            }
        }
        //join server room DISPOSED AS IT IS NOT NEEDED
        else if (screen == 5) {

        }
        //create server NOT IMPLEMENTED YET
        else if (screen == 6) {
            int x = 0;
            int y = 0;
            for (int f = 0; f < createmenu.length; f++) {
                switch (f) {
                    case 1:
                        x = 0;
                        y = 0;
                        break;
                    case 2:
                        x = 0;
                        y = 0;
                        break;
                }
                g.drawImage(createmenu[f], x, y, null);
            }
        }
        //server room
        else if (screen == 7) {
            int x = 0;
            int y = 0;
            for (int h = 0; h < servermenu.length; h++) {
                switch (h) {
                    case 1:
                        x = 1080;
                        y = 600;
                        break; // leavegame text
                    case 2:
                        x = 660;
                        y = 600;
                        break; // ready text
                    case 3:
                        x = 475;
                        y = 110;
                        break; // Server text
                    case 4:
                        x = 100;
                        y = 600;
                        break; // start game text
                    //joined players as cards
                    case 5:
                        x = 190;
                        y = 270;
                        break;
                    case 6:
                        x = 430;
                        y = 270;
                        break;
                    case 7:
                        x = 670;
                        y = 270;
                        break;
                    case 8:
                        x = 910;
                        y = 270;
                        break;
                    case 9:
                        x = 1150;
                        y = 270;
                        break;
                }

                if (h == 0)
                    g.drawImage(servermenu[h].getScaledInstance(1600, 800, Image.SCALE_SMOOTH), x - 40, y, null);
                else if (h < 5)
                    g.drawImage(servermenu[h], x, y, null);
                else if (h < 10)
                    g.drawImage(servermenu[h].getScaledInstance(183, 275, Image.SCALE_SMOOTH), x, y, null);

            }
            int x1 = 0;
            int y1 = 0;
            g.setColor(Color.GRAY);
            g.setFont(new Font("Papyrus", Font.ITALIC, 23));
            for (int i = 0; i < playeramount; i++) {
                switch (i) {
                    case 0:
                        x1 = 230;
                        y1 = 585;
                        break;
                    case 1:
                        x1 = 470;
                        y1 = 585;
                        break;
                    case 2:
                        x1 = 710;
                        y1 = 585;
                        break;
                    case 3:
                        x1 = 950;
                        y1 = 585;
                        break;
                    case 4:
                        x1 = 1190;
                        y1 = 585;
                        break;
                }
                g.drawString(players[i].getName(), x1, y1);
            }
        }
        //Poker table
        else if (screen == 8) {
            int x = 0;
            int y = 0;
            int cards = 2;
            int rotation = 0;
            boolean clockwise = true;
            int chipsstart = 18;
            int buttonsstart = 14;
            //this will round the g1 value to where the button pictures actually start in the folder
            int buttonsstartfolder = 3;

            //THE FOR LOOP MAY SOMETIMES PRINT INSUFFICIENTLY DUE
            //TO WHERE IT ENDS, CHECK THAT OUT
            for (int g1 = 0; g1 < 67; g1++) {
                Image background = null;
                Image leavetable = null;
                switch (g1) {
                    case 0:
                        background = pokertable[g1].getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
                        break; //background
                    case 1:
                        leavetable = pokertable[g1].getScaledInstance(85, 40, Image.SCALE_SMOOTH);
                        x = 1440;
                        y = 10;
                        break;//leave table text
                    //Cards
                    //center bottom AKA player 1
                    case 2:
                        x = 690;
                        y = 490;
                        rotation = 10;
                        clockwise = false;
                        break;
                    case 3:
                        x = 760;
                        y = 490;
                        rotation = 10;
                        clockwise = true;
                        break;
                    //top right
                    case 4:
                        x = 1100;
                        y = 140;
                        rotation = 10;
                        clockwise = false;
                        break;
                    case 5:
                        x = 1160;
                        y = 140;
                        rotation = 10;
                        clockwise = true;
                        break;
                    //left
                    case 6:
                        x = 110;
                        y = 390;
                        rotation = 100;
                        clockwise = true;
                        break;
                    case 7:
                        x = 110;
                        y = 315;
                        rotation = 280;
                        clockwise = false;
                        break;
                    //right
                    case 8:
                        x = 1370;
                        y = 390;
                        rotation = 260;
                        clockwise = false;
                        break;
                    case 9:
                        x = 1370;
                        y = 315;
                        rotation = 260;
                        clockwise = true;
                        break;
                    //top left
                    case 10:
                        x = 360;
                        y = 140;
                        rotation = 10;
                        clockwise = false;
                        break;
                    case 11:
                        x = 420;
                        y = 140;
                        rotation = 10;
                        clockwise = true;
                        break;
                    //pot
                    case 12:
                        x = 720;
                        y = 90;
                        rotation = 10;
                        clockwise = false;
                        break;
                    case 13:
                        x = 780;
                        y = 90;
                        rotation = 10;
                        clockwise = true;
                        break;
                    //Buttons
                    case 14:
                        x = 440;
                        y = 550;
                        break;
                    case 15:
                        x = 550;
                        y = 550;
                        break;
                    case 16:
                        x = 940;
                        y = 550;
                        break;
                    case 17:
                        x = 1050;
                        y = 550;
                        break;
                    //Chips P1 bottom
                    case 18:
                        x = 530;
                        y = 490;
                        break;
                    case 19:
                        x = 590;
                        y = 490;
                        break;
                    case 20:
                        x = 940;
                        y = 490;
                        break;
                    case 21:
                        x = 1000;
                        y = 490;
                        break;
                    case 22:
                        x = 1060;
                        y = 490;
                        break;
                    //P2 top right
                    case 23:
                        x = 1045;
                        y = 150;
                        break;
                    case 24:
                        x = 1045;
                        y = 210;
                        break;
                    case 25:
                        x = 1265;
                        y = 150;
                        break;
                    case 26:
                        x = 1265;
                        y = 210;
                        break;
                    case 27:
                        x = 1155;
                        y = 280;
                        break;
                    //P3 Left
                    case 28:
                        x = 240;
                        y = 290;
                        break;
                    case 29:
                        x = 240;
                        y = 350;
                        break;
                    case 30:
                        x = 240;
                        y = 410;
                        break;
                    case 31:
                        x = 240;
                        y = 470;
                        break;
                    case 32:
                        x = 240;
                        y = 530;
                        break;
                    //P4 Right
                    case 33:
                        x = 1310;
                        y = 290;
                        break;
                    case 34:
                        x = 1310;
                        y = 350;
                        break;
                    case 35:
                        x = 1310;
                        y = 410;
                        break;
                    case 36:
                        x = 1310;
                        y = 470;
                        break;
                    case 37:
                        x = 1310;
                        y = 530;
                        break;
                    //P5 top left
                    case 38:
                        x = 302;
                        y = 150;
                        break;
                    case 39:
                        x = 302;
                        y = 210;
                        break;
                    case 40:
                        x = 525;
                        y = 150;
                        break;
                    case 41:
                        x = 525;
                        y = 210;
                        break;
                    case 42:
                        x = 417;
                        y = 280;
                        break;
                    //P6 Top
                    case 43:
                        x = 780;
                        y = 230;
                        break;
                    case 44:
                        x = 670;
                        y = 130;
                        break;
                    case 45:
                        x = 670;
                        y = 190;
                        break;
                    case 46:
                        x = 880;
                        y = 190;
                        break;
                    case 47:
                        x = 880;
                        y = 130;
                        break;

                    //Community cards, assumes all cards are printed
                    case 48:
                        x = 525;
                        y = 320;
                        break;
                    case 49:
                        x = 635;
                        y = 320;
                        break;
                    case 50:
                        x = 745;
                        y = 320;
                        break;
                    case 51:
                        x = 855;
                        y = 320;
                        break;
                    case 52:
                        x = 965;
                        y = 320;
                        break;
                    // Money: Can be 6 digits at max
                    case 53:
                        x = 10;
                        y = 10;
                        break;
                    case 54:
                        x = 40;
                        y = 10;
                        break;
                    case 55:
                        x = 60;
                        y = 10;
                        break;
                    case 56:
                        x = 80;
                        y = 10;
                        break;
                    case 57:
                        x = 100;
                        y = 10;
                        break;
                    case 58:
                        x = 120;
                        y = 10;
                        break;
                    case 59:
                        x = 140;
                        y = 10;
                        break;

                }

                if (g1 == 0) {
                    g.drawImage(background, 0, 0, null);
                } else if (g1 == 1) {
                    g.drawImage(leavetable, x, y, null);
                }
                //Players hand
                else if (g1 == 2 || g1 == 3) {
                    BufferedImage opencard = hands.get(g1 - 2);
                    if (opencard != null) {
                        BufferedImage card = this.rotateImage(hands.get(g1 - 2), rotation, clockwise);
                        g.drawImage(card.getScaledInstance(153, 213, Image.SCALE_SMOOTH), x, y, null);
                    }
                }
                //AIs hands
                else if (g1 < 12 && singleplayer) {
                    BufferedImage card = null;
                    BufferedImage opencard = hands.get(g1 - 2);
                    if (opencard != null) {
                        if (promptendfeatures) {
                            card = this.rotateImage(opencard, rotation, clockwise);
                        } else
                            card = this.rotateImage(closedcard, rotation, clockwise);

                        g.drawImage(card.getScaledInstance(102, 142, Image.SCALE_SMOOTH), x, y, null);
                    }
                }

                //Chips
                else if (g1 > chipsstart - 1 && g1 < chipsstart + chipos.length) {
                    BufferedImage ch = chipos[g1 - chipsstart];
                    if (ch == null)
                        System.out.println("Chips don't exist, this guy is poor");
                    else
                        g.drawImage(ch, x, y, null);
                }
                //Community Cards
                else if (g1 < 48 + comcards.size() && g1 >= 48) {
                    BufferedImage card = this.rotateImage(comcards.get(g1 - 48), 0, clockwise);
                    g.drawImage(card.getScaledInstance(102, 142, Image.SCALE_SMOOTH), x, y, null);

                }
                //Buttons
                else if (g1 > buttonsstart - 1 && g1 < buttonsstart + 4) {
                    BufferedImage button = this.rotateImage(pokertable[g1 - buttonsstartfolder], 0, false);
                    if (g1 != buttonsstart + 1 || canCheck)
                        g.drawImage(button.getScaledInstance(110, 85, Image.SCALE_SMOOTH), x, y, null);
                }
                //Money
                else if (g1 < 60 && g1 > 52) {
                    g.drawImage(currentmoniesp1.get(g1 - 53).getScaledInstance(30, 30, Image.SCALE_SMOOTH), x, y, null);
                }
            }
            //The pot money, frame and the images are being drawn here
            g.drawImage(potframe.getScaledInstance(328, 276, Image.SCALE_SMOOTH), 623, 20, null);
            g.drawImage(pot.getScaledInstance(328, 276, Image.SCALE_SMOOTH), 630, 20, null);
            //potmoney being drawn
            g.setFont(new Font("Papyrus", Font.ITALIC, 38));
            g.setColor(Color.GRAY);

            g.drawString(smoney, 704, 180);
            //the borders bein drawn

            Player[] ranked = Arrays.copyOf(players, players.length);
            Arrays.sort(ranked);
            for (int i = 0; i < ranked.length; i++) {
                if (players[i].playing)
                    ranked[i].setBorder(borders[i]);
                else
                    ranked[i].setBorder(null);
            }


            //when the game has ended, these are drawn to notify the player
            if (promptendfeatures) {
                g.setFont(new Font("Papyrus", Font.ITALIC, 44));
                g.setColor(Color.BLACK);
                g.drawImage(winbox1.getScaledInstance(275,35,Image.SCALE_SMOOTH), 645, 280, null);
                g.drawString("Winner(s)", 681, 250);
                g.setFont(new Font("Papyrus", Font.ITALIC, 20));
                g.drawString(game.getWinner(), 677, 302);
                g.drawImage(winbox2, 0, 600, null);
                if ((p1.ifWon() || p1.getBudget() > 0))
                    g.drawImage(winbox3, 1270, 600, null);
            }

            //the actions and playernames being drawn
            int x1 = 0;
            int y1 = 0;
            for (int i = 0; i < players.length; i++) {
                switch (i) {
                    //p1 , center bottom
                    case 0:
                        x1 = 720;
                        y1 = 700;
                        break;
                    // top right
                    case 1:
                        x1 = 1100;
                        y1 = 48;
                        break;
                    // left
                    case 2:
                        x1 = 100;
                        y1 = 240;
                        break;
                    // right
                    case 3:
                        x1 = 1348;
                        y1 = 240;
                        break;
                    // top left
                    case 4:
                        x1 = 350;
                        y1 = 48;
                        break;
                }

                g.setFont(new Font("Papyrus", Font.ITALIC, 30));
                g.setColor(Color.BLACK);
                BufferedImage br = players[i].getBorder();
                if (br != null) {
                    if (players[i].getBorder() == borders[2])
                        g.drawImage(players[i].getBorder().getScaledInstance(183, 140, Image.SCALE_SMOOTH), x1 - 11, y1 - 40, null);
                    else if (players[i].getBorder() == borders[0])
                        g.drawImage(players[i].getBorder().getScaledInstance(183, 140, Image.SCALE_SMOOTH), x1 - 20, y1 - 40, null);
                    else
                        g.drawImage(players[i].getBorder().getScaledInstance(183, 110, Image.SCALE_SMOOTH), x1 - 10, y1 - 30, null);
                }
                if (i != 0 && singleplayer) {
                    AI ai = (AI) players[i];
                    String s = ai.getAction();
                    g.drawString(s, x1, y1 + 60);

                } else {
                    y1 += 20;
                    x1 += 5;
                    g.setColor(Color.GREEN);
                }
                g.drawString(players[i].getName(), x1, y1 + 30);
                //g.drawImage(,x,y,null)
            }
        }
    }

    //when hovering over the buttons the buttons should flash
    //implement later
    public void checkPos(int x, int y) {

    }


    //Custom method to check which button is pressed and function accordingly
    // will be used for input
    public void pressOption(int x, int y) {
        //screen 1: Main menu
        //screen 2: singleplayer
        //screen 3: multiplayer
        //screen 4: join/create server
        //screen 5: join server
        //screen 6: server room
        //screen 7: the poker table

        //Pressing the singleplayer button
        if (screen == 1 && x > sp && x < mainmenu[2].getWidth() + sp && y > 430 && y < 430 + mainmenu[2].getHeight()) {
            screen = 2;
            singleplayer = true;

        }
        //Pressing the multiplayer button
        else if (screen == 1 && x > sp && x < sp + mainmenu[3].getWidth() && y > 520 && y < 520 + mainmenu[3].getHeight()) {
            screen = 3;
        }
        //pressing the quit button
        else if (screen == 1 && x > sp - 50 && x < sp - 50 + mainmenu[5].getWidth() && y > 575 && y < 575 + mainmenu[5].getHeight()) {
            System.exit(0);
        }
        //Pressing the return to main menu button
        else if ((screen == 2 || screen == 3) && x > sp - 150 && y > 660 && y < 660 + gamemenu[5].getHeight() && x < sp - 150 + gamemenu[5].getWidth()) {
            screen = 1;
            singleplayer = false;

        }
        //pressing the return button when in the multi menu or the join menu
        else if ((screen == 4 || screen == 5) && x > 600 && x < 600 + joinmenu[3].getWidth() && y > 700 && y < 700 + joinmenu[3].getHeight()) {
            openholdemcreate = false;
            openholdemjoin = false;
            screen = 1;
            this.repaint();
        }
        // Pressing the texas holdem button when in multiplayer
        else if ((screen == 3) && x > sp - 140 && y > 430 && x < sp - 140 + gamemenu[2].getWidth() && y < 430 + gamemenu[2].getHeight()) {
            screen = 4;
        }

        //pressing texas holdem button in singplayer
        else if ((screen == 2) && x > 1060 && x < 1060 + gamemenu[2].getWidth() && y > 430 && y < 430 + gamemenu[2].getHeight())//|| screen == 6)
        {

            this.setUpGame();
            screen = 8;
        }
        //pressing leave table in the game
        else if (screen == 8 && x < 1440 + 85 && x > 1440 && y < 10 + 40 && y > 10) {
            screen = 1;

            start = true;
            this.setUpGame();
            promptendfeatures = false;
        }
        if (singleplayer) {
            // BUTTONS FOR SINGLEPLAYER GAME
            // FOLD 440
            if (!promptendfeatures && screen == 8 && x > 440 && y > 550 && y < 550 + 85 && x < 440 + 110) {
                action = "fold";
                int i = 1;
                while (!promptendfeatures && game.playersAtDesk() > 1) {
                    System.out.println("Playing: " + i + " Round");
                    playOneRound();
                }
            }
            // CHECK
            else if (!promptendfeatures && canCheck && screen == 8 && x > 550 && y > 550 && y < 550 + 85 && x < 550 + 110) {
                action = "check";
                playOneRound();
            }
            // CALL
            else if (!promptendfeatures && screen == 8 && x > 940 && y > 550 && y < 550 + 85 && x < 940 + 110) {
                action = "call";
                playOneRound();
            }
            // RAISE
            else if (!promptendfeatures && screen == 8 && x > 1050 && y > 550 && y < 550 + 85 && x < 1050 + 110) {
                System.out.println("RAISED!!");
                openholdemraise = true;

            }
            //total reset
            else if (promptendfeatures && screen == 8 && x > 0 && x < 0 + winbox2.getWidth() && y > 600 && y < 600 + winbox2.getHeight()) {
                start = true;
                this.setUpGame();
                promptendfeatures = false;
                canCheck = false;
                for (int i = 1; i < players.length; i++) {
                    AI ai = (AI) players[i];
                    ai.setChange(true);
                }
            }
            //next game reset
            else if ((p1.ifWon() || p1.getBudget() > 0) && promptendfeatures && screen == 8 && x > 1270 && x < 1270 + winbox3.getWidth() && y > 600 && y < 600 + winbox3.getHeight()) {
                game.resetGame();
                this.setUpGame();
                ArrayList<Player> playersInGame = new ArrayList<Player>();
                for (int i = 1; i < players.length; i++) {
                ((AI) game.playerArray[i]).setRaiseCount(0);
            }
                for (int i = 0; i < players.length; i++) {
                if (players[i].playing) {
                    playersInGame.add(players[i]);
                }
            }
            if (game.bbIndex >= playersInGame.size()) {
                game.bbIndex = 0;
            }
            if (game.sbIndex >= playersInGame.size()) {
                game.sbIndex = 0;
            }
            game.payBlinds(playersInGame, game.bigBlind, game.smallBlind, game.bbIndex, game.sbIndex);
            Common.println("Small blind betted by player " + (game.sbIndex + 1));
            Common.println("Big blind betted by player " + (game.bbIndex + 1));
            game.bbIndex++;
            game.sbIndex++;
            
                canCheck = false;
                promptendfeatures = false;
                for (int i = 1; i < players.length; i++) {
                    AI ai = (AI) players[i];
                    ai.setChange(true);
                }
            }

            //END OF SINGLEPLAYER BUTTONS
        }
        //pressing the create server button)
        if (screen == 4 && x < 600 + multimenu[1].getWidth() && x > 600 && y < 300 + multimenu[1].getHeight() && y > 300) {
            openholdemcreate = true;
            openholdemjoin = false;
            this.repaint();
        }
        //pressing join server in texas holdem
        else if (screen == 4 && x > 600 && y > 420 && x < 600 + multimenu[2].getWidth() && y < 420 + multimenu[2].getHeight()) {
            openholdemjoin = true;
            openholdemcreate = false;
            this.repaint();
        }
        //press change music
        else if (screen == 1 && x > sp - 350 && y > 630 && y < 630 + musicstuff[0].getHeight() && x < sp - 350 + musicstuff[0].getWidth()) {
          
        }
        //pressing turn music on/off
        else if(screen == 1 && x > sp - 350 && y > 690 && y < 690 + musicstuff[1].getHeight() && x < sp - 350 + musicstuff[1].getWidth()) {
         
        }
    }

    //Creates a new instance of game.

    public void setUpGame() {

        //MURAT && EGEMEN TESTER TIMEEE
        if (singleplayer) {
            System.out.println("Start: " + start);
            playeramount = 5;
    /*
    AI ai1 = new AI(300,100,100,100,50);
    AI ai2 = new AI(300,100,100,100,50);
    AI ai3 = new AI(300,100,100,100,50);
    AI ai4 = new AI(300,100,100,100,50);
    AI ai5 = new AI(300,100,100,100,50);
    */
 
            if (start) {
              ArrayList<Player> playersInGame = new ArrayList<Player>();
                players = new Player[playeramount];
                p1 = new Player(300, 100, 100, 100, 50, p1name);
                for (int i = 0; i < players.length; i++) {
                    if (i == 0)
                        players[i] = p1;
                    else
                        players[i] = new AI(300, 100, 100, 100, 50, "AI John " + i);
                }
                game = new Game(players);

                start = false;
                for (int i = 1; i < players.length; i++) {
                ((AI) game.playerArray[i]).setRaiseCount(0);
             }
                
             for (int i = 0; i < players.length; i++) {
                if (players[i].playing) {
                    playersInGame.add(players[i]);
                }
            }
            if (game.bbIndex >= playersInGame.size()) {
                game.bbIndex = 0;
            }
            if (game.sbIndex >= playersInGame.size()) {
                game.sbIndex = 0;
            }
            game.payBlinds(playersInGame, game.bigBlind, game.smallBlind, game.bbIndex, game.sbIndex);
            Common.println("Small blind betted by player " + (game.sbIndex + 1));
            Common.println("Big blind betted by player " + (game.bbIndex + 1));
            game.bbIndex++;
            game.sbIndex++;
            }

            p1.calculateBudget();
            int money = p1.getBudget();
            currentmoniesp1 = this.arrangeMoney(money, 6);
            currenttotalmonies = this.arrangeMoney(game.getTotalPot(), 7);
            int gtp = game.getTotalPot();
            ds = 0;
            while (gtp > 0) {
                gtp = gtp / 10;
                ds++;
            }
            smoney = "";
            if (ds == 0)
                ds += 1;
            for (int i = 0; i < 7 - ds; i++) {
                smoney += "0";
            }
            smoney += game.getTotalPot() + "";
            try {
                comcards = new ArrayList<BufferedImage>();
                game.getComCardPictures(comcards);
            } catch (IOException ie) {
            }


            for (int i = 0; i < players.length; i++) {
                try {
                    if (i == 0)
                        chipos = new BufferedImage[25];
                    players[i].getChipPhotos(chipos, i);
                } catch (IOException ie) {
                }
            }

            try {

                hands = new ArrayList<BufferedImage>();
                //AI cards
                for (Player p : players) {
                    if (p.isPlaying())
                        p.getHandPicture(hands);
                    else {
                        hands.add(null);
                        hands.add(null);
                    }
                }
            } catch (IOException ie) {
            }


        }
    }
    //Money printer go brrrrr
    //Prepares the money counter image
    public ArrayList<BufferedImage> arrangeMoney(int money, int digits) {
        int digcounter = 0;
        int[] digs = new int[digits];
        ArrayList<BufferedImage> monies = new ArrayList<BufferedImage>();
        while (money > 0) {
            digs[(digits - 1) - digcounter] = money % 10;
            money = money / 10;
            digcounter++;
        }
        while (digcounter < digits) {
            digs[(digits - 1) - digcounter] = 0;
            digcounter++;
        }
        for (int i = 0; i < digcounter; i++) {
            monies.add(pokermoney[digs[i]]);
        }
        if (digits == 6)
            monies.add(0, pokermoney[10]);
        return monies;
    }

    //Method used to return an image for a given angle
    public BufferedImage rotateImage(BufferedImage img, double teta, boolean clockwise) {
        int wid = img.getWidth();
        int hei = img.getHeight();

        double rad = Math.toRadians(teta);
        double sin = Math.abs(Math.sin(rad));
        double cos = Math.abs(Math.cos(rad));

        int rotwid = (int) Math.floor(wid * cos + hei * sin);
        int rothei = (int) Math.floor(wid * sin + hei * cos);
        BufferedImage newimg = new BufferedImage(rotwid, rothei, BufferedImage.TYPE_INT_ARGB);
        int xTop = wid / 2;
        int yLeft = hei / 2;
        Graphics2D g2 = newimg.createGraphics();
        AffineTransform afft = new AffineTransform();
        afft.translate((rotwid - wid) / 2, (rothei - hei) / 2);

        if (!clockwise)
            rad = rad * -1;

        afft.rotate(rad, xTop, yLeft);
        g2.setTransform(afft);
        g2.drawImage(img, 0, 0, this);
        g2.dispose();

        return newimg;
    }

    public int getP1Budget() {
        p1.calculateBudget();
        return p1.getBudget();
    }

    //Rotates the game around the table for one round
    public void playOneRound() {
        int playingCount = 0;
        boolean cont = true;
        boolean stop = false;
        


        if (game.playersAtDesk() > 1) {

            

            
            

            //game.handleBlinds();


            //Loops through players to make them play one round
            for (int i = 0; i < game.playerArray.length; i++) {
                if (i == 0 && game.playerArray[i].playing) {

                    playingCount++;

                    ArrayList<Chip> playerPot = game.playerArray[i].play(game.pot, action, raise, true);

                    game.pot.addToPot(playerPot, game.playerArray[i]);
                    Common.println("Budget:" + game.playerArray[i].getBudget());
                    game.playNumber += 1;
                    game.nextRound();
                    if (game.gameEnded) {
                        cont = false;
                        break;
                    }

                } else {
                    if (game.playerArray[i].playing) {
                        playingCount++;

                        ArrayList<Chip> aiPot = ((AI) game.playerArray[i]).play(game.comCards.display(), game.pot.potValue(game.pot.getHighestPot()) - game.pot.potValue(game.pot.getPotOf(game.playerArray[i])));
                        //Common.println("Pot of AI:" +  game.pot.potValue(aiPot));
                        game.pot.addToPot(aiPot, game.playerArray[i]);
                        game.playNumber += 1;
                        game.nextRound();
                        if (game.gameEnded) {
                            promptendfeatures = true;
                            this.repaint();
                            break;
                        }
                    }


                }
                this.setUpGame();

                //Advances to next round if applicable

            }
            canCheck = p1.canCheck(game.pot);


        }
        //proper reset mechanc

    }


    //GETTERS AND SETTERS
    public boolean holdemraise() {
        boolean b = openholdemraise;
        openholdemraise = false;
        return b;
    }

    public boolean holdemjoin() {
        boolean b = openholdemjoin;
        openholdemjoin = false;
        return b;
    }

    public boolean holdemcreate() {
        return openholdemcreate;
    }

    //TODO implement this properly
    public void setRaisedAmount(int raised) {
        raise = raised;
    }

    public int getScreen() {
        return screen;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean firstActionTaken() {
        return firstact;
    }

    public void setFirstAction(boolean b) {
        firstact = b;
    }
}