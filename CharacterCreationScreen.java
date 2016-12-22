import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

public class CharacterCreationScreen {
    private int step = 1;
    private String aaa = "";
    private static final int MESSAGE_X = 300;
    private static final int MESSAGE_Y = 100;
    public static ArrayList<Button> buttons = new ArrayList<Button>();
    public static ArrayList<Character> typed = new ArrayList<Character>();
    
    private class Button {
        private int x;
        private int y;
        private String text;
        
        public Button(int x, int y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
            buttons.add(this);
        }
        
        public String click(int a, int b) {
            if (a > x && a < x+100 && b > y && b < y + 100) {
                buttons.removeAll(buttons);
                return text;
            }
            return "";
        }
        
        public void render(Graphics2D g2d) {
            g2d.setColor(Color.gray);
            g2d.fillRect(x, y, 100, 50);
            
            g2d.setColor(Color.black);
            g2d.drawString(text, x + 5, y + 30);
        }
    }
    
    public CharacterCreationScreen() {
        for (int i = 0; i < 4; i++) {
            buttons.add(new Button((i * 150) + 200, MESSAGE_Y + 200, "wow" + i));
        }
    }
    
    public CharacterSheet[] createCharacters() {
        return new CharacterSheet[3];
    }
    
    public void click(MouseEvent e) {
        step++;
        if (step > 4)
            BestOfTheWest.setMode("Town");
        for (Button b: buttons) {
            aaa = b.click(e.getX(), e.getY());
            if (!aaa.equals("")) {
                break;
            }
        }
    }
    
    public void type(char c) {
        typed.add(c);
    }
    
    public String listToString(ArrayList<Character> list) {
        String result = "";
        for (Character c: list) {
            result += c;
        }
        return result;
    }
    
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(192, 0, 575, 575);
        
        g2d.setColor(Color.black);
        if (!aaa.equals(""))
            g2d.drawString("You chose " + aaa, MESSAGE_X, 200);
        
        g2d.drawString(listToString(typed), MESSAGE_X, 400);
        switch (step) {
            case 1:
                g2d.drawString("Choose a name!", MESSAGE_X, MESSAGE_Y);
                break;
            case 2:
                g2d.drawString("Choose a race!", MESSAGE_X, MESSAGE_Y);
                break;
            case 3:
                g2d.drawString("Choose a class!", MESSAGE_X, MESSAGE_Y);
                break;
            default:
                g2d.drawString("Dunzo", MESSAGE_X, MESSAGE_Y);
                break;
        }
        
        for(Button b: buttons) {
            b.render(g2d);
        }
    }
}