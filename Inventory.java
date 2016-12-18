import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Inventory{
  // stupid Inventory class, just an array of items, no renderino
  private CharacterSheet owner;
  private Item[][] contents = new Item[2][2];
  public BufferedImage img = null;
  public BufferedImage cMenu = null;
  private int x;
  private int y;
  private boolean cMenuVis = false;
  //THERE IS NO SLOT THREE! HAHAHAHA
  private int selectedItemS1 = 3;
  private int selectedItemS2 = 3;
  
  public Inventory(CharacterSheet o, int x, int y){
    owner = o;
    for(int i = 0; i < 2; i++){
      for(int j = 0; j < 2; j++){
        contents[i][j] = null; 
      }
    }
    this.x = x;
    this.y = y;
    try{
      img = ImageIO.read(new File("images/inventory.png"));
      cMenu = ImageIO.read(new File("images/itemContext.png"));
    }
    catch(IOException e){     
    }
    
  }
  
  public void add(Item b, int s1, int s2){
    
    contents[s1][s2] = b;
    contents[s1][s2].setLocation(this,((s1*2)+s2));
  }
  
  public void remove(int s1, int s2){
    
    contents[s1][s2] = null;
  }
  
  
  public void paint(Graphics2D g2d){
    
    g2d.drawImage(img,x,y,null);
    
    if(cMenuVis == true){
      g2d.drawImage(cMenu,x+200,y,null);
    }
    
    for(int i = 0; i < 2 ; i++){
      for(int j = 0; j < 2 ; j++){
        if(contents[i][j] == null){
          
        }
        else{
          //if there is something in the inventory, proceed to draw it in the CORRECT PLACE 
          contents[i][j].paint(g2d, x+(5*((j%2)+1))+(j%2)*64, y+(5*((i%2)+1))+(i%2)*64); 
        }
      }
    }
  }
  
  public void use(int s1, int s2){
    
    if(contents[s1][s2].getType() == "Weapon"){
      
      Item temp = owner.getWeap();
      contents[s1][s2].use(owner);
      //make sure fists can't get put back into the inventory
      if(temp.getName() == "Fists"){
        
        remove(s1,s2);
      }
      else{
        
        add(temp, s1, s2);
      }
    }
    
    else if(contents[s1][s2].getType() == "Armour"){
      
      Item temp = owner.getArmour();
      contents[s1][s2].use(owner);
      //make sure fists can't get put back into the inventory
      if(temp.getName() == "Dumb!!!"){       
        remove(s1,s2);
      }
      else{        
        add(temp, s1, s2);
      }
    }
    
    else if(contents[s1][s2].getType() == "Hat"){
      
      Item temp = owner.getHat();
      contents[s1][s2].use(owner);
      //make sure fists can't get put back into the inventory
      if(temp.getName() == "Dumb!!!"){        
        remove(s1,s2);
      }
      else{        
        add(temp, s1, s2);
      }
    }
    
    else{
      contents[s1][s2].use(owner);
      remove(s1,s2);
    }
  }
  
  public boolean checkEmpty(int s1, int s2) {
    return contents[s1][s2] == null;
  }
  
  public void click(int x, int y){
    
    if(cMenuVis == true){
      if(x > (this.x+210) && x < (this.x+260) && y > (this.y+50) && y < (this.y+70)){
        System.out.println("IM GONNA DROP"); 
        if(selectedItemS1 == 3 || selectedItemS2 == 3){
          cMenuVis = false;
        }
        else{
          remove(selectedItemS1,selectedItemS2); 
          selectedItemS1 = 3;
          selectedItemS2 = 3;
        }
        cMenuVis = false;
      }
      
      else if(x > (this.x+270) && x < (this.x+310) && y > (this.y+50) && y < (this.y+70)){
        System.out.println("IM GONNA EQUIP"); 
        //check to see if the selected item slots is the STUPID DUMMY number
        if(selectedItemS1 == 3 || selectedItemS2 == 3){
          cMenuVis = false;
        }
        else{
          use(selectedItemS1,selectedItemS2); 
          selectedItemS1 = 3;
          selectedItemS2 = 3;
        }
        cMenuVis = false;
      }
      
      else if(x > (this.x+330) && x < (this.x+370) && y > (this.y+50) && y < (this.y+70)){
        System.out.println("IM GONNA MOVE"); 
        if(owner.selectedSheet == owner){
          System.out.println("This is your item!!!"); 
        }
        //owner.selectedSheet.collect(contents[selectedItemS1][selectedItemS2]);
        else if(owner.selectedSheet.collect(contents[selectedItemS1][selectedItemS2]) == false){
          
          System.out.println("There's no space!!!"); 
        }
        else{
          remove(selectedItemS1,selectedItemS2);
        }
        cMenuVis = false;
      }
    }
    
    for(int i = 0; i < 2 ; i++){
      
      //set the bounds of the button
      int yMin = (this.y+(5*((i%2)+1))+(i%2)*64);
      int yMax = (((this.y+(5*((i%2)+1))+(i%2)*64))+64);
      
      for(int j = 0; j < 2 ; j++){
        //continue setting the bounds of the button
        int xMin = (this.x+(5*((j%2)+1))+(j%2)*64);
        int xMax = ((this.x+(5*((j%2)+1))+(j%2)*64)+64);
        
        if(contents[i][j] == null){
          // System.out.println("There is nothing in slot " + ((i*2)+j));
        }
        else{
          //dev code
          // System.out.println("Item, Slot:" + contents[i][j].getName() + "," + contents[i][j].getSlot()); 
          // System.out.println("x min, x max:" + xMin + "," + xMax); 
          // System.out.println("y min, y max:" + yMin + "," + yMax);
          //  System.out.println("mouse x,y: " + x + "," + y); 
          //check if the mosue is on the item
          
          
          
          if(x > xMin && x < xMax && y > yMin && y < yMax){
            System.out.println("you clicked: " + contents[i][j].getName() + " in slot " + contents[i][j].getSlot()); 
            selectedItemS1 = i;
            selectedItemS2 = j;
            if(cMenuVis == false){
              cMenuVis = true;
            }
            else{
              cMenuVis = false;
            }
          }
          
        }
        
      }
    }
  }   
}
