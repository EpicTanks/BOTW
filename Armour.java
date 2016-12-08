import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Armour extends Item{
   //habstract fields
  private Inventory location;
  private String name;
  private String description;
  private int price; 
  private int slot;
  public BufferedImage img = null;
  
   public Armour(String n, String d, int p){    
    name = n;
    description = d;
    price = p;
  }
   
   public Armour() {
       //return random armour
   }
   
     public void paint(Graphics2D g2d, int x, int y){
    g2d.drawImage(img, x, y, 64, 64, null);
  }
   
   public void use(CharacterSheet c){
     c.setArmour(this);
   }
   
  public void setLocation(Inventory l, int s){
   location = l; 
   slot = s;
  }
  
    public Inventory getLocation(){
   return location; 
  }
  
      public String getName(){
   return name; 
  }  
    
  public int getSlot(){
   return slot; 
  }
}
