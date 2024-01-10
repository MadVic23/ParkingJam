import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import objects.*;
import acm.graphics.GDimension;

public class GameTest {

	@Nested
	@DisplayName("Game Initialization")
	class Initialization {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
		}

		@Test
		@DisplayName("Game initializes correctly")
		void testGameInitialization() {
			assertNotNull(game);
		}

		@Test
		@DisplayName("Game has correct dimensions")
		void testGameDimensions() {
			assertEquals(600, Game.WIDTH);
			assertEquals(600, Game.HEIGHT);
		}

		@Test
		@DisplayName("Game has initial level set to 1")
		void testInitialLevel() {
			assertEquals(1, Game.level);
		}

		@Test
		@DisplayName("Game initializes with empty moves stack")
		void testEmptyMovesStack() {
			assertTrue(game.movesDeque.isEmpty());
		}
	}

	@Nested
	@DisplayName("Game Level")
	class LevelTests {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
			game.start();
		}

		@Test
		@DisplayName("Run Level initializes the level")
		void testRunLevel() throws FileNotFoundException {
			game.runLvl();

			assertNotNull(Game.lvl);
		}

		@Test
		@DisplayName("Undo")
		void testUndo() throws FileNotFoundException {
			game.runLvl();
			game.movesDeque.push((ArrayList<Vehicle>) game.lvl.getVehiclesOnPanel());
			int dim = game.movesDeque.size();
			game.undo();
			assertEquals(dim-1, game.movesDeque.size());
		}

		//		@Test
		//		@DisplayName("Level Passed")
		//		void testLevelPassed() {
		//			Box box = new Box (2, 1);
		//			game.lvl.getPanel().addVehicle(VehicleModel.MYCAR, 2, 5, 2, false);
		//			game.levelPassed();
		//			assertTrue(2, game.level);
		//		}

		@Test
		@DisplayName("Run Win displays win message")
		void testRunWin() {
			game.runWin();

			assertNotNull(game.win);
		}

		@Test
		@DisplayName("Final Win Slide")
		void testWinSlide() throws FileNotFoundException {
			game.runWin();
			game.finalWinSlide();
			assertEquals(game.dog.getX(), 235);
		}
	}

	@Nested
	@DisplayName("HUD")
	class HUDTests {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
			game.start();
		}

		@Test
		@DisplayName("HUD initializes with correct elements")
		void testHUDInitialization() throws FileNotFoundException {
			game.hud();

			assertNotNull(game.imgReset);
			assertNotNull(game.undo);
			assertNotNull(game.restart);
		}

		@Test
		@DisplayName("HUD displays punctuation and moves")
		void testHUDPunctuationAndMoves() throws FileNotFoundException {
			game.runLvl();

			assertNotNull(game.punctua);
			assertNotNull(game.moves);
		}
	}

	@Nested
	@DisplayName("Save and load")
	class SaveAndLoadTests {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
			game.start();
		}

		//		@Test
		//		@DisplayName("Save Game")
		//		void testSaveGame() throws FileNotFoundException {
		//			game.runLvl();
		//
		//			assertNotNull(Game.lvl);
		//		}

		@Test
		@DisplayName("Load Game Test")
		void testLoadGame() throws FileNotFoundException, IOException, ClassNotFoundException {
			int aux = game.lvl.getPunctua();
			game.saveGame();
			game.loadGame();
			assertEquals(aux, game.lvl.getPunctua());
		}
	}

	@Nested
	@DisplayName("Reset and Restart")
	class ResetAndRestartTests {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
			game.start();
		}

		@Test
		@DisplayName("ResetLevel")
		void testResetLevel() throws FileNotFoundException {
			game.resetLevel();
			assertEquals(game.lvl.getPunctua(), 100);
		}

		@Test
		@DisplayName("RestartLevel")
		void testRestartLevel() throws FileNotFoundException {
			game.resetLevel();
			assertEquals(game.level, 1);
		}
	}

	@Nested
	@DisplayName("Mouse Listener")
	class MouseListenerTests {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
			game.start();
		}

		@Test
		@DisplayName("MousePressedImgPlay")
		void mousePressedImgPlay() throws IOException {
			game.handleMousePressed( game.imgPlay.getX(), game.imgPlay.getY());
			assertEquals(game.obj, game.imgPlay);
		}

		@Test
		@DisplayName("MousePressedObjPlay")
		void mousePressedObjPlay() throws IOException {
			game.handleMousePressed( game.play.getX(), game.play.getY());
			assertEquals(game.obj, game.play);
		}

		@Test
		@DisplayName("MousePressedUndo")
		void mousePressedUndo() throws IOException {
			game.runLvl();
			game.handleMousePressed(game.undo.getX(), game.undo.getY());
			assertEquals(game.obj, game.undo);
		}

		@Test
		@DisplayName("MousePressedSave")
		void mousePressedSave() throws IOException {
			game.runLvl();
			game.handleMousePressed(game.save.getX(), game.save.getY());
			assertEquals(game.obj, game.save);
		}
	}

	@Nested
	@DisplayName("Mouse Listener Load")
	class MouseListenerLoadTests {

		private Game game;

		@BeforeEach
		void setUp() {
			game = new Game();
			game.start();
		}

		@Test
		@DisplayName("MousePressedLoad")
		void mousePressedLoad() throws IOException {
			game.handleMousePressed(game.load.getX(), game.load.getY());
			assertEquals(game.obj, game.load);
		}

		@Test
		@DisplayName("MousePressedImgReset")
		void mousePressedImgReset() throws IOException {
			game.runLvl();
			game.handleMousePressed(game.imgReset.getX(), game.imgReset.getY());
			assertEquals(game.obj.getX(), game.imgReset.getX());
		}

		@Test
		@DisplayName("MousePressedRestart")
		void mousePressedRestart() throws IOException {
			game.runLvl();
			game.handleMousePressed(game.restart.getX(), game.restart.getY());
			assertEquals(game.obj.getX(), game.restart.getX());
		}

	}

}

class MouseListenerLoadImgTests {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
		game.start();
	}

	@Test
	@DisplayName("MousePressedImgLoad")
	void mousePressedimgLoad() throws IOException {
		game.handleMousePressed(game.imgLoad.getX(), game.imgLoad.getY());
		assertEquals(game.obj, game.imgLoad);
	}
}

@Nested
@DisplayName("Mouse Move Tests")
class MouseMoveTests {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
		game.start();
	}

	@Test
	@DisplayName("mouseMoveImgPlay")
	void testMouseMoveImgPlay() throws FileNotFoundException, IOException, ClassNotFoundException {
		game.handleMouseMove(game.imgPlay.getX(), game.imgPlay.getY());
		GDimension dim = new GDimension(150, 75);
		assertEquals(game.imgPlay.getSize(), dim);
	}

	@Test
	@DisplayName("mouseMovePlay")
	void testMouseMovePlay() throws FileNotFoundException, IOException, ClassNotFoundException {
		game.handleMouseMove(game.play.getX(), game.play.getY());
		GDimension dim = new GDimension(150, 75);
		assertEquals(game.imgPlay.getSize(), dim);
	}

	@Test
	@DisplayName("mouseMoveImgExit")
	void testMouseMoveImgExit() throws FileNotFoundException, IOException, ClassNotFoundException {
		game.handleMouseMove(game.imgExit.getX(), game.imgExit.getY());
		GDimension dim = new GDimension(150, 75);
		assertEquals(game.imgExit.getSize(), dim);
	}

	@Test
	@DisplayName("mouseMoveExit")
	void testMouseMoveExit() throws FileNotFoundException, IOException, ClassNotFoundException {
		game.handleMouseMove(game.exit.getX(), game.exit.getY());
		GDimension dim = new GDimension(150, 75);
		assertEquals(game.imgExit.getSize(), dim);
	}

	@Test
	@DisplayName("mouseMoveImgLoad")
	void testMouseMoveImgLoad() throws FileNotFoundException, IOException, ClassNotFoundException {
		game.handleMouseMove(game.imgLoad.getX(), game.imgLoad.getY());
		GDimension dim = new GDimension(150, 75);
		assertEquals(game.imgLoad.getSize(), dim);
	}

	@Test
	@DisplayName("mouseMoveLoad")
	void testMouseMoveLoad() throws FileNotFoundException, IOException, ClassNotFoundException {
		game.handleMouseMove(game.load.getX(), game.load.getY());
		GDimension dim = new GDimension(150, 75);
		assertEquals(game.imgLoad.getSize(), dim);
	}
}
@Nested
@DisplayName("Box Calculations")
class BoxCalculations {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
		game.start();
	}

	@Test
	@DisplayName("Box Spa")
	void testBoxSpa() {
		Box b = new Box ((int)(40.0 / game.boxHei()), (int)(80.0 / game.boxWid()));
		Box b2 =  game.boxSpa(80, 40);
		assertEquals(b,b2);
	}

	@Test
	@DisplayName("Get Box vehicle")
	void testGetBoxV() throws FileNotFoundException {
		game.runLvl();
		Vehicle v =  game.getBoxVeh(80.0, 40.0);
		assertEquals(game.lvl.getVehiclesOnPanel().get(0),v);
	}

	@Test
	@DisplayName("Calculate Box width returns correct value")
	void testCalculateBoxWidth() {

		double expectedWidth = (Game.WIDTH - 100) / game.lvl.getColumns();
		assertEquals(expectedWidth, game.boxWid());
	}

	@Test
	@DisplayName("Calculate Box height returns correct value")
	void testCalculateBoxHeight() throws FileNotFoundException {
		game.runLvl();

		double expectedHeight = (Game.HEIGHT - 100) / game.lvl.getRows();
		assertEquals(expectedHeight, game.boxHei());
	}

	@Test
	@DisplayName("Calculate Box returns correct Box object")
	void testCalculateBox() throws FileNotFoundException {
		game.runLvl();

		double x = 200;
		double y = 300;
		int expectedRow = (int) (y / game.boxHei());
		int expectedCol = (int) (x / game.boxWid());

		Box box = game.boxSpa(x, y);

		assertEquals(expectedRow, box.getRow());
		assertEquals(expectedCol, box.getCol());
	}

}

class LevelPassedTests {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
		game.start();
	}

	@Test
	@DisplayName("LevelPassedE")
	void mousePressedLevelPassedE() throws FileNotFoundException {
		game.handleLevelpassed();
		assertEquals(game.punctua.getX(), game.WIDTH/2.0 + 200.0);
	}
}

class HandleMouseTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
		game.start();
	}

	@Test
	@DisplayName("Handle Mouse Test")
	void handleMouse() throws FileNotFoundException {
		game.runLvl();
		game.selectedVeh = game.lvl.getVehiclesOnPanel().get(0);
		game.obj = game.getElementAt(80.0, 40.0);
		game.handleMouseReleased(80.0, 40.0);
		assertEquals(game.punct, 99);
	}

	@Test
	@DisplayName("LevelPassedE")
	void mousePressedLevelPassedE() throws FileNotFoundException {
		game.handleLevelpassed();
		assertEquals(game.punctua.getX(), game.WIDTH/2.0 + 200.0);
	}
}
