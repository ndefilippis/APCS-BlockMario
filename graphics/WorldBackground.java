package graphics;

import java.awt.Color;
import java.awt.Graphics;


public class WorldBackground implements Background{
	Background b;

	public WorldBackground(int world, int level, char section){
		if(section == 'A'){
			b = new AboveGround();
		}
		if(section == 'U'){
			b = new UnderGround();
		}
		if(section == 'D'){
			b = new Dungeon();
		}
		if(section == 'W'){
			b = new Water();
		}
	}

	public static class AboveGround implements Background{
		public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
			for (int i = 0; i < 100; i++) {
				int c = Math.min(255, Math.max(0, i + yOffset / 10 + 100));
				g.setColor(new Color(0, c, 255));
				g.fillRect(0, i * height / 100, width, (i + 1)
						* height / 100);
			}
			
			for(int i = -1; i < 6; i++){
				g.setColor(Color.GREEN);
				g.fillRect(i*400-xOffset%400, 300 - yOffset, 150, 150);
				g.setColor(new Color(139, 69, 19));
				g.fillRect(i*400-xOffset%400+50, 450 - yOffset, 50, 400);
			}
			g.setColor(Color.WHITE);
			for(int i = 0; i < 3; i++)
				g.fillRect(i*800 - (xOffset+(int)System.currentTimeMillis()/50)%800, -100-yOffset, 100, 50);
			
		
		}
	}
	public static class UnderGround implements Background{

		@Override
		public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
		}
	}
	
	public static class Dungeon implements Background{
		@Override
		public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
			g.setColor(new Color(216, 167, 167));
			g.fillRect(0, 0, width, height);
		}
	}
	public static class Water implements Background{
		@Override
		public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
			for (int i = 0; i < 100; i++) {
				int c = Math.min(255, Math.max(0, i + yOffset / 10 + 100));
				g.setColor(new Color(0, c, 255));
				g.fillRect(0, i * height / 100, width, (i + 1)
						* height / 100);
			}
		}
	}
	@Override
	public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
		b.draw(g, width, height, xOffset, yOffset);
	}
	public Background getB() {
		return b;
	}
}
