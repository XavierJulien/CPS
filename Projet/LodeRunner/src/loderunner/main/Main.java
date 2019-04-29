package loderunner.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Item;
import loderunner.data.ItemType;
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
		editScreenContract = m.buildMap("src/loderunner/maps/map1.txt");
		EngineContract engineContract = new EngineContract(engine);
		engineContract.init(editScreenContract, new Coord(1,1), null, t);
		Scanner sc = new Scanner(System.in);
		System.out.println("---------------");
		System.out.println(engine.getEnvi().toString());
		class Listener extends Thread implements KeyListener{

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.equals(KeyEvent.VK_UP)) {
					engineContract.addCommand(Command.UP);
				}
				if(e.equals(KeyEvent.VK_DOWN)) {
					engineContract.addCommand(Command.DOWN);
				}
				if(e.equals(KeyEvent.VK_LEFT)) {
					engineContract.addCommand(Command.LEFT);
				}
				if(e.equals(KeyEvent.VK_RIGHT)) {
					engineContract.addCommand(Command.RIGHT);
				}

				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		Listener l = new Listener();
		l.start();
		while(true) {
			System.out.println("Veuillez saisir un d�placement(UP,DOWN,LEFT,RIGHT) : ");
			String s = sc.nextLine();
			if(s.equals("STOP")) break;
			switch (s) {
			case "UP" : {engineContract.addCommand(Command.UP);break;}
			case "DOWN" : {engineContract.addCommand(Command.DOWN);break;}
			case "LEFT" : {engineContract.addCommand(Command.LEFT);break;}
			case "RIGHT" : {engineContract.addCommand(Command.RIGHT);break;}
			case "DIGR" : {engineContract.addCommand(Command.DIGR);break;}
			case "DIGL" : {engineContract.addCommand(Command.DIGL);break;}
			}
			engineContract.step();
			if(engineContract.getTreasures().isEmpty()) {
				break;
			}
			System.out.println("---------------");
			System.out.println(engine.getEnvi().toString());
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
