package entities;

import game.Direction;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import area.Water;

public class Squid extends Enemy{
	private long time;
	public Squid(int x, int y, int width, int height, Direction d) {
		super(x, y, width, height, d);
		time = System.currentTimeMillis();
		gravityAffected = false;
	}
	
	public void update(){
		if(currentArea instanceof Water && Game.getPlayer().currentArea instanceof Water && System.currentTimeMillis() > 1500+time){
			time = System.currentTimeMillis();
			setVx(10*Math.signum(Game.getPlayer().x()-x));
			setVy(10*Math.signum(Game.getPlayer().y()-y));
		}
		else if(System.currentTimeMillis() < 1000+time){
			setVx(Math.abs(Vx)*Math.signum(Game.getPlayer().x()-x));
			setVy(Math.abs(Vy)*Math.signum(Game.getPlayer().y()-y));
			setVx(Vx-0.05*Math.signum(Vx));
			setVy(Vy-0.05*Math.signum(Vy));

		}
		else{
			setVx(Vx-0.05*Math.signum(Vx));
			setVy(Vy-0.05*Math.signum(Vy));
		}
		super.update();
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

}
