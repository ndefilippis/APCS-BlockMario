package game;
import entities.BigFish;
import entities.BossBoundry;
import entities.Breakable;
import entities.Castle;
import entities.Coin;
import entities.Entity;
import entities.FireSpin;
import entities.Fish;
import entities.FlagPole;
import entities.FlyingTurtle;
import entities.HealthUp;
import entities.InvisibleBoundry;
import entities.Key;
import entities.Lava;
import entities.Mook;
import entities.MovingPlatform;
import entities.Pipe;
import entities.PitBoundry;
import entities.Plant;
import entities.QuestionBlock;
import entities.Shooter;
import entities.Squid;
import entities.Support;
import entities.Turtle;
import entities.Wall;
import graphics.WorldBackground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import area.PipeArea;
import area.Water;


public class LevelLoader {
	
	public static void LoadLevel(String s, Level level) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(s)));
		while(br.ready()){
			loadSubLevel(level, br, br.read()-48);
		}
		level.linkAreas();
	}
	public static void loadSubLevel(Level main, BufferedReader br, int sub) throws IOException {
		boolean mainsub = false;;
		if(br.read() == '*')
			mainsub = true;
		int world = br.read()-48;
		int level = br.read()-48;
		char section = (char)br.read();
		WorldBackground b = new WorldBackground(world, level, section);
		boolean addWater = false;
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		if(section == 'W'){
			addWater = true;
			x = (br.read()-48)*100+(br.read()-48)*10+br.read()-48;
			y = (br.read()-48)*100+(br.read()-48)*10+br.read()-48;
			width = (br.read()-48)*100+(br.read()-48)*10+br.read()-48;
			height = (br.read()-48)*100+(br.read()-48)*10+br.read()-48;
		}
		SubLevel sl = new SubLevel(main, sub, b, ((br.read()-48)*10+br.read()-48)*50);
		if(addWater){
			sl.addArea(new Water(x*50, y*50, width*50, height*50));
		}
		LoadEntities(sl, br, 0, 0);
		main.addSubLevel(sl, mainsub);
	}
	
	private static void LoadEntities(SubLevel sublevel, BufferedReader br, int x, int y) throws IOException{
		int index = 0;
		while (br.ready()) {
			String line = br.readLine();
			x = 0;
			for(int i = 0; i < line.length(); i++){
				Entity e = null;
				char dat = line.charAt(i);
				switch(dat){					
					case 'w':
						e = new Wall(x, y, 50, 50);
						break;
					case 'M':
						sublevel.startX = x;
						sublevel.startY = y;
						break;
					case 't':
						e = new Turtle(x, y, 20 ,20, Direction.LEFT);
						break;
					case 'e':
						e = new Mook(x, y, Direction.LEFT);
						break;
					case 'I':
						e = new InvisibleBoundry(x, y, 50, 50);
						break;
					case 'm':
						e = new MovingPlatform(x, y, 50, 25, 0, 1, x, x, y-(line.charAt(i+1)-48)*50, y);
						i++;
						break;
					case 'h':
						e = new HealthUp(x, y, 20, 20);
						break;
					case 'K':
						e = new Key(x, y);
						break;
					case 'G':
						e = new BigFish(x, y);
						break;
					case '7':
						e = new BossBoundry(x, y, 50, 50);
						break;
					case 'L':
						e = new Lava(x, y, 50, 50);
						break;
					case 'T':
						e = new FlyingTurtle(x, y, 20, 20, Direction.LEFT);
						break;
					case 'c':
						e = new Coin(x+18, y+20);
						break;
					case 'd':
						e = new Plant(x, y, 30, 50);
						break;
					case 'p':
						e = new Pipe(x, y, 50, 50, getDirection(line.charAt(i+1), true));
						i++;
						break;
					case 'P':
						e = new Pipe.PipeTop(x, y, 50, 50, getDirection(line.charAt(i+1), true));
						i++;
						break;
					case '|':
						e = new Support(x, y, 50, 50);
						break;
					case 'f':
						sublevel.addArea(new Water(x, y, 50, 50));
						e = new Fish(x, y, Direction.LEFT);
						break;
					case 's':
						e = new Shooter(x, y, Direction.LEFT);
						break;
					case 'i':
						e = new PitBoundry(x, y, 50, 50);
						break;
					case 'l':
						e = new FlagPole(x+40, y, 50);
						break;
					case 'b':
						e = new Breakable(x, y, 50, 50);
						break;
					case 'F':
						e = new FlagPole(x+40, y, 50);
						e.setIndex(index);
						index++;
						sublevel.add(e);
						e = new FlagPole.Flag(x, y, 50, 50);
						break;
					case '?':
						e = new QuestionBlock(x, y, 50, 50);
						break;
					case '&':
						e = new FireSpin(x, y, 50, 50);
						break;
					case 'S':
						sublevel.addArea(new Water(x, y, 50, 50));
						e = new Squid(x, y, 20, 30, Direction.LEFT);
						break;
					case 'A':
						boolean b = false;
						if(line.charAt(i+1) == 'E'){
							b = true;
						}
						if(line.charAt(i+1) == 'e'){
							b = false;
						}
						Direction d = getDirection(line.charAt(i + 2), b);
						int trans = Integer.parseInt(line.substring(i+3,i+4));
						switch(d){
							case LEFT:
								sublevel.addArea(new PipeArea(x, y+19, 10, 12, b, d, trans));
								break;
							case RIGHT:
								sublevel.addArea(new PipeArea(x+40, y+19, 10, 12, b, d, trans));
								break;
							case TOP:
								sublevel.addArea(new PipeArea(x+19, y, 12, 10, b, d, trans));
								break;
							case BOTTOM:
								sublevel.addArea(new PipeArea(x+19, y+40, 12, 10, b, d, trans));
						}
						
						i+=3;
						break;
					case '!':
						e = new Castle(x, y, line.charAt(i+1)-48);
						i++;
						break;
					case '-':
						return;
					default:
						break;
				}
				if(e != null){
					sublevel.add(e);
					e.setIndex(index);
					index++;				
				}
				x += 50;
			}
			y += 50;
		}
	}

	private static Direction getDirection(char charAt, boolean b) {
		Direction d;
		switch(charAt){
			case '>':
				if(b)
					d = Direction.RIGHT;
				else
					d = Direction.LEFT;
				break;
			case '<':
				if(b)
					d = Direction.LEFT;
				else
					d = Direction.RIGHT;
				break;
			case '^':
				if(b)
					d = Direction.TOP;
				else
					d = Direction.BOTTOM;
				break;
			case 'v':
				if(b)
					d = Direction.BOTTOM;
				else
					d = Direction.TOP;
				break;
			default:
				d = null;
		}
		return d;
	}
}
