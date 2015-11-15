package entities;

import game.Direction;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class FlagPole extends Entity{
	public static boolean play = true;
	
	public FlagPole(int x, int y, int height) {
		super(x, y, 10, height);
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
	}
	
	public static class Flag extends Entity implements Updated{
		
		public Flag(int x, int y, int width, int height) {
			super(x, y, width, height);
			passThrough = true;
		}

		@Override
		public void collide(Entity m, Direction d) {
		}

		@Override
		public void draw(Graphics g, int x, int y) {
			g.setColor(Color.WHITE);
			g.fillPolygon(new int[]{x+width-1, x, x+width-1}, new int[]{y, y+height/2, y+height}, 3);
		}

		@Override
		public void update() {
			if(Game.getPlayer() != null && Game.getPlayer().isSlideFlag()){
				if(y < Game.getCurrentArea().ground)
				y += 5;
			}
		}
	}

	@Override
	public void collide(Entity m, Direction d) {
		if(play){
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream("src/resources/endLevel.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			play = false;
		}
		((Character)m).slideFlag();
	}

}
