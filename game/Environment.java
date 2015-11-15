package game;

import java.util.ArrayList;

import area.Area;
import entities.Character;
import entities.Entity;
import entities.Sprite;
import entities.Updated;

public class Environment {
	public static final double GRAV_CONSTANT = 1D;
	public static final double DEFAULT_GROUND = 800D;
	public static final double RANGE = 0.2;
	private static ArrayList<Entity> entities;
	private static ArrayList<Updated> updates;
	private static ArrayList<Area> areas;
	private SubLevel sublevel;

	public Environment(SubLevel subLevel) {
		this.sublevel = subLevel;
		entities = new ArrayList<Entity>();
		areas = new ArrayList<Area>();
		updates = new ArrayList<Updated>();
	}

	public static void addArea(Area a) {
		areas.add(a);
		if (a instanceof Updated) {
			updates.add((Updated) a);
		}
	}

	public void load() {
		entities = new ArrayList<Entity>();
		areas = new ArrayList<Area>();
		updates = new ArrayList<Updated>();
		for (Entity e : sublevel.entities) {
			add(e);
		}
		for (Area a : sublevel.areas) {
			addArea(a);
		}
	}

	public static void add(Entity e) {
		if (e instanceof Updated) {
			updates.add((Updated) e);
		}
		entities.add(e);
	}

	public void tick() {
		if (InputHandler.left) {
			Game.getPlayer().setVx(-Character.SPEED);
		}
		if (InputHandler.right) {
			Game.getPlayer().setVx(Character.SPEED);
		}
		if(!InputHandler.left && !InputHandler.right){
			if(!Game.getPlayer().isSlideFlag() && !Game.getPlayer().downBlocked()){
				Game.getPlayer().setVx(Game.getPlayer().Vx()-Math.signum(Game.getPlayer().Vx())*0.1);
				if(Math.abs(Game.getPlayer().Vx()) < 0.3){
					Game.getPlayer().setVx(0);
				}
			}
			else{
				Game.getPlayer().setVx(0);
			}
		}
		ArrayList<Updated> up = getUpdatedinRange(GameRunner.g.mp().xOffset(),
				GameRunner.g.mp().yOffset(), GameRunner.g.mp().getWidth(),
				GameRunner.g.mp().getHeight());
		for (int i = up.size() - 1; i >= 0 && i < up.size(); i--) {
			up.get(i).update();
		}
	}

	public static ArrayList<Entity> getVisibleEntities(int x, int y, int width,
			int height) {
		ArrayList<Entity> temp = new ArrayList<Entity>();
		for (int i = Environment.getEntities().size() - 1; i >= 0
				&& i <= Environment.getEntities().size(); i--) {
			Entity e = Environment.getEntities().get(i);
			if (e.x() + e.width() >= x && e.x() <= x + width
					&& e.y() + e.height() >= y && e.y() <= y + height) {
				temp.add(e);
			}
		}
		return temp;
	}

	public static ArrayList<Updated> getUpdatedinRange(int x, int y, int width,
			int height) {
		ArrayList<Updated> temp = new ArrayList<Updated>();
		for (int i = 0; i < updates.size(); i++) {
			if (updates.get(i) instanceof Entity) {
				Entity e = (Entity) updates.get(i);
				if (e.x() + e.width() > x - width * RANGE
						&& e.x() < x + width + width * RANGE
						&& e.y() + e.height() > y - height * RANGE
						&& e.y() < y + height + height * RANGE) {
					temp.add((Updated) e);
				}
			}
			if (updates.get(i) instanceof Area) {
				Area e = (Area) updates.get(i);
				if (e.x() + e.width() > x - width * RANGE
						&& e.x() < x + width + width * RANGE
						&& e.y() + e.height() > y - height * RANGE
						&& e.y() < y + height + height * RANGE) {
					temp.add((Updated) e);
				}
			}
		}
		return temp;
	}

	public synchronized static void collision(Sprite s, int x, int y) {
		s.clearBlocks();
		for (int i = getEntities().size() - 1; i >= 0
				&& i < getEntities().size(); i--) {
			Entity e = getEntities().get(i);
			if (e instanceof Character && ((Character) e).invincible)
				continue;
			if (s instanceof Character && e.equals(((Character) s).getHold())) {
				continue;
			}
			if (!e.equals(s) && x + s.width() >= e.x()
					&& x <= e.x() + e.width() && y + s.height() >= e.y()
					&& y <= e.y() + e.height()) {
				if (s.y() + s.height() > e.y() && s.y() < e.y() + e.height()
						&& s.x() + s.width() <= e.x() && x + s.width() > e.x()) { // left
					if (!e.passThrough())
						s.collide(e, Direction.LEFT);
					e.collide(s, Direction.RIGHT);
				} else if (s.y() + s.height() > e.y()
						&& s.y() < e.y() + e.height()
						&& s.x() >= e.x() + e.width() && x < e.x() + e.width()) { // right
					if (!e.passThrough())
						s.collide(e, Direction.RIGHT);
					e.collide(s, Direction.LEFT);
				}
				if (s.x() + s.width() > e.x() && s.x() < e.x() + e.width()
						&& s.y() >= e.y() + e.height()
						&& y < e.y() + e.height()) { // top
					if (!e.passThrough())
						s.collide(e, Direction.BOTTOM);
					e.collide(s, Direction.TOP);
				} else if (s.x() + s.width() > e.x()
						&& s.x() < e.x() + e.width()
						&& s.y() + s.height() <= e.y()
						&& y + s.height() > e.y()) { // down
					if (!e.passThrough())
						s.collide(e, Direction.TOP);
					e.collide(s, Direction.BOTTOM);
				}
			}
			if (e.x() < s.x() + s.width() && e.x() + e.width() > s.x()
					&& s.y() + s.height() + 1 > e.y() && s.y() < e.y()
					&& !e.passThrough()) {
				s.setDownBlocked(true);
			}
			if (e.x() < s.x() + s.width() && e.x() + e.width() > s.x()
					&& s.y() - 1 < e.y() + e.height()
					&& s.y() + s.height() > e.y() + e.height()
					&& !e.passThrough()) {
				s.setUpBlocked(true);
			}
			if (e.y() < s.y() + s.height() && e.y() + e.height() > s.y()
					&& s.x() + s.width() + 1 > e.x() && s.x() < e.x()
					&& !e.passThrough()) {
				s.setRightBlocked(true);
			}
			if (e.y() < s.y() + s.height() && e.y() + e.height() > s.y()
					&& s.x() - 1 < e.x() + e.width()
					&& s.x() + s.width() > e.x() + e.width()
					&& !e.passThrough()) {
				s.setLeftBlocked(true);
			}
		}
	}

	public static ArrayList<Entity> getEntities() {
		return entities;
	}

	public static ArrayList<Area> getAreas() {
		return areas;
	}

	public static void remove(Entity e) {
		entities.remove(e);
		if (e instanceof Updated)
			updates.remove(e);
	}

	public void Servertick() {
		if (InputHandler.left) {
			Game.getPlayer().setVx(-Character.SPEED);
		}
		if (InputHandler.right) {
			Game.getPlayer().setVx(Character.SPEED);
		}
		for (int i = updates.size() - 1; i >= 0 && i < updates.size(); i--) {
			updates.get(i).update();
		}
	}

}
