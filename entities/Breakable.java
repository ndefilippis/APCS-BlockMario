package entities;

import game.Direction;
import game.Environment;
import game.Game;
import graphics.WorldBackground;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Breakable extends Boundry implements Updated {
	private int moveDown = 0;
	private boolean breakUp = false;
	private long timeBreakUp;

	public Breakable(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.ORANGE);
		if (Game.getCurrentArea().getB().getB() instanceof WorldBackground.AboveGround) {
			g.setColor(Color.ORANGE);
		}
		if (Game.getCurrentArea().getB().getB() instanceof WorldBackground.UnderGround) {
			g.setColor(Color.BLUE);
		}
		if (Game.getCurrentArea().getB().getB() instanceof WorldBackground.Dungeon) {
			g.setColor(Color.GRAY);
		}
		if (breakUp) {
			int time = (int) (System.currentTimeMillis() - timeBreakUp) / 25;
			g.fillRect(x - time, y + time * time - 5, width / 4, height / 4);
			g.fillRect(x - time, y + time * time + height / 2 - 5, width / 4,
					height / 4);
			g.fillRect(x + time + width, y + time * time - 5, width / 4,
					height / 4);
			g.fillRect(x + time + width, y + time * time + height / 2 - 5,
					width / 4, height / 4);
			return;
		}
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);

		g.drawLine(x, y, x + width, y);
		g.drawLine(x, y + height / 3, x + width, y + height / 3);
		g.drawLine(x, y + 2 * height / 3, x + width, y + 2 * height / 3);
		g.drawLine(x, y + height, x + width, y + height);
		g.drawLine(x, y, x, y + height);
		g.drawLine(x + width, y, x + width, y + height);
		g.drawLine(x + width / 3, y, x + width / 3, y + height / 3);
		g.drawLine(x + 2 * width / 3, y, x + 2 * width / 3, y + height / 3);

		g.drawLine(x + width / 3, y + 2 * height / 3, x + width / 3, y + height);

		g.drawLine(x + width / 2, y + height / 3, x + width / 2, y + 2 * height
				/ 3);
		g.drawLine(x + 2 * width / 3, y + 2 * height / 3, x + 2 * width / 3, y
				+ height);
	}

	public void collide(Entity e, Direction d) {
		if (d == Direction.TOP && e instanceof Character && !breakUp) {
			if (((Character) e).leveled()) {
				breakUp = true;
				passThrough = true;
				timeBreakUp = System.currentTimeMillis();
				try {
					AudioPlayer.player.start(new AudioStream(
							new FileInputStream("src/resources/break.wav")));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				y -= 5;
				moveDown = 5;
			}
		}
		if (((d == Direction.LEFT || d == Direction.RIGHT) && e instanceof Turtle.Shell)
				&& !breakUp) {
			breakUp = true;
			passThrough = true;
			timeBreakUp = System.currentTimeMillis();
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"src/break.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void update() {
		if (moveDown != 0) {
			y++;
			moveDown--;
		}
		if (breakUp) {
			if (System.currentTimeMillis() > timeBreakUp + 1000) {
				Environment.remove(this);
			}
		}
	}
}
