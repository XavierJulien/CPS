package loderunner.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import loderunner.contracts.EditableScreenContract;
import loderunner.data.Cell;
import loderunner.services.EditableScreenService;

public class MapBuilder {
	
	EditableScreenContract edit;
	private int height,width;
	
	public MapBuilder(EditableScreenService delegate) {
		this.edit = new EditableScreenContract(delegate);
	}
	
	public EditableScreenContract buildMap(String filename) {
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
						case "x" : {edit.setNature(j, i, Cell.MTL);break;}
						case "H" : {edit.setNature(j, i, Cell.LAD);break;}
						case "-" : {edit.setNature(j, i, Cell.HDR);break;}
						case "U" : {edit.setNature(j, i, Cell.HOL);break;}
						default : throw new Error(line_tab[j]+" est mauvais");
					}
				}
				}
			
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return edit;
	}
}
