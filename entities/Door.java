package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;


public class Door extends Entity {

	public Door(int x, int y, int width, int height) {
		super(x, y, 25, 50);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void collide(Entity m, Direction d) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height-10);
		g.fillOval(x+width/2, y, width/2, 10);
		g.setColor(new Color(66, 33, 00));
		g.fillRect(x-5, y-5, width-10, height-5);
		g.setColor(Color.BLACK);
		g.drawLine(x+width/2, y, x+width, y+height);
	}

}
