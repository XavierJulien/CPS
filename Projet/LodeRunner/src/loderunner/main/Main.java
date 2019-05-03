package loderunner.main;

import java.util.ArrayList;
import java.util.Scanner;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Map;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;

public class Main{

	public static void main(String[] args) {
		
		//Lives
		int lives = 3;
		
		ArrayList<String> filenames = new ArrayList<String>();
		for(int i = 1;i<4;i++) {
			filenames.add("src/loderunner/maps/map"+i+".txt");//changer le nombre de clés
		}
		Scanner sc = new Scanner(System.in);
		for(int i = 0;i<filenames.size();i++) {
			//EDITOR
			EditableScreenImpl edit = new EditableScreenImpl();
			
			//ENGINE
			EngineImpl engine = new EngineImpl();
			
			ArrayList<Coord> guards = new ArrayList<>();
			guards.add(new Coord(6,2));
			//MAP
			EditableScreenContract editScreenContract;
			MapBuilder m = new MapBuilder(edit);
			Map map = m.buildMap(filenames.get(i));
			editScreenContract = map.getEdit();
			EngineContract engineContract = new EngineContract(engine);
			engineContract.init(editScreenContract, map.getPlayer(), guards, map.getTreasures());
			System.out.println("-------MAP N°"+i+"--------");
			System.out.println(engineContract.getEnvi().toString());
			System.out.println("---------------------------");
			System.out.println("---LIVES : "+lives+"---SCORE : "+engineContract.getScore()+"---");
			while(true) {
				if(engineContract.getStatus() == GameState.Win) {System.out.println("--------WELLPLAYED--------");break;}
				if(engineContract.getStatus() == GameState.Loss){System.out.println("--------YOU LOSE--------");break;}
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
				if(engineContract.getStatus() == GameState.Win) {
					System.out.println(engineContract.getEnvi().toString());
					System.out.println("---------------------------");
					System.out.println("---LIVES : "+lives+"---SCORE : "+engineContract.getScore()+"---");
					System.out.println("\n---------WELLPLAYED--------");
					break;
				}else {
					System.out.println("-------PLAYING--------");
					System.out.println(engineContract.getEnvi().toString());
					System.out.println("---------------------------");
					System.out.println("---LIVES : "+lives+"---SCORE : "+engineContract.getScore()+"---");	
				}
			}
			if(engineContract.getStatus() == GameState.Loss){
				if(lives == 0) {
					System.out.println("--------YOU LOOSE--------");
					break;
				}else {
					System.out.println("--------RETRY--------");
					i=i-1;
					lives-=1;
				}
			}
		}
		sc.close();
		System.out.println("--------END-------");
	}
}
