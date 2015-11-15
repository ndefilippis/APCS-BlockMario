package graphics;

import game.InputHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	private JPanel jp;
	private static MenuPanel menuPanel;
	private static MainPanel mp;
	private static PausePanel pp;
	private static WorldPanel wp;
	private static KeyListener key, pause, world;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu, editMenu;
	private JMenuItem newFile, openFile, saveFile;
	private JMenuItem undoEdit, redoEdit;
	private boolean keyAdd;
	
	public MainFrame(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Graphics");
		jp = new JPanel();
		jp.setBackground(Color.WHITE);
		jp.setPreferredSize(new Dimension(1200, 800));
		jp.setLayout(null);
		createMenu();
		mp = new MainPanel();
		mp.setBounds(0, 0, 1200, 800);
		menuPanel = new MenuPanel(this);
		menuPanel.setBounds(0, 0, 1200, 800);
		add(menuPanel);
		key = new InputHandler();
		pause = new PausePanel.MenuHandler();
		world = new WorldPanel.Listener();
		getContentPane().add(jp);
		pack();
	}
	
	private void createMenu(){
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);	
		menuBar.add(fileMenu);
		menuBar.add(editMenu);		
		
		newFile = new JMenuItem("New File");
		openFile = new JMenuItem("Open File");
		saveFile = new JMenuItem("Save");
		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		
		undoEdit = new JMenuItem("Undo");
		redoEdit = new JMenuItem("Edit");
		editMenu.add(undoEdit);
		editMenu.add(redoEdit);
		
		setJMenuBar(menuBar);
	}
	
	public void startGame(){
		remove(menuPanel);
		wp = new WorldPanel();
		wp.setBounds(0, 0, 1200, 800);
		addKeyListener(world);
		jp.add(wp);
		revalidate();
		repaint();
	}
	
	public void startLevel(){
		removeKeyListener(world);
		mp = new MainPanel();
		if(!keyAdd){
			addKeyListener(key);
			keyAdd = true;
		}
		mp.setBounds(0, 0, 1200, 800);
		jp.add(mp);
		jp.remove(wp);
		revalidate();
		repaint();
	}

	
	public void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}

	public void gameOver() {
		remove(mp);
		GameOverPanel gop = new GameOverPanel();
		gop.setBounds(0, 0, 1200, 800);
		add(gop);
		revalidate();
		repaint();
		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() < time + 5000){
			
		}
		remove(gop);
		add(mp);
		revalidate();
		repaint();
	}
	
	public MainPanel mp(){
		mp.repaint();
		return mp;
	}
	
	public void pause(){
		jp.remove(mp);
		removeKeyListener(key);
		keyAdd = false;
		pp = new PausePanel();
		addKeyListener(pause);
		pp.setBounds(350, 200, 500, 400);
		jp.add(pp);
		jp.add(mp);
		revalidate();
		repaint();
	}
	
	public void resume(){
		jp.remove(pp);
		removeKeyListener(pause);
		addKeyListener(key);
		keyAdd = true;
		revalidate();
		repaint();
	}

	public void noServer() {
		menuPanel.noServer();
	}

	public void goToWorld() {
		remove(mp);
		wp.setBounds(0, 0, 1200, 800);
		addKeyListener(world);
		jp.add(wp);
		revalidate();
		repaint();
	}
}
