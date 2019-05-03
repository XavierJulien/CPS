package loderunner.data;

import loderunner.services.CharacterService;

public class CellContent {
	private CharacterService character = null;
	private CharacterService guard =null;
	private Item item = null;

	public CharacterService getCharacter() {return character;}
	public Item getItem() {return item;}
	public CharacterService getGuard() {return guard;}

	public void setCharacter(CharacterService character) {this.character = character;}
	public void setItem(Item item) {this.item = item;}
	public void setGuard(CharacterService guard) {this.guard = guard;}
}
