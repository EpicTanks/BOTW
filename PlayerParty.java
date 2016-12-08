import java.awt.event.KeyEvent;

public class PlayerParty extends DungeonObject {
    private CharacterSheet[] sheets;
    
    public PlayerParty(int x, int y, Level l, CharacterSheet[] sheets) {
        super(x, y, l, "images/sprites/player.png");
        this.sheets = sheets;
    }
    
    public boolean takeAction(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32:
                return true; //skip the turn with spacebar
            case 38:
                return action("Up"); //move up with up arrow
            case 40:
                return action("Down"); //move down with down arrow
            case 37:
                return action("Left"); //move left with left arrow
            case 39:
                return action("Right"); //move right with right arrow
            default:
                return false; //do nothing with any other keys
        }
    }
    
    public boolean action(String direction) {
        if (direction.equals("Up")) {
            if (l.containsTreasure(x, y - 1)) {
                if (takeTreasure(l.getTreasure(x, y - 1)))
                    l.deleteTreasureAt(x, y - 1);
            }
            if (l.containsEnemy(x, y - 1)) {
                //fight it
            } else if (l.isEmpty(x, y - 1)) {
                move(0);
            }
            return true;
        } else if (direction.equals("Down")) {
            if (l.containsTreasure(x, y + 1)) {
                if (takeTreasure(l.getTreasure(x, y + 1)))
                    l.deleteTreasureAt(x, y + 1);
            } else if (l.containsEnemy(x, y + 1)) {
                //fight it
            } else if (l.isEmpty(x, y + 1)) {
                move(2);
            }
            return true;
        } else if (direction.equals("Left")) {
            if (l.containsTreasure(x - 1, y)) {
                if (takeTreasure(l.getTreasure(x - 1, y)))
                    l.deleteTreasureAt(x - 1, y);
            } else if (l.containsEnemy(x - 1, y)) {
                //fight it
            } else if (l.isEmpty(x - 1, y)) {
                move(3);
            }
            return true;
        } else if (direction.equals("Right")) {
            if (l.containsTreasure(x + 1, y)) {
                if (takeTreasure(l.getTreasure(x + 1, y)))
                    l.deleteTreasureAt(x + 1, y);
            } else if (l.containsEnemy(x + 1, y)) {
                //fight it
            } else if (l.isEmpty(x + 1, y)) {
                move(1);
            }
            return true;
        }
        return false;
    }
    
    private boolean takeTreasure(TreasureBox t) {
        if (sheets[0].collect(t.contents())) {
            return true;
        } else if (sheets[1].collect(t.contents())) {
            return true;
        } else if (sheets[2].collect(t.contents())) {
            return true;
        }
        return false;
    }
    
    public void move(int d) {
        switch(d) {
            case 0:
                y--;
                break;
            case 1:
                x++;
                break;
            case 2:
                y++;
                break;
            case 3:
                x--;
                break;
            default:
                throw new RuntimeException("Jake wrote a bad switch statement.");
        }
    }
}
