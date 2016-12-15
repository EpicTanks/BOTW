import javax.imageio.*;
import java.io.*;
import java.awt.*;

import java.awt.image.*;


public class Armour extends Item{
  //fields
  public BufferedImage img = null;
  private String equipType;
  private int protection;
  private int strMod;
  private int smrtMod;
  private int spchMod;
  private int spdMod;
  private String[][] armourCatalogue ={
    {"Clothing","Plain Clothes","Bandit Outfit","Deputy's Outfit","Dress Clothes","Governer's Dress"},
    {"Robes","Plain Robes","Cultist Robes"},
    {"Armour","Magic Armour","Plate Mail"},
    {"Leather Armour","Leather Armour","High Grade Leather",},
    {"Special Armour","Red Dragon Hide","Brass Dragon Hide"},
    {"Hat","Cowboy Hat","Metal Helmet","Fancy Hat","Deputy's Ten Gallon","Army Surplus Helmet","Twenty Gallon Hat","Solar Hat"},
    {"Magic Hat","Wizard Hat","Senior Wizard Hat","Very Good Wizard Hat"},
    {"Special Hat","Red Dragon's Head","Brass Dragon's Head","Arachnid Gun Hat"},
  };
  
  
  public Armour() {
    int i = (int) (8*Math.random());
    int j = (int) ((armourCatalogue[i].length-1)*Math.random()+1);
    equipType = armourCatalogue[i][0];
    type = "Armour";
    
    
   
    try {
      setData();
    } catch (IOException e) {
      System.out.println("setData() failed, default stats kept.");
    }
    
    
    try{
      img = ImageIO.read(new File("images/items/armour/" + equipType + ".png")); 
    }
    catch(IOException e){     
    }
    
    if(i <= 4){
      equipType = "Armour";
    }
    else{
      equipType = "Hat"; 
    }
    
    
    if(equipType == "Armour"){
      
      description = "A Nice Set of Armour";
    }
    else{
      
      description = "A Nice Hat!";
    }
    
    name = armourCatalogue[i][j];
    price = 6;
    
    
    
  }
  
  
  
  
   public void setData() throws IOException{
    BufferedReader reader = null;
    try {
      File file = new File("gamedata/itemStats/armourStats/" + equipType + "/" +  name + ".txt");
      reader = new BufferedReader(new FileReader(file));
    }
    catch (FileNotFoundException e) {
      System.out.println("gamedata/itemStats/armourStats/" + equipType + "/" +  name + ".txt");
      throw new IOException();
    }
    
    protection = Integer.parseInt(reader.readLine());
    strMod = Integer.parseInt(reader.readLine());
    smrtMod = Integer.parseInt(reader.readLine());
    spchMod = Integer.parseInt(reader.readLine());
    price = Integer.parseInt(reader.readLine());
  }
  
   
  public void paint(Graphics2D g2d, int x, int y){
    g2d.drawImage(img, x, y, 64, 64, null);
  }
  
  public void use(CharacterSheet c){
    c.setArmour(this);
  }
  
  public int protect(){
   return protection; 
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