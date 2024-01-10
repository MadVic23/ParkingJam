package objects;
import java.util.*;
import java.io.Serializable;

public class Panel implements Serializable {

	private int numRows;
	private int numCols;
	private ArrayList<Vehicle> addV = new ArrayList<>();
	private Vehicle[][] panel1;
	private Vehicle v;

	public Panel(int rows, int cols) 
	{
		this.numRows = rows;
		this.numCols = cols;
		panel1 = new Vehicle[rows][cols];
		v = new Vehicle();
	}


	public int getNumCols() 
	{
		return numCols; 
	}

	public void setNumCols(int cols)
	{
		this.numCols = cols;
	}


	public int getNumRows() 
	{
		return numRows; 
	}

	public void setNumRows(int rows)
	{
		this.numRows = rows;
	}

	public boolean isVehicleOnSpace(Box space) 
	{
		boolean res = true;
		if (panel1[space.getRow()][space.getCol()] != null) {
			return res;
		}
		res = false;
		return res;
	}

	public List<Vehicle> getVehicleOnPanel()
	{
		return addV;
	}

	public void setVehicleOnPanel(List<Vehicle> l) {
		addV = (ArrayList<Vehicle>) l;

	}

	public Vehicle getVehicle(Box s) 
	{
		if (panel1[s.getRow()][s.getCol()] != null)
		{
			return panel1[s.getRow()][s.getCol()];
		}
		return null;	
	}

	public VehicleModel getVehicleModel() 
	{
		return v.getVehicleModel();
	}



	public void addVehicle(VehicleModel model, int startRow, int startCol, int length, boolean vert) 
	{
		Vehicle tempVehicle = new Vehicle(model, startRow, startCol, length,vert);
		Box[] tempArray = tempVehicle.boxesOccupied();

		for (int i = 0; i < tempVehicle.getLength();i++ )
		{
			if (tempArray[i].getRow() < 0 || tempArray[i].getRow() >= numRows || tempArray[i].getCol() < 0 || tempArray[i].getCol() >= numCols)
			{
				return;
			}
			tempArray[i] = new Box( tempVehicle.boxesOccupied()[i].getRow(),tempVehicle.boxesOccupied()[i].getCol());
			if (isVehicleOnSpace(tempArray[i])) {
				return;
			}
		}
		for (int i = 0; i < tempVehicle.getLength(); i++) {
			panel1[tempVehicle.boxesOccupied()[i].getRow()][tempVehicle.boxesOccupied()[i].getCol()] = tempVehicle;
		}	
		addV.add(tempVehicle); 
	}


	public boolean moveBoxes(Box start, int numSpaces) {
		if (possibleMoveBoxes(start, numSpaces))
		{
			Vehicle moveVehicle = getVehicle(start);
			Box[] spaceVehicle = moveVehicle.boxesOccupied();
			for (int i = 0; i < moveVehicle.boxesOccupied().length; i++)
			{
				panel1[spaceVehicle[i].getRow()][spaceVehicle[i].getCol()] = null;
			}

			moveVehicle.move(numSpaces);
			addVehicle(moveVehicle.getVehicleModel(), moveVehicle.getStartRow(), moveVehicle.getStartCol(), moveVehicle.getLength(), moveVehicle.isVertical());
			addV.remove(addV.indexOf(getVehicle(start)));


			return true;
		}
		return false;
	}


	public boolean possibleMoveBoxes(Box start, int numSpaces) {

		Vehicle canMoveCar = getVehicle(start);
		if (canMoveCar == null) 
		{
			return false;
		}
		Box[] spaceOnTrail = canMoveCar.boxesOccup(numSpaces);
		for (int i = 0; i < Math.abs(numSpaces); i++)
		{
			if (spaceOnTrail[i].getRow() < 0 || spaceOnTrail[i].getRow() >= numRows || spaceOnTrail[i].getCol() < 0 || spaceOnTrail[i].getCol() >= numCols) 
			{
				return false;
			}
			if (isVehicleOnSpace(spaceOnTrail[i]))
			{
				return false;
			}
		}
		return true;
	}


	public Vehicle getV() {
		return v;
	}


	public void setV(Vehicle v) {
		this.v = v;
	}




}
