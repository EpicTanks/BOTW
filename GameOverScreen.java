import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOverScreen {
	
	public GameOverScreen() {
	}

	public void render(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.setFont(new Font("ComicSansMS", Font.PLAIN, 50));
		g2d.drawString("GAME OVER", 200, 200);
	}
}
