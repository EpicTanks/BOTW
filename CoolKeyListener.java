import java.awt.event.*;

public class CoolKeyListener implements KeyListener {
    private Level l;
    private LevelTest lt;
    
    public CoolKeyListener(Level l, LevelTest lt) {
        setLevel(l);
        this.lt = lt;
    }
    
    public void setLevel(Level l) {
        this.l = l;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27 && !lt.getDungeonMode()) {
            Console.clear("You descended into a new dungeon.");
            lt.newLevel(1);
            lt.toggleDungeonMode();
        } else {
            if (lt.getDungeonMode()) {
                if (l.isPlayerTurn() && l.getPlayer().takeAction(e)) {
                    l.setEnemyTurn();
                }
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}

