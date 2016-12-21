import java.io.*;

public class Enemy extends DungeonObject {
    //constants
    private static final int SIGHT_RANGE = 8;
    
    //naming variables
    private String name = "";
    static String temp = "";
    private static final String[][] possibleNames = {
        {"bandit", "Rat", "Ranger Rat", "Giant Rat", "Bandit", "Kool Bandit", "Ghostly Bandit", "Dwarf Bandit", "Giblo"},
        {"haunted", "Rat", "Ranger Rat", "Giant Rat", "Ghostly Bandit"},
        {"mines", "Rat", "Ranger Rat", "Giant Rat", "Dwarf Bandit", "Cave Ogre", "Kobold", "Gray Ooze"},
        {"canyon", "Rat", "Ranger Rat", "Giant Rat", "Dwarf Bandit", "Gray Ooze", "Wild Bear", "Ruin Raider", "Ratssassin"},
        {"ruins", "Rat", "Ranger Rat", "Giant Rat", "Kobold", "Wild Bear", "Hedgehog", "Giant Hedgehog", "Ruin Raider", "Elder Elf"},
        {"dragonhell", "Rat", "Ranger Rat", "Giant Rat", "Dragon God"},
        {"nega", "Rat", "Ranger Rat", "Giant Rat", "Super Bandit"}
    };
    
    //Stats (default is 1 for everything)
    private int maxhp = 1;
    private int hp = 1;
    private int strength = 1;
    private int speed = 1;
    
    //Ranged status
    private int isRanged;
    
    
    //Constructor
    public Enemy(int x, int y, Level l, String theme) {
        super(x, y, "images/sprites/enemies/" + (temp = findName(theme)) + ".png");
        name = temp;
        try {
            setData();
        } catch (IOException e) {
            System.out.println("setData() failed, default stats kept.");
        }
        scaleStats(l.getFloor() - 1);
    }
    
    //Returns a name based on the provided theme.
    public static String findName(String theme) {
        for (int i = 0; i < possibleNames.length; i++) {
            if (possibleNames[i][0].equals(theme)) {
                return possibleNames[i][(int) (Math.random() * possibleNames[i].length - 1) + 1];
            }
        }
        return "error";
    }
    
    //Sets this enemies stats and loads its picture.
    private void setData() throws IOException{
        
        //load file
        BufferedReader reader = null;
        try {
            File file = new File("gamedata/enemies/" + name + ".txt");
            reader = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e) {
            System.out.println("Whoops! Missing gamedata/enemies/" + name + ".txt");
            throw new IOException();
        }
        
        //set stats
        strength = Integer.parseInt(reader.readLine());
        speed = Integer.parseInt(reader.readLine());
        maxhp = strength * 2;
        hp = maxhp;
        
        //set ranged status
        isRanged = Integer.parseInt(reader.readLine());
        
        reader.close();
    }
    
    //Increase this enemies stats based on the provided int.
    public void scaleStats(int mod) {
        maxhp += 2 * mod;
        hp = maxhp;
        strength += mod;
        speed += mod;
    }
    
    //Choose an action.
    public void takeTurn(PlayerParty p) {
        if ((x == p.getX() && (y == p.getY() - 1 || y == p.getY() + 1))
                || (y == p.getY() && (x == p.getX() - 1 || x == p.getX() + 1))) {
            if(isRanged != 1) {
                attack(p); //pure ranged enemies cant attack within 1 square
            }
        } else if (canSee(p)){
            if (isRanged > 0 && getDistanceTo(p) < 5) {
                attack(p);
            } else {
            	moveToward(p);
            }
        } else {
            moveAtRandom();
        }
    }
    
    //Take an amount of damage. Also calls the method to remove this enemy from the level.
    public void takeDamage(int damage) {
        hp -= damage;
        if(hp <= 0) {
        	BestOfTheWest.getLevel().removeObject(this);
            BestOfTheWest.c.addMessage("The " + name + " was slain.");
        }
    }
    
    public String getName() {
        return name;
    }
    
    private void attack(PlayerParty p) {
        if(isRanged > 0) {
        	BestOfTheWest.c.addMessage("The " + name + " shot you!");
        } else {
        	BestOfTheWest.c.addMessage("The " + name + " punched ya!");
        }
        p.takeDamage(strength);
    }
    
    private void moveToward(PlayerParty p) {
        if(speed > 0 && name != "grayooze") {
            int ul = shortestPath(SIGHT_RANGE, x, y - 1);
            int dl = shortestPath(SIGHT_RANGE, x, y + 1);
            int ll = shortestPath(SIGHT_RANGE, x - 1, y);
            int rl = shortestPath(SIGHT_RANGE, x + 1, y);
            int high = max4(ul, dl, ll, rl);
            
            if(rl == high && BestOfTheWest.getLevel().isEmpty(x + 1, y)) {
                x++;
            } else if (dl == high && BestOfTheWest.getLevel().isEmpty(x, y + 1)) {
                y++;
            } else if(ll == high && BestOfTheWest.getLevel().isEmpty(x - 1, y)) {
                x--;
            } else if (ul == high && BestOfTheWest.getLevel().isEmpty(x, y - 1)) {
                y--;
            }
        } else {
            //Do nothing if you have no speed
        }
    }
    
    private int shortestPath(int moves, int cX, int cY) {
        if (moves <= 0 || !BestOfTheWest.getLevel().isEmpty(cX, cY)) {
            return -1;
        }
        if (BestOfTheWest.getLevel().containsPlayer(cX, cY)) {
            return moves;
        }
        moves--;
        return max4(shortestPath(moves, cX + 1, cY), shortestPath(moves, cX - 1, cY), 
                    shortestPath(moves, cX, cY + 1), shortestPath(moves, cX, cY - 1));
    }
    
    private static int max4(int a, int b, int c, int d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }
    
    //move in a random direction (idling)
    private void moveAtRandom() {
        if (speed > 0 && name != "grayooze") {
            int r = (int) (Math.random() * 4) + 1;
            
            switch(r) {
                case 1: 
                    if(BestOfTheWest.getLevel().isEmpty(x, y + 1)) {
                    y++;
                }
                    break;
                case 2:
                    if(BestOfTheWest.getLevel().isEmpty(x + 1, y)) {
                    x++;
                }
                    break;
                case 3:
                    if(BestOfTheWest.getLevel().isEmpty(x, y - 1)) {
                    y--;
                }
                    break;
                case 4:
                    if(BestOfTheWest.getLevel().isEmpty(x - 1, y)) {
                    x--;
                }
                    break;
                default:
                    break;
            }
        } else {
            //Do nothing if you have no speed
        }
    }
    
    //return true if this enemy can see the given player
    private boolean canSee(PlayerParty p) {
        if (getDistanceTo(p) <= SIGHT_RANGE) {
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
