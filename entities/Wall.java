package entities;
import game.Direction;
import game.Game;
import graphics.WorldBackground;

import java.awt.Color;
import java.awt.Graphics;


public class Wall extends Boundry{
	public Wall(int x, int y, int width, int height){
		super(x, y, width, height);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return x + " " + y + " " + width + " " + height;
	}

	@Override
	public void collide(Entity e, Direction d) {
		
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		if(Game.getCurrentArea().getB().getB() instanceof WorldBackground.AboveGround){
			g.setColor(Color.BLACK);
		}
		if(Game.getCurrentArea().getB().getB() instanceof WorldBackground.UnderGround){
			g.setColor(Color.BLUE);
		}
		if(Game.getCurrentArea().getB().getB() instanceof WorldBackground.Dungeon){
			g.setColor(Color.GRAY);
		}
		if(Game.getCurrentArea().getB().getB() instanceof WorldBackground.Water){
			g.setColor(new Color(0, 0, 50));
		}
		g.fillRect(x, y, width, height);
	}

}
