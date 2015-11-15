package entities;
import game.Direction;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Boundry extends Entity{
	
	public Boundry(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}
	
	public void collide(Entity e, Direction d){
		
	}
}
