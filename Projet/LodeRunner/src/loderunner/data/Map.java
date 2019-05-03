package loderunner.data;

import java.util.ArrayList;

import loderunner.contracts.EditableScreenContract;

public class Map {

	private EditableScreenContract edit;
	private Coord player;
	private ArrayList<Item> treasures;
	private ArrayList<Coord> guards;

	public Map(EditableScreenContract edit,Coord player,ArrayList<Item> treasures,ArrayList<Coord> guards) {
		this.edit = edit;
		this.player = player;
		this.treasures = treasures;
		this.guards = guards;
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
	public ArrayList<Coord> getGuards() {
		return guards;
	}
}
