package loderunner.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.data.Command;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.data.Map;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;

public class Main implements KeyListener{

	public static void main(String[] args) {

		//EDITOR
		EditableScreenImpl edit = new EditableScreenImpl();
		
		//TREASURES
		ArrayList<Item> t = new ArrayList<>();
		t.add(new Item(9, 1,ItemType.Treasure));
		
		//ENGINE
		EngineImpl engine = new EngineImpl();
		
		//MAP
		EditableScreenContract editScreenContract;
		MapBuilder m = new MapBuilder(edit);
		//RUN
		ArrayList<String> filenames = new ArrayList<String>();
		
		for(int i = 0;i<2;i++) {
			filenames.add("src/loderunner/maps/map"+i+".txt");//changer le nombre de clés
		}
		Scanner sc = new Scanner(System.in);
		for(String filename : filenames) {
			Map map = m.buildMap(filename);
			editScreenContract = map.getEdit();
			EngineContract engineContract = new EngineContract(engine);
			engineContract.init(editScreenContract, map.getPlayer(), null, map.getTreasures());
			System.out.println("---------------");
			System.out.println(engine.getEnvi());
			while(true) {
				System.out.println("Veuillez saisir un déplacement(UP,DOWN,LEFT,RIGHT) : ");
				String s = sc.nextLine();
				if(s.equals("STOP")) break;
				switch (s) {
				case "z" : {engineContract.addCommand(Command.UP);break;}
				case "s" : {engineContract.addCommand(Command.DOWN);break;}
				case "q" : {engineContract.addCommand(Command.LEFT);break;}
				case "d" : {engineContract.addCommand(Command.RIGHT);break;}
				case "e" : {engineContract.addCommand(Command.DIGR);break;}
				case "a" : {engineContract.addCommand(Command.DIGL);break;}
				default : {engineContract.addCommand(Command.NEUTRAL);break;}
				}
				engineContract.step();
				if(engineContract.getTreasures().isEmpty()) {
					System.out.println("---------------");
					System.out.println(engine.getEnvi().toString());
					break;
				}
				System.out.println("---------------");
				System.out.println(engine.getEnvi().toString());
			}
		}
		sc.close();
		System.out.println("--------END-------");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
