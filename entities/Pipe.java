package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;

public class Pipe extends Boundry {
	protected Direction d;
	public Pipe(int x, int y, int width, int height, Direction d) {
		super(x, y, width, height);
		this.d = d;
	}

	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.GREEN);
		g.fillRect(x,y, width, height);
		g.setColor(Color.BLACK);
		if(d == Direction.BOTTOM || d == Direction.TOP){
			g.drawLine(x, y, x, y+height);
			g.drawLine(x+width, y, x+width, y+height);
		}
		else{
			g.drawLine(x, y, x+width, y);
			g.drawLine(x, y+height, x+width, y+height);
		}
	}
	
	public static class PipeTop extends Boundry{
		protected Direction d;
		
		public PipeTop(int x, int y, int width, int height, Direction d) {
			super(x, y, width, height);
			this.d = d;
		}
		
		public void draw(Graphics g, int x, int y){
			g.setColor(Color.GREEN);
			g.fillRect(x,y, width, height);
			if(d == Direction.LEFT){
				g.setColor(Color.BLACK);
				g.drawLine(x+10, y, x+width, y);
				g.drawLine(x+10, y+height, x+width, y+height);
				g.fillRect(x-1, y-3, 12, height+6);
				g.setColor(Color.GREEN);
				g.fillRect(x, y-2, 10, height+4);
			}
			if(d == Direction.RIGHT){
				g.setColor(Color.BLACK);
				g.drawLine(x, y, x+width-10, y);
				g.drawLine(x, y+height, x+width-10, y+height);
				g.fillRect(x+width-11, y-3, 12, height+6);
				g.setColor(Color.GREEN);
				g.fillRect(x+width-10, y-2, 10, height+4);
			}
			if(d == Direction.BOTTOM){
				g.setColor(Color.BLACK);
				g.drawLine(x, y, x, y+height-10);
				g.drawLine(x+width, y, x+width, y+height-10);
				g.fillRect(x-3, y+height-11, width+6, 12);
				g.setColor(Color.GREEN);
				g.fillRect(x-2,y+height-10, width+4, 10);
			}
			if(d == Direction.TOP){
				g.setColor(Color.BLACK);
				g.drawLine(x, y+10, x, y+height);
				g.drawLine(x+width, y+10, x+width, y+height);
				g.fillRect(x-3, y-1, width+6, 12);
				g.setColor(Color.GREEN);
				g.fillRect(x-2, y, width+4, 10);
			}			
		}
	}
}
