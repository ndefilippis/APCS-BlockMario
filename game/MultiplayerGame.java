package game;
import java.io.IOException;
import java.util.ArrayList;

import entities.Character;
import entities.Entity;
import graphics.MainFrame;

public class MultiplayerGame extends Game{
	public MultiplayerGame(MainFrame mf) throws IOException {
		super(mf);
	}

	private static ArrayList<Character> MPcharacters;
	
	public void restart() throws IOException{
		super.restart();
		for(Character p : MPcharacters){
			Environment.add(p);
		}
	}

	public void startGame(String name) throws IOException {
		MPcharacters = new ArrayList<Character>();
		super.startGame(name);
	}
	
	public static void addPlayer(String substring) {
		Character MPChar = new Character(substring);
		MPChar.setX(currentLevel.getMain().startX);
		MPChar.setY(currentLevel.getMain().startY);
		Environment.add(MPChar);
		MPcharacters.add(MPChar);
	}
	
	public static void setPlayer(String substring) {
		String[] data = substring.split(":");
		if(data[0].equals(Game.c.getName()))
			return;
		for(Entity e : Environment.getEntities()){
			if(e instanceof Character && e.toString().equals(data[0])){
				((Character)e).setX(Integer.parseInt(data[1]));
				((Character)e).setY(Integer.parseInt(data[2]));
			}
		}
	}

	public static void setObject(String substring) {
		String[] data = substring.split(":");
		ArrayList<Entity> temp = Environment.getEntities();
		if(Integer.parseInt(data[0]) == 0)
			return;
		for(int i = temp.size()-1; i >= 0; i--){
			Entity e = temp.get(i);
			if(e instanceof Character){
				continue;
			}
			if(e.num() == Integer.parseInt(data[0])){
				e.setX(Integer.parseInt(data[1]));
				e.setY(Integer.parseInt(data[2]));
			}
		}
	}
}
