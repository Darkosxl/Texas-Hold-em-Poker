//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020
import javax.swing.JFrame;
  import javax.swing.JTextArea;
  import javax.swing.JComboBox;
  import java.awt.GridLayout;
  import java.awt.FlowLayout;
  import javax.swing.JLabel;
  import javax.swing.JPanel;
  import javax.swing.JButton;
  import java.awt.event.ActionListener;
  import java.awt.event.ActionEvent;

  //Class Definition: SERVER CREATOR, NOT RELEVANT FOR NOW
public class HoldemCreator extends JFrame
{
 private JComboBox<String> ports;
 private JComboBox<String> players;
 private JButton create;
 private JButton exit;
 private Boolean enabler;
 private JTextArea namer;
 
 public HoldemCreator()
 {
  enabler = false; 
  namer = new JTextArea("Write your name here");
  this.setTitle("Create Server");
  this.setSize(400,400);
  this.setLayout(new GridLayout(3,1));
  exit = new JButton("exit");
  create = new JButton("create");
  exit.addActionListener(new ButtonListener());
  create.addActionListener(new ButtonListener());
  
  String[] available = {"4442", "8348", "9234", "1423", "4141","9531", "7143"};
  String[] playernum = {"2","3","4","5","6"};
  
  players = new JComboBox<String>(playernum);
  ports = new JComboBox<String>(available);
  JLabel info = new JLabel("Please pick a port and the number of players, in order");
  info.setHorizontalAlignment(JLabel.CENTER);
  
  JPanel portpan = new JPanel();
  portpan.setLayout(new FlowLayout());
  portpan.add(ports);
  portpan.add(players);
  portpan.add(namer);
  
  JPanel buttonpan = new JPanel();
  buttonpan.setLayout(new GridLayout(1,2));
  buttonpan.add(create);
  buttonpan.add(exit);
  this.add(info);
  this.add(portpan);
  this.add(buttonpan);
 }
 class ButtonListener implements ActionListener
 {
  public void actionPerformed(ActionEvent ae)
  {
   if(ae.getSource() == create)
   {
    enabler = true; 
   }
   else
    enabler = false;
   HoldemCreator.this.dispose();
  }
 }
 public String getName()
 {
  return namer.getText(); 
 }
 public int getPort()
 {
   return Integer.parseInt((String)ports.getSelectedItem());
 }
 public int getPlayers()
 {
   return Integer.parseInt((String)ports.getSelectedItem()); 
 }
 public boolean ifEnabled()
 {
  return enabler; 
 }
}