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
    private BufferedImage floorImage = null;
    private BufferedImage wallImage = null;
    private BufferedImage wall2Image = null;
    private BufferedImage cornerImage = null;
    private boolean playerTurn = true;
    private static final int SCALE = 16;
    private static final int OFFSET = 192;
    
    public Level(int floor, CharacterSheet[] sheets) {
        this.floor = floor; //will be used to set the theme later
        this.sheets = sheets;
        
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
            //loadLevel("floor2.txt");
            genLevel();
        } catch (IOException e) {
            System.err.println("Whoops. Missing a file!");
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
    
    //generates a floor at random
    public void genLevel() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int) (Math.random() * 10) + ".txt"));
        
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j][i] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j + 12][i] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j + 24][i] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j][i + 12] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j + 12][i + 12] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j + 24][i + 12] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j][i + 24] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j + 12][i + 24] = line.charAt(j);
            }
        }
        reader = new BufferedReader(new FileReader("gamedata/rooms/" + (int)(Math.random() * 10)+ ".txt"));
        for (int i = 0; i < 12; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 12; j++) {
                layout[j + 24][i + 24] = line.charAt(j);
            }
        }
        
        //close off the outside;
        for(int i = 0; i < 36; i++) {
            layout[0][i] = '|';
            layout[35][i] = '|';
            layout[i][0] = '-';
            layout[i][35] = '-';
        }
        layout[0][0] = layout[0][35] = layout[35][0] = layout[35][35] = 'c';
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
