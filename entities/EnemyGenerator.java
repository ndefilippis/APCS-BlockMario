package entities;

import game.Direction;
import game.Environment;

import java.awt.Color;
import java.awt.Graphics;


public class EnemyGenerator extends Entity implements Updated{
	private long lastEnemyGenTime;
	private Direction opening;
	private Direction dir = Direction.LEFT;
	
	public EnemyGenerator(int x, int y, int width, int height, Direction opening) {
		super(x, y, width, height);
		lastEnemyGenTime = System.currentTimeMillis();
		this.opening = opening;
	}
	
	@Override
	public void update(){
		if(System.currentTimeMillis() > lastEnemyGenTime + 7000){
			Enemy e;
			switch(opening){
				case TOP:
					e = new Mook(x + width()/2 - 10, y-20, dir);
					break;
				case BOTTOM:
					e = new Mook(x + width()/2 - 10, y + height, dir);
					break;
				case LEFT:
					e = new Mook(x - 20, y + height()/2 - 10, dir);
					break;
				case RIGHT:
					e = new Mook(x + width, y + height/2 - 10, dir);
					break;
				default:
					e = null;
			}
			Environment.add(e);
			e.setBehavior(1);
			if(dir == Direction.LEFT)
				dir = Direction.RIGHT;
			else
				dir = Direction.LEFT;
			lastEnemyGenTime = System.currentTimeMillis();
		}
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}

	@Override
	public String toString() {
		
		return null;
	}

	@Override
	public void collide(Entity m, Direction d) {
		// TODO Auto-generated method stub
		
	}

}
