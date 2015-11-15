package entities;

import game.Direction;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import area.Water;

public class Fish extends Enemy {
	public boolean inWater;
	
	public Fish(int x, int y, Direction d) {
		super(x, y, 20, 15, d);
		gravityAffected = false;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(new Color(144, 0, 0));
		g.fillRect(x, y, width, height);
	}
	
	public void update(){
		if(currentArea instanceof Water && Game.getPlayer().currentArea instanceof Water){
			setVx((5*Math.random()+1)*Math.signum(Game.getPlayer().x()-x));
			setVy((5*Math.random()+1)*Math.signum(Game.getPlayer().y()-y));
		}
		else{
			if(direction == Direction.LEFT)
				setVx(-1);
			else{
				setVx(1);
			}
			setVy(0);
		}
		super.update();
	}
	
}
