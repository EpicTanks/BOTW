import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public abstract class Item {
  protected Inventory location;
  protected String name;
  protected String description;
  protected int price;
  protected int slot;
  
  public abstract void use(CharacterSheet c);
  public abstract void paint(Graphics2D g2d, int x, int y);
  public abstract void setLocation(Inventory l, int s);
  public abstract Inventory getLocation();
  public abstract int getSlot();
  public abstract String getName();
  
  public static Item getItem() {
    switch((int) (Math.random() * 3) + 1) {
      case 1:
        return new Armour();
      case 2:
        return new SpellBook();
      case 3:
        return new Weapon();
      default:
        throw new RuntimeException("Jake wrote a bad switch statement. Blame him.");
    }
  }
}