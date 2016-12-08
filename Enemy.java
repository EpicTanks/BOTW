import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Enemy extends DungeonObject {
    public String name = "";
    static String temp = "";
    private static final String[][] possibleNames = {
        {"bandit", "rat", "rangerrat", "giantrat", "bandit", "koolbandit", "ghostlybandit", "dwarfbandit", "giblo"},
        {"haunted", "rat", "rangerrat", "giantrat", "ghostlybandit"},
        {"mines", "rat", "rangerrat", "giantrat", "dwarfbandit", "caveogre", "kobold", "grayooze"},
        {"canyon", "rat", "rangerrat", "giantrat", "dwarfbandit", "grayooze", "wildbear", "ruinraider", "ratssassin"},
        {"ruins", "rat", "rangerrat", "giantrat", "kobold", "wildbear", "hedgehog", "gianthedgehog", "ruinraider", "elderelf"},
        {"dragonhell", "rat", "rangerrat", "giantrat", "dragongod"},
        {"nega", "rat", "rangerrat", "giantrat", "superbandit"}
    };
    
    public Enemy(int x, int y, Level l, String theme) {
        super(x, y, l, "art/sprites/enemies/" + (temp = findName(theme)) + ".png");
        name = temp;
    }
    
    public static String findName(String theme) {
        for (int i = 0; i < possibleNames.length; i++) {
            if (possibleNames[i][0].equals(theme)) {
                return possibleNames[i][(int) (Math.random() * possibleNames[i].length - 1) + 1];
            }
        }
        return "error";
    }
    
    public void takeTurn(PlayerParty p) {
        if ((x == p.getX() && (y == p.getY() - 1 || y == p.getY() + 1))
                || (y == p.getY() && (x == p.getX() - 1 || x == p.getX() + 1))) {
            //attack(PlayerParty);
        } else if (canSee(p)){
            moveToward(p);
        } else {
            moveAtRandom();
        }
    }
    
    private void moveToward(PlayerParty p) {
        if(x < p.getX() && l.isEmpty(x + 1, y)) {
            x++;
        } else if(x > p.getX() && l.isEmpty(x - 1, y)) {
            x--;
        } else if (y < p.getY() && l.isEmpty(x, y + 1)) {
            y++;
        } else if(y > p.getY() && l.isEmpty(x, y - 1)) {
            y--;
        }
    }
    
    //move in a random direction (idling)
    private void moveAtRandom() {
        int r = (int) (Math.random() * 4) + 1;
        
        switch(r) {
            case 1: 
                if(l.isEmpty(x, y + 1)) {
                    y++;
                }
                break;
            case 2:
                if(l.isEmpty(x + 1, y)) {
                    x++;
                }
                break;
            case 3:
                if(l.isEmpty(x, y - 1)) {
                    y--;
                }
                break;
            case 4:
                if(l.isEmpty(x - 1, y)) {
                    x--;
                }
                break;
            default:
                break;
        }
    }
    
    //return true if this enemy can see the given player
    public boolean canSee(PlayerParty p) {
        if (getDistanceTo(p) <= 8) {
            return true;
        }
        return false;
    }
    
    //return the distance between this enemy and the given player
    public int getDistanceTo(PlayerParty p) {
        int xDis = Math.abs(p.getX()-x);
        int yDis = Math.abs(p.getY()-y);
        
        return (int) Math.sqrt(Math.abs(xDis * xDis + yDis * yDis));
    }
}
