package entities;

import game.Direction;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;


public class Key extends Sprite {

	private boolean collected = false;
	private long time;

	public Key(int x, int y) {
		super(x, y, 20, 20);
		time = System.currentTimeMillis();
	}

	@Override
	public void update() {
		if(System.currentTimeMillis() > time + 10000 && collected){
			collected = false;
			passThrough = false;
			Game.getPlayer().setKey(false);
			time = System.currentTimeMillis();
		}
	}

	@Override
	public String toString() {
		return null;
	}
	
	public void collide(Entity e, Direction d){
		super.collide(e, d);
		if(e instanceof Character && !collected){
			((Character)e).setKey(true);
			collected = true;
			passThrough = true;
		}
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(new Color(255, 128, 0));
		if(!collected){
			g.fillOval(x, y, width, height);
		}
		else{
			long currTime = System.currentTimeMillis() - time;
			g.setColor(new Color(1.0f, 0.5f, 0.0f, currTime/10100.0f));
			if(currTime % (50000/(currTime/50)) < 100){
				g.fillOval(x, y, width, height);
			}
		}
	}
	
}
