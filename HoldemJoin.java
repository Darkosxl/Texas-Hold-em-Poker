//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

//Class Definition: SERVER CLASS, NOT RELEVANT FOR NOW!
public class HoldemJoin extends JFrame
{
 private int port;

 private String name;
 
 private JButton joinserver;
 private JButton exit;
 private JComboBox<String> ports;
 private JTextArea namebox;
 
 private Boolean enabled;
 public HoldemJoin()
 {
 
  this.setTitle("Connecting to Server");
  this.setLayout(new GridLayout(3,1));
  this.setSize(400,400);
  name = "";
  port = 0;
  JLabel lport = new JLabel("Select the port please: ");
  JLabel lname = new JLabel("Write ya Name: ");
  lport.setHorizontalAlignment(JLabel.CENTER);
  lname.setHorizontalAlignment(JLabel.CENTER);
  
  String[] available = {"4442", "8348", "9234", "1423", "4141", "9531", "7143"};
  
  ports = new JComboBox<String>(available);
  
  namebox = new JTextArea("Username O' Client");
  
  JPanel infopan = new JPanel();
  infopan.setLayout(new GridLayout(1,2));
  JPanel inpan = new JPanel();
  inpan.setLayout(new FlowLayout());
  
  infopan.add(lport);
  infopan.add(lname);
  
  inpan.add(ports);
  inpan.add(namebox);
  
  joinserver = new JButton("Join Server");
  exit = new JButton("Exit"); 
  joinserver.addActionListener(new ButtonListener());
  exit.addActionListener(new ButtonListener());
  JPanel buttonpan = new JPanel();
  buttonpan.add(joinserver);
  buttonpan.add(exit);
  
  this.add(infopan);
  this.add(inpan);
  this.add(buttonpan);
 }
 class ButtonListener implements ActionListener
 {
  public void actionPerformed(ActionEvent ae)
  {
   if(ae.getSource() == exit)
   {
    enabled = false;
    HoldemJoin.this.dispose(); 
   }
   if(ae.getSource() == joinserver)
   {
    port = Integer.parseInt((String)ports.getSelectedItem());
    name = namebox.getText();
    //TODO: TRY TO CONNECT TO THE SERVER HERE
    //THEN DISPOSE THIS FRAME
    enabled = true;
    HoldemJoin.this.dispose();
   }
  }
 } 
 public boolean ifEnabled()
 {
  return enabled; 
 }
 public int getPort()
 {
  return port;
 }
 public String getName()
 {
  return name; 
 }
}