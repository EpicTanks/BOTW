import javax.swing.*;
import java.awt.*;

public class LevelTest extends JPanel {
    private Level l;
    private CharacterSheet[] sheets = new CharacterSheet[3];
    private CoolKeyListener k;
    
    public LevelTest() {
        sheets[0] = new CharacterSheet("bob", 0);
        sheets[1] = new CharacterSheet("bob2", 1);
        sheets[2] = new CharacterSheet("jim", 2);
        CharacterSheet.selectedSheet = sheets[0];
        l = new Level(0, sheets, this);
        
        addKeyListener(k = new CoolKeyListener(l));
        setFocusable(true);
        
        addMouseListener(new CoolMouseListener(sheets));
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g);
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        l.render(g2d);
        
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
