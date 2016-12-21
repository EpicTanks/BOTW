import java.awt.*;

public abstract class Item {
  protected Inventory location;
  protected String name;
  protected String description;
  protected String type;
  protected int price;
  protected int slot;
  
  public abstract void use(CharacterSheet c);
  public abstract void paint(Graphics2D g2d, int x, int y);
  public abstract void setLocation(Inventory l, int s);
  public abstract Inventory getLocation();
  public abstract int getSlot();
  public String getName(){
   return name; 
  }
  
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
  
  public String getType(){
   return type; 
  }
}