package area;
import entities.Sprite;
import game.Environment;

import java.awt.Graphics;
import java.util.ArrayList;


public abstract class Area {
	protected int x, y, width, height;
	
	public Area(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public ArrayList<Sprite> inArea(){
		ArrayList<Sprite> temp = new ArrayList<Sprite>();
		for(int i = Environment.getEntities().size()-1; i >= 0; i--){
			if(Environment.getEntities().get(i) instanceof Sprite){
				Sprite s = (Sprite)Environment.getEntities().get(i);
				if(s.x() + s.width() > x && s.x() < x + width && s.y()+s.height() > y && s.y() < y + height){
					temp.add(s);
				}
			}
		}
		return temp;
	}

	public void draw(Graphics g, int x, int y){
	
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}

	public int width() {
		// TODO Auto-generated method stub
		return width;
	}

	public int height() {
		// TODO Auto-generated method stub
		return height;
	}
}
