package game;

import java.io.IOException;

import network.GameClient;
import graphics.MainFrame;

public class GameRunner {
	public static Game g;
	public static MainFrame mf;
	
	public static void main(String[] args){
		mf = new MainFrame();
		mf.display();
	}

	public static void startSingle() {
		try {
			g = new Game(mf);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			g.startGame("Mario");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startMulti() {
		try {
			g = new MultiplayerGame(mf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameClient gc = new GameClient(mf, g);
		gc.start();
	}
}
