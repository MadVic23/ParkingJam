package objects;

import java.util.*;
import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level implements Serializable {

	private Panel panel;
	private int numMoves;
	private Box boxWin;
	private int num;
	private int punctua;
	private int globalPunctua;
	private String path;

	public Level(String path, int num, int punctua, int globalPunctua) throws FileNotFoundException {
		this.path = path;
		this.num = num;
		this.punctua = punctua;
		this.globalPunctua = globalPunctua;
		panel = loadBoard(path);
	}

	public final Panel loadBoard(String path) throws FileNotFoundException {

		File level = new File(path);
		boolean exit = false;
		boolean auto = false;
		boolean truck = false;
		boolean mycar = false;
		boolean mycarAdd = false;
		List<Character> list = new ArrayList<>();

		try (Scanner sc = new Scanner(level)) {
			sc.nextLine();
			int rows = sc.nextInt() - 2;
			int cols = sc.nextInt() - 2;
			sc.nextLine(); // this is for skipping the first line
			Panel panel2 = new Panel(rows, cols);
			for (int i = 0; sc.hasNextLine() && (i < rows+1); i++) {
				String line = sc.nextLine();
				for (int j = 0; j < line.length(); j++) {
					char c = line.charAt(j);
					int representacion = c;

					if(c == '@') {
						exit = true;
					}
					else if (c =='*') {
						mycar = true;
					}
					else if (!list.contains(c) && representacion >= 97 && representacion <= 114) {
						auto = true;
					}
					else if(!list.contains(c) && representacion >=115 && representacion <= 122) {
						truck = true;
					}

					if(mycar && !mycarAdd) {
						if(line.charAt(j+1) == '*') {
							panel2.addVehicle(VehicleModel.MYCAR, i-1, j-1, 2, false);
						}
						else {
							panel2.addVehicle(VehicleModel.MYCAR, i-1, j-1, 2, true);
						}
						mycarAdd = true;
					}
					if(auto) {
						if(j != line.length()-1 && line.charAt(j+1) == c) {
							panel2.addVehicle(VehicleModel.AUTO, i-1, j-1, 2, false);
						}
						else {
							panel2.addVehicle(VehicleModel.AUTO, i-1, j-1, 2, true);
						}
						list.add(c);
						auto = false;

					}
					if (truck) {

						if(j != line.length()-1 && line.charAt(j+1) == c) {
							panel2.addVehicle(VehicleModel.TRUCK, i-1, j-1, 3, false);
						}
						else {
							panel2.addVehicle(VehicleModel.TRUCK, i-1, j-1, 3, true);

						}
						list.add(c);
						truck = false;
					}
					if (exit) {
						boxWin = new Box(i-1,j-2);
						exit = false;
					}
				}
			}
			numMoves = 0;
			return panel2;
		} 
	}

	public int getColumns() {
		return panel.getNumCols();
	}

	public int getRows() {
		return panel.getNumRows();
	}

	public int getNumMoves() {
		return numMoves;
	}

	public int getNum() {
		return num;
	}

	public void incrementMoves() {
		numMoves++;
	}

	public Vehicle getVehicle(Box space) {
		return panel.getVehicle(space);
	}


	public boolean moveBoxes(Box space, int numSpaces) {
		if(panel.possibleMoveBoxes(space,numSpaces)) {
			panel.moveBoxes(space, numSpaces);
			numMoves++;
			if (numSpaces == 0)
				numMoves--;
			return true;
		}
		return false;
	}

	public List<Vehicle> getVehiclesOnPanel() {
		return panel.getVehicleOnPanel();
	}

	public void setVehiclesOnPanel(List<Vehicle> l) {
		panel.setVehicleOnPanel(l);
	}

	public boolean carwin() {
		boolean res = false;
		Vehicle veh = panel.getVehicle(boxWin);
		if(veh == null) {
			return res;	
		}
		if(veh.getVehicleModel() == VehicleModel.MYCAR) {
			res = true;
		}
		return res;
	}

	public int getPunctua() {
		return punctua;
	}

	public void setPunctua(int punctua) {
		this.punctua = punctua;
	}

	public int getGlobalPunctua() {
		return globalPunctua;
	}

	public void setGlobalPunctua(int globalPunctua) {
		this.globalPunctua = globalPunctua;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
