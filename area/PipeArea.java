package area;

import entities.Character;
import entities.Sprite;
import entities.Updated;
import game.Direction;
import game.InputHandler;
import game.SubLevel;

import java.util.ArrayList;


public class PipeArea extends Area implements Updated{
	private SubLevel location;
	private boolean entrance;
	public int n;
	public Direction d;
	public PipeArea linkedArea;
	
	public PipeArea(int x, int y, int width, int height, boolean entrance, Direction d,  int n) {
		super(x, y, width, height);
		this.n = n;
		this.d = d;
		this.entrance = entrance;
	}
	
	public int getN(){
		return n;
	}
	
	public void setSubLevel(SubLevel location, PipeArea pa){
		this.location = location;
		linkedArea = pa;
	}
	
	public boolean entrance(){
		return entrance;
	}

	@Override
	public void update() {;
		ArrayList<Sprite> t = inArea();
		for(int i = 0; i < t.size(); i++){
			
			if(entrance && t.get(i) instanceof Character){
				if(d == Direction.TOP && InputHandler.up || d == Direction.BOTTOM && InputHandler.down || d == Direction.LEFT && InputHandler.left || d == Direction.RIGHT && InputHandler.right){
					((Character)t.get(i)).sendPipe(d, location, linkedArea);
					
				}
			}
		}
	}

	public int y() {
		return y;
	}
	
	public int x(){
		return x;
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}

}
