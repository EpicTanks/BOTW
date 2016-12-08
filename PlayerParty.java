import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class PlayerParty extends DungeonObject {
    //CharacterSheet[] sheets;
    
    public PlayerParty(int x, int y, Level l) {//, CharacterSheet[] sheets) {
        super(x, y, l, "art/sprites/enemies/rat.png");
        //this.sheets = sheets;
    }
    
    public boolean move(String direction) {
        if (direction.equals("Up") && l.isEmpty(x, y - 1)) {
            y--;
        } else if (direction.equals("Down") && l.isEmpty(x, y + 1)) {
            y++;
        } else if (direction.equals("Left") && l.isEmpty(x - 1, y)) {
            x--;
        } else if (direction.equals("Right") && l.isEmpty(x + 1, y)) {
            x++;
        } else {
            return false;
        }
        return true;
    }
}