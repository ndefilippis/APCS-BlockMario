package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;

public class Support extends Entity{

	public Support(int x, int y, int width, int height) {
		super(x, y, width, height);
		passThrough = true;
	}

	@Override
	public void collide(Entity m, Direction d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		
	}

}
