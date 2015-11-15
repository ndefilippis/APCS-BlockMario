package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;


public class Mook extends Enemy{

	public Mook(int x, int y, Direction d) {
		super(x, y, 20, 20, d);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
}
