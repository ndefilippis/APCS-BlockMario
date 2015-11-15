package entities;
import game.Direction;

import java.awt.Color;
import java.awt.Graphics;



public class MovingPlatform extends Boundry implements Updated, Moveable{
	private int maxX, minX, maxY, minY;
	private int Vx, Vy;
	
	public MovingPlatform(int x, int y, int width, int height, int Vx, int Vy, int minX, int maxX, int minY, int maxY) {
		super(x, y, width, height);
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.Vx = Vx;
		this.Vy = Vy;
	}
	
	@Override
	public void update(){
		x += Vx;
		y += Vy;
		if(x > maxX){
			x = maxX;
			Vx = -Vx;
		}
		if(x < minX){
			x = minX;
			Vx = -Vx;
		}
		if(y >= maxY){
			y = maxY;
			Vy = -Vy;
		}
		if(y <= minY){
			y = minY;
			Vy = -Vy;
		}
	}
	
	@Override
	public void collide(Entity e, Direction d){
		return;
	}

	@Override
	public String toString() {
		return "Moving Platform";
	}

	@Override
	public double Vx() {
		return Vx;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}

	@Override
	public double Vy() {
		return Vy;
	}

	

}
