package graphics;
import entities.Entity;
import game.Game;
import game.Environment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import area.Area;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	private int yOffset = -150, xOffset;
	private int YBORDER = 50, LXBORDER = 200, RXBORDER = 300;

	public MainPanel() {
		setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Game.getCurrentArea().background.draw(g, getWidth(), getHeight(), xOffset, yOffset);
		ArrayList<Area> temp1 = Environment.getAreas();
		for(int i = temp1.size()-1; i >= 0; i--){
			Area a = temp1.get(i);
			a.draw(g, a.x() - xOffset, a.y() - yOffset);
		}
		g.setColor(Color.BLACK);
		ArrayList<Entity> temp = Environment.getVisibleEntities(xOffset, yOffset, getWidth(), getHeight());
		for (int i = 0; i < temp.size(); i++) {
			Entity e = temp.get(i);
			e.draw(g, e.x() - xOffset, e.y() - yOffset);
		}
		
		Game.getPlayer().drawHealth(g, true);
		Game.getPlayer().drawCoins(g);
		Game.getPlayer().drawLives(g);
		Game.drawTime(g);
		
	}
	
	public int xOffset(){
		return xOffset;
	}
	
	public int yOffset(){
		return yOffset;
	}

	public void setOffset() {
		YBORDER = getHeight()/16;
		LXBORDER = getWidth()/6;
		RXBORDER = getWidth()/4;
		if (Game.getPlayer().y() - yOffset < YBORDER){
			yOffset = Game.getPlayer().y() - YBORDER;
		}
		if (Game.getPlayer().y() + Game.getPlayer().height() - yOffset > getHeight() - YBORDER){
			yOffset = YBORDER + Game.getPlayer().y() + Game.getPlayer().height() - getHeight();
		}
		if (Game.getPlayer().x() - xOffset < LXBORDER){
			xOffset = Game.getPlayer().x() - LXBORDER;
		}
		if (Game.getPlayer().x() + Game.getPlayer().width() - xOffset > getWidth() - RXBORDER) {
			xOffset = RXBORDER + Game.getPlayer().x() + Game.getPlayer().width() - getWidth();
		}
	}

}
