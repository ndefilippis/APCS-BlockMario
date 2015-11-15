package entities;
import game.Direction;




public abstract class Grabbable extends Sprite{
	public Grabbable(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public abstract void drop(Direction d);
}
