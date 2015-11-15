package entities;


import game.Direction;
import game.Environment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import area.Area;


public abstract class Sprite extends Entity implements Updated, Moveable{
 protected double Vx, Vy;
 protected final double TERMINAL_VELOCITY = 100D;
 protected final double FRICTION = 2D;
 protected boolean upBlocked, downBlocked, leftBlocked, rightBlocked;
 protected boolean gravityAffected = true;
 protected int altitude;
 protected boolean bounce = false;
 protected Direction direction;
 protected Area currentArea;
 protected double gravity = Environment.GRAV_CONSTANT;
 
 public Sprite(int x, int y, int width, int height){
  super(x, y, width, height);
 }
 
 public void clearBlocks(){
  upBlocked = false;
  downBlocked = false;
  leftBlocked = false;
  rightBlocked = false;
 }
 
 public double Vx(){
  return Vx;
 }
 
 public double Vy(){
  return Vy;
 }
 
 public void setVx(double vx){
  this.Vx = vx;
 }
 
 public void setVy(double vy){
  this.Vy = vy;
 }
 
 public boolean downBlocked(){
  return downBlocked;
 }
 public boolean rightBlocked(){
  return rightBlocked;
 }
 public boolean leftBlocked(){
  return leftBlocked;
 }
 public boolean upBlocked(){
  return upBlocked;
 }
 
 public void setDownBlocked(boolean b){
  downBlocked = b;
 }
 public void setUpBlocked(boolean b){
  upBlocked = b;
 }
 public void setLeftBlocked(boolean b){
  leftBlocked = b;
 }
 public void setRightBlocked(boolean b){
  rightBlocked = b;
 }
 
 public void destroy(){
  Environment.remove(this);
 }
 
 public Area currentArea(){
  ArrayList<Area> temp = Environment.getAreas();
  for(int i = 0; i < temp.size(); i++){
   if(temp.get(i).inArea().contains(this))
    return temp.get(i);
  }
  return null;
 }
 
 @Override
 public void update() {
  currentArea = currentArea();
  Environment.collision(this, (int)(x + Vx), (int)(y + Vy));
  move();
  gravity = Environment.GRAV_CONSTANT;
 }
 
 public void move(){
  if(Vy <= 0 && !upBlocked || Vy >= 0 && !downBlocked){
   y += Vy;
   
   if(gravityAffected)
    Vy += gravity;
   if(Vy > TERMINAL_VELOCITY)
    Vy = TERMINAL_VELOCITY;
  }
  if(Vx <= 0 && !leftBlocked || Vx >= 0 && !rightBlocked){
   x += Vx;
  }
 }
 
 public void collide(Entity e, Direction d){
  if(passThrough)
   return;
  if(!(e instanceof Sprite && !((Sprite)e).passThrough)){
   int vx = 0;
   int vy = 0;
   if(e instanceof Sprite){
    vx = (int)((Sprite)e).Vx();
    vy = (int)((Sprite)e).Vy();
   }
   switch(d){
   case RIGHT:
    setX(e.x() + e.width() + vx);
    break;
   case LEFT:
    setX(e.x() - width() + vx);
    break;
   case TOP:
    setY(e.y() - height() + vy);
    break;
   case BOTTOM:
    setY(e.y() + e.height() + vy);
    break;
   }
   if(d == Direction.BOTTOM && Vy < 0 || d == Direction.TOP && Vy > 0){
    Vy = 0;
   }
   else if(!bounce && (d == Direction.RIGHT && Vx < 0 || d == Direction.LEFT && Vx > 0)){
    Vx = 0;
   }
   if(e instanceof Moveable && d == Direction.TOP ){
    Moveable m = ((Moveable)e);
    x += m.Vx();
    y += m.Vy();
   }
  }
 }
 
 public void draw(Graphics g, int x, int y){
  g.drawString(num()+"", x, y-10);
  g.setColor(Color.BLACK);
  g.fillRect(x, y, width, height);
 }

 public void setGravity(double d) {
  gravity = d;
 }
}
