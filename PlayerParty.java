import java.awt.event.KeyEvent;

public class PlayerParty extends DungeonObject {
    private CharacterSheet[] sheets;
    private boolean isShooting = false;
    
    //Constructor
    public PlayerParty(int x, int y, Level l, CharacterSheet[] sheets) {
        super(x, y, l, "images/sprites/player.png");
        this.sheets = sheets;
    }
    
    //Does something based on the input. Returns false if the turn is not passed.
    public boolean takeAction(KeyEvent e) {
        //System.out.println(e.getKeyCode());
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
            case 82:
            	return getFirstAlive().reload();
            case 83:
                return shoot();
            default:
                Console.addMessage("Invalid key. Press something else.");
                return false; //do nothing with any other keys
        }
    }
    
    //Chooses an action based on what is in the player's way (if anything)
    public boolean action(String direction) {
        int dir = 0;
        Object nextTo = null;
        
        if (isShooting) { //change
            if (getFirstAlive().getWeap().getIsRanged()) {
                Console.addMessage("You fire!");
                isShooting = false;
                tryShoot(direction);
            } else {
                Console.addMessage("You fail at firing your melee weapon");
            }
            isShooting = false;
        } else {
        	switch (direction) {
        		case "Up":
        			nextTo = l.getThingAt(x, y - 1);
        			dir = 0;
        			break;
        		case "Right":
        			nextTo = l.getThingAt(x + 1, y);
        			dir = 1;
        			break;
        		case "Down":
        			nextTo = l.getThingAt(x, y + 1);
        			dir = 2;
        			break;
        		case "Left":
        			nextTo = l.getThingAt(x - 1, y);
        			dir = 3;
        			break;
        		default:
        			throw new RuntimeException("Bad direction.");
        	}
        	System.out.println(nextTo != null ? nextTo.getClass() : null);
            
            if (nextTo instanceof TreasureBox) {
            	System.out.println("wowee");
                if (takeTreasure((TreasureBox)nextTo))
                    l.removeObject((TreasureBox)nextTo);
                return true;
            } else if (nextTo instanceof Enemy) {
                attack((Enemy)nextTo);
                return true;
            } else if (!(nextTo instanceof Character)) {
                move(dir);
                return true;
            }
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
    
    private boolean shoot() {
        if (!isShooting) {
            Console.addMessage("You get ready to shoot your gun.");
            isShooting = true;
        } else {
            Console.addMessage("You put down your gun.");
            isShooting = false;
        }
        return false;
    }
    
    private void tryShoot(String direction) {
    	switch (direction) {
    		case "Up":
    			shootInDirection(0, -1);
    			break;
    		case "Right":
    			shootInDirection(1, 0);
    			break;
    		case "Down":
    			shootInDirection(0, 1);
    			break;
    		case "Left":
    			shootInDirection(-1, 0);
    			break;
    		default:
    			throw new RuntimeException("Bad direction.");
    	}
    }
    
    private void shootInDirection(int xmod, int ymod) {
    	int range = getFirstAlive().getWeap().getRange();
    	for (int i = 0; i < range; i++) {
    		Object target = l.getThingAt(x + (i * xmod), y + (i * ymod));
    		if (target instanceof Enemy) {
    			rangedAttack((Enemy) target);
    			return;
    		}
    		if (target instanceof Character) {
    			Console.addMessage("You hit a wall xd");
    			return;
    		}
    	}
    }
    
    //deals damage to an enemy and prints out a message
    private void attack(Enemy e) {
        int d = rollDamage();
        Console.addMessage("Dealt " + d + " damage to the " + e.getName() + ".");
        e.takeDamage(d);
    }
    
    private void rangedAttack(Enemy e) {
        e.takeDamage(getFirstAlive().shoot());
    }
    
    //returns the damage dealt by the first party member that is alive
    private int rollDamage() {
        return getFirstAlive().rollDamage();
    }
    
    //deals damage to the first party member that is alive
    public void takeDamage(int damage) {
        getFirstAlive().takeDamage(damage);
    }
    
    public CharacterSheet getFirstAlive() {
        if (sheets[0].isAlive()) {
            return sheets[0];
        } else if (sheets[1].isAlive()) {
            return sheets[1];
        } else if (sheets[2].isAlive()) {
            return sheets[2];
        } else {
            Console.addMessage("You heckin lose!");
            stall();
            return null;
        }
    }
   
    //just puts the program into an infinite loop until we make an actual game over screen
    public void stall() {
        while (true);
    }
}
