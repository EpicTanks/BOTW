import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class PlayerParty extends DungeonObject {
    private boolean isReady = false;
    private BufferedImage overlay = null;
    
    //Constructor
    public PlayerParty(int x, int y) {
        super(x, y, "images/sprites/player.png");
        
        try {
            overlay = ImageIO.read(new File("images/tiles/overlay.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    //Does something based on the input. Returns false if the turn is not passed.
    public boolean takeAction(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32:
                BestOfTheWest.c.addMessage("Waited around for a while.");
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
                return BestOfTheWest.getLevel().useStairs(x, y); //use stairs with .
            case 82:
                return getFirstAlive().reload(); //reload with r
            case 83:
                return changeReadiness();
            default:
                BestOfTheWest.c.addMessage("Invalid key. Press something else.");
                return false; //do nothing with any other keys
        }
    }
    
    //Chooses an action based on what is in the player's way (if anything)
    public boolean action(String direction) {
        Object nextTo = null;
        
        if (isReady) {
            if (getFirstAlive().getWeap().getIsRanged()) {
                isReady = false;
                tryShoot(direction);
            } else {
                BestOfTheWest.c.addMessage("You fail at firing your melee weapon");
            }
            isReady = false;
            return true;
        } else {
            switch (direction) {
                case "Up":
                    nextTo = BestOfTheWest.getLevel().getThingAt(x, y - 1);
                    break;
                case "Right":
                    nextTo = BestOfTheWest.getLevel().getThingAt(x + 1, y);
                    break;
                case "Down":
                    nextTo = BestOfTheWest.getLevel().getThingAt(x, y + 1);
                    break;
                case "Left":
                    nextTo = BestOfTheWest.getLevel().getThingAt(x - 1, y);
                    break;
                default:
                    throw new RuntimeException("Bad direction.");
            }
            
            if (nextTo instanceof TreasureBox) {
                if (takeTreasure((TreasureBox)nextTo))
                    BestOfTheWest.getLevel().removeObject((TreasureBox)nextTo);
                return true;
            } else if (nextTo instanceof Enemy) {
                attack((Enemy)nextTo, false);
                return true;
            } else if (!(nextTo instanceof Character)) {
                move(direction);
                return true;
            }
        }
        
        return false;
    }
    
    //moves the player
    public void move(String direction) {
        switch(direction) {
            case "Up":
                y--;
                break;
            case "Right":
                x++;
                break;
            case "Down":
                y++;
                break;
            case "Left":
                x--;
                break;
            default:
                throw new RuntimeException("Jake wrote a bad switch statement.");
        }
    }
    
    //Tries to pick up a treasure. Returns false if there is no room in the whole party.
    private boolean takeTreasure(TreasureBox t) {
        if (BestOfTheWest.sheets[0].collect(t.contents())) {
            return true;
        } else if (BestOfTheWest.sheets[1].collect(t.contents())) {
            return true;
        } else if (BestOfTheWest.sheets[2].collect(t.contents())) {
            return true;
        }
        return false;
    }
    
    private boolean changeReadiness() {
        if (!isReady) {
            BestOfTheWest.c.addMessage("You get ready to shoot your gun.");
            isReady = true;
        } else {
            BestOfTheWest.c.addMessage("You put down your gun.");
            isReady = false;
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
        
        BestOfTheWest.c.addCloseMessage(getFirstAlive().getName() + " pulls the trigger...");
        if (!getFirstAlive().isLoaded()) {
            BestOfTheWest.c.addMessage("And nothing happens! " + getFirstAlive().getName() + " needs to reload!");
            return;
        } else {
            BestOfTheWest.c.addCloseMessage("The gun fires!");
        }
        for (int i = 0; i <= range; i++) {
            Object target = BestOfTheWest.getLevel().getThingAt(x + (i * xmod), y + (i * ymod));
            if (target instanceof Enemy) {
                attack((Enemy) target, true);
                return;
            } else if (target instanceof Character) {
                getFirstAlive().shoot();
                BestOfTheWest.c.addMessage("The bullet harmlessly bounced off the wall.");
                return;
            }
        }
        getFirstAlive().shoot();
        BestOfTheWest.c.addMessage("The bullet quickly lost speed and hit the ground.");
    }
    
    //deals damage to an enemy and prints out a message
    private void attack(Enemy e, boolean ranged) {
        int d;
        if (ranged) {
            d = getFirstAlive().shoot();
        } else {
            d = getFirstAlive().rollDamage();
        }
        BestOfTheWest.c.addMessage("Dealt " + d + " damage to the " + e.getName() + ".");
        e.takeDamage(d);
    }
    
    //deals damage to the first party member that is alive
    public void takeDamage(int damage) {
        getFirstAlive().takeDamage(damage);
    }
    
    public CharacterSheet getFirstAlive() {
        if (BestOfTheWest.sheets[0].isAlive()) {
            return BestOfTheWest.sheets[0];
        } else if (BestOfTheWest.sheets[1].isAlive()) {
            return BestOfTheWest.sheets[1];
        } else if (BestOfTheWest.sheets[2].isAlive()) {
            return BestOfTheWest.sheets[2];
        } else {
            BestOfTheWest.c.addMessage("You heckin lose!");
            stall();
            return null;
        }
    }
    
    //just puts the program into an infinite loop until we make an actual game over screen
    public void stall() {
        while (true);
    }
    
    @Override
    public void render(Graphics2D g2d, int scale, int offset) {
        super.render(g2d, scale, offset);
        
        if (isReady && getFirstAlive().getWeap().getIsRanged()) {
            for (int i = 1; i <= getFirstAlive().getWeap().getRange(); i++) {
                g2d.drawImage(overlay, ((x + i) * scale) + offset, y * scale, scale, scale, null);
                g2d.drawImage(overlay, ((x - i) * scale) + offset, y * scale, scale, scale, null);
                g2d.drawImage(overlay, (x * scale) + offset, (y + i) * scale, scale, scale, null);
                g2d.drawImage(overlay, (x * scale) + offset, (y - i) * scale, scale, scale, null);
            }
        }
    }
}
