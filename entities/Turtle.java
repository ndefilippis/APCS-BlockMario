package entities;

import game.Direction;
import game.InputHandler;
import game.Environment;

import java.awt.Color;
import java.awt.Graphics;


public class Turtle extends Enemy{

	public Turtle(int x, int y, int width, int height, Direction d) {
		super(x, y, width, height, d);
	}
	
	public void update(){
		Environment.collision(this, (int)(x + Vx), (int)(y + Vy));
		if(!downBlocked){
			setX((int)(x-Math.signum(Vx)));
			setVx(-Vx);
			
		}
		super.update();
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(new Color(50, 75, 50));
		g.fillRect(x, y+height/2, width, height/2);
		g.setColor(Color.YELLOW);
		if(direction == Direction.LEFT)
			g.fillRect(x, y, width/2, height/2);
		else
			g.fillRect(x+width/2, y, width/2, height/2);
	}
	
	public void collide(Entity e, Direction d){
		super.collide(e, d);
		if(e instanceof Character && d == Direction.BOTTOM){
			Environment.add(new Shell(x, y+height/2, width, height/2-1));
		}
	}
	
	public static class Shell extends Grabbable{
		private boolean moving, hurtPlayer = false;
		private long timeSinceLastMoved;
		public Shell(int x, int y, int width, int height) {
			super(x, y, width, height);
			timeSinceLastMoved = System.currentTimeMillis();
			bounce = true;
		}
		
		public void update(){
			super.update();
			if(!moving && System.currentTimeMillis() > timeSinceLastMoved + 10000){
				destroy();
				Environment.add(new Turtle(x, y-height, width, height*2, direction));
			}
		} 
		
		public void draw(Graphics g, int x, int y){
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
		}
		
		public void collide(Entity e, Direction hit){
			if(e instanceof Boundry && (hit == Direction.LEFT || hit == Direction.RIGHT)){
				Vx = -Vx;
				hurtPlayer = true;
			}
			if(!moving && e instanceof Character && (hit == Direction.LEFT || hit == Direction.RIGHT)){
				if(InputHandler.grab){
					((Character)e).grab(this);
				}
				else{
					moving = true;
					if(hit== Direction.LEFT)
						setVx(-7);
					else{
						setVx(7);
					}
				}
			}
			
			super.collide(e, hit);
			
			if(moving && (hit == Direction.LEFT || hit == Direction.RIGHT)){
				if(e instanceof Character){
					if(hurtPlayer){
						((Character)e).hurt();
					}
				}
				else if(moving && e instanceof Mob){
					((Mob)e).destroy();
				}
			}

			if(moving && e instanceof Character && hit == Direction.BOTTOM){
				Vx = 0;
				moving = false;
				timeSinceLastMoved = System.currentTimeMillis();
			}
			
		}

		@Override
		public void drop(Direction q) {
			moving = true;
			direction = q;
			if(q == Direction.LEFT){
				setVx(-7);
			}
			else{
				setVx(7);
			}
		}
	}

}
