package entities;

import java.awt.Color;
import java.awt.Graphics;

import game.Direction;
import game.Environment;

public class FlyingTurtle extends Turtle {
	private int min, max;
	
	public FlyingTurtle(int x, int y, int width, int height, Direction d) {
		super(x, y, width, height, d);
		gravityAffected = false;
		min = y - 50;
		max = y + 50;
		setVy(2);
	}
	
	public void update(){
		setVx(0);
		setVy(Math.signum(Vy)*2);
		x += Vx;
		y += Vy;
		if(y >= max){
			y = max;
			Vy = -Vy;
		}
		if(y <= min){
			y = min;
			Vy = -Vy;
		}
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(new Color(50, 75, 50));
		g.fillRect(x, y+height/2, width, height/2);
		g.setColor(Color.YELLOW);
		if(direction == Direction.LEFT)
			g.fillRect(x, y, width/2, height/2);
		else
			g.fillRect(x+width/2, y, width/2, height/2);
			
		g.setColor(Color.WHITE);
		g.fillRect(x+width/2+5, y-10, 10, 15);
	}
	
	public void collide(Entity e, Direction d){
		if(e instanceof Character && d == Direction.BOTTOM){
			destroy();
			Environment.add(new Turtle(x, y+20,20, 20, Direction.LEFT));
			((Character)e).setVy(-10);
		}
		else{
			super.collide(e, d);
		}
	}

}
