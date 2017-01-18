import javax.imageio.*;  
import java.io.*;  
import java.awt.*;  
import java.awt.image.*;  

public class SpellBook extends Item{  
    //habstract fields  
    public BufferedImage img = null;  
    private String[][] spellCatalogue ={  
        {"Black","Fireball","Iceball","Ballball"},  
        {"White","Light Heal","Heal","Circle Heal","Good Circle Heal"},        
        {"Green","Grasbol"},  
        //Below Spells contain special effects that need extra code to be dealt with  
        //As such, they will be added if we have time 
      //"Magic Bullet","Lesser Curseball","Okay Curseball","Good Curseball"
      //"Supar Strenth"
        {"Grey","Steal Blood","Steal Soul","Haste","Raise Dead","Steal Blood","Steal Soul","Haste"},  
        {"Orange","Fan The Hammer","Lever Screw","Freak Shot","Lethal Shot","Fan The Hammer","Lever Screw","Freak Shot"}     
    };  
    
    
    //spellbook specific fields  
    private String spell;  
    private String colour;  
    private int reqSmarts;  
    private Ability contents;  
    
    public SpellBook(){   
        int i = (int) (3*Math.random());  
        int j = (int) ((spellCatalogue[i].length-1)*Math.random()+1);  
        name = spellCatalogue[i][j];  
        description = "Wowee! What a classy book!";  
        price = 6;  
        spell = spellCatalogue[i][j];  
        colour = spellCatalogue[i][0];  
        reqSmarts = 5;  
        type = "Spellbook";  
        contents = new Ability(name);  
        try{  
            img = ImageIO.read(new File("images/items/spellbooks/" + colour + "_book.png"));   
        }  
        catch(IOException e){       
        }  
    }  
    
    public Inventory getLocation(){  
        return location;   
    }  
    
    public int getSlot(){  
        return slot;   
    }  
    
    
    public void setLocation(Inventory l, int s){  
        location = l;   
        slot = s;  
    }  
    
    public void paint(Graphics2D g2d, int x, int y){  
        g2d.drawImage(img, x, y, 64, 64, null);  
    }  
    
    public void use(CharacterSheet c){  
        if(c.noOfAbils == 5){  
            System.out.println("You can't learn any more abilities!");  
        }  
        else{  
            for(int k = 0; k < c.abilities.length; k++){
              if(c.abilities[k]!=null){
                if(c.abilities[k].toString() == name){  
                    System.out.println("You already know " + name);  
                    return;  
                }
                }  
            }  
            System.out.println("You learned " + name);  
            System.out.println(name + ":" + description);  
            c.abilities[c.noOfAbils] = contents;  
            c.noOfAbils+=1;  
        }      
    }
    
    
    public void statsToString(){
      BestOfTheWest.c.addCloseMessage("Spell: " + name);
        BestOfTheWest.c.addCloseMessage("");
        if(contents.getDamage() > 0){
        BestOfTheWest.c.addCloseMessage("Damage: " + contents.getDamage());
        }
        if(contents.getDamage() < 0){
        BestOfTheWest.c.addCloseMessage("Healing: " + -contents.getDamage());
        }
        if(!contents.getGood()){
          BestOfTheWest.c.addCloseMessage("Range: " + contents.getRange());
        }
        BestOfTheWest.c.addCloseMessage("MP Cost: " + contents.getMPCost());
    }
}  
