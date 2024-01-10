
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import objects.*;

public class PanelTest {

	private Panel panel;

	@BeforeEach
	public void setUp() {
		panel = new Panel(6, 6);
	}

	@Nested
	@DisplayName("Test addVehicle method")
	class AddVehicleTest {
		@Test
		public void testAddVehicle() {
			// Agregar un vehículo al panel
			panel.addVehicle(VehicleModel.MYCAR, 2, 1, 2, false);

			// Comprobar si el vehículo se agregó correctamente
			ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) panel.getVehicleOnPanel();
			assertEquals(1, vehicles.size()); // Comprueba el número de vehículos en el panel

			Vehicle vehicle = panel.getVehicle(new Box(2, 1));
			assertNotNull(vehicle); // Comprueba si el vehículo está presente en la posición dada
			assertEquals(VehicleModel.MYCAR, vehicle.getVehicleModel());
		}
		
		@Test
		public void testGetVehicleModel() {
			Vehicle v = new Vehicle(VehicleModel.MYCAR, 2, 1, 2, false);
			panel.setV(v);

			assertEquals(VehicleModel.MYCAR, panel.getVehicleModel());
		}
		
		@Test
		public void testGetV() {
			Vehicle v = new Vehicle(VehicleModel.MYCAR, 2, 1, 2, false);
			panel.setV(v);

			assertEquals(v, panel.getV());
		}
		
		@Test
		public void testGetVehicle() {
			panel.addVehicle(VehicleModel.MYCAR, 2, 1, 2, false);

			ArrayList<Vehicle> newL = new ArrayList<Vehicle>();
			
			panel.setVehicleOnPanel(newL);
			assertEquals(panel.getVehicleOnPanel(), newL);
		}
		
		@Test
		public void testGetVehicleNull() {
			Box box = new Box (0,0);
			Vehicle v = panel.getVehicle(box);
			assertNull(v);
		}
		
		@Test
		public void testPossibleMoveBoxesF() {
			Box box = new Box (0,0);
			boolean res = panel.possibleMoveBoxes(box, 1);
			assertFalse(res);
		}
	}
	
	



	@Nested
	@DisplayName("Test possibleMoveBoxes method")
	class PossibleMoveBoxesTest {
		@Test
		public void testPossibleMoveBoxes() {
			// Agregar un vehículo al panel
			panel.addVehicle(VehicleModel.MYCAR, 2, 1, 2, false);

			// Comprobar si es posible mover las cajas del vehículo
			Box start = new Box(2, 1);
			int numSpaces = 2;
			boolean canMove = panel.possibleMoveBoxes(start, numSpaces);

			assertTrue(canMove); // Comprueba si es posible mover las cajas del vehículo
		}
	}

	@Nested
	@DisplayName("Test getVehicle method")
	class GetVehicleTest {
		@Test
		public void testGetVehicle() {
			// Agregar un vehículo al panel
			panel.addVehicle(VehicleModel.MYCAR, 2, 1, 2, false);

			// Obtener el vehículo en una posición específica
			Box space = new Box(2, 1);
			Vehicle vehicle = panel.getVehicle(space);

			assertNotNull(vehicle); // Comprueba si hay un vehículo en la posición dada
			assertEquals(VehicleModel.MYCAR, vehicle.getVehicleModel());
		}
	}

	@Nested
	@DisplayName("Test isVehicleOnSpace method")
	class IsVehicleOnSpaceTest {
		@Test
		public void testIsVehicleOnSpace() {
			// Agregar un vehículo al panel
			panel.addVehicle(VehicleModel.MYCAR, 2, 1, 2, false);

			// Comprobar si hay un vehículo en una posición específica
			Box space = new Box(2, 1);
			boolean isVehiclePresent = panel.isVehicleOnSpace(space);

			assertTrue(isVehiclePresent); // Comprueba si hay un vehículo en la posición dada
		}
	}

	@Nested
	@DisplayName("Test getNumRows and getNumCols methods")
	class RowsAndColsTest {
		@Test
		public void testGetNumRows() {
			assertEquals(6, panel.getNumRows());
		}

		@Test
		public void testGetNumCols() {
			assertEquals(6, panel.getNumCols());
		}

		@Test
		public void testSetNumRows() {
			panel.setNumRows(7);
			assertEquals(7, panel.getNumRows());
		}

		@Test
		public void testSetNumCols() {
			panel.setNumCols(7);
			assertEquals(7, panel.getNumCols());
		}
	}


}

