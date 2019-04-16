package loderunner.data;

import loderunner.services.CharacterService;

public class CellContent {
	private CharacterService character;
	private Item item;
	
	public CellContent(CharacterService character,Item item) {
		this.character = character;
		this.item = item;
	}

	public CharacterService getCharacter() {return character;}
	public Item getItem() {return item;}

	public void setCharacter(CharacterService character) {this.character = character;}
	public void setItem(Item item) {this.item = item;}
	
}
