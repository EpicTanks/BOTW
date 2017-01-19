import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BestOfTheWest extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L; // eclipse says we need this
	public static final MusicPlayer m = new MusicPlayer();
	public static final Console c = new Console();
	public static final CharacterSheet[] sheets = new CharacterSheet[3];

	private static TitleScreen ts = new TitleScreen();
	private static Level l = new Level(1);
	private static Town t = new Town();
	private static CharacterCreationScreen cc = new CharacterCreationScreen();
	private static String mode = "Title";
	public static int partyMoney = 0;

	private static KeyListener k = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 27 && getMode().equals("Town")) {
				c.clear("You descended into a new dungeon.");
				m.changeTrack("Travel");
				setMode("Dungeon");
				newLevel(1);
			} else {
				if (getMode().equals("Dungeon")) {
					if (l.isPlayerTurn() && l.getPlayer().takeAction(e)) {
						l.setEnemyTurn();
					}
				} else if (getMode().equals("Character Creation")) {
					cc.type((char) e.getKeyCode());
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	};

	private static MouseListener k2 = new MouseListener() {

		public void mousePressed(MouseEvent e) {
			for (int i = 0; i < sheets.length; i++) {
				sheets[i].click(e.getX(), e.getY());
			}
			if (getMode().equals("Town")) {
				t.click(e);
			} else if (getMode().equals("Character Creation")) {
				cc.click(e);
			} else if (mode.equals("Title")) {
				m.changeTrack("charCreation");
				mode = "Character Creation";
			}
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}
	};

	public BestOfTheWest() {
		for (int i = 0; i < 3; i++) {
			sheets[i] = new CharacterSheet(i);
		}
		CharacterSheet.selectedSheet = sheets[0];
		t = new Town();

		addKeyListener(k);
		addMouseListener(k2);
		setFocusable(true);

	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		switch (mode) {
		case "Title":
			ts.render(g2d);
			break;
		case "Character Creation":
			cc.render(g2d);
			break;
		case "Town":
			t.render(g2d);
			break;
		case "Dungeon":
			l.render(g2d);
			break;
		}

		if (!mode.equals("Title")) {
			for (CharacterSheet c : sheets) {
				c.paint(g2d);
			}
			c.paint(g2d);
		}
	}

	public static void newLevel(int floor) {
		l = new Level(floor);
		c.addMessage(l.getTheme() + " !!!");
	}

	public static void setMode(String m) {
		mode = m;
	}

	public static String getMode() {
		return mode;
	}

	public void run() {
		JFrame frame = new JFrame("Best Of The West");
		frame.add(this);
		frame.setSize(816, 639); // this is stupid, but it fits the title
									// picture
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		while (true) {
			repaint();
			l.takeEnemyTurn();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		BestOfTheWest lt = new BestOfTheWest();
		Thread a = new Thread(lt);
		a.start();
		m.start();
	}

	public static Level getLevel() {
		return l;
	}
}
