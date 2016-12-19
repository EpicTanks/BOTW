import java.awt.*;
import java.util.LinkedList;

//This class is abstract because there will only ever be 1 console, and I don't want anyone trying to create an instance
public abstract class Console {
    
    //List of messages
    private static LinkedList<String> messageList = new LinkedList<String>();
    
    //Constants
    private static final int X = 0;
    private static final int Y = 225;
    private static final int FONT_SIZE = 14;
    private static final int MAX_LINES = (350 / FONT_SIZE) - 1;
    private static final int MAX_CHARS = 175 / (FONT_SIZE / 2);
    private static final Color BROWN = new Color(255, 190, 100);
    
    public static void paint(Graphics2D g2d) {
        g2d.setColor(BROWN);
        g2d.fillRect(X, Y, 192, 350);
        
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Segoe Print", Font.PLAIN, FONT_SIZE)); 
        for (int i = 0; i < messageList.size(); i++) {
            g2d.drawString(messageList.get(i), X + 2, Y + FONT_SIZE + (i * FONT_SIZE));
        }
    }
    
    public static void addMessage(String s) {
        
        //Splits up the string into pieces if it's too big to fit on 1 line.
        while(s.length() > MAX_CHARS) {
            String toAdd = "";
            String[] words = s.split(" ");
            
            for(String word: words) {
                if(toAdd.length() + word.length() > MAX_CHARS)
                    break;
                else
                    toAdd += word + " ";
            }
            
            messageList.add(toAdd);
            s = s.substring(toAdd.length());
        }
        messageList.add(s);
        
        //Removes the oldest messages if there are too many to fit inside the box
        while(messageList.size() > MAX_LINES) {
            messageList.remove();
        }
        
        messageList.add(""); //Add a blank line between each message to improve readability
    }
    
    public static void addCloseMessage(String s) {
        
        //Splits up the string into pieces if it's too big to fit on 1 line.
        while(s.length() > MAX_CHARS) {
            String toAdd = "";
            String[] words = s.split(" ");
            
            for(String word: words) {
                if(toAdd.length() + word.length() > MAX_CHARS)
                    break;
                else
                    toAdd += word + " ";
            }
            
            messageList.add(toAdd);
            s = s.substring(toAdd.length());
        }
        messageList.add(s);
        
        //Removes the oldest messages if there are too many to fit inside the box
        while(messageList.size() > MAX_LINES) {
            messageList.remove();
        }
    }
    
    public static void clear() {
        messageList = new LinkedList<String>();
    }
    
    public static void clear(String s) {
        clear();
        addMessage(s);
    }
}