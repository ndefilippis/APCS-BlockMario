package entities;

import game.Direction;
import game.Environment;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Shooter extends Enemy{
	private long lastMissileTime;
	private Direction d;
	
	public Shooter(int x, int y, Direction d) {
		super(x, y, 50, 100, d);
		this.d = d;
		gravityAffected = false;
		lastMissileTime = System.currentTimeMillis();
	}
	
	public void update(){
		Missile m;
		if(System.currentTimeMillis() > lastMissileTime + 1000){
			switch(d){
				case LEFT:
					m = new Missile(x - 20, y  - 10, 20, 20, Direction.LEFT);
					m.setVx(-10);
					break;
				case RIGHT:
					m = new Missile(x + width, y - 10, 20, 20, Direction.RIGHT);
					m.setVx(10);
					break;
				default:
					m = null;
			}
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream("src/resources/shooterShoot.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Environment.add(m);
			lastMissileTime = System.currentTimeMillis();
		}
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height/4);
		g.fillRect(x+width/4, y+height/4, width/2, 3*height/4);
	}
	
	
	public class Missile extends Enemy{

		public Missile(int x, int y, int width, int height, Direction d) {
			super(x, y, width, height, d);
			gravityAffected = false;
		}

		@Override
		public String toString() {
			return null;
		}
		
		@Override
		public void collide(Entity e, Direction d){
			destroy();
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream("src/rocketExplosion.wav")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		public void draw(Graphics g, int x, int y){
			g.setColor(Color.GRAY);
			g.fillRect(x, y+height/4, width, height/2);
		}
	}
}
