import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class CharacterCreationScreen {
	private BufferedImage backGround = null;
	private int step = 1;
	private int createdCharacters = 0;
	private static final int MESSAGE_X = 350;
	private static final int MESSAGE_Y = 150;
	private static final int LEFT_BOUND = 225;
	private static final int BOTTOM_BOUND = 570;
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	public static ArrayList<Character> typed = new ArrayList<Character>();
	private Point p;

	private static final int NAME_SELECTION = 1;
	private static final int RACE_SELECTION = 2;
	private static final int CLASS_SELECTION = 3;
	private static final int CONFIRM = 4;
	private static final String[] RACES = { "Human", "Elf", "Dwarf", "Orc", "Lizardman", "Halfling" };
	private static final String[] RACE_DESCRIPTIONS = { "+1 to all stats", "+2 speed, -2 strength",
			"+2 strength, -2 speed", "+2 strength, -2 smarts", "+2 smarts, -2 speech", "+2 speech, -2 smarts" };
	private static final String[] CLASSES = { "Cowboy", "Desperado", "Doctor", "Brute", "Wizard", "Hunter" };
	private static final String[] CLASS_DESCRIPTIONS = { "Gets a gun, and he's fast!!!",
			"Gets a gun, and he's freindly!!!", "Gets no gun, but he's smart!!",
			"Gets an ACTAUL REAL knife. He's strong too.", "Spooky smart man with staff and fireball!",
			"Strong with musket, hunts things !!!" };

	private String name = "";
	private String race = "";
	private String clss = "";

	private class Button {
		private int x;
		private int y;
		private String text;
		private String desc;

		public Button(int x, int y, String text, String desc) {
			this.x = x;
			this.y = y;
			this.text = text;
			this.desc = desc;
			buttons.add(this);
		}

		public String click(int a, int b) {
			if (a > x && a < x + 100 && b > y && b < y + 100) {
				buttons.removeAll(buttons);
				return text;
			}
			return "";
		}

		public void render(Graphics2D g2d) {
			g2d.setColor(Color.gray);
			g2d.fillRect(x, y, 100, 50);

			g2d.setColor(Color.white);
			g2d.drawString(text, x + 5, y + 30);
		}

		public void drawDesc(Graphics2D g2d, int a, int b) {
			if (a > x && a < x + 100 && b > y && b < y + 100)
				g2d.drawString(desc, a, b);
		}
	}

	public CharacterCreationScreen() {
		try {
			backGround = ImageIO.read(new File("images/CharacterScreen1.png"));
		} catch (IOException e) {
			System.out.println("Whoops! Missing images/CharacterScreen1.png");
		}
	}

	public void saveCharacter() {
		BestOfTheWest.sheets[createdCharacters] = new CharacterSheet(name, race, clss, createdCharacters++);
		name = "";
		race = "";
		clss = "";
		if (createdCharacters == 3) {
			BestOfTheWest.m.changeTrack("Title");
			BestOfTheWest.setMode("Town");
		}
	}

	public void click(MouseEvent e) {
		if (step == RACE_SELECTION) {
			for (Button b : buttons) {
				race = b.click(e.getX(), e.getY());
				if (!race.equals("")) {
					step = CLASS_SELECTION;
					createChoices(CLASSES, CLASS_DESCRIPTIONS);
					break;
				}
			}
		} else if (step == CLASS_SELECTION) {
			for (Button b : buttons) {
				clss = b.click(e.getX(), e.getY());
				if (!clss.equals("")) {
					step = CONFIRM;
					break;
				}
			}
		} else if (step == CONFIRM) {
			step = NAME_SELECTION;
			saveCharacter();
		}
	}

	private void createChoices(String[] choices, String[] descs) {
		for (int i = 0; i < 3; i++) {
			buttons.add(new Button((i * 150) + 250, MESSAGE_Y + 50, choices[i], descs[i]));
		}
		for (int i = 3; i < 6; i++) {
			buttons.add(new Button(((i - 3) * 150) + 250, MESSAGE_Y + 150, choices[i], descs[i]));
		}
	}

	public void type(char c) {
		if (step == NAME_SELECTION) {
			switch (c) {
			case KeyEvent.VK_ENTER:
				if (typed.size() > 0) {
					name = listToString(typed);
					typed = new ArrayList<Character>();
					step = RACE_SELECTION;
					createChoices(RACES, RACE_DESCRIPTIONS);
				}
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (typed.size() > 0) {
					typed.remove(typed.size() - 1);
				}
				break;
			default:
				if ((c >= 'A' && c <= 'Z') || c == ' ') {
					if (typed.size() == 0) {
						typed.add(c);
					} else if (typed.size() < 8) {
						typed.add(Character.toLowerCase(c));
					}
				}
				break;
			}
		}
	}

	public String listToString(ArrayList<Character> list) {
		String result = "";
		for (Character c : list) {
			result += c;
		}
		return result;
	}

	public void render(Graphics2D g2d) {
		p = MouseInfo.getPointerInfo().getLocation();

		g2d.drawImage(backGround, 193, 0, 608, 600, null);

		g2d.setColor(Color.white);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));

		switch (step) {
		case NAME_SELECTION:
			g2d.drawString("Type in a name! (Hit enter to submit)", MESSAGE_X, MESSAGE_Y);
			g2d.drawString(listToString(typed), MESSAGE_X, MESSAGE_Y + 30);
			break;
		case RACE_SELECTION:
			g2d.drawString("Choose a race!", MESSAGE_X, MESSAGE_Y);
			break;
		case CLASS_SELECTION:
			g2d.drawString("Choose a class!", MESSAGE_X, MESSAGE_Y);
			break;
		case CONFIRM:
			g2d.drawString("You created " + name + ", the " + race + " " + clss, MESSAGE_X, MESSAGE_Y);
			g2d.drawString("Click to continue!", MESSAGE_X, MESSAGE_Y + 15);
			break;
		default:
			g2d.drawString("Dunzo", MESSAGE_X, MESSAGE_Y);
			break;
		}

		for (Button b : buttons) {
			b.render(g2d);
		}

		g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2d.drawString("Current Character: " + (createdCharacters + 1), LEFT_BOUND, BOTTOM_BOUND - 60);
		g2d.drawString("Name: " + name, LEFT_BOUND + 5, BOTTOM_BOUND - 45);
		g2d.drawString("Race: " + race, LEFT_BOUND + 5, BOTTOM_BOUND - 30);
		g2d.drawString("Class: " + clss, LEFT_BOUND + 5, BOTTOM_BOUND - 15);

		for (Button b : buttons) {
			b.drawDesc(g2d, (int) p.getX(), (int) p.getY());
		}
	}
}