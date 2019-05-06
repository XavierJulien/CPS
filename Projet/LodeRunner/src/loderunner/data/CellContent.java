package loderunner.data;

import loderunner.services.CharacterService;
import loderunner.services.GuardService;

public class CellContent {
	private CharacterService character = null;
	private GuardService guard =null;
	private Item item = null;

	public CharacterService getCharacter() {return character;}
	public Item getItem() {return item;}
	public GuardService getGuard() {return guard;}

	public void setCharacter(CharacterService character) {this.character = character;}
	public void setItem(Item item) {this.item = item;}
	public void setGuard(GuardService guard) {this.guard = guard;}
}
