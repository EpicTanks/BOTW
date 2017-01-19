import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.imageio.*;
//a
public class Town {
    private BufferedImage image;
    private BufferedImage poster;
    private BufferedImage sleep;
    private BufferedImage learn;
    private BufferedImage shopBoxes;
    private static final int OFFSET = 192;
    private static int posterx=(OFFSET+300);
    private static int postery=320;
    private static int sleepx=(OFFSET+45);
    private static int sleepy=440;
    private static int learny=60;
    private static int shopx=(OFFSET+10);
    private static int shopy=80;
    private Item a,b;
    
    public Town() {
        try {
            image = ImageIO.read(new File("images/townmenu.png"));
            poster= ImageIO.read(new File("images/WantedPoster.png"));
            sleep = ImageIO.read(new File("images/SleepButton.png"));
            learn = ImageIO.read(new File("images/LearningEmporium.png"));
            shopBoxes=ImageIO.read(new File("images/ShopBoxes.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        chooseItems();
    }
    
    public void click(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
<<<<<<< HEAD
        int speechChange=Math.max((CharacterSheet.selectedSheet.getSpch()/2), 1);
=======
        int speechChange=(CharacterSheet.selectedSheet.getSpch()/2);
>>>>>>> origin/master
        if (((x>posterx)&&(x<posterx+260))&&((y>postery)&&(y<postery+260)))
        {
            BestOfTheWest.setMode("Dungeon");
            BestOfTheWest.c.addMessage("You take the wanted poster and make way to the dungeon");
            BestOfTheWest.m.changeTrack("Travel");
        }
        else if (((x>sleepx)&&(x<sleepx+210))&&((y>sleepy)&&(y<sleepy+75)))
        {
            BestOfTheWest.c.addMessage("Your party rests at the inn, cleansing the body and mind.");
            BestOfTheWest.sheets[0].heal(1000);
            BestOfTheWest.sheets[1].heal(1000);
            BestOfTheWest.sheets[2].heal(1000);
            BestOfTheWest.sheets[0].restoreMP();
            BestOfTheWest.sheets[1].restoreMP();
            BestOfTheWest.sheets[2].restoreMP();
        }
        else if(((x>posterx)&&(x<posterx+116))&&((y>learny)&&(y<learny+83)))//Prices are augmented by half of the speech stat of character who is learning
            
        {
            if (BestOfTheWest.partyMoney>50/speechChange){
                BestOfTheWest.partyMoney-=50/speechChange;
                CharacterSheet.selectedSheet.augmentStat("a");
                BestOfTheWest.c.addMessage(CharacterSheet.selectedSheet.getName()+ " took a course in Combat Training!");
                BestOfTheWest.c.addMessage("The Party Paid $"+ 50/speechChange);
                
            }
            else
<<<<<<< HEAD
            	BestOfTheWest.c.addMessage("You dont have enough Money. You need $" + 50/speechChange);
=======
                BestOfTheWest.c.addMessage("You dont have enough Money");
>>>>>>> origin/master
            
        }
        else if(((x>posterx+121)&&(x<posterx+237))&&((y>learny)&&(y<learny+83)))
        {
            if (BestOfTheWest.partyMoney>50/speechChange){
                BestOfTheWest.partyMoney-=50/speechChange;
                CharacterSheet.selectedSheet.augmentStat("b");
                BestOfTheWest.c.addMessage(CharacterSheet.selectedSheet.getName()+ " learned Proper Running Technique!");
                BestOfTheWest.c.addMessage("The Party Paid $"+ 50/speechChange);
                
            }
            else
<<<<<<< HEAD
            	BestOfTheWest.c.addMessage("You dont have enough Money. You need $" + 50/speechChange);
=======
                BestOfTheWest.c.addMessage("You dont have enough Money");
>>>>>>> origin/master
        }
        else if(((x>posterx)&&(x<posterx+116))&&((y>learny+88)&&(y<learny+171)))
        {
            if (BestOfTheWest.partyMoney>50/speechChange){
                BestOfTheWest.partyMoney-=50/speechChange;
                CharacterSheet.selectedSheet.augmentStat("c");
                BestOfTheWest.c.addMessage(CharacterSheet.selectedSheet.getName()+ " read a book. ON THEIR OWN!");
                BestOfTheWest.c.addMessage("The Party Paid $"+ 50/speechChange);
            }
            else
<<<<<<< HEAD
            	BestOfTheWest.c.addMessage("You dont have enough Money. You need $" + 50/speechChange);
=======
                BestOfTheWest.c.addMessage("You dont have enough Money");
>>>>>>> origin/master
        }
        else if(((x>posterx+121)&&(x<posterx+237))&&((y>learny+88)&&(y<learny+171)))
        {
            if (BestOfTheWest.partyMoney>50/speechChange)
            {
                BestOfTheWest.partyMoney-=50/speechChange;
                CharacterSheet.selectedSheet.augmentStat("d");
                BestOfTheWest.c.addMessage(CharacterSheet.selectedSheet.getName()+ " gave a speech in the mirror!");
                BestOfTheWest.c.addMessage("The Party Paid $"+ 50/speechChange);
                
            }
            else
<<<<<<< HEAD
                BestOfTheWest.c.addMessage("You dont have enough Money. You need $" + 50/speechChange);
=======
                BestOfTheWest.c.addMessage("You dont have enough Money");
>>>>>>> origin/master
        }
        else if(((x>shopx+3)&&(x<shopx+67))&&((y>shopy+2)&&(y<shopy+66)))
        {
            a.statsToString(); 
        }
        else if(((x>shopx+3)&&(x<shopx+67))&&((y>shopy+75)&&(y<shopy+139)))
        {
            b.statsToString(); 
        }
        else if(((x>shopx+156)&&(x<shopx+242))&&((y>shopy+33)&&(y<shopy+66)))
        {
        	System.out.println(speechChange);
            if((a.getPrice()/speechChange)<BestOfTheWest.partyMoney)
            {
                BestOfTheWest.partyMoney-=(a.getPrice()/speechChange);
                BestOfTheWest.c.addMessage(CharacterSheet.selectedSheet.getName()+" bought "+a.getName()+" for $"+(a.getPrice()/speechChange));
                if (BestOfTheWest.sheets[0].collect(a)) {
                } else if (BestOfTheWest.sheets[1].collect(a)) {
                } else if(BestOfTheWest.sheets[2].collect(a)){
                }
                chooseItems();
            }
            else
<<<<<<< HEAD
                BestOfTheWest.c.addMessage("You dont have enough Money. You need $" + a.getPrice()/speechChange);
=======
                BestOfTheWest.c.addMessage("You dont have enough Money");
>>>>>>> origin/master
        }
        else if(((x>shopx+156)&&(x<shopx+242))&&((y>shopy+106)&&(y<shopy+139)))
        {
            if((b.getPrice()/speechChange)<BestOfTheWest.partyMoney)
            {
                BestOfTheWest.partyMoney-=(b.getPrice()/speechChange);
                BestOfTheWest.c.addMessage(CharacterSheet.selectedSheet.getName()+" bought "+b.getName()+" for $"+(b.getPrice()/speechChange));
                if (BestOfTheWest.sheets[0].collect(b)) {
                } else if (BestOfTheWest.sheets[1].collect(b)) {
                } else if(BestOfTheWest.sheets[2].collect(b)){
                }
                chooseItems();
            }
            else
<<<<<<< HEAD
                BestOfTheWest.c.addMessage("You dont have enough Money. You need $" + b.getPrice()/speechChange);
        }
=======
                BestOfTheWest.c.addMessage("You dont have enough Money");
        }
        else
            BestOfTheWest.c.addMessage("Clicked Nothing");
>>>>>>> origin/master
    }
    public void chooseItems(){
        a=Item.getItem();
        b=Item.getItem();
    }
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(image, OFFSET, 0, null);
        g2d.drawImage(poster, posterx, postery, null);
        g2d.drawImage(sleep, sleepx, sleepy, null);
        g2d.drawImage(learn, posterx, learny, null);
        g2d.drawImage(shopBoxes,shopx,shopy,null);
        g2d.drawImage(a.img,shopx+3,shopy+2,64,64,null);
        g2d.drawImage(b.img,shopx+3,shopy+75,64,64,null);
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        g2d.drawString((a.getName()+" $"+a.getPrice()),shopx+78,shopy+20);
        g2d.drawString((b.getName()+" $"+b.getPrice()),shopx+78,shopy+90);
    }
}