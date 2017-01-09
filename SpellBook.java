import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class SpellBook extends Item{
    //habstract fields
    public BufferedImage img = null;
    private String[][] spellCatalogue ={
        {"Black","Fireball","Iceball","Ballball","Magic Bullet","Lesser Curseball","Okay Curseball","Good Curseball"},
        {"White","Light Heal","Heal","Banish","Circle Heal","Full Heal","Light Heal","Heal"},
        {"Grey","Steal Blood","Steal Soul","Haste","Raise Dead","Steal Blood","Steal Soul","Haste"},
        {"Green","Turn Aminal","Supar Strenth","Grasbol","Turn Aminal","Supar Strenth","Grasbol","Turn Aminal"},
        {"Orange","Fan The Hammer","Lever Screw","Freak Shot","Lethal Shot","Fan The Hammer","Lever Screw","Freak Shot"}   
    };
    
    
    //spellbook specific fields
    private String spell;
    private String colour;
    private int reqSmarts;
    private Ability contents;
    
    public SpellBook(){ 
        int i = (int) (5*Math.random());
        int j = (int) (7*Math.random()+1);
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
                if(c.abilities[k] == contents){
                    System.out.println("You already know " + name);
                    return;
                }
            }
            System.out.println("You learned " + name);
            System.out.println(name + ":" + description);
            c.abilities[c.noOfAbils] = contents;
            c.noOfAbils+=1;
        }    
    }
}
