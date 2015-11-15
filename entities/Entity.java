package entities;

import game.Direction;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity{
	protected int x, y, width, height;
	protected boolean passThrough;
	protected int serialNum;

	public Entity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public void setIndex(int serialNum){
		this.serialNum = serialNum;
	}
	
	public abstract void collide(Entity m, Direction d);
	
	public abstract void draw(Graphics g, int x, int y);
	
	public Rectangle2D.Double getBounds(){
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public boolean passThrough(){
		return passThrough;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}
	
	public int num(){
		return serialNum;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

}
