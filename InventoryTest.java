import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.KeyListener;
import java.awt.event.*; 

public class InventoryTest extends JPanel{
  
  private CharacterSheet[] bobs = new CharacterSheet[3];
  private SpellBook gboy = new SpellBook();
  private SpellBook bboy = new SpellBook();
  private SpellBook sboy = new SpellBook();
  private SpellBook aboy = new SpellBook();
  private CoolMouseListener cml = new CoolMouseListener(bobs);
  
  public InventoryTest(){
    for(int i = 0; i < bobs.length; i++){
     bobs[i] = new CharacterSheet("bob"+i, i); 
    }
    
    bobs[0].collect(gboy, 0,0);
    bobs[2].collect(bboy, 1,0);
    bobs[2].collect(sboy, 0,1);
    bobs[1].collect(aboy, 1,1);
    addMouseListener(cml);
    setFocusable(true); 
  }
  
  
  public void paint(Graphics g){
    Graphics2D g2d = (Graphics2D) g;
    super.paint(g);
    for(int i = 0; i < bobs.length; i++){
     bobs[i].paint(g2d); 
    }
  }
  
  public static void main(String[] args){
    JFrame freme = new JFrame("Best of the West -- Inventroy Tets");
    InventoryTest it = new InventoryTest();
    freme.add(it);
    freme.setSize(800,600);
    freme.setVisible(true);
    freme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
    
    while(true){
     it.repaint(); 
    }
    
  }
}