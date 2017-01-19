import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CreditScreen {
 
 public CreditScreen() {
     
 }

 public void render(Graphics2D g2d) {
 g2d.setColor(Color.BLUE);
 g2d.setFont(new Font("ComicSansMS", Font.PLAIN, 40));
 g2d.drawString("YOU HECKIN' WIN !!!", 200, 200);
 g2d.drawString("Game by: Jake, Daniel, and Marvin", 200, 245);
 }
}
