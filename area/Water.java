package area;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.Sprite;
import entities.Updated;
import entities.Character;

public class Water extends Area implements Updated{

	public Water(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void update(){
		ArrayList<Sprite> temp = inArea();
		for(int i = 0; i < temp.size(); i++){
			Sprite s = temp.get(i);
			if(Math.abs(s.Vx()) > 5)
				s.setVx(Math.signum(s.Vx())*5);
			if(Math.abs(s.Vy()) > 7)
				s.setVy(Math.signum(s.Vy())*7);
			if(s instanceof Character){
				((Character)s).jump = false;
			}
			temp.get(i).setGravity(0.3);
		}
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(new Color(23, 56, 160));
		g.fillRect(x, y, width, height);
	}

}
