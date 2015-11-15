package entities;
import game.Direction;



public abstract class Mob extends Sprite{
	
	
	public Mob(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void setVx(double vx){
		super.setVx(vx);
		if(Vx < 0)
			direction = Direction.LEFT;
		if(Vx > 0)
			direction = Direction.RIGHT;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}


}
