//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Class Definition: The view and logic controller for raising in the game
public class HoldemRaise extends JFrame {
    private JTextArea money;
    private JButton allin;
    private JButton setraise;
    private JButton doraise;
    private JButton exit;
    private JLabel currentraise;
    private int maxmoney;
    private int minraise;
    private int raisedmoney;
    private JLabel prompter;
    private boolean decision;

    //TODO: make this more dynamic
    //TODO: implement min money depending on round due to big small blind
    public HoldemRaise(int maxmoney, int minraise) {
        //Prepare the visuals
        this.minraise = minraise;
        decision = false;
        this.setSize(670, 400);
        this.maxmoney = maxmoney;
        this.setTitle("Raise");
        this.setLayout(new GridLayout(4, 1));
        money = new JTextArea();
        money.setText("0");
        currentraise = new JLabel("Current raise = 0");

        JPanel buttonpan = new JPanel();
        buttonpan.setLayout(new GridLayout(1, 4));
        allin = new JButton("All in");
        setraise = new JButton("Set Current Raise");
        exit = new JButton("Stop raising");
        doraise = new JButton("Commit to Raise");

        allin.addActionListener(new ButtonListener());
        setraise.addActionListener(new ButtonListener());
        exit.addActionListener(new ButtonListener());
        doraise.addActionListener(new ButtonListener());

        doraise.setEnabled(false);

        buttonpan.add(allin);
        buttonpan.add(setraise);
        buttonpan.add(doraise);
        buttonpan.add(exit);
        prompter = new JLabel("Please Write the amount you want to raise to");
        prompter.setHorizontalAlignment(JLabel.CENTER);
        currentraise.setHorizontalAlignment(JLabel.CENTER);
        this.add(prompter);
        this.add(money);
        this.add(buttonpan);
        this.add(currentraise);
    }


    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            //If exit, do nothing
            if (ae.getSource() == exit) {
                decision = false;
                HoldemRaise.this.dispose();
            } else if (ae.getSource() == allin) {
                //If all in, go all in
                raisedmoney = maxmoney;
                doraise.setEnabled(true);
                HoldemRaise.this.currentraise.setText("Current raise = " + raisedmoney + " Max: " + maxmoney);
            } else if (ae.getSource() == setraise) {
                //If there is a raise set, do that.
                int i = Integer.parseInt(HoldemRaise.this.money.getText());
                if (i > maxmoney && i > minraise)
                    HoldemRaise.this.money.setText("Not possible, write a value less than max");
                else {
                    raisedmoney = i;
                    doraise.setEnabled(true);
                }
                HoldemRaise.this.currentraise.setText("Current raise = " + raisedmoney + " Max: " + maxmoney);

            } else if (ae.getSource() == doraise) {
                //Just raise it. No set raises.
                decision = true;
                doraise.setEnabled(false);
                maxmoney = maxmoney - raisedmoney;
                HoldemRaise.this.currentraise.setText("Current raise = " + raisedmoney + " Max: " + maxmoney);
                HoldemRaise.this.dispose();
            }
        }
    }

    //Getter and Setter methods, pretty self explainatory.
    public boolean getDecision() {
        return decision;
    }

    public int returnRaise() {
        return raisedmoney;
    }

    public void setMax(int max) {
        maxmoney = max;
    }

    public void setMin(int min) {
        min = minraise;
    }
}