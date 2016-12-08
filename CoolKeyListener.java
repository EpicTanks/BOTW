import java.awt.event.*;

public class CoolKeyListener implements KeyListener {
    private Level l;
    
    public CoolKeyListener(Level l) {
        this.l = l;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(l.isPlayerTurn() && l.getPlayer().move(KeyEvent.getKeyText(e.getKeyCode()))) {
            l.setEnemyTurn();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
