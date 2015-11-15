package game;
import entities.Entity;
import graphics.Background;
import graphics.WorldBackground;

import java.util.ArrayList;

import area.Area;


public class SubLevel{
	private Level main;
	private int sub;
	public ArrayList<Entity> entities;
	public ArrayList<Area> areas;
	public Environment world;
	public int startX, startY;
	public Background background;
	public int ground;
	
	public SubLevel(Level main, int sub, Background b, int ground) {
		areas = new ArrayList<Area>();
		entities = new ArrayList<Entity>();
		this.world = new Environment(this);
		this.sub = sub;
		this.main = main;
		this.background = b;
		this.ground = ground;
	}
	
	public WorldBackground getB(){
		return (WorldBackground)background;
	}
	
	public Level main(){
		return main;
	}
	
	public int getSub(){
		return sub;
	}
	
	public void addArea(Area a){
		areas.add(a);
	}

	public void add(Entity e) {
		entities.add(e);
	}
	
	public void add(Entity e, int index){
		entities.add(index, e);
		e.setIndex(index);
	}

}
