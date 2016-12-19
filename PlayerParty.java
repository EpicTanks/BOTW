import java.awt.event.KeyEvent;

public class PlayerParty extends DungeonObject {
    private CharacterSheet[] sheets;
    
    //Constructor
    public PlayerParty(int x, int y, Level l, CharacterSheet[] sheets) {
        super(x, y, l, "images/sprites/player.png");
        this.sheets = sheets;
    }
    
    //Does something based on the input. Returns false if the turn is not passed.
    public boolean takeAction(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32:
                Console.addMessage("Waited around for a while.");
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
                Console.addMessage("Invalid key. Press something else.");
                return false; //do nothing with any other keys
        }
    }
    
    //Chooses an action based on what is in the player's way (if anything)
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
    
    //moves the player
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
    
    //Tries to pick up a treasure. Returns false if there is no room in the whole party.
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
    
    //deals damage to an enemy and prints out a message
    private void attack(Enemy e) {
        int d = rollDamage();
        Console.addMessage("Dealt " + d + " damage to the " + e.getName() + ".");
        e.takeDamage(d);
    }
    
    //returns the damage dealt by the first party member that is alive
    private int rollDamage() {
        if (sheets[0].isAlive()) {
            return sheets[0].rollDamage();
        } else if (sheets[1].isAlive()) {
            return sheets[1].rollDamage();
        } else if (sheets[2].isAlive()) {
            return sheets[2].rollDamage();
        } else {
            Console.addMessage("You can't attack if everyone is dead!");
            return 0;
        }
    }
    
    //deals damage to the first party member that is alive
    public void takeDamage(int damage) {
        if (sheets[0].isAlive()) {
            sheets[0].takeDamage(damage);
        } else if (sheets[1].isAlive()) {
            sheets[1].takeDamage(damage);
        } else if (sheets[2].isAlive()) {
            sheets[2].takeDamage(damage);
        } else {
            Console.addMessage("You heckin lose!");
            stall();
        }
    }
   
    //just puts the program into an infinite loop until we make an actual game over screen
    public void stall() {
        while (true);
    }
}
