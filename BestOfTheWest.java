import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BestOfTheWest extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L; // eclipse says we need this
    
    public static final MusicPlayer m = new MusicPlayer();
    public static final Console c = new Console();
    public static final CharacterSheet[] sheets = new CharacterSheet[3];
    private static Level l = new Level(1);
    private static Town t = new Town();
    private static String mode = "Town";
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
                }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
        }
    };
    
    public BestOfTheWest() {
        sheets[0] = new CharacterSheet("Bob", 0);
        sheets[1] = new CharacterSheet("Bill", 1);
        sheets[2] = new CharacterSheet("Jim", 2);
        t = new Town();
        
        addMouseListener(new CoolMouseListener(sheets, t, this));
        addKeyListener(k);
        setFocusable(true);
        
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
        c.paint(g2d);
    }
    
    public static void newLevel(int floor) {
        l = new Level(floor);
    }
    
    public static void setMode(String m) {
        mode = m;
    }
    
    public static String getMode() {
        return mode;
    }
    
    public void run() {
        JFrame frame = new JFrame("COOL LEVEL");
        frame.add(this);
        frame.setSize(1000, 800);
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
