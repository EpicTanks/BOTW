import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.imageio.*;

public class Town {
  private BufferedImage image;
  private BufferedImage poster;
  private BufferedImage sleep;
  private static final int OFFSET = 192;
  private static int posterx=(OFFSET+300);
  private static int postery=320;
  private static int sleepx=(OFFSET+45);
  private static int sleepy=440;
  
  public Town() {
    try {
      image = ImageIO.read(new File("images/townmenu.png"));
      poster= ImageIO.read(new File("images/WantedPoster.png"));
      sleep = ImageIO.read(new File("images/SleepButton.png"));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public void click(MouseEvent e) {
    if (((e.getX()>posterx)&&(e.getX()<posterx+260))&&((e.getY()>postery)&&(e.getY()<postery+260)))
    {
      BestOfTheWest.setMode("Dungeon");
      BestOfTheWest.c.addMessage("You take the wanted poster and make way to the dungeon");
    }
    else if (((e.getX()>sleepx)&&(e.getX()<sleepx+210))&&((e.getY()>sleepy)&&(e.getY()<sleepy+75)))
    {
      BestOfTheWest.sheets[0].heal(1000);
      BestOfTheWest.sheets[1].heal(1000);
      BestOfTheWest.sheets[2].heal(1000);
      BestOfTheWest.sheets[0].restoreMP();
      BestOfTheWest.sheets[1].restoreMP();
      BestOfTheWest.sheets[2].restoreMP();
      BestOfTheWest.c.addMessage("Your party rests at the inn, cleansing the body and mind.");
    }
    else
      BestOfTheWest.c.addMessage("Clicked Nothing");
  }
  
  public void render(Graphics2D g2d) {
    g2d.drawImage(image, OFFSET, 0, null);
    g2d.drawImage(poster, posterx, 320, null);
    g2d.drawImage(sleep, sleepx, 440, null);
  }
}