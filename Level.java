import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.nio.*;
import java.util.ArrayList;

public class Level {
    private int floor;
    private String theme;
    private char[][] layout = new char[36][36];
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<TreasureBox> treasureList = new ArrayList<TreasureBox>();
    private PlayerParty player;
    //private CharacterSheet[] characters = new CharacterSheet[3];
    private BufferedImage floorImage = null;
    private BufferedImage wallImage = null;
    private BufferedImage wall2Image = null;
    private BufferedImage cornerImage = null;
    private boolean playerTurn = true;
    private static final int SCALE = 16;
    private static final int OFFSET = 192;
    
    public Level(int floor) {
        this.floor = floor; //will be used to set the theme later
        
        //set the theme (for now, just mines)
        theme = "mines";
        
        //try to load the images based on the theme
        try {
            floorImage = ImageIO.read(new File("images/tiles/" + theme + "/floor.png"));
            wallImage = ImageIO.read(new File("images/tiles/" + theme + "/wall.png"));
            wall2Image = ImageIO.read(new File("images/tiles/" + theme + "/wall2.png"));
            cornerImage = ImageIO.read(new File("images/tiles/" + theme + "/corner.png"));
        } catch (IOException e) {
            System.out.println("Whoops! Missing an image in art/tiles/" + theme + "/");
        }
        
        //try to load the level
        try {
            loadLevel("floor2.txt");
        } catch (IOException e) {
            System.err.println("Whoops. No file!");
            throw new RuntimeException(e);
        }
        
        //create enemies and treasure boxes
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout[x].length; y++) {
                if(layout[x][y] == 'e') {
                    enemyList.add(new Enemy(x, y, this, theme)); //create an enemy
                    layout[x][y] = '.'; //place a floor under the enemy
                }
                if(layout[x][y] == 't') {
                    treasureList.add(new TreasureBox(x, y, this)); //create a treasure
                    layout[x][y] = '.'; //place a floor under the treasure
                }
            }
        }
        
        //create the player
//        for(CharacterSheet c: characters) {
//            c = new CharacterSheet("marvin", 0, 0, 0, 0, 0, "wow", "gudablity");
//        }
        player = new PlayerParty(1, 1, this);//, characters);
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
    
    //generates a floor at random based on the theme
    public void genLevel() {
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
    
    //returns true if there is nothing at coodinate (x, y)
    public boolean isEmpty(int x, int y) {
        char c = layout[x][y];
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
    
    public PlayerParty getPlayer() {
        return player;
    }
    
    //draws the world
    public void render(Graphics2D g2d) {
        
        //draw the walls/floor
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout[x].length; y++) {
                
                BufferedImage img = null;
                switch (layout[x][y]) {
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
