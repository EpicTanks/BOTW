import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.util.ArrayList;

public class Level {
    private int floor;
    private String theme;
    private char[][] layout = new char[36][36];
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<TreasureBox> treasureList = new ArrayList<TreasureBox>();
    private PlayerParty player;
    private CharacterSheet[] sheets;
    private LevelTest lt;
    private BufferedImage floorImage = null;
    private BufferedImage wallImage = null;
    private BufferedImage wall2Image = null;
    private BufferedImage cornerImage = null;
    private BufferedImage stairUp = null;
    private BufferedImage stairDown = null;
    private boolean playerTurn = true;
    private static final int SCALE = 16;
    private static final int OFFSET = 192;
    
    public Level(int floor, CharacterSheet[] sheets, LevelTest lt) {
        this.floor = floor; //will be used to set the theme later
        this.sheets = sheets;
        this.lt = lt;
        
        //set the theme (for now, just mines)
        theme = "mines";
        
        //try to load the images based on the theme
        try {
            floorImage = ImageIO.read(new File("images/tiles/" + theme + "/floor.png"));
            wallImage = ImageIO.read(new File("images/tiles/" + theme + "/wall.png"));
            wall2Image = ImageIO.read(new File("images/tiles/" + theme + "/wall2.png"));
            cornerImage = ImageIO.read(new File("images/tiles/" + theme + "/corner.png"));
            stairUp = ImageIO.read(new File("images/tiles/StairsUp.png"));
            stairDown = ImageIO.read(new File("images/tiles/Stairs.png"));
        } catch (IOException e) {
            System.out.println("Whoops! Missing an image in art/tiles/" + theme + "/");
        }
        genLevel();
        
        
        //create enemies and treasure boxes
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout[x].length; y++) {
                if(layout[y][x] == 'e') {
                    enemyList.add(new Enemy(x, y, this, theme)); //create an enemy
                    layout[y][x] = '.'; //place a floor under the enemy
                }
                if(layout[y][x] == 't') {
                    treasureList.add(new TreasureBox(x, y, this)); //create a treasure
                    layout[y][x] = '.'; //place a floor under the treasure
                }
            }
        }
        player = new PlayerParty(1, 1, this, sheets);
    }
    
    //loads a floor from a text file
    public void loadLevel(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        for (int i = 0; i < 36; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 36; j++) {
                layout[j][i] = line.charAt(j);
            }
        }
    }
    
    public void genLevel() {
        layout = RandomLevel.genLevel(floor);
    }
    
    //Moves the enemies
    public void takeEnemyTurn() {
        if(!playerTurn) {
            for (Enemy e: enemyList) {
                e.takeTurn(player);
            }
            playerTurn = true;
        }
    }
    
    public void deleteTreasureAt(int x, int y) {
        for (final Iterator iterator = treasureList.iterator(); iterator.hasNext(); ) {
            TreasureBox target = (TreasureBox) iterator.next();
            if (target.getX() == x && target.getY() == y) {
                iterator.remove();
            }
        }
    }
    
    public void removeEnemy(Enemy e) {
        enemyList.remove(e);
    }
    
    //returns true if there is nothing at coodinate (x, y)
    public boolean isEmpty(int x, int y) {
        char c = layout[y][x];
        if (c == '-' || c == '|' || c == 'c') {
            return false;
        }
        for (Enemy e: enemyList) {
            if(e.getX() == x && e.getY() == y) {
                return false;
            }
        }
        for (TreasureBox t: treasureList) {
            if (t.getX() == x && t.getY() == y) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsTreasure(int x, int y) {
        for (TreasureBox t: treasureList) {
            if (x == t.getX() && y == t.getY())
                return true;
        }
        return false;
    }
    
    public boolean containsEnemy(int x, int y) {
        for (Enemy e: enemyList) {
            if (x == e.getX() && y == e.getY())
                return true;
        }
        return false;
    }
    
    public boolean useStairs(int x, int y) {
        if (layout[y][x] == 'd') {
            Console.clear("You walked down the stairs.");
            lt.newLevel(floor + 1);
            return true; //generate new level with floor+1
        } else if (layout[y][x] == 'u') {
            Console.clear("You retreated to the first level.");
            lt.newLevel(1);
            return true; //goto town
        } else {
            Console.addMessage("There are no stairs there!");
            return false; //do nothing
        }
    }
    
    public boolean containsPlayer(int x, int y) {
        return x == player.getX() && y == player.getY();
    }
    
    public PlayerParty getPlayer() {
        return player;
    }
    
    public TreasureBox getTreasure(int x, int y) {
        for (TreasureBox t: treasureList) {
            if (x == t.getX() && y == t.getY())
                return t;
        }
        return null;
    }
    
    public Enemy getEnemy(int x, int y) {
        for (Enemy e: enemyList) {
            if (x == e.getX() && y == e.getY())
                return e;
        }
        return null;
    }
    
    public int getFloor() {
        return floor;
    }
    
    //draws the world
    public void render(Graphics2D g2d) {
        
        //draw the walls/floor
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout[x].length; y++) {
                
                BufferedImage img = null;
                switch (layout[y][x]) {
                    case 'c':
                        img = cornerImage;
                        break;
                    case '-':
                        img = wallImage;
                        break;
                    case '|':
                        img = wall2Image;
                        break;
                    case '.':
                        img = floorImage;
                        break;
                    case 'u':
                        img = stairUp;
                        break;
                    case 'd':
                        img = stairDown;
                        break;
                    default:
                        img = null;
                        break;
                }
                
                g2d.drawImage(img, (x * SCALE) + OFFSET, y * SCALE, SCALE, SCALE, null);
            }
        }
        
        //draw the enemies
        for (Enemy e: enemyList) {
            e.render(g2d, SCALE, OFFSET);
        }

        //draw the treasure
        for (TreasureBox t: treasureList) {
            t.render(g2d, SCALE, OFFSET);
        }
        
        //draw the player
        player.render(g2d, SCALE, OFFSET);
    }
        
    public void setEnemyTurn() {
        playerTurn = false;
    }
    
    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
