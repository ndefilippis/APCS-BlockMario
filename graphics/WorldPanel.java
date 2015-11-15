package graphics;

import game.Game;
import game.GameRunner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WorldPanel extends JPanel{
	private static int world = 1;
	private static int level = 1;
	private static int currentWorld;
	private static int currentLevel;
	public WorldPanel(){
		addKeyListener(new Listener());
		setBackground(new Color(255, 222, 200));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(255, 242, 237));
		for(int i = 0; i < 4; i++){
			g.fillRect(50, 100+i*getHeight()/5, getWidth()-100, 20);
		}
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(Game.isPassed(j+1, i+1)){
					g.setColor(new Color(128, 121, 114));
				}
				else{
					g.setColor(new Color(255, 242, 237));
				}
				g.fillRect(50+i*(getWidth()-125)/3, 85+j*(getHeight())/5, 50, 50);
			}
		}
		g.setColor(Color.BLACK);
		for(int i = 1; i <= 4; i++){
			g.drawString(i+"", 10, i*(getHeight())/5-50);
		}
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				g.drawString(i+"-"+j, j*(getWidth()-125)/3-295, i*getHeight()/5);
			}
		}
		g.setColor(Color.RED);
		g.fillRect(level*(getWidth()-125)/3-303, world*(getHeight())/5-70, 40, 40);
		repaint();
	}
	
	
	public static class Listener implements KeyListener{
		boolean up, left, down, right;
		@Override
		public void keyPressed(KeyEvent e) {
			if(!up && e.getKeyCode() == KeyEvent.VK_UP && world  > 1){
				up = true;
				if(Game.isPassed(world-1, level) || isCurrent(world-1, level)){
					world--;
				}
				
			}
			if(!left && e.getKeyCode() == KeyEvent.VK_LEFT && level > 1){
				left = true;
				if(Game.isPassed(world, level-1) || isCurrent(world, level-1)){
					level--;
				}
				
			}
			if(!right && e.getKeyCode() == KeyEvent.VK_RIGHT && level < 4){
				right = true;
				if(Game.isPassed(world, level+1) || isCurrent(world, level+1)){
					level++;
				}
				
			}
			if(!down && e.getKeyCode() == KeyEvent.VK_DOWN && world < 4){
				up = true;
				if(Game.isPassed(world+1, level) || isCurrent(world+1, level)){
					world++;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
				Game.setLevel(world, level);
				try {
					GameRunner.g.startLevel(world, level);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		private boolean isCurrent(int world, int level) {
			return currentWorld == world && currentLevel == level;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP){
				up = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				left = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				right = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				down = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {			
		}
		
	}
	
	public static void nextLevel(){
		level++;
		if(level > 4){
			world++;
			level = 1;
		}
		currentLevel = level;
		currentWorld = world;
	}
}
