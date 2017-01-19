import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class CharacterSheet{
  private String name;
  private int hp;
  private int mp;
  private int bp;
  private int str = 2;
  private int spd;
  private int smrt;
  private int spch;
  private int partyOrder;
  private Inventory inv;
  public Armour armr = new Armour(0);
  public Armour hat = new Armour(1);
  public BufferedImage portrait = null;
  public BufferedImage bg = null;
  public BufferedImage abilBG = null;
  public BufferedImage dead = null;
  public Ability[] abilities = new Ability[5];
  private boolean invVis;
  private boolean statVis;
  public Weapon weap = new Weapon("Fists","Fist");
  public int noOfAbils = 0;
  public static CharacterSheet selectedSheet;
  public static Ability selectedAbility;
  public static CharacterSheet caster;
  public String race;
  public String clss;
  
  public CharacterSheet(String name, String race, String clss, int po) {
    this.name = name;
    this.race = race;
    this.clss = clss;
    partyOrder = po;
    loadImages(name);
    inv = new Inventory(this,192,partyOrder*74);
    for(int k = 0; k < abilities.length; k++){
      abilities[k] = null;
    }
    
    this.str = 3;
    this.spd = 3;
    this.spch = 3;
    this.smrt = 3;
    
    switch (race) {
      case "Human":
        str += 1;
        spd += 1;
        spch += 1;
        smrt += 1;
        break;
      case "Elf":
        spd += 2;
        str -= 2;
        break;
      case "Dwarf":
        str += 2;
        spd -= 2;
        break;
      case "Orc":
        str += 2;
        smrt -= 2;
        break;
      case "Lizardman":
        smrt += 2;
        spch -= 2;
        break;
      case "Halfling":
        spch += 2;
        smrt -= 2;
        break;
    }
    
    switch (clss) {
      case "Cowboy":
        spd += 1;
        weap = new Weapon("Chicken Cooper", "Pistol");
        break;
      case "Desperado":
        spd += 1;
        weap = new Weapon("Chicken Cooper", "Pistol");
        break;
      case "Doctor":
        smrt += 1;
        abilities[0] = new Ability("Light Heal");
        break;
      case "Brute":
        str += 1;
        weap = new Weapon("Knife", "Sword");
        break;
      case "Wizard":
        smrt += 1;
        weap = new Weapon("Staff", "Staff");
        abilities[0] = new Ability("Fireball");
        break;
      case "Hunter":
        str += 1;
        weap = new Weapon("Old Musket", "Musket");
        break;
    }
    this.hp = str * 5;
    this.mp = smrt * 2;
    maxBP();
  }
  
  //Unknown character
  public CharacterSheet(int po){
    race = "mystery";
    name = "";
    partyOrder = po;
    inv = new Inventory(this,192,partyOrder*74); 
    loadImages(name);
    this.str = 0;
    this.spd = 0;
    this.spch = 0;
    this.smrt = 0;
    this.hp = 0;
    this.mp = 0;
    this.bp = 0;
    for(int k = 0; k < abilities.length; k++){
      abilities[k] = null;
    }
  }
  
  public void loadImages(String n){    
    try{
      portrait = ImageIO.read(new File("images/portraits/colour"+((int)(Math.random()*2)+1)+"/" + race + "_male.png")); 
      bg = ImageIO.read(new File("images/cSheet.png"));
      abilBG = ImageIO.read(new File("images/cMenu.png"));
      dead = ImageIO.read(new File("images/portraits/death.png"));
    }
    catch(IOException e){     
    } 
    invVis = false;
  }
  
  public Weapon getWeap(){
    return weap;
  }
  
  public void heal(int mod){      
    hp += mod;
    
    if(hp > str*2){
      hp = str*2;
      BestOfTheWest.c.addMessage(name + " heals all their wounds!");
    }
    else
      BestOfTheWest.c.addMessage(name + " heals for " + mod + "!");
  }
  public void restoreMP(){
    mp=smrt*2; 
  }
  
  public int shoot(){
    if(bp <= 0){
      return 0;
    }
    else{
      bp --;
      return rollDamage();
    }
  }
  
  public boolean reload() {
    if (weap.getIsRanged()) {
      bp = weap.getClip();
      BestOfTheWest.c.addMessage(name + " reloads his gun");
      return true;
    } else {
      BestOfTheWest.c.addMessage("You can't reload if you have no gun!");
      return false;
    }
    
  }
  
  public void augmentStat(String a)
  {
    switch(a)
    {
    case "a":
      this.str++;
      break;
    case "b":
      this.spd++;
      break;
      case "c":
      this.smrt++;
      break;
      case "d":
      this.spch++;
      break;
    }
  }
  
  public void maxBP(){
    bp = weap.getClip(); 
  }
  
  public void click(int x, int y){
    //BestOfTheWest.c.addMessage("x,y: " + x + "," + y);
    // System.out.println("top(y must b lesr): " + ( (partyOrder*75)+5) + ". bottom(y must be bigr):" + ((partyOrder*75)+40));
    
    if(x > 75 && x < 105 &&  (y < (partyOrder*75)+5  || y > (partyOrder*75)+70)){
      statVis = false;
      invVis = false;
    }
    
    if(x > 5 && x < 69 && y > (partyOrder*75)+5 && y < (partyOrder*75)+69){
      selectedSheet = this;
      statsToString(); 
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
    
    if(statVis == true){
      for(int k = 0; k < abilities.length; k++){
        if(x >= 197 && x <= 320 && y >= 7+((partyOrder*70)+(partyOrder*5)+((k*22)+(k*5))) && y <= ((partyOrder*70)+(partyOrder*5)+((k*22)+(k*5)))+29 && abilities[k] != null){
          if(selectedSheet == this && hp > 0){
            BestOfTheWest.c.clear();
            BestOfTheWest.c.addMessage(name + " has prepared " + abilities[k].toString() + ", press 'C' to cast.");
            selectedAbility = abilities[k];
            caster = this;
          }
          abilities[k].statsToString();
          BestOfTheWest.c.setClear();
        }
      }
    }
    
  }
  
  
  public void statsToString(){
    BestOfTheWest.c.clear(name + ", " + race + " " + clss);
    BestOfTheWest.c.addCloseMessage("Strength: " + str);
    BestOfTheWest.c.addCloseMessage("Speed: " + spd);
    BestOfTheWest.c.addCloseMessage("Smarts: " + smrt);
    BestOfTheWest.c.addCloseMessage("Speech: " + spch);
    BestOfTheWest.c.addCloseMessage("");
    weap.statsToString();
    BestOfTheWest.c.addCloseMessage("");
    armr.statsToString();
    BestOfTheWest.c.addCloseMessage("");
    hat.statsToString();
    BestOfTheWest.c.addCloseMessage("Party Funds: "+BestOfTheWest.partyMoney);
    BestOfTheWest.c.setClear();
  }
  
  public void paint(Graphics2D g2d){
    
    g2d.drawImage(bg, 0, partyOrder*74,null);
    if(isAlive()) {
    	g2d.drawImage(portrait, 5, (partyOrder*74)+5, 64, 64, null);
    } else {
    	g2d.drawImage(dead, 5, (partyOrder*74)+5, 64, 64, null);
    }
    if(selectedSheet == this){
      g2d.setColor(Color.yellow);
      g2d.drawRect(5,(partyOrder*74)+5,64,64);
    }
    g2d.setFont(new Font("Consolas",Font.PLAIN,15));
    g2d.setColor(Color.white);
    g2d.drawString("HP: " + hp, 135, (partyOrder*74)+20);
    g2d.drawString("MP: " + mp, 135, (partyOrder*74)+43);
    g2d.drawString("BP: " + (weap.getIsRanged() ? bp : "/"), 135, (partyOrder*74)+65);
    
    
    if(invVis == true){
      inv.paint(g2d);
    }
    if(statVis == true){
      g2d.drawImage(abilBG, 192, partyOrder*74,null);
      g2d.setFont(new Font("Consolas",Font.PLAIN,15));
      g2d.setColor(Color.white);
      
      for(int k = 0; k < abilities.length; k++){
        //g2d.drawRect(197,7+((partyOrder*70)+(partyOrder*5)+((k*22)+(k*5))),320-192,22);
        if(abilities[k] == null){
          
        }
        else{
          g2d.drawString(abilities[k].toString(), 200, ((partyOrder*74)+20)+((25*k))+(k*2));
        }
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
          BestOfTheWest.c.addMessage(name + " picked up the " + q.name);         
          return true;
        }        
      }
    }
    return false;
  }
  
  public int rollDamage() {
    return ((int) (Math.random() * str) + 1)+weap.rollDamage(); //1 to str damage
  }
  
  public void use(int s1, int s2){
    inv.use(s1, s2);
  }
  
  public void remove(int s1, int s2){
    inv.remove(s1, s2);
  }  
  
  public void setArmour(Armour a, int eType) {
		if (eType == 0) {
			//remove old bonuses
			str -= armr.getStrMod();
			smrt -= armr.getSmrtMod();
			spd -= armr.getSpdMod();
			spch -= armr.getSpchMod();
			
			//set armour
			armr = a;
			str += a.getStrMod();
			smrt += a.getSmrtMod();
			spd += a.getSpdMod();
			spch += a.getSpchMod();
		} else {
			hat = a;
		}
	}
  
  public int getMP(){
    return mp;               
  }
  
  public void lowerMP(int amount){
    mp -= amount;
  }
  
  public Armour getArmour(){
    return armr; 
  }
  
  public Armour getHat(){
    return hat;
  }
  
  public boolean isAlive() {
    return hp > 0;
  }
  
  public void takeDamage(int damage) {
    damage -= armr.protect();
    if(damage < 0)
      damage = 0;
    
    hp -= damage;
    
    BestOfTheWest.c.addMessage(name + " took " + damage + " damage.");
    
    if(hp < 0) {
      hp = 0;
    }
  }
  public int getSpch()
  {
    return spch;
  }
  
  public String getName() {
    return name;
  }
  
  public boolean isLoaded() {
    return bp > 0;
  }

  	public int getSpd() {
		return spd;
	}
}