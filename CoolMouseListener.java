import java.awt.event.*;

public class CoolMouseListener implements MouseListener {
  
  private CharacterSheet[] cs;
  
  public CoolMouseListener(CharacterSheet[] c){
    cs = c;
  }
  
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
      for(int i = 0; i < cs.length; i++){
      cs[i].click(e.getX(),e.getY());
      }
    }
}
