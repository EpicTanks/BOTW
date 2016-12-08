import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public abstract class Item {
    private Inventory location;
    private String name;
    private String description;
    private int price;
    private int slot;
    
    public abstract void use(CharacterSheet c);
    public abstract void paint(Graphics2D g2d, int x, int y);
    public abstract void setLocation(Inventory l, int s);
    public abstract Inventory getLocation();
    public abstract int getSlot();
    public abstract String getName();
    
    public static Item getItem() {
        switch((int) (Math.random() * 2) + 1) {
            case 1:
                return new Armour();
            case 2:
                return new SpellBook();
            default:
                throw new RuntimeException("Jake wrote a bad switch statement.");
        }
    }
}
