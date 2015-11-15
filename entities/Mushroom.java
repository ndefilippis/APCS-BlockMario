package entities;

import game.Direction;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Mushroom extends Sprite {

	public Mushroom(int x, int y) {
		super(x, y, 20, 20);
		setVx(1);
	}

	@Override
	public void collide(Entity m, Direction d) {
		if(m instanceof Character){
			destroy();
			((Character)m).levelUp();
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream("src/resources/gainMushroom.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(d == Direction.RIGHT){
			setVx(-1);
		}
		if(d == Direction.LEFT){
			setVx(1);
		}
	}
	
	public void update(){
		x+=Vx;
		super.update();
		if(direction == Direction.LEFT){
			setVx(-1);
		}
		if(direction == Direction.RIGHT){
			setVx(1);
		}
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height/2);
		g.setColor(Color.YELLOW);
		g.fillRect(x+width/4, y+height/2, height/2, width/2);
	}

}
