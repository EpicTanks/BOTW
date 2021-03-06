import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Level {
	
	//Constants
	private static final Color EDGE_COLOUR = new Color(124, 84, 53);
	private static final int SCALE = 16;
	private static final int OFFSET = 193;
	
	//General info
	private int floor;
	private String theme;
	
	//Level Contents
	private char[][] layout = new char[36][36];
	private ArrayList<DungeonObject> objectList = new ArrayList<DungeonObject>();
	private PlayerParty player;
	private boolean playerTurn = true;
	
	//Images
	private BufferedImage floorImage = null;
	private BufferedImage wallImage = null;
	private BufferedImage wall2Image = null;
	private BufferedImage cornerImage = null;
	private BufferedImage stairUp = null;
	private BufferedImage stairDown = null;

	//Constructor
	public Level(int floor) {
		this.floor = floor;
		Random r = new Random();
		//String difficulty;

		if (floor < 4) {
			switch (r.nextInt(2)) { //50% bandit, 50% ruins
			case 0:
				theme = "ruins";
				break;
			case 1:
				theme = "bandit";
				break;
			}
			genLevel();
		} else if (floor < 7) {
			switch (r.nextInt(4)) { //50% mines, 25% canyon, 25% ruins
			case 0:
				theme = "canyon";
				break;
			case 1:
			case 2:
				theme = "mines";
				break;
			case 3:
				theme = "ruins";
				break;
			}
			genLevel();
		} else if (floor < 10) {
			switch (r.nextInt(2)) { //50% haunted, 50% canyon
			case 0:
				theme = "haunted";
				break;
			case 1:
				theme = "canyon";
				break;
			}
			genLevel();
		} else {
			theme = "dragonhell";
			try {
				loadLevel("boss.txt");
			} catch (IOException e) {
				System.out.println("Missing boss.txt");
			}
		}

		// try to load the images based on the theme
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

		// create enemies and treasure boxes
		for (int x = 0; x < layout.length; x++) {
			for (int y = 0; y < layout[x].length; y++) {
				if (layout[y][x] == 'e') {
					objectList.add(new Enemy(x, y, this, theme)); // create an
																	// enemy
					layout[y][x] = '.'; // place a floor under the enemy
				}
				if (layout[y][x] == 't') {
					objectList.add(new TreasureBox(x, y, this)); // create a
																	// treasure
					layout[y][x] = '.'; // place a floor under the treasure
				}
				if (layout[y][x] == 'b') {
					objectList.add(new Boss(x, y, this));
				}
			}
		}
		player = new PlayerParty(1, 1);
	}

	// loads a floor from a text file
	public void loadLevel(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		for (int i = 0; i < 36; i++) {
			String line = reader.readLine();
			for (int j = 0; j < 36; j++) {
				layout[j][i] = line.charAt(j);
			}
		}

		reader.close();
	}

	public void genLevel() {
		layout = RandomLevel.genLevel(floor);
	}

	// Moves the enemies
	public void takeEnemyTurn() {
		if (!playerTurn) {
			for (DungeonObject o : objectList) {
				if (o instanceof Enemy)
					((Enemy) o).takeTurn(player);
			}
			playerTurn = true;
		}
	}

	public void removeObject(DungeonObject o) {
		objectList.remove(o);
	}

	// returns true if there is nothing at coodinate (x, y)
	public boolean isEmpty(int x, int y) {
		char c = layout[y][x];
		if (c == '-' || c == '|' || c == 'c') {
			return false;
		}
		for (DungeonObject o : objectList) {
			if (o.getX() == x && o.getY() == y) {
				return false;
			}
		}
		return true;
	}

	public boolean useStairs(int x, int y) {
		if (layout[y][x] == 'd') {
			BestOfTheWest.c.clear("You walked down the stairs.");
			BestOfTheWest.newLevel(floor + 1);
			return true; // generate new level with floor+1
		} else if (layout[y][x] == 'u') {
			BestOfTheWest.c.clear("You retreated to the town.");
			BestOfTheWest.setMode("Town");
			BestOfTheWest.m.changeTrack("Title");
			return true; // goto town
		} else {
			BestOfTheWest.c.addMessage("There are no stairs there!");
			return false; // do nothing
		}
	}

	public boolean containsPlayer(int x, int y) {
		return x == player.getX() && y == player.getY();
	}

	public PlayerParty getPlayer() {
		return player;
	}

	public Object getThingAt(int x, int y) {
		for (DungeonObject o : objectList) {
			if (o.getX() == x && o.getY() == y)
				return o;
		}
		if (!isEmpty(x, y)) {
			return 'w';
		}
		return null;
	}

	public int getFloor() {
		return floor;
	}

	// draws the world
	public void render(Graphics2D g2d) {

		// draw the background
		g2d.setColor(EDGE_COLOUR);
		g2d.fillRect(0, 0, 800, 600);

		// draw the walls/floor
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

		// draw dungeon objects
		for (DungeonObject o : objectList) {
			o.render(g2d, SCALE, OFFSET);
		}

		// draw the player
		player.render(g2d, SCALE, OFFSET);
	}

	public void setEnemyTurn() {
		playerTurn = false;
	}

	public boolean isPlayerTurn() {
		return playerTurn;
	}

	public String getTheme() {
		return theme;
	}
}
