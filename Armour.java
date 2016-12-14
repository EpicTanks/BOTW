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
    {"Clothing","Plain Clothes","Bandit Outfit","Deputy's Outfit","Dress Clothes","Governer's Dress"},
    {"Robes","Plain Robes","Cultist Robes"},
    {"Armour","Leather Armour","High Grade Leather","Magic Armour","Plate Mail","Red Dragon Hide","Brass Dragon Hide"},
    {"Hat","Cowboy Hat","Wizard Hat","Metal Helmet","Fancy Hat","Senior Wizard Hat","Deputy's Ten Gallon","Army Surplus Helmet","Twenty Gallon Hat","Very Good Wizard Hat","Arachnid Gun Hat","Solar Hat","Red Dragon's Head","Brass Dragon's Head"}
  };
  
   
   public Armour() {
       int i = (int) (4*Math.random());
       int j = (int) ((armourCatalogue[i].length-1)*Math.random()+1);
       equipType = armourCatalogue[i][0];
       type = "Armour";
       if(equipType == "Armour" || equipType == "Clothing" || equipType == "Robes"){
       
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