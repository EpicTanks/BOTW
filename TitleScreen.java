import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.imageio.*;

class TitleScreen {
    private BufferedImage image;
    
    public TitleScreen() {
        try {
            image = ImageIO.read(new File("images/title.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(image, 0, 0, null);
    }
}