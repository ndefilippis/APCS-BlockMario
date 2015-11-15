package graphics;

import game.Game;
import game.GameRunner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MenuPanel extends JPanel{
	private JFileChooser fc = new JFileChooser();
	JLabel start = new JLabel("Start Game!");
	JLabel game = new JLabel("APCS Platformer");
	JLabel load = new JLabel("Load Game");
	JLabel multi = new JLabel("Multiplayer");
	JLabel noServer = new JLabel("Could not Make Connection to Server");
	MainFrame mf;
	private long noServerTime;
	private boolean showNoServerMessage;
	
	public MenuPanel(MainFrame mf){
		this.mf = mf;
		start.setFont(new Font("Arial", Font.BOLD, 20));
		load.setFont(new Font("Arial", Font.BOLD, 20));
		multi.setFont(new Font("Arial", Font.BOLD, 20));
		game.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		noServer.setForeground(Color.RED);
		start.addMouseListener(new gameListener());
		load.addMouseListener(new gameListener());
		multi.addMouseListener(new gameListener());
		add(start);
		add(game);
		add(load);
		add(multi);
		add(noServer);
		noServer.setVisible(false);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		start.setBounds(getWidth()/2-start.getWidth()/2, 300, start.getWidth(), start.getHeight());
		load.setBounds(getWidth()/2-load.getWidth()/2, 400, load.getWidth(), load.getHeight());
		game.setBounds(getWidth()/2-game.getWidth()/2, 0, game.getWidth(), game.getHeight());
		multi.setBounds(getWidth()/2-multi.getWidth()/2, 350, multi.getWidth(), multi.getHeight());
		if(showNoServerMessage){
			if(System.currentTimeMillis() > noServerTime + 2000){
				noServer.setVisible(false);
				showNoServerMessage = false;
			}
			else{
				noServer.setVisible(true);
			}
		}
	}
	
	public void noServer(){
		noServerTime = System.currentTimeMillis();
		showNoServerMessage = true;
	}
	
	private class gameListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(start))
				GameRunner.startSingle();
			if(e.getSource().equals(load)){
				int val = fc.showOpenDialog(MenuPanel.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Game.loadSave(file);
				}
			}
			if(e.getSource().equals(multi)){
				GameRunner.startMulti();
			}
				
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JLabel)(e.getSource())).setForeground(Color.RED);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JLabel)(e.getSource())).setForeground(Color.BLACK);
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
}
