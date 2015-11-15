package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameOverPanel extends JPanel{
 private long time;
 private boolean done;
 private int gameX = 0;
 private int overX = 0;
 
 public GameOverPanel(){
  time = System.currentTimeMillis();
  setBackground(Color.BLACK);
 }
 
 public void paintComponent(Graphics g){
  super.paintComponent(g);
  g.setColor(Color.RED);
  g.setFont(new Font("SeguoeUI", Font.BOLD, 30));
  if(System.currentTimeMillis() < time + 3000){
   update();
  }
  else{
   done = true;
  }
  g.drawString("Chris",gameX , getHeight()/2);
  g.drawString("Is amazing", overX, getHeight()/2);
  
 }
 
 private void update(){
  long currTime = System.currentTimeMillis();
  gameX = (int)(currTime - time)*(getWidth()-100)/6000;
  overX = getWidth() - (int)(currTime - time)*(getWidth()-100)/6000;
  repaint();
 }
 public boolean done(){
  return done;
 }
 
 public static void main(String[] args){
  JFrame f = new JFrame();
  GameOverPanel gop = new GameOverPanel();
  gop.setBounds(0,0,1200,600);
  f.add(gop);
  gop.setPreferredSize(new Dimension(1200, 800));
  f.pack();
  f.setVisible(true);
 }
}
