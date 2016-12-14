import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Armour extends Item{
   //fields
  public BufferedImage img = null;
  private String equipType;
  private String[][] armourCatalogue ={
    {"Armour","Plain Clothes","Leather Armour","Bandit Outfit","High Grade Leather","Deputy's Outfit","Plain Robes","Cultist Robes","Magic Armour","Plate Mail","Dress Clothes","Governer's Dress","Red Dragon Hide","Brass Dragon Hide"},
    {"Hat","Cowboy Hat","Wizard Hat","Metal Helmet","Fancy Hat","Senior Wizard Hat","Deputy's Ten Gallon","Army Surplus Helmet","Twenty Gallon Hat","Very Good Wizard Hat","Arachnid Gun Hat","Solar Hat","Red Dragon's Head","Brass Dragon's Head"}
  };
  
   
   public Armour() {
       int i = (int) (2*Math.random());
       int j = (int) (13*Math.random()+1);
       equipType = armourCatalogue[i][0];
       type = "Armour";
       if(equipType == "Armour"){
       
        description = "A Nice Set of Armour";
       }
       else{
      
        description = "A Nice Hat!";
       }
       name = armourCatalogue[i][j];
       price = 6;
       
           try{
      img = ImageIO.read(new File("images/items/armour/" + equipType + ".png")); 
    }
    catch(IOException e){     
    }
         
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
  
    
  public int getSlot(){
   return slot; 
  }
}