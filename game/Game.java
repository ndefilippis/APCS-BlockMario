package game;

import entities.Character;
import entities.FlagPole;
import graphics.MainFrame;
import graphics.MainPanel;
import graphics.WorldPanel;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Game {
	protected MainFrame mf;
	protected static Character c;
	private static boolean isRunning = true;
	protected static Level currentLevel;
	private static SubLevel currentSubLevel;
	private static ArrayList<World> worlds;
	private static long time;

	private static boolean paused = false, started = false;
	public static int currLevel = 1;
	public static int currWorld = 1;

	public Game(MainFrame mf) throws IOException {
		this.mf = mf;
		worlds = new ArrayList<World>();
		for(int i = 1; i <= 4; i++){
			worlds.add(new World(i));
			for(int j = 1; j <= 4; j++){
				Level lev = new Level(i, j);
				worlds.get(i-1).addLevel(lev);
			}
		}
	}

	public void startGame(String name) throws IOException {
		c = new Character(name);
		mf.startGame();
		started = false;
		startGameLoop();
	}

	public void startGameLoop() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}

	public void gameLoop() {
		playBackground();
		double update = 1000000000D / 60D;
		double lastTime = System.nanoTime();
		while (isRunning) {
			Thread.yield();
			if (!paused) {
				double currTime = System.nanoTime();
				if (currTime - lastTime > update && !paused && started) {
					currentSubLevel.world.tick();
					lastTime = currTime;
					mf.mp().repaint();
				}
			}
		}
	}

	public void playBackground() {
		AudioInputStream inputStream = null;
		try {
			inputStream = AudioSystem.getAudioInputStream(new File("src/resources/background.wav"));
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.loop(Clip.LOOP_CONTINUOUSLY);		
	}

	public void restart() throws IOException {
		if (c.getLives() == 0) {
			gameOver();
		} else {
			startLevel(currWorld, currLevel);
		}
		c.fullHealth();
		if (c.leveled()) {
			c.levelDown();
		}
	}

	private void gameOver() {
		mf.gameOver();
		Game.c.restoreLives();
		try {
			restart();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Character getPlayer() {
		return c;
	}

	public static ArrayList<World> getWorlds() {
		return worlds;
	}

	public static Level getCurrentLevel() {
		return currentLevel;
	}

	public static SubLevel getCurrentArea() {
		return currentSubLevel;
	}

	public static void drawTime(Graphics g) {
		long currTime = (System.currentTimeMillis() - time) / 1000;
		g.drawString(currTime + "", 800, 20);
	}

	public static void setSublevel(SubLevel sublevel) {
		currentSubLevel = sublevel;
	}

	public static boolean paused() {
		return paused;
	}

	public void pause() {
		paused = true;
		mf.pause();
	}

	public void resume() {
		paused = false;
		mf.resume();
	}

	public MainPanel mp() {
		if (mf != null)
			return mf.mp();
		return null;
	}

	public static void loadSave(File file) {
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] data = s.nextLine().split(":");
		currWorld = Integer.parseInt(data[0]);
		currLevel = Integer.parseInt(data[1]);
		GameRunner.startSingle();
		for(int i = 0; i < currWorld-1; i++){
			for(int j = 0; j < 4; j++){
				worlds.get(i).levels.get(j).pass();
				WorldPanel.nextLevel();
			}
		}
		for(int i = 0; i < currLevel-1; i++){
			worlds.get(currWorld-1).levels.get(i).pass();
			WorldPanel.nextLevel();
		}
		Game.c.setCoins(Integer.parseInt(s.nextLine()));
		Game.c.setLives(Integer.parseInt(s.nextLine()));
		
	}
	
	public MainFrame mf(){
		return mf;
	}

	public void startLevel(int world, int level) throws IOException {
		Level lev = worlds.get(world-1).levels.get(level-1);
		LevelLoader.LoadLevel("src/resources/level" + world+level + ".txt", lev);
		currentLevel = lev;
		currentSubLevel = lev.getMain();
		currentSubLevel.world.load();
		c.setCurrentLevel(currentLevel);
		c.setX(lev.getMain().startX);
		c.setY(lev.getMain().startY);
		c.fullHealth();
		Environment.add(c);
		FlagPole.play = true;
		time = System.currentTimeMillis();
		started = true;
		mf.startLevel();
		
	}

	public void finishLevel() {
		c.setCoins(c.levelCoins);
		started = false;
		currentLevel.pass();
		currLevel++;
		if(currLevel > 4){
			currWorld++;
		}
		WorldPanel.nextLevel();
		mf.goToWorld();
	}

	public static void setLevel(int world, int level) {
		currWorld = world;
		currLevel = level;
		
	}

	public static boolean isPassed(int world, int level) {
		return worlds.get(world-1).levels.get(level-1).isPassed();
	}

}
