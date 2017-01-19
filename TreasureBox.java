import java.util.Random;

public class TreasureBox extends DungeonObject {

	private Item contains;
	private int money;

	public TreasureBox(int x, int y, Level l) {
		super(x, y, "images/sprites/box.png");
		contains = Item.getItem();
		
		Random r = new Random();
		if (r.nextInt(1) == 0) {
			money = r.nextInt(15);
		} else {
			money = 0;
		}
	}

	public Item contents() {
		return contains;
	}
	
	public int getMoney() {
		return money;
	}
}
