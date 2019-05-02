package loderunner.data;

import java.util.ArrayList;

import loderunner.contracts.EditableScreenContract;

public class Map {

	private EditableScreenContract edit;
	private Coord player;
	private ArrayList<Item> treasures;

	public Map(EditableScreenContract edit,Coord player,ArrayList<Item> treasures) {
		this.edit = edit;
		this.player = player;
		this.treasures = treasures;
	}
	
	public EditableScreenContract getEdit() {
		return edit;
	}
	
	public Coord getPlayer() {
		return player;
	}
	
	public ArrayList<Item> getTreasures() {
		return treasures;
	}
}
