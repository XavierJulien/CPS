package loderunner.data;

public class Item {
	private static int cpt = 0;
	
	private final int id;
	private final ItemType nature;
	private int hgt,col;
	
	public Item(int hgt, int col, ItemType nature) {
		this.id = Item.cpt++;
		this.nature = nature;
		this.hgt = hgt;
		this.col = col;
	}
	
	public int getId() {return id;}
	public ItemType getNature() {return nature;}
	public int getHgt() {return hgt;}
	public int getCol() {return col;}
	public void setCol(int col) {this.col = col;}
	public void setHgt(int hgt) {this.hgt = hgt;}
}
