package loderunner.data;

import java.util.ArrayList;

import loderunner.contracts.EditableScreenContract;

public class Map {

	private EditableScreenContract edit;
	private Coord player;
	private Item gauntlet;
	private ArrayList<Item> treasures;
	private ArrayList<Coord> guards;
	private ArrayList<Teleporteur> teleporteurs;

	public Map(EditableScreenContract edit,Coord player,ArrayList<Item> treasures,ArrayList<Coord> guards,ArrayList<Teleporteur> teleporteurs,Item gauntlet) {
		this.edit = edit;
		this.player = player;
		this.treasures = treasures;
		this.guards = guards;
		this.teleporteurs = teleporteurs;
		this.gauntlet = gauntlet;
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
	
	public ArrayList<Teleporteur> getTeleporteurs() {
		return teleporteurs;
	}
	
	public Item getGauntlet() {
		return gauntlet;
	}
}
