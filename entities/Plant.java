package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Plant extends Boundry{

	public Plant(int x, int y, int width, int height) {
		super(x, y, width, height);
		passThrough = true;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.GREEN);
		g.fillPolygon(new int[]{x, x+width, x, x+width}, new int[]{y, y, y+height, y+height}, 4);
	}

}
