import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import objects.*;
public class VehicleTest {
    
    @Test
    public void testGetStart() {
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        Box start = vehicle.getStart();
        
        assertEquals(2, start.getRow());
        assertEquals(3, start.getCol());
    }
    
    @Test
    public void testGetStartRow() {
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        int startRow = vehicle.getStartRow();
        
        assertEquals(2, startRow);
    }
    
    @Test
    public void testGetStartCol() {
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        int startCol = vehicle.getStartCol();
        
        assertEquals(3, startCol);
    }
    
    @Test
    public void testGetLength() {
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        int length = vehicle.getLength();
        
        assertEquals(2, length);
    }
    
    @Test
    public void testIsVertical() {
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        boolean isVertical = vehicle.isVertical();
        
        assertTrue(isVertical);
    }
    
    @Test
    public void testSetStartRow() {
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        vehicle.setStartRow(5);
        
        assertEquals(5, vehicle.getStartRow());
    }
    
    @Test
    public void testSetStartCol() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        vehicle.setStartCol(7);
        
        assertEquals(7, vehicle.getStartCol());
    }
    
    @Test
    public void testSetVertical() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        boolean prev = vehicle.getVertical();
        vehicle.setVertical(false);
        assertEquals(!prev, vehicle.getVertical());
    }
    
    @Test
    public void testGetVehicleModel() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        VehicleModel vehicleModel = vehicle.getVehicleModel();
        
        assertEquals(VehicleModel.MYCAR, vehicleModel);
    }
    
    @Test
    public void testGetVehicleModelNull() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.NULL, 2, 3, 2, true);
        
        VehicleModel vehicleModel = vehicle.getVehicleModel();
        
        assertEquals("", vehicleModel.toString());
    }
    
    @Test
    public void testBoxesOccup() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        Box[] boxesOccupied = vehicle.boxesOccup(2);
        
        assertEquals(2, boxesOccupied.length);

    }
    
    @Test
    public void testMove() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        vehicle.move(3);
        
        assertEquals(5, vehicle.getStartRow());
        
        vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, false);
        
        vehicle.move(4);
        
        assertEquals(7, vehicle.getStartCol());
    }
    
    @Test
    public void testBoxesOccupied() {
        
        Vehicle vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 2, true);
        
        Box[] boxesOccupied = vehicle.boxesOccupied();
        
        assertEquals(2, boxesOccupied.length);
        
        vehicle = new Vehicle(VehicleModel.MYCAR, 2, 3, 3, false);
        
        boxesOccupied = vehicle.boxesOccupied();
        
        assertEquals(3, boxesOccupied.length);

    }
}
