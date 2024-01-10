
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import java.io.FileInputStream;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.io.File;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import objects.*;

public class Game extends GraphicsProgram {

	public static final String DIRWORK = System.getProperty("user.dir");
	public static final String PATH = DIRWORK + "\\src\\main\\resources\\images\\";
	public static final String LEVELPATH =  DIRWORK + "\\src\\main\\resources\\levels\\";

	File levelsFolder = new File(LEVELPATH);
	File[] lista = levelsFolder.listFiles();
	int nlevels = lista.length;

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	public static final String ARIA = "Berlin Sans FB Demi-35";
	public static final String GREY = "grey_button.png";
	public static final String BLUE = "blue_button.png";
	public static final String LP ="Level Points: ";

	double iniX;
	double iniY;

	double prevX;
	double prevY;

	double xx;
	double yy;

	Deque<ArrayList<Vehicle>> movesDeque = new ArrayDeque<>();

	static Level lvl;
	String vehN;
	ArrayList<Vehicle> listVeh;
	GObject obj;
	Vehicle selectedVeh;
	GLabel punctua;
	GLabel moves;
	GLabel punctuaGlobal;

	int punct = 100;
	int globalPunct = 0;
	int globalPunctAux = 0;
	static int level = 1;

	GImage imgPlay;
	GImage imgExit;
	GImage imgLoad;

	GImage save;
	GImage undo;
	GImage imgReset;
	GImage restart;

	GLabel play;
	GLabel exit;
	GLabel win;
	GLabel load;

	GImage dog;

	GLabel nameLevel;

	GImage backgroundImage;
	GImage backgroundGrey;

	GImage tittle;

	@Override
	public void init() {
		setSize(WIDTH+100, HEIGHT+100);
	}

	public void run() {
		menu();
		addMouseListeners();
	}

	void setBackGround() {
		backgroundImage = new GImage(PATH + "fondo.jpg");
		backgroundGrey = new GImage(PATH + "greyOnly.jpg");

		backgroundImage.setSize(WIDTH-20.0, HEIGHT-20.0);
		backgroundImage.setLocation(-30, -30);
		backgroundGrey.setSize(WIDTH+100.0, HEIGHT+100.0);

		add(backgroundGrey);
		add(backgroundImage);
	}


	public void runLvl() throws FileNotFoundException {
		removeAll();
		setBackGround();
		lvl = new Level(LEVELPATH + "level_"+level+".txt", level, punct, globalPunct);
		drawCars();
		hud();
	}

	public void runLoadLvl(int levelL, int punctL, int globalPunctL) {

		globalPunctAux = globalPunctL;
		level = levelL;
		punct = punctL;
		globalPunct = globalPunctL;
		setBackGround();
		drawCars();
		hud();
	}

	public void runWin() {
		winSlide();
	}

	public void hud() {
		punctuation();

		// reset
		imgReset = new GImage(PATH + "reset.png");
		imgReset.setSize(75, 75);
		imgReset.setLocation(600, HEIGHT - 550.0);
		add(imgReset);

		// undo
		undo = new GImage(PATH + "undo.png");
		undo.setSize(119, 60);
		undo.setLocation(575, HEIGHT - 450.0);
		add(undo);

		// save
		save = new GImage(PATH + "save.png");
		save.setSize(119, 60);
		save.setLocation(575, HEIGHT - 350.0);
		add(save);

		// restart game
		restart = new GImage(PATH + "restart.png");
		restart.setSize(119, 60);
		restart.setLocation(575, HEIGHT - 250.0);
		add(restart);

		nameLevel = new GLabel("Level " + level, 65 ,615);
		nameLevel.setFont("Berlin Sans FB Demi-50");

		add(nameLevel);

	}

	public final void saveGame() throws IOException{
		ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(DIRWORK + "\\src\\main\\resources\\saved\\saved.bin"));
		try {
			escritor.writeObject(lvl);
			
		} catch (IOException e) {
		}
		finally {
			escritor.close();
		}
	}

	public final void loadGame() throws IOException{
		ObjectInputStream lector = new ObjectInputStream(new FileInputStream(DIRWORK + "\\src\\main\\resources\\saved\\saved.bin"));

		try {
			lvl = (Level) lector.readObject();
			removeAll();
			runLoadLvl(lvl.getNum(), lvl.getPunctua(), lvl.getGlobalPunctua());
		}
		catch (IOException | ClassNotFoundException e) {} 
		finally {
			lector.close();
		}
	}

	double boxWid() {
		return (WIDTH-100.0) / lvl.getColumns();
	}

	double boxHei() {
		return (HEIGHT-100.0) / lvl.getRows();
	}

	private int calculateBox(double initialX, double initialY, double finalX, double finalY, Vehicle veh) {
		double numMoveY = finalY - initialY;
		double numMoveX = finalX - initialX;
		if (veh != null) {
			if (veh.isVertical())
				return (int)(numMoveY / (boxHei() - 5));
			else
				return (int)(numMoveX / (boxWid() - 5));
		}
		return 0;
	}

	Box boxSpa(double x, double y) {
		return new Box((int)(y / boxHei()), (int)(x / boxWid()));
	}

	private double getIniX(Vehicle veh) {
		return veh.getStartCol() * boxWid();
	}

	private double getIniY(Vehicle veh) {
		return veh.getStartRow() * boxHei();
	}

	Vehicle getBoxVeh(double x, double y) {
		Box s = boxSpa(x,y);
		return lvl.getVehicle(s);
	}

	public void handleMousePressed (double iniX2, double iniY2) throws IOException {
		obj = getElementAt(iniX2, iniY2);
		if (obj == imgPlay || obj == play) {
			try {
				runLvl();
			} catch (FileNotFoundException e1) {
			}}
		else if (obj == imgExit || obj == exit) {
			System.exit(0); }
		else if (obj == imgReset) {
			try {
				resetLevel();
			} catch (FileNotFoundException e1) {
			}}
		else if (obj == undo) {
			undo();
		}
		else if (obj == restart) {
			try {
				restartGame();
			} catch (FileNotFoundException e1) {
			}}
		else if (obj == save) {
			saveGame();
		}
		else if (obj == load || obj == imgLoad) {
			loadGame();
		}
		else if(isVehicle()) {
			selectedVeh = getBoxVeh(iniX2,iniY2);
		}
	}

	private boolean isVehicle() {
		return (obj instanceof GImage) &&  obj != imgExit && obj != backgroundImage && obj != restart && obj != load && obj != imgLoad && obj != imgPlay && obj != imgReset && obj != undo && obj != save && obj != backgroundGrey && obj != tittle;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		iniX = e.getX(); iniY = e.getY(); prevX = e.getX(); prevY = e.getY();
		try {
			handleMousePressed(iniX, iniY);
		} catch (IOException e1) {
		}}

	public void undo() {
		if (!movesDeque.isEmpty()) {
			ArrayList<Vehicle> l = movesDeque.pop();
			clear();
			lvl.setVehiclesOnPanel(l);
			runLoadLvl(level, punct, globalPunct);
		}
	}

	public void resetLevel() throws FileNotFoundException {
		clear();
		punct = 100;
		runLvl();
	}

	public void restartGame () throws FileNotFoundException {
		clear();
		punct = 100;
		level = 1;
		runLvl();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (obj instanceof GLabel)
			return;
		if (isVehicle()) {
			if(selectedVeh.isVertical()) {
				obj.move(0, e.getY() - prevY);
				prevY = e.getY();
			}
			else {
				obj.move(e.getX() - prevX, 0);
				prevX = e.getX();
			}
		}
	}

	public void handleMouseReleased(double x, double y) {
		int boxes = calculateBox(iniX, iniY, x, y, selectedVeh);

		if (!lvl.moveBoxes(selectedVeh.getStart(), boxes)) {
			obj.setLocation(getIniX(selectedVeh), getIniY(selectedVeh));
		} else {
			obj.setLocation(getIniX(selectedVeh), getIniY(selectedVeh));
			movesDeque.push((ArrayList<Vehicle>) lvl.getVehiclesOnPanel());
			if (lvl.getNumMoves() < 10 ) {
				punct = punct - 1;
				punctua.setLabel(LP+punct);
			} else if ((lvl.getNumMoves() >= 10 && lvl.getNumMoves() < 20)) {
				punct = punct - 2;
				punctua.setLabel(LP+punct);
			} else if (lvl.getNumMoves() >= 21 ) {
				punct = punct - 4;
				punctua.setLabel(LP+punct);
			}
			moves.setLabel("Moves: "+lvl.getNumMoves());
			lvl.setPunctua(punct);
			levelPassed();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (obj instanceof GLabel)
			return;
		if (obj != null && isVehicle()) {
			double x = e.getX();
			double y = e.getY();
			handleMouseReleased(x, y);
		}
	}


	public void handleMouseMove(double xx, double yy) {
		if(getElementAt(xx, yy) == imgPlay || getElementAt(xx, yy) == play) {

			imgPlay.setImage(PATH+ BLUE);
			imgPlay.setSize(150, 75);
		}
		if(getElementAt(xx, yy) == imgExit || getElementAt(xx, yy) == exit) {

			imgExit.setImage(PATH+BLUE);
			imgExit.setSize(150, 75);
		}
		if(getElementAt(xx, yy) == imgLoad || getElementAt(xx, yy) == load) {

			imgLoad.setImage(PATH+BLUE);
			imgLoad.setSize(150, 75);
		}

		if(getElementAt(xx,yy) != imgExit && getElementAt(xx, yy) != exit ) {

			imgExit.setImage(PATH+ GREY);
			imgExit.setSize(150, 75);
		}

		if(getElementAt(xx,yy) != imgPlay && getElementAt(xx, yy) != play) {
			imgPlay.setImage(PATH+ GREY);
			imgPlay.setSize(150, 75);
		}

		if(getElementAt(xx,yy) != imgLoad && getElementAt(xx, yy) != load) {
			imgLoad.setImage(PATH+ GREY);
			imgLoad.setSize(150, 75);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		handleMouseMove(e.getX(),  e.getY());}

	private void drawCars() {
		listVeh = (ArrayList<Vehicle>) lvl.getVehiclesOnPanel();
		for (int i = 0; i < listVeh.size(); i++) {
			drawCar(listVeh.get(i));
		}
	}

	private void punctuation() {
		punctuaGlobal = new GLabel("Global Points: "+globalPunctAux,325,665);
		punctuaGlobal.setFont(ARIA);
		add(punctuaGlobal);

		punctua = new GLabel(LP+ punct,325,615);
		punctua.setFont(ARIA);
		add(punctua);

		moves = new GLabel("Moves: "+0,325,565);
		moves.setFont(ARIA);
		add(moves);
	}

	void handleLevelpassed() {
		removeAll();

		int aux = punct;

		backgroundGrey = new GImage(PATH + "grey.jpg");
		backgroundGrey.setSize(WIDTH+100.0, HEIGHT+100.0);
		backgroundGrey.sendToBack();
		add(backgroundGrey);

		level++;
		globalPunct += punct;
		punct = 100;
		if(level == nlevels+1) {
			globalPunct /= nlevels;
			punctua = new GLabel(String.valueOf(aux), WIDTH/2.0+200.0, HEIGHT/2.0-98.0);
			punctua.setFont(ARIA);
			add(punctua);

			punctua = new GLabel(String.valueOf(globalPunct),WIDTH/2.0+100.0,HEIGHT/2.0);
			punctua.setFont(ARIA);
			add(punctua);
			finalWinSlide();
		}
		else {
			globalPunctAux = globalPunct/(level-1);
			punctua = new GLabel(String.valueOf(aux), WIDTH/2.0+200.0, HEIGHT/2.0-98.0);
			punctua.setFont(ARIA);
			add(punctua);
			runWin();
		}
	}

	void levelPassed() {
		if(lvl.carwin()) {
			handleLevelpassed();}}

	private void menu() {

		backgroundGrey = new GImage(PATH + "grey.jpg");
		backgroundGrey.setSize(WIDTH+100.0, HEIGHT+100.0);
		backgroundGrey.sendToBack();
		add(backgroundGrey);

		tittle = new GImage(PATH + "tittle.png");
		tittle.setSize(220,100);
		tittle.setLocation(250, 50);
		add(tittle);

		imgPlay = new GImage(PATH + GREY);
		imgPlay.setSize(150, 75);
		imgPlay.setLocation(275, HEIGHT/2.0-100.0);
		play = new GLabel("Play",imgPlay.getX()+37,imgPlay.getY()+45);
		play.setFont(ARIA);

		add(imgPlay);
		add(play);

		imgExit = new GImage(PATH + GREY);
		imgExit.setSize(150, 75);
		imgExit.setLocation(275, HEIGHT/2.0+100.0);
		exit = new GLabel("Exit",imgExit.getX()+43,imgExit.getY()+45);
		exit.setFont(ARIA);
		add(imgExit);
		add(exit);

		imgLoad = new GImage(PATH + GREY);
		imgLoad.setSize(150, 75);
		imgLoad.setLocation(275, HEIGHT/2.0);
		load = new GLabel("Load",imgLoad.getX()+35,imgLoad.getY()+45);
		load.setFont(ARIA);

		add(imgLoad);
		add(load);

	}

	private void winSlide() {

		imgPlay = new GImage(PATH + GREY);

		imgPlay.setLocation(275, HEIGHT/2.0-25);
		play = new GLabel("Continue",imgPlay.getX()+5,imgPlay.getY()+45);
		play.setFont(ARIA);

		add(imgPlay);
		add(play);

		imgExit = new GImage(PATH + GREY);
		imgExit.setLocation(275, HEIGHT/2.0+75);
		exit = new GLabel("Exit",imgExit.getX()+45,imgExit.getY()+45);
		exit.setFont(ARIA);

		add(imgExit);
		add(exit);

		win = new GLabel("Congratulations, You Win!",WIDTH/2.0-150.0, HEIGHT/2.0-200.0);
		win.setFont(ARIA);
		add(win);
		win = new GLabel("These are your points: ", WIDTH/2.0-145.0, HEIGHT/2.0-100.0);
		win.setFont(ARIA);
		add(win);

		dog = new GImage(PATH + "dog.png");
		dog.setSize(223.6,178.4);
		dog.setLocation(235, HEIGHT/2.0+145.0);
		add(dog);

	}

	void finalWinSlide() {

		imgExit = new GImage(PATH + GREY);
		imgExit.setLocation(275, HEIGHT/2.0+50.0);
		exit = new GLabel("Exit",imgExit.getX()+45,imgExit.getY()+45);
		exit.setFont(ARIA);
		add(imgExit);
		add(exit);

		win = new GLabel("Congratulations, You Win!",WIDTH/2.0-150.0,HEIGHT/2.0-200.0);
		win.setFont(ARIA);
		add(win);
		win = new GLabel("These are your points: ", WIDTH/2.0-145.0,HEIGHT/2.0-100.0);
		win.setFont(ARIA);
		add(win);
		win = new GLabel("Global: ", WIDTH/2.0-50.0,HEIGHT/2.0);
		win.setFont(ARIA);

		add(win);
		add(dog);
	}

	private void drawCar(Vehicle veh) {
		GImage car;
		if (!veh.isVertical()) {
			vehN = PATH + veh.getVehicleModel().toString() + ".png";
			car = new GImage(vehN,getIniX(veh),getIniY(veh));
			if (veh.getVehicleModel() == VehicleModel.TRUCK) {
				car.setSize(boxWid() * 3, boxHei());
			}
			else
				car.setSize(boxWid() * 2, boxHei());
		}
		else {
			vehN = PATH + veh.getVehicleModel().toString() + "_vert.png";
			car = new GImage(vehN,getIniX(veh),getIniY(veh));
			if (veh.getVehicleModel() == VehicleModel.TRUCK) {
				car.setSize(boxWid(), boxHei() * 3);
			}
			else
				car.setSize(boxWid(), boxHei() * 2);
		}
		add(car);
	}

	public static void main(String[] args) {
		new Game().start();}
}

