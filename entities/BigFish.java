package entities;
import game.Direction;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import area.Water;

public class BigFish extends Enemy{
	private int health = 5;
	private long time;
	private boolean invincible;
	private long invincibleTime;
	public BigFish(int x, int y) {
		super(x, y, 150, 100, Direction.LEFT);
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
		if (System.currentTimeMillis() > invincibleTime + 3000)
			invincible = false;
	}
	
	public void draw(Graphics g, int x, int y){
		if (invincible) {
			if ((System.currentTimeMillis() - invincibleTime) % 500 < 100) {
				return;
			}
		}
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, width, height);
		for (int i = 0; i < health; i++) {
			g.fillRect(x+i * width/5, y-20, width/5-3, 10);
		}
		for (int i = 0; i < 5 - health; i++) {
			g.setColor(Color.GRAY);
			g.fillRect(x+(i + health) * width/5, y-20, width/5-3, 10);
		}
		
	}
	public void collide(Entity e, Direction d){
		if(e instanceof Character){
			if(((Character)e).hasKey()){
				if(!invincible){
					health--;
					invincible = true;
					invincibleTime = System.currentTimeMillis();
					((Character)e).setKey(false);
					if(health == 0){
						BossBoundry.open();
						destroy();
					}
				}
			}
			else if(!invincible){
				((Character)e).hurt();
			}
		}
	}
}
