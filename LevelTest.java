import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings(value = {})
public class LevelTest extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L; // eclipse says we need this
	private Level l;
	private Town t;
	private CharacterSheet[] sheets = new CharacterSheet[3];
	private String mode = "Town";
	private static MusicPlayer m = new MusicPlayer();
	private KeyListener k = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 27 && getMode().equals("Town")) {
				Console.clear("You descended into a new dungeon.");
				switchTrack("Travel");
				setMode("Dungeon");
				newLevel(1);
			} else {
				if (getMode().equals("Dungeon")) {
					if (l.isPlayerTurn() && l.getPlayer().takeAction(e)) {
						l.setEnemyTurn();
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	};

	public LevelTest() {
		sheets[0] = new CharacterSheet("Bob", 0);
		sheets[1] = new CharacterSheet("Bill", 1);
		sheets[2] = new CharacterSheet("Jim", 2);
		CharacterSheet.selectedSheet = sheets[0];
		l = new Level(1, sheets, this);
		t = new Town();

		addKeyListener(k);
		setFocusable(true);

		addMouseListener(new CoolMouseListener(sheets, t, this));
	}

	public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g);
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        switch (mode) {
        	case "Title":
        		//render title
        		break;
        	case "Character Creation":
        		//render cc
        		break;
        	case "Town":
        		t.render(g2d);
        		break;
        	case "Dungeon":
        		l.render(g2d);
        		break;
        }
        
        //always draw these
        for (CharacterSheet c : sheets) {
            c.paint(g2d);
        }
        Console.paint(g2d);
    }

	public void move() {
		l.takeEnemyTurn();
	}

	public void newLevel(int floor) {
		l = new Level(floor, sheets, this);
	}

	public void setMode(String m) {
		this.mode = m;
	}

	public String getMode() {
		return mode;
	}

	public void switchTrack(String tune) {
		m.changeTrack(tune);
	}

	public void run() {
		JFrame frame = new JFrame("COOL LEVEL");
		frame.add(this);
		frame.setSize(1000, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		while (true) {
			repaint();
			move();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		LevelTest lt = new LevelTest();
		Thread a = new Thread(lt);
		a.start();
		m.start();
	}
}
