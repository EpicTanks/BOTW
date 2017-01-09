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
    
    
    hpMod = Integer.parseInt(reader.readLine());
    dmg = Integer.parseInt(reader.readLine());
    range = Integer.parseInt(reader.readLine());
    spdMod = Integer.parseInt(reader.readLine());
    strMod = Integer.parseInt(reader.readLine());
    mpCost = Integer.parseInt(reader.readLine());
    bpCost = Integer.parseInt(reader.readLine());
    
    
    reader.close();
  }
  
  public String toString(){
   return name; 
  }
  
  
}