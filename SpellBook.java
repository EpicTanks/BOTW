import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class SpellBook extends Item{
  //habstract fields
  private Inventory location;
  private int slot;
  private String name;
  private String description;
  private int price; 
  public BufferedImage img = null;
  
  
  //spellbook specific fields
  private String spell;
  private String colour;
  private int reqSmarts;
  
  public SpellBook(String n, String d, int p, String s, String c, int rs){    
    name = n;
    description = d;
    price = p;
    spell = s;
    colour = c;
    reqSmarts = rs;
    
    try{
      img = ImageIO.read(new File("images/" + colour + "_book.png")); 
    }
    catch(IOException e){     
    }
  }
  
  public SpellBook() {
      //return a random spellbook
  }
  
  public SpellBook(String n, String c){
    colour = c;
    name = n;
    
    try{
      img = ImageIO.read(new File("images/" + colour + "_book.png")); 
    }
    catch(IOException e){     
    }
  }
    
  public Inventory getLocation(){
   return location; 
  }
  
  public int getSlot(){
   return slot; 
  }
  
    public String getName(){
   return name; 
  }
  
  public void setLocation(Inventory l, int s){
   location = l; 
   slot = s;
  }
  
  public void paint(Graphics2D g2d, int x, int y){
    g2d.drawImage(img, x, y, 64, 64, null);
    g2d.setColor(Color.white);
    g2d.drawString("s:" + slot,x,y+5);
  }
  
  public void use(CharacterSheet c){
    System.out.println("You learned " + name);
    System.out.println(name + ":" + description);
  }
  
}