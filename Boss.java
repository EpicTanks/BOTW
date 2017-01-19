import java.io.IOException;

public class Boss extends Enemy {

 public Boss(int x, int y, Level l) {
  super(x, y, l, "dragonhell");
  name = "Dragon God";
  try {
   setData();
  } catch (IOException e) {
   System.out.println("setData() failed, default stats kept.");
  }
  setImage("images/sprites/enemies/" + name + ".png");
 }
 
 @Override
 public void takeDamage(int damage) {
  hp -= damage;
  BestOfTheWest.c.addMessage("The " + name + " took " + damage + " damage!");
  if (hp <= 0) {
   BestOfTheWest.setMode("Credits");
   BestOfTheWest.m.changeTrack("theend");
  }
 }
}
