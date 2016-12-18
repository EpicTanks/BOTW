import java.awt.*;
import java.util.LinkedList;

//This class is abstract because there will only ever be 1 console, and I don't want anyone trying to create an instance
public abstract class Console {
    private static LinkedList<String> messageList = new LinkedList<String>();
    
    private static final int X = 0;
    private static final int Y = 225;
    private static final int FONT_SIZE = 16;
    private static final int MAX_LINES = 351 / FONT_SIZE;
    private static final int MAX_CHARS = 175 / (FONT_SIZE / 2);
    
    public static void paint(Graphics2D g2d) {
        g2d.setColor(new Color(255,190,100));
        g2d.fillRect(X, Y, 192, 351);
        
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Segoe Print", Font.PLAIN, FONT_SIZE)); 
        for (int i = 0; i < messageList.size(); i++) {
            g2d.drawString(messageList.get(i), X + 2, Y + FONT_SIZE + (i * FONT_SIZE));
        }
    }
    
    public static void addMessage(String s) {
        while(s.length() > MAX_CHARS) {
            String toAdd = "";
            String[] words = s.split(" ");
            
            for(String word: words) {
                if(toAdd.length() + word.length() > MAX_CHARS)
                    break;
                else
                    toAdd += word + " ";
            }
            s = s.substring(toAdd.length());
            
            messageList.add(toAdd);
        }
        messageList.add(s);
        
        while(messageList.size() > MAX_LINES) {
            messageList.remove();
        }
    }
    
    public static void clear() {
        messageList = new LinkedList<String>();
    }
}