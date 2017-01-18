public class TreasureBox extends DungeonObject {

	private Item contains;

	public TreasureBox(int x, int y, Level l) {
		super(x, y, "images/sprites/box.png");
		contains = Item.getItem();
	}

	public Item contents() {
		return contains;
	}
}
