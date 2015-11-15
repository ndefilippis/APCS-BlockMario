package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Coin extends Sprite {

	public Coin(int x, int y) {
		super(x, y, 15, 20);
		gravityAffected = false;
		passThrough = true;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawLine(x+width/2, y+3, x+width/2, y+height-3);
	}
	public void collide(Entity e, Direction d){
		if(e instanceof Character){
			((Character)e).addCoin();
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream("src/resources/ding.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			destroy();
		}
	}
}
