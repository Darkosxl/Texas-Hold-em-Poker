//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

//Class definition: Contains methods that are shared and might be useful for debugging, generally array printing algorithms

public class Common {
  
  public static void println(String s) {
    System.out.println(s);
  }
  public static void print(String s) {
    System.out.print(s);
  }
  public static void println(int i) {
    System.out.println(i);
  }
  public static void print(int i) {
    System.out.print(i);
  }

  
  //Prints an integer array
  public static void printArray(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      print(arr[i] + " ");
    }
    println("");
  }

  //Prints a string array
  public static void printArray(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      print(arr[i] + " ");
    }
    println("");
  }

  //Prints a 2D integer array
  public static void printArray2D (int[][] arr) {
      int rows = arr.length;
      int cols = arr[0].length;
      
      for (int y = 0; y < rows; y++) {
        for (int x = 0; x < cols; x++) {
          print(arr[y][x]);
        }
        println("");
      }
  }
}