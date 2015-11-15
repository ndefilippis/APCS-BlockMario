package entities;

import game.Direction;

import java.awt.Graphics;


public class InvisibleBoundry extends Boundry{

	public InvisibleBoundry(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collide(Entity m, Direction d) {
		
	}
	
	public void draw(Graphics g, int x, int y){
	}

}
