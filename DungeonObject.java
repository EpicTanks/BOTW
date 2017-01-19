import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public abstract class DungeonObject {
	protected int y;
	protected int x;
	protected BufferedImage sprite = null;

	public DungeonObject(int x, int y, String imgPath) {
		this.x = x;
		this.y = y;
		setImage(imgPath);
	}

	protected void setImage(String path) {
		try {
			sprite = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Whoops! Missing " + path);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void render(Graphics2D g2d, int scale, int offset) {
		if (sprite != null) {
			g2d.drawImage(sprite, (x * scale) + offset, y * scale, scale, scale, null);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect((x * scale) + offset, y * scale, scale, scale);
		}
	}
}
