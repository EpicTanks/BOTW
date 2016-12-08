import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class TreasureBox extends DungeonObject {
    
    private Item contains;
    public TreasureBox(int x, int y, Level l) {
        super(x, y, l, "images/sprites/box.png");
        contains = Item.getItem();
    }
    
    public Item collect() {
        l.deleteTreasureAt(x, y);
        return contains;
    }
}

