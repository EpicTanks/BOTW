import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class CharacterSheet{
 private String name;
 private int hp;
 private int mp;
 private int bp;
 private int str;
 private int spd;
 private int smrt;
 private int spch;
 private Inventory inv;
 private Armour armr;
 private String[] abilities = new String[2];
 
 public CharacterSheet(String name, int mp, int str, int spd, int smrt, int spch, String ab1, String ab2){
   //Births a new character from the bowels of the machine god
   this.name = name;
   //set stats
   this.mp = mp;
   //yuo dont get a gune
   this.bp = 0;
   //set ability scores
   this.str = str;
   this.spd = spd;
   this.spch = spch;
   this.smrt = smrt;
   this.hp = str*2;
   //set starting abilities
   abilities[0] = ab1;
   abilities[1] = ab2;
   //create the player's inventory
   inv = new Inventory(name);
   //no armr to start
 }
 
 public CharacterSheet(String name){
  this.name = name;
   inv = new Inventory(name); 
 }
 
 public void changeHP(int mod){
  hp += mod;
 }
 
 public void changeBP(int mod){
   if(bp <= 0){
     //reload the gune, setting bp to max once more.
   }
   else{
   bp += mod;
   }
 }
 
 public void paint(Graphics2D g2d){
  inv.paint(g2d); 
 }
 
 public void useAbility(String a){
   for(int k = 0; k < abilities.length; k++){
     //ensure teh player has this ability
     if (abilities[k] == a){
      //get it ON 
     }
     else{
      // newnewne 
     }
     
   }
      
 }
    public void collect(Item i, int s){
    inv.add(i,s); 
   }
    
    public void use(int slot){
      inv.use(slot,this);
    }
    
    public void setArmour(Armour a){
      armr = a;
    }
    
    
}