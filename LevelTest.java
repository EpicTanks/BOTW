import javax.swing.*;
import java.awt.*;

public class LevelTest extends JPanel {
    private static Level l = new Level(0);
    
    public LevelTest() {
        addKeyListener(new CoolKeyListener(l));
        setFocusable(true);
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g);
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        l.render(g2d);
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
            l.takeEnemyTurn();
        }
    }
} 