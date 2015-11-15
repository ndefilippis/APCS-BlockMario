package graphics;

import game.Game;
import game.GameRunner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PausePanel extends JPanel {
	private JLabel cont = new JLabel("Continue");
	private JLabel options = new JLabel("Settings");
	private JLabel save = new JLabel("Save");
	private JLabel world = new JLabel("World Map");
	private JLabel exit = new JLabel("Exit");
	
	public PausePanel(){
		setBackground(Color.BLACK);
		BorderFactory.createLineBorder(Color.WHITE, 5);
		setFocusable(true);
		setVisible(true);
		cont.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		options.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		save.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		exit.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		world.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		cont.setForeground(Color.WHITE);
		options.setForeground(Color.WHITE);
		exit.setForeground(Color.WHITE);
		save.setForeground(Color.WHITE);
		world.setForeground(Color.WHITE);
		cont.addMouseListener(new MouseHandler());
		options.addMouseListener(new MouseHandler());
		exit.addMouseListener(new MouseHandler());
		save.addMouseListener(new MouseHandler());
		world.addMouseListener(new MouseHandler());
		add(cont);
		add(options);
		add(save);
		add(exit);
		add(world);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		cont.setBounds(getWidth()/4, 50, cont.getWidth(), cont.getHeight());
		options.setBounds(getWidth()/4, 100, options.getWidth(), options.getHeight());
		save.setBounds(getWidth()/4, 150, save.getWidth(), save.getHeight());
		world.setBounds(getWidth()/4, 200, world.getWidth(), world.getHeight());
		exit.setBounds(getWidth()/4, getHeight()/4+200, exit.getWidth(), exit.getHeight());
		
	}
	private class MouseHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(cont)){
				GameRunner.g.resume();
			}
			if(e.getSource().equals(options)){
				
			}
			if(e.getSource().equals(save)){
				PrintWriter out = null;
				try {
					out = new PrintWriter(new File("save.txt"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				out.println(Game.currWorld+":"+Game.currLevel);
				out.println(Game.getPlayer().coins());
				out.println(Game.getPlayer().getLives());
				out.close();
			}
			if(e.getSource().equals(world)){
				GameRunner.g.mf().goToWorld();
			}
			if(e.getSource().equals(exit)){
				System.exit(0);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JLabel)e.getSource()).setForeground(Color.RED);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JLabel)e.getSource()).setForeground(Color.WHITE);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class MenuHandler implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				GameRunner.g.resume();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
	}
}
