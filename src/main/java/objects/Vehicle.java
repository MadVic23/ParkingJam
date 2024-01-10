package objects;

import java.io.Serializable; 
public class Vehicle implements Serializable {

	VehicleModel model;
	Box start;
	int length;
	boolean isVertical;


	public Vehicle(VehicleModel model, int startRow, int startCol, int length, boolean isVertical) {
		this.model = model;
		start = new Box(startRow, startCol);
		this.length = length;
		this.isVertical = isVertical;
	}


	public Vehicle() {
	}


	public Box getStart() {
		return this.start;
	}


	public int getStartRow() {
		return this.start.getRow();
	}


	public int getStartCol() {
		return this.start.getCol();
	}


	public int getLength() {
		return this.length;
	}


	public boolean isVertical() {
		return this.isVertical;
	}


	public void setStartRow(int newRow) {
		this.start.setRow(newRow);
	}


	public void setStartCol(int newCol) {
		this.start.setCol(newCol);
	}


	public VehicleModel getVehicleModel() {
		return this.model;
	}



	public Box[] boxesOccup(int numBoxes) {
		Box[] boxesOccupied;
		boxesOccupied = new Box[Math.abs(numBoxes)];
		if (numBoxes < 0) {
			numBoxes = Math.abs(numBoxes);
			if (this.isVertical) {
				for (int i = 0; i < numBoxes; i++) {
					boxesOccupied[i] = new Box(start.getRow() - i - 1, start.getCol());
				}
			}
			else {
				for (int i = 0; i < numBoxes; i++) {
					boxesOccupied[i] = new Box(start.getRow(), start.getCol() - i - 1);
				}
			}

		}
		else {
			if (this.isVertical) {
				for (int i = 0; i < numBoxes; i++) {
					if (this.length == 3) {
						boxesOccupied[i] = new Box(start.getRow() + i + 3, start.getCol());
					}
					else {
						boxesOccupied[i] = new Box(start.getRow() + i + 2, start.getCol());
					}
				}
			}
			else {
				for (int i = 0; i < numBoxes; i++) {
					if (this.length == 3) {
						boxesOccupied[i] = new Box(start.getRow(), start.getCol() + i + 3);
					}
					else {
						boxesOccupied[i] = new Box(start.getRow(), start.getCol() + i + 2);
					}
				}
			}
		}

		return boxesOccupied;
	}


	public void move(int numBoxes) {
		if (this.isVertical) {
			setStartRow(this.start.getRow() + numBoxes);
		}
		else {
			setStartCol(this.start.getCol() + numBoxes);
		}
	}

	public Box[] boxesOccupied() {

		int uno = 1;
		int cero = 0;
		int dos = 2;
		
		Box[] boxArray;
		if (this.length == 2) {
			boxArray = new Box[2];
		}
		else {
			boxArray = new Box[3];
		}
		boxArray[0] = start;
		if (this.isVertical) {
			boxArray[uno] = new Box(boxArray[cero].getRow() + 1, boxArray[cero].getCol());
			if (this.length == 3) {
				boxArray[dos] = new Box(boxArray[uno].getRow() + 1, boxArray[uno].getCol());
			}
		}
		else {
			boxArray[uno] = new Box(boxArray[cero].getRow(), boxArray[cero].getCol() + 1);
			if (this.length == 3) {
				boxArray[dos] = new Box(boxArray[uno].getRow(), boxArray[uno].getCol() + 1);
			}
		}

		return boxArray;
	}


	public boolean getVertical() {
		return isVertical;
	}
	
	public void setVertical(boolean b) {
		isVertical = b;
	}
}
