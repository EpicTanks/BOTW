import java.awt.event.KeyEvent;

public class PlayerParty extends DungeonObject {
    private CharacterSheet[] sheets;
    
    public PlayerParty(int x, int y, Level l, CharacterSheet[] sheets) {
        super(x, y, l, "images/sprites/player.png");
        this.sheets = sheets;
    }
    
    public boolean takeAction(KeyEvent e) {
        System.out.println(e.getKeyCode());
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
            case 46:
                return l.useStairs(x, y);
            default:
                return false; //do nothing with any other keys
        }
    }
    
    public boolean action(String direction) {
        int a = 0;
        int b = 0;
        int dir = 0;
        
        if (direction.equals("Up")) {
            a = x;
            b = y - 1;
            dir = 0;
        } else if (direction.equals("Down")) {
            a = x;
            b = y + 1;
            dir = 2;
        } else if (direction.equals("Left")) {
            a = x - 1;
            b = y;
            dir = 3;
        } else if (direction.equals("Right")) {
            a = x + 1;
            b = y;
            dir = 1;
        }
        
        if (l.containsTreasure(a, b)) {
            if (takeTreasure(l.getTreasure(a, b)))
                l.deleteTreasureAt(a, b);
            return true;
        } else if (l.containsEnemy(a, b)) {
            attack(l.getEnemy(a, b));
            return true;
        } else if (l.isEmpty(a, b)) {
            move(dir);
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
    
    private void attack(Enemy e) {
        int d = rollDamage();
        System.out.println("Dealt " + d + " damage to the " + e.getName() + ".");
        e.takeDamage(d);
    }
    
    private int rollDamage() {
        if (sheets[0].isAlive()) {
            return sheets[0].rollDamage();
        } else if (sheets[1].isAlive()) {
            return sheets[1].rollDamage();
        } else if (sheets[2].isAlive()) {
            return sheets[2].rollDamage();
        } else {
            System.out.println("You can't attack if everyone is dead!");
            return 0;
        }
    }
    
    public void takeDamage(int damage) {
        if (sheets[0].isAlive()) {
            sheets[0].takeDamage(damage);
        } else if (sheets[1].isAlive()) {
            sheets[1].takeDamage(damage);
        } else if (sheets[2].isAlive()) {
            sheets[2].takeDamage(damage);
        } else {
            System.out.println("You heckin lose!");
        }
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
