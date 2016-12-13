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
  
  
  //1 defaults, yo.
  private int dmglo = 1;  
  private int dmghi = 1;
  private int clip = 1;
  private int range = 1;
  private int price = 1;
  
  public Weapon(){
    int i = (int) (8*Math.random());
    int j = (int) ((weaponCatalogue[i].length-1)*Math.random()+1);
    //System.out.println("Yuo got a " +  weaponCatalogue[i][0]);
    name = weaponCatalogue[i][j];
    description = "Yeehaw! What a VERY WELL MADE weaponry device!";
    price = 6;
    wType = weaponCatalogue[i][0];
     try {
            setData();
        } catch (IOException e) {
            System.out.println("setData() failed, default stats kept.");
        }
    try{
      img = ImageIO.read(new File("images/items/weapons/" + wType + ".png")); 
    }
    catch(IOException e){     
      System.out.println("Hey hey!There's no image for " + wType);
    }
    
  }
  
  public Weapon(String n, String c){
    name = n;
     wType = c;
     
     try {
            setData();
        } catch (IOException e) {
            System.out.println("setData() failed, default stats kept.");
        }
    try{
      img = ImageIO.read(new File("images/items/weapons/" + wType + ".png")); 
    }
    catch(IOException e){     
      System.out.println("Hey hey!There's no image for " + wType);
    }
  }
  
  public void setData() throws IOException{
    BufferedReader reader = null;
    try {
      File file = new File("gamedata/itemStats/" + wType + "/" +  name + ".txt");
      reader = new BufferedReader(new FileReader(file));
    }
    catch (FileNotFoundException e) {
      System.out.println("gamedata/itemStats/" + wType + "/" +  name + ".txt");
      throw new IOException();
    }
    
    dmglo = Integer.parseInt(reader.readLine());
    dmghi = Integer.parseInt(reader.readLine());
    clip = Integer.parseInt(reader.readLine());
    range = Integer.parseInt(reader.readLine());
    price = Integer.parseInt(reader.readLine());
  }
  
  public int rollDamage(){
   return (int) (Math.random() * dmghi) + dmglo; 
  }
  
  public void use(CharacterSheet c){
    c.weap = this;
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