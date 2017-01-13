public class TreasureBox extends DungeonObject {
  
  private Item contains;
  private int money;
  public TreasureBox(int x, int y, Level l) {
    super(x, y, "images/sprites/box.png");
    if((int)(Math.random()*2)==0)
      money = ((int)(Math.random()*10)+1);
    else
      contains = Item.getItem();
  }
  
  public Item contents() {
    return contains;
  }
  public int getMoney() {
    return money;
  }
}

