import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.imageio.*;

public class Town {
    private BufferedImage image;
    private static final int OFFSET = 192;
    
    public Town() {
        try {
            image = ImageIO.read(new File("images/townmenu.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void click(MouseEvent e) {
     BestOfTheWest.c.addMessage("Click!");
    }
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(image, OFFSET, 0, null);
    }
}