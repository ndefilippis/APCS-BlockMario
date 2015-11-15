package entities;

import java.awt.Color;
import java.awt.Graphics;

public class BossBoundry extends Boundry implements Updated{
	private static boolean open = false;

	public BossBoundry(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public static void open(){
		open = true;
	}
	
	public void draw(Graphics g, int x, int y){
		if(!open){
			g.setColor(new Color(171, 255, 171));
			g.fillRect(x, y, width, height);
		}
	}

	@Override
	public void update() {
		if(open){
			passThrough = true;
		}
	}
}
