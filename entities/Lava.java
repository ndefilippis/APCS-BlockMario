package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Lava extends PitBoundry {

	public Lava(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}
}
