package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class InputHandler implements KeyListener{
		public static boolean up;
		public static boolean down;
		public static boolean left;
		public static boolean right;
		public static boolean grab;
		private boolean restartHold;

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP){
				Game.getPlayer().jump();
				up = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				down = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				left = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_W){
				Game.c.jump();
				up = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_S){
				down = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				left = true;
				//Game.c2.Vx = -5;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				//Game.c2.Vx = 5;
				right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_B){
				grab = true;
			}
			if(!restartHold && e.getKeyCode() == KeyEvent.VK_R){
				restartHold = true;
				try {
					GameRunner.g.restart();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(!Game.paused() && e.getKeyCode() == KeyEvent.VK_ESCAPE){
				GameRunner.g.pause();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP){
				Game.getPlayer().releaseJump();
				up = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				down = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				left = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				right = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_W){
				Game.c.releaseJump();
				up = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_S){
				down = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				//Game.c2.Vx = 0;
				left = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				//Game.c2.Vx = 0;
				right = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_B){
				Game.getPlayer().dropHold();
				grab = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				restartHold = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
