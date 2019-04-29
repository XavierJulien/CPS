package loderunner.main;

import java.util.ArrayList;
import java.util.Scanner;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;

public class Main {

	public static void main(String[] args) {

		//EDITOR
		EditableScreenImpl edit = new EditableScreenImpl();
		
		//TREASURES
		ArrayList<Item> t = new ArrayList<>();
		t.add(new Item(4, 1,ItemType.Treasure));
		
		//ENGINE
		EngineImpl engine = new EngineImpl();
		
		//RUN
		EditableScreenContract editScreenContract = new EditableScreenContract(edit);
		editScreenContract.init(5, 5);
		editScreenContract.setNature(0, 0, Cell.MTL);
		editScreenContract.setNature(1, 0, Cell.MTL);
		editScreenContract.setNature(2, 0, Cell.MTL);
		editScreenContract.setNature(3, 0, Cell.MTL);
		editScreenContract.setNature(4, 0, Cell.MTL);
		editScreenContract.setNature(2, 1, Cell.HDR);
		editScreenContract.setNature(0, 2, Cell.PLT);
		editScreenContract.setNature(1, 2, Cell.PLT);
		editScreenContract.setNature(2, 2, Cell.HDR);
		editScreenContract.setNature(3, 2, Cell.PLT);
		editScreenContract.setNature(4, 2, Cell.PLT);
		EngineContract engineContract = new EngineContract(engine);
		engineContract.init(editScreenContract, new Coord(1,1), null, t);
		Scanner sc = new Scanner(System.in);
		System.out.println("---------------");
		System.out.println(engine.getEnvi().toString());
		while(true) {
			System.out.println("Veuillez saisir un d�placement(UP,DOWN,LEFT,RIGHT) : ");
			String s = sc.nextLine();
			if(s.equals("STOP")) break;
			switch (s) {
			case "UP" : {engine.getCommands().add(Command.UP);break;}
			case "DOWN" : {engine.getCommands().add(Command.DOWN);break;}
			case "LEFT" : {engine.getCommands().add(Command.LEFT);break;}
			case "RIGHT" : {engine.getCommands().add(Command.RIGHT);break;}
			case "DIGR" : {engine.getCommands().add(Command.DIGR);break;}
			case "DIGL" : {engine.getCommands().add(Command.DIGL);break;}
			}
			engineContract.step();
			System.out.println("---------------");
			System.out.println(engine.getEnvi().toString());
		}
		sc.close();
		System.out.println("--------END-------");
	}

}
