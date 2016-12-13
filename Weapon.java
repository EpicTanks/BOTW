import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Weapon extends Item{
   public BufferedImage img = null;
   private String wType;
  private String[][] weaponCatalogue ={
    {"Pistol","Chicken Cooper","Six-Shooter","Super Shooter","Brigadier","The Good Boy Gun"},
    {"Rifle","L.A.R.D.","Screwball","Big Man","Bolt Action Riflee"},
    {"Musket","Old Musket","War Relic","Devil's Breath"},
    {"Axe","Axe","Battleaxe"},
    {"Club","Club","Rail-Spike"},
    {"Staff","Staff","Ritual Staff"},
    {"Sword","Sword","War Sabre","Shank","Knife"},
    {"BBGun","B.B. Boy"}
  }; 
  
  public Weapon(){
    int i = (int) (8*Math.random());
    int j = (int) ((weaponCatalogue[i].length-1)*Math.random()+1);
    //System.out.println("Yuo got a " +  weaponCatalogue[i][0]);
    name = weaponCatalogue[i][j];
    description = "Yeehaw! What a VERY WELL MADE weaponry device!";
    price = 6;
    wType = weaponCatalogue[i][0];
    
        try{
      img = ImageIO.read(new File("images/items/weapons/" + wType + ".png")); 
    }
    catch(IOException e){     
      System.out.println("Hey hey!There's no image for " + wType);
    }
  }
  
  public void use(CharacterSheet c){
    c.str += 100;
  }
  
  public void paint(Graphics2D g2d, int x, int y){
    g2d.drawImage(img, x, y, 64, 64, null);
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
}