//Team Texas Hold'em
//Cem Berke, Egemen Balban, Murat Diken, Yigit Sen
//March 2020

//Class Definition: Chip Object and related operations

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Chip {

  private int number;
  public int value;
  private String imgName;
  
  public Chip(int number, int value) {
    //Instead of creating multiple objects with same value and to avoid possible errors, the number of the chips
    //are modifiable.
    this.number = number;
    this.value = value;
    //Uses structural name to access the image.
    imgName = Integer.toString(value) + ".png";
  }

  //Returns the total value of the chip at that value.
  public int totalValue() {
    return number * value;
  }

  //Modifier method that removes the amount of chips requested
  public void removeChips(int numberRemoved) {
    if (numberRemoved <= number) {
      number -= numberRemoved;
    }
  }
  //Modifier method that adds the amount of chips requested
  public void addChips(int numberAdded) {
    number += numberAdded;
  }
  //Accessor method that returns the number of chips used
  public int getNumber() {
    return number;
  }
  //Accessor method that returns the image file associated with the chip.
  public BufferedImage getImg() throws IOException {
    return ImageIO.read(new File(imgName));
  }
  
}