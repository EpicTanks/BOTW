import java.io.*;
import java.awt.*;

public class Ability{
  private String name;
  private int hpMod;
  private int dmg = 2;
  private int range = 3; 
  private int spdMod;
  private int strMod;
  private int mpCost = 1;
  private int bpCost = 0;
  
  public Ability(String s){  
    name = s;
    
    try {
      setData();
    } catch (IOException e) {
      System.out.println("setData() failed, default stats kept.");
    }       
  } 
  
  
  public void setData() throws IOException{
    BufferedReader reader = null;
    try {
      File file = new File("gamedata/abilities/" +  name + ".txt");
      reader = new BufferedReader(new FileReader(file));
    }
    catch (FileNotFoundException e) {
      System.out.println("gamedata/abilities/" +  name + ".txt");
      throw new IOException();
    }
    
    
    dmg = Integer.parseInt(reader.readLine());
    range = Integer.parseInt(reader.readLine());
    spdMod = Integer.parseInt(reader.readLine());
    strMod = Integer.parseInt(reader.readLine());
    mpCost = Integer.parseInt(reader.readLine());
    bpCost = Integer.parseInt(reader.readLine());
    
    
    reader.close();
    

    
  }
  
  public boolean getGood(){
    if((spdMod >= 0 || strMod >= 0) && dmg <= 0){
     return true;
    }
    else{
    return false;
    }
  }
  
  public int getMPCost(){
    return mpCost; 
  }
  
  
  public String toString(){
   return name; 
  }
  
  public void statsToString(){
   BestOfTheWest.c.addMessage("Spell: " + name);
   BestOfTheWest.c.addCloseMessage("");
   if(dmg > 0){
   BestOfTheWest.c.addCloseMessage("Damage: " + dmg);
   BestOfTheWest.c.addCloseMessage("Range: " + range);
   }
   if(dmg < 0){
    BestOfTheWest.c.addCloseMessage("Healing: " + -dmg); 
   }
   if(spdMod != 0){
    BestOfTheWest.c.addCloseMessage("Speed Modifer: " + spdMod); 
   }
   if(strMod != 0){
    BestOfTheWest.c.addCloseMessage("Strength Modifer: " + strMod); 
   }
   BestOfTheWest.c.addCloseMessage("MP Cost: " + mpCost);
   BestOfTheWest.c.addCloseMessage("");
  }
  
  public int getRange(){
   return range; 
  }
  
  public int getDamage(){
  return dmg;
  }
}