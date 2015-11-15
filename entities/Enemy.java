package entities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import game.Direction;



public abstract class Enemy extends Mob {
	public boolean kill;

	public Enemy(int x, int y, int width, int height, Direction d) {
		super(x, y, width, height);
		if(d == Direction.LEFT)
			setBehavior(-1);
		else{
			setBehavior(1);
		}
	}

	public void setBehavior(int i) {
		setVx(i);
	}

	public void update(){
		behave();
		super.update();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Hey";
	}
	
	public void behave(){
	}
	
	@Override
	public void collide(Entity e, Direction d) {
		super.collide(e, d);
		if (d == Direction.LEFT) {
			setVx(-1);
		}
		if (d == Direction.RIGHT) {
			setVx(1);
		}
		if(e instanceof Character){
			if(d == Direction.BOTTOM){
				try {
					AudioPlayer.player.start(new AudioStream(new FileInputStream("src/resources/enemyKill.wav")));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				destroy();
			}
			else
				((Character)e).hurt();
		}
	}

}
