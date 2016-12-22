import javax.swing.*;
import java.awt.*;

public class LevelTest extends JPanel {
    private static final long serialVersionUID = 1L; //eclipse says we need this
    private Level l;
    private Town t;
    private CharacterSheet[] sheets = new CharacterSheet[3];
    private CoolKeyListener k;
    private boolean dungeonMode = false;
    
    public LevelTest() {
        sheets[0] = new CharacterSheet("Bob", 0);
        sheets[1] = new CharacterSheet("Bill", 1);
        sheets[2] = new CharacterSheet("Jim", 2);
        CharacterSheet.selectedSheet = sheets[0];
        l = new Level(1, sheets, this);
        t = new Town();
        
        addKeyListener(k = new CoolKeyListener(l, this));
        setFocusable(true);
        
        addMouseListener(new CoolMouseListener(sheets, t, this));
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g);
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        if (dungeonMode) {
            l.render(g2d);
        } else {
            t.render(g2d);
        }
        
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
        k.setLevel(l);
    }
    
    public void toggleDungeonMode() {
        dungeonMode = !dungeonMode;
    }
    
    public boolean getDungeonMode() {
        return dungeonMode;
    }
    
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("COOL LEVEL");
        LevelTest lt = new LevelTest();
        frame.add(lt);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        while(true) {
            lt.repaint();
            lt.move();
        }
    }
}
