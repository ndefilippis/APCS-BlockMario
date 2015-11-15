package entities;

import game.Direction;

import java.awt.Graphics;


public class PitBoundry extends Boundry{

	public PitBoundry(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g, int x, int y){
		
	}

	@Override
	public void collide(Entity m, Direction d) {
		if(m instanceof Sprite){
			((Sprite)m).destroy();
		}
	}

}
