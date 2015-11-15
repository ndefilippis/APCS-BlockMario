package entities;

import game.Direction;
import game.Game;
import game.GameRunner;
import game.Level;
import game.MultiplayerGame;
import game.SubLevel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import network.GameClient;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import area.PipeArea;
import area.Water;

public class Character extends Mob {
	private int health = 5;
	private int maxHealth = 5;
	private int coins;
	public boolean invincible, jump, isJumping;
	public int levelCoins;
	private long invincibleTime;
	private String name;
	private Grabbable hold;
	private Level currentLevel;
	private boolean downPipe, upPipe;
	private Direction pipeDirection;
	private long timeDownPipe;
	private SubLevel pipeLocation;
	private PipeArea pipeLinkedArea;
	private boolean slideFlag;
	private boolean levelUp;
	private boolean key;
	public static int SPEED = 5;
	private static int lives = 5;

	public Character(String name) {
		super(0, 0, 20, 20);
		this.setName(name);
		currentLevel = Game.getCurrentLevel();
	}

	public void jump() {
		if (downBlocked || !jump) {
			if (!downBlocked)
				jump = true;
			Vy = -20 * gravity;
			isJumping = true;
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream("src/resources/jump.wav")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public Grabbable getHold() {
		return hold;
	}

	public void restoreLives() {
		lives = 5;
	}

	public void draw(Graphics g, int x, int y) {
		if (GameRunner.g instanceof MultiplayerGame) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Comic Sans", Font.BOLD, 20));
			// g.drawString(name+"", x, y-20);
		}
		if (invincible) {
			if ((System.currentTimeMillis() - invincibleTime) % 500 < 100) {
				return;
			}
		}
		g.setColor(Color.RED);
		if(key){
			g.setColor(new Color(255, 128, 0));
			g.fillRect(x-5, y+height-(int)(System.currentTimeMillis()/25)%height, width+10, 3);
		}
		g.fillRect(x, y, width, height);
		if (currentArea instanceof Water) {
			g.setColor(new Color(117, 196, 214));
			g.drawOval(
					(int) (x + 5 - 10 * (Math
							.cos(System.currentTimeMillis() / 100))),
					(int) (y - System.currentTimeMillis() % 1000 / 20), 10, 10);
		}
	}

	@Override
	public void update() {
		if (downPipe) {

			setVy(0);
			setVx(0);
			if (System.currentTimeMillis() > timeDownPipe + 1000) {
				downPipe = false;
				upPipe = true;
				currentLevel.goToSubLevel(pipeLocation, pipeLinkedArea);
				try {
					AudioPlayer.player.start(new AudioStream(
							new FileInputStream("src/resources/upPipe.wav")));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			if (pipeDirection == Direction.LEFT) {
				setX(x - 1);
			}
			if (pipeDirection == Direction.RIGHT) {
				setX(x + 1);
			}
			if (pipeDirection == Direction.TOP) {
				setY(y - 1);
			}
			if (pipeDirection == Direction.BOTTOM) {
				setY(y + 1);
			}
			return;
		}
		if (upPipe) {
			if (pipeLinkedArea.d == Direction.LEFT && x > pipeLinkedArea.x()
					|| pipeLinkedArea.d == Direction.RIGHT
					&& x < pipeLinkedArea.x() + pipeLinkedArea.width() - 20
					|| pipeLinkedArea.d == Direction.TOP
					&& y > pipeLinkedArea.y()
					|| pipeLinkedArea.d == Direction.BOTTOM
					&& y < pipeLinkedArea.y() + pipeLinkedArea.height() - 20) {
				upPipe = false;
				pipeLinkedArea = null;
				pipeLocation = null;
				pipeDirection = null;
				return;
			}
			setVy(0);
			setVx(0);
			if (pipeLinkedArea.d == Direction.LEFT) {
				setX(x + 1);
			}
			if (pipeLinkedArea.d == Direction.RIGHT) {
				setX(x - 1);
			}
			if (pipeLinkedArea.d == Direction.TOP) {
				setY(y + 1);
			}
			if (pipeLinkedArea.d == Direction.BOTTOM) {
				setY(y - 1);
			}
			return;
		}
		if (slideFlag) {
			Vx = 0;

			if (y < Game.getCurrentArea().ground-50) {
				y += 5;
			} else {
				slideFlag = false;
				x += 5;
				Vx = 5;
				Vy = -3;
			}
			return;
		}
		if (System.currentTimeMillis() > invincibleTime + 3000)
			invincible = false;
		if (x < 0)
			x = 0;
		super.update();
		if (GameRunner.g != null && GameRunner.g.mp() != null)
			GameRunner.g.mp().setOffset();
		if (downBlocked) {
			if (isJumping && Vy > 0) {
				try {
					AudioPlayer.player.start(new AudioStream(
							new FileInputStream("src/resources/land.wav")));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isJumping = false;
			}
			jump = false;
		}
		if (GameRunner.g instanceof MultiplayerGame) {
			GameClient.sendMessage(x + ":" + y);
		}
		if (hold != null) {
			if (direction == Direction.RIGHT) {
				hold.setX(x + width);
				hold.setY(y + height / 2);
			} else {
				hold.setX(x - hold.width - 1);
				hold.setY(y + height / 2 + 1);
			}
		}
	}

	@Override
	public String toString() {
		return getName();
	}

	public void releaseJump() {
		if (Vy < -6) {
			Vy = -6;
		}
	}

	public boolean isSlideFlag() {
		return slideFlag;
	}

	@Override
	public void collide(Entity e, Direction d) {
		if (e.equals(hold))
			return;
		super.collide(e, d);
		if (d != Direction.TOP && e instanceof Enemy && !invincible && !(((Enemy)e) instanceof BigFish)) {
			hurt();
			invincible(System.currentTimeMillis());
		}
	}

	private void invincible(long time) {
		invincible = true;
		invincibleTime = time;
	}

	public void hurt() {
		if (!invincible) {
			health--;
			invincible(System.currentTimeMillis());
			if (levelUp)
				levelDown();
		}
		if (health == 0) {
			destroy();
		}
	}

	public void drawHealth(Graphics g, boolean b) {
		if (b) {
			g.setColor(Color.RED);
			for (int i = 0; i < health; i++) {
				g.fillRect(i * 51, 0, 50, 20);
			}
			for (int i = 0; i < maxHealth - health; i++) {
				g.setColor(Color.GRAY);
				g.fillRect((i + health) * 51, 0, 50, 20);
			}
		} else {
			int width = GameRunner.g.mp().getWidth();
			g.setColor(Color.BLUE);
			for (int i = 0; i < health; i++) {
				g.fillRect(width - (i + 1) * 51, 0, 50, 20);
			}
			for (int i = 0; i < maxHealth - health; i++) {
				g.setColor(Color.GRAY);
				g.fillRect(width - (i + health + 1) * 51, 0, 50, 20);
			}
		}
	}

	public void drawCoins(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(600, 10, 15, 20);
		g.setColor(Color.BLACK);
		g.drawLine(607, 13, 607, 27);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Comic Sans", Font.BOLD, 20));
		g.drawString("x"+(levelCoins + coins), 625, 25);
	}

	public void fullHealth() {
		health = maxHealth;
	}
	
	public int coins(){
		return coins;
	}
	public void setCoins(int n){
		coins = n;
	}
	
	public void setLives(int n){
		lives = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void grab(Grabbable s) {
		hold = s;
	}

	public void destroy() {
		lives--;
		levelCoins = 0;
		try {
			AudioPlayer.player.start(new AudioStream(new FileInputStream(
					"src/resources/die.wav")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			GameRunner.g.restart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dropHold() {
		if (hold != null)
			hold.drop(direction);
		hold = null;
	}

	public void addCoin() {
		levelCoins++;
	}

	public void setCurrentLevel(Level lev) {
		currentLevel = lev;
	}

	public void goToSubLevel(SubLevel sublevel, PipeArea pa) {
		currentLevel.goToSubLevel(sublevel, pa);
	}

	public void sendPipe(Direction d, SubLevel location, PipeArea linkedArea) {
		if(!downPipe){
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"src/resources/downPipe.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		downPipe = true;
		pipeLocation = location;
		pipeLinkedArea = linkedArea;
		pipeDirection = d;
		timeDownPipe = System.currentTimeMillis();
	}

	public int getLives() {
		return lives;
	}

	public void drawLives(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(507, 10, 20, 20);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Comic Sans", Font.BOLD, 20));
		g.drawString("x" + (lives), 535, 25);
	}

	public void slideFlag() {
		slideFlag = true;
	}

	public void levelUp() {
		if (!levelUp) {
			y -= height;
			height *= 2;
			levelUp = true;
		}
	}

	public boolean leveled() {
		return levelUp;
	}

	public void levelDown() {
		y += height / 2;
		height /= 2;
		levelUp = false;
		try {
			AudioPlayer.player.start(new AudioStream(new FileInputStream(
					"src/resources/loseMushroom.wav")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setKey(boolean b){
		key = b;
	}

	public boolean hasKey() {
		return key;
	}
}
