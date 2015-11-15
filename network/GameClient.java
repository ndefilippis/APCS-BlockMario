package network;
import game.Game;
import game.GameRunner;
import game.MultiplayerGame;
import graphics.MainFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class GameClient extends Thread{
    static BufferedReader in;
    static PrintWriter out;
    static String name;
    private MainFrame mf;
    private Game g;
    
    public GameClient(MainFrame mf, Game g){
    	this.mf = mf;
    	this.g = g;
    }

    private String getPlayerName() {
    	name = JOptionPane.showInputDialog(
                GameRunner.g.mp(),
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
        return name;
    }


    public void run() {
        // Make connection and initialize streams
        Socket socket = null;
		try{
       		socket = new Socket("localhost", 9001);
		}
		catch(ConnectException e){
			mf.noServer();
			return;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			in = new BufferedReader(new InputStreamReader(
			    socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Process all messages from server, according to the protocol.
        while (true) {
            String line = null;
			try {
				line = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(line == null)
            	continue;
            if (line.startsWith("NAME")) {
                out.println(getPlayerName());
            }
            else if(line.startsWith("ACCEPTNAME")){
            	try {
					g.startGame(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if(line.startsWith("ADDNAME")){
            	MultiplayerGame.addPlayer(line.substring(8));
            }
            else if (line.startsWith("LOCPLYR")) {
            	MultiplayerGame.setPlayer(line.substring(8));
            }
            else if(line.startsWith("SETOBJ")){
            	MultiplayerGame.setObject(line.substring(7));
            }
            else if(line.startsWith("EXIT")){
             	break;
            }
        }
        try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void sendMessage(String s){
    	if(out != null)
    	out.println(s);
    }
}