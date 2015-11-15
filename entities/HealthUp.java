package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;


public class HealthUp extends Sprite {

	public HealthUp(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.RED);
		g.fillOval(x+width/2, y-height/2, width, height);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void collide(Entity e, Direction d){
		if(e instanceof Character){
			((Character)e).fullHealth();
			destroy();
		}
	}

}
