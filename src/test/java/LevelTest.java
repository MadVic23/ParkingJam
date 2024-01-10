
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import objects.*;

public class LevelTest {

	private Level level;

	@BeforeEach
	public void setUp() throws FileNotFoundException {
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\levels\\level_1.txt";
		level = new Level(path, 1, 100, 0); // Ejemplo: nivel 1
	}

	@Nested
	@DisplayName("Test setupLevel method")
	class SetupLevelTest {
		@Test
		public void testSetupLevel() {
			ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) level.getVehiclesOnPanel();
			assertEquals(8, vehicles.size());
		}
	}

	@Nested
	@DisplayName("Test moveBoxes method")
	class MoveBoxesTest {
		@Test
		public void testMoveBoxes() {
			Box start = new Box(1, 0);
			int numSpaces = 1;

			// Realizar el movimiento
			boolean moveSuccessful = level.moveBoxes(start, numSpaces);
			assertFalse(moveSuccessful);
		}
	}

	@Nested
	@DisplayName("Test incrementMoves method")
	class IncrementMovesTest {
		@Test
		public void testIncrementMoves() {
			int initialNumMoves = level.getNumMoves();

			// Incrementar el número de movimientos
			level.incrementMoves();

			// Comprobar el número de movimientos después de incrementar
			assertEquals(initialNumMoves + 1, level.getNumMoves());
		}
	}

	@Nested
	@DisplayName("Test getPath")
	class RowsAndColumnsTest {
		@Test
		public void testGetPath() {
			assertEquals(System.getProperty("user.dir") + "\\src\\main\\resources\\levels\\level_1.txt", level.getPath());
		}

		@Test
		public void testSetPath() {
			level.setPath("\\src\\main\\resources\\levels\\level_2.txt");
			assertEquals("\\src\\main\\resources\\levels\\level_2.txt", level.getPath());
		}
	}

	@Nested
	@DisplayName("Test getPunctua")
	class punctuaTest {
		@Test
		public void testGetPunctua() {
			assertEquals(100, level.getPunctua());
		}

		@Test
		public void testSetPunctua() {
			level.setPunctua(33);
			assertEquals(33, level.getPunctua());
		}
	}

	@Nested
	@DisplayName("Test getPunctua")
	class globalPunctuaTest {
		@Test
		public void testGetGlobalPunctua() {
			assertEquals(0, level.getGlobalPunctua());
		}

		@Test
		public void testSetGlobalPunctua() {
			level.setGlobalPunctua(33);
			assertEquals(33, level.getGlobalPunctua());
		}
	}

	@Nested
	@DisplayName("Test getVehicle method")
	class GetVehicleTest {
		@Test
		public void testGetVehicle() {
			Box space = new Box(2, 1);
			Vehicle vehicle = level.getVehicle(space);

			assertNotNull(vehicle); // Comprueba si hay un vehículo en la posición dada
			assertEquals(VehicleModel.MYCAR, vehicle.getVehicleModel());
		}
	}

	@Nested
	@DisplayName("Test carwin method")
	class carWinTest {
		@Test
		public void testCarWin() {
			assertFalse(level.carwin());
		}
	}


}
