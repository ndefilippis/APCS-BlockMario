package game;
import java.io.IOException;
import java.util.ArrayList;

import area.Area;
import area.PipeArea;


public class Level {
	public int worldNum;
	public int area;
	public ArrayList<SubLevel> sublevels;
	protected int sub = 0;
	private SubLevel mainArea;
	private boolean passed;
	
	public Level(int world, int area){
		passed = false;
		sublevels = new ArrayList<SubLevel>();
		this.worldNum = world;
		this.area = area;
	}
	
	public void pass(){
		passed = true;
	}
	
	public boolean isPassed(){
		return passed;
	}
	
	public void addSubLevel(SubLevel sublevel, boolean main){
		sublevels.add(sublevel);
		if(main){
			this.mainArea = sublevel;
		}
			
	}
	
	public void goToSubLevel(SubLevel sublevel, PipeArea pa){
		sublevel.world.load();
		Game.setSublevel(sublevel);
		if(pa.d == Direction.TOP){
			Game.getPlayer().setX(pa.x()+pa.width()/2-Game.getPlayer().width()/2);
			Game.getPlayer().setY(pa.y()-20);
		}
		if(pa.d == Direction.BOTTOM){
			Game.getPlayer().setX(pa.x()+pa.width()/2-Game.getPlayer().width()/2);
			Game.getPlayer().setY(pa.y()+20);
		}
		if(pa.d == Direction.LEFT){
			Game.getPlayer().setX(pa.x()-20);
			Game.getPlayer().setY(pa.y()+pa.height()+19-Game.getPlayer().height()-1);
		}
		if(pa.d == Direction.RIGHT){
			Game.getPlayer().setX(pa.x()+20);
			Game.getPlayer().setY(pa.y()+pa.height()+19-Game.getPlayer().height()-1);
		}
		Environment.add(Game.getPlayer());
		GameRunner.g.mp().setOffset();
	}
	
	public void linkAreas(){
		for(SubLevel sl : sublevels){
			for(Area a : sl.areas){
				if(a instanceof PipeArea){
					for(SubLevel slLink : sublevels){
						if(((PipeArea)a).getN() == slLink.getSub() && ((PipeArea)a).entrance()){
							for(Area link : slLink.areas){
								if(link instanceof PipeArea && !((PipeArea)link).entrance() && ((PipeArea)link).getN() == sl.getSub()){
									((PipeArea)a).setSubLevel(slLink, (PipeArea)link);
								}
							}
						}
					}
				}
			}
		}
	}

	public SubLevel getMain() {
		return mainArea;
	}

	public void reload() throws IOException {
		LevelLoader.LoadLevel("src/resources/level"+Game.currWorld+Game.currLevel+".txt", this);
		for(SubLevel sl : sublevels){
			sl.world.load();	
		}
	}
}
