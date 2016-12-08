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
  private int partyOrder;
  private Inventory inv;
  private Armour armr;
  private Armour hat;
  public BufferedImage portrait = null;
  public BufferedImage bg = null;
  public BufferedImage abilBG = null;
  public String[] abilities = new String[5];
  private boolean invVis;
  private boolean statVis;
  public int noOfAbils = 0;
  
  public CharacterSheet(String name, int mp, int str, int spd, int smrt, int spch, int po, String ab1, String ab2){
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
    //create the player's inventory
    inv = new Inventory(this,192,partyOrder*74);
    //no armr to start
    loadImages(name);
    partyOrder = po;
  }
  
  //dumbed down creator
  public CharacterSheet(String name, int po){
    this.name = name;
    partyOrder = po;
    inv = new Inventory(this,192,partyOrder*74); 
    loadImages(name);
    this.hp = 5;
    this.mp = 5;
    this.bp = 5;
    for(int k = 0; k < abilities.length; k++){
      abilities[k] = ("");
    }
  }
  
  public void loadImages(String n){    
    try{
      portrait = ImageIO.read(new File("images/portraits/" + n + ".png")); 
      bg = ImageIO.read(new File("images/cSheet.png"));
      abilBG = ImageIO.read(new File("images/cMenu.png"));
    }
    catch(IOException e){     
    } 
    invVis = false;
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
  
  public void click(int x, int y){
    // System.out.println("x,y: " + x + "," + y);
    // System.out.println("top(y must b lesr): " + ( (partyOrder*75)+5) + ". bottom(y must be bigr):" + ((partyOrder*75)+40));
    
    if(x > 75 && x < 105 &&  (y < (partyOrder*75)+5  || y > (partyOrder*75)+70)){
      statVis = false;
      invVis = false;
    }
    
    if(x > 75 && x < 105 && y > (partyOrder*75)+5 && y < (partyOrder*75)+35){
      if(invVis == true){
        invVis = false; 
      }
      else{
        statVis = false;
        invVis = true;             
      }
      
      
    }
    if(x > 75 && x < 105 && y > (partyOrder*75)+40 && y < (partyOrder*75)+70){
      if(statVis == true){
        statVis = false; 
      }
      else{
        invVis = false;
        statVis = true;             
      }
      
    }
    
    if(invVis == true){
      inv.click(x,y); 
    }
    
    
  }
  
  public void paint(Graphics2D g2d){
    
    g2d.drawImage(bg, 0, partyOrder*74,null);
    g2d.drawImage(portrait, 5, (partyOrder*74)+5, 64, 64, null);
    g2d.setFont(new Font("Consolas",Font.PLAIN,15));
    g2d.setColor(Color.white);
    g2d.drawString("HP: " + hp, 135, (partyOrder*74)+20);
    g2d.drawString("MP: " + mp, 135, (partyOrder*74)+43);
    g2d.drawString("BP: " + bp, 135, (partyOrder*74)+65);
    
    
    if(invVis == true){
      inv.paint(g2d);
    }
    if(statVis == true){
      g2d.drawImage(abilBG, 192, partyOrder*74,null);
      g2d.setFont(new Font("Consolas",Font.PLAIN,15));
      g2d.setColor(Color.white);
      for(int k = 0; k < abilities.length; k++){
        g2d.drawString(abilities[k], 200, ((partyOrder*74)+20)+((25*k))+(k*2));
      }
    }
    
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
  
  
  public void collect(Item i, int s1, int s2){
    inv.add(i,s1,s2); 
  }
  
  public boolean collect(Item q){
    for(int i = 0; i < 2; i++){
      for(int j = 0; j < 2; j++){
        if(inv.checkEmpty(i,j)){
          inv.add(q,i,j);
          System.out.println(name + " picked up the " + q.name);         
          return true;
        }        
      }
    }
    return false;
  }
  
  public void use(int s1, int s2){
    inv.use(s1, s2);
  }
  
  public void remove(int s1, int s2){
    inv.remove(s1, s2);
  }  
  
  public void setArmour(Armour a){
    armr = a;
  }
  
  public boolean isAlive() {
      return hp > 0;
  }
  
  public void takeDamage(int damage) {
      hp -= damage;
  }
}