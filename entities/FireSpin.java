package entities;

import java.awt.Color;
import java.awt.Graphics;

import game.Direction;
import game.Environment;


public class FireSpin extends Boundry implements Updated{
 Fireball[] fires = new Fireball[5];
 private boolean add = true;
 private long time;
 public FireSpin(int x, int y, int width, int height) {
  super(x, y, width, height);
  for(int i = 1; i <= 5; i++){
   fires[i-1] = new Fireball(x+width+i*10, y+i*10, 20, 20);
  }
  time = System.currentTimeMillis();
 }
 
 public void update(){
   if(add){
     for(int i = 0; i < fires.length; i++){
       Environment.add(fires[i]);
     }
     add = false;
   }
  long currTime = System.currentTimeMillis()-time;
  for(int i = 1; i <= fires.length; i++){
   fires[i-1].setX((int)(x+width/2+i*25*Math.cos(2*Math.PI*currTime/1000D)));
   fires[i-1].setY((int)(y+height/2+i*25*Math.sin(2*Math.PI*currTime/1000D)));
  }
 } 
 
 
 public class Fireball extends Sprite implements Updated{

  public Fireball(int x, int y, int width, int height) {
   super(x, y, width, height);
   gravityAffected = false;
  }
  
  public void collide(Entity e, Direction d){
   if(e instanceof Character){
    ((Character)e).hurt();
   }
  }
  
  public void draw(Graphics g, int x, int y){
   g.setColor(Color.ORANGE);
   g.fillOval(x, y, width, height);
  }
  
 }
}
