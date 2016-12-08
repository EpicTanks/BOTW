import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class TreasureBox extends DungeonObject {
    public TreasureBox(int x, int y, Level l) {
        super(x, y, l, "art/sprites/box.png");
    }
}