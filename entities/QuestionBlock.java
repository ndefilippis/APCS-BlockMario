package entities;

import game.Direction;
import game.Environment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class QuestionBlock extends Entity{

 public QuestionBlock(int x, int y, int width, int height) {
  super(x, y, width, height);
 }

 @Override
 public void collide(Entity m, Direction d) {
  if(d == Direction.TOP && m instanceof Character || ((d == Direction.LEFT || d == Direction.RIGHT) && m instanceof Turtle.Shell)){
   Environment.remove(this);
   Environment.add(new Wall(x, y, width, height));
   Environment.add(new Mushroom(x, y-40));
  }
 }

 @Override
 public void draw(Graphics g, int x, int y) {
  g.setColor(Color.BLUE);
  g.fillRect(x, y, width, height);
  g.setColor(Color.WHITE);
  g.setFont(new Font("SegueUI", Font.BOLD, 20));
  g.drawString("?", x+width/2-10, y+height/2+10);
 }

}
