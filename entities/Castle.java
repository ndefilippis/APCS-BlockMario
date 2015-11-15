package entities;

import game.Direction;
import game.GameRunner;

import java.awt.Color;
import java.awt.Graphics;


public class Castle extends Entity {

	public Castle(int x, int y, int n) {
		super(x, y, 200, 200);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collide(Entity m, Direction d) {
		if(m instanceof Character){
			GameRunner.g.finishLevel();
		}
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(new Color(174, 64, 17));
		g.fillRect(x, y, width, height);
		for(int i = 0; i < 4; i++){
			g.fillRect(x+(int)(i*width/3.4), y-25, 25, 25);
		}
		g.setColor(Color.BLACK);
		g.fillRect(x+width/2-25, y+height-75, 50, 75);
		g.drawLine(x, y+10, x+width, y+10);
	}

}
