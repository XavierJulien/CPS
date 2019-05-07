package loderunner.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import loderunner.contracts.EditableScreenContract;
import loderunner.data.Cell;
import loderunner.data.Coord;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.data.Map;
import loderunner.data.Teleporteur;
import loderunner.services.EditableScreenService;

public class MapBuilder {
	
	EditableScreenContract edit;
	private int height,width;
	ArrayList<Item> treasures = new ArrayList<>();
	Coord player;
	ArrayList<Coord> guards = new ArrayList<>();
	ArrayList<Teleporteur> teleporteurs = new ArrayList<>();
	Item gauntlet;
	public MapBuilder(EditableScreenService delegate) {
		this.edit = new EditableScreenContract(delegate);
	}
	
	public Map buildMap(String filename) {
		try {
			FileReader fr = new FileReader(new File(filename));
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			String[] pos = line.split(" ");
			width = Integer.parseInt(pos[0]);
			height = Integer.parseInt(pos[1]);
			edit.init(width, height);
			for(int i = height-1;i>=0;i--) {
				line = br.readLine();
				String[] line_tab = line.split("",width);
				for(int j = 0;j<width;j++) {
					switch(line_tab[j]) {
						case "." : {edit.setNature(j, i, Cell.EMP);break;}
						case "=" : {edit.setNature(j, i, Cell.PLT);break;}
						case "X" : {edit.setNature(j, i, Cell.MTL);break;}
						case "H" : {edit.setNature(j, i, Cell.LAD);break;}
						case "-" : {edit.setNature(j, i, Cell.HDR);break;}
						case "U" : {edit.setNature(j, i, Cell.HOL);break;}
						default : throw new Error(line_tab[j]+" est mauvais");
					}
				}
			}
			line = br.readLine();
			String[] player_split= line.split(" ");
			player = new Coord(Integer.parseInt(player_split[0]),Integer.parseInt(player_split[1]));
			line = br.readLine();
			String[] treasures_split= line.split(";");
			for(String t : treasures_split) {
				String[] Coord_split = t.split(",");
				treasures.add(new Item(Integer.parseInt(Coord_split[0]),Integer.parseInt(Coord_split[1]),ItemType.Treasure));
			}
			line = br.readLine();
			String[] guards_split= line.split(";");
			for(String t : guards_split) {
				String[] Coord_split = t.split(",");
				guards.add(new Coord(Integer.parseInt(Coord_split[0]),Integer.parseInt(Coord_split[1])));
			}
			line = br.readLine();
			if(line != null) {
				String[] tp_split= line.split(";");
				for(int i = 0;i<tp_split.length;i=i+2) {
					String[] Coord_splitA = tp_split[i].split(",");
					String[] Coord_splitB = tp_split[i+1].split(",");
					teleporteurs.add(new Teleporteur(new Coord(Integer.parseInt(Coord_splitA[0]),Integer.parseInt(Coord_splitA[1])),
													 new Coord(Integer.parseInt(Coord_splitB[0]),Integer.parseInt(Coord_splitB[1]))));
				}
			}
			if(line != null) {
				line = br.readLine();
				String[] gauntlet_split= line.split(" ");
				gauntlet = new Item(Integer.parseInt(gauntlet_split[0]),Integer.parseInt(gauntlet_split[1]),ItemType.Gauntlet);
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Map(edit,player,treasures,guards,teleporteurs,gauntlet);
	}
}
