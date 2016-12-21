import java.awt.event.*;

public class CoolMouseListener implements MouseListener {
    
    private CharacterSheet[] cs;
    private Town t;
    private BestOfTheWest lt;
    
    public CoolMouseListener(CharacterSheet[] c, Town t, BestOfTheWest lt) {
        cs = c;
        this.t = t;
        this.lt = lt;
    }
    
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < cs.length; i++) {
            cs[i].click(e.getX(), e.getY());
        }
        if (lt.getMode().equals("Town")) {
            t.click(e);
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
}
