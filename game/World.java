package game;

import java.util.ArrayList;

public class World {
	public ArrayList<Level> levels;
	
	public World(int n){
		levels = new ArrayList<Level>();
	}
	
	public void addLevel(Level lev){
		levels.add(lev);
	}
}
