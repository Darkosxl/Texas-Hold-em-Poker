public class HoldemServer
{
 private int playernum;
 private int port;
 //private 
 public HoldemServer(int playernum, int port)
 {
  this.playernum = playernum;
  this.port = port;
  ServerSocket server = new ServerSocket(port);
 }
}