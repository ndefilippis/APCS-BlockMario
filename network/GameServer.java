package network;
import entities.Character;
import entities.Entity;
import game.Environment;
import game.Level;
import game.LevelLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class GameServer {

    private static final int PORT = 9001;
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    private static Environment environment;
    private static Level level;

    public static void main(String[] args) throws Exception {
    	level = new Level(1, 1);
    	LevelLoader.LoadLevel("src/level11.txt", level);
    	environment = new Environment(level.getMain());
    	environment.load();
        System.out.println("The game server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
        	double update = 1000000000D / 60D;
    		double lastTime = System.nanoTime();
    		new Listener(listener).start();
            while (true) {
        		double currTime = System.nanoTime();
        		if(currTime - lastTime > update){
        			level.getMain().world.Servertick();
        			lastTime = currTime;
        		}
        	}
        } finally {
            listener.close();
        }
    }
    
    private static class Listener extends Thread{
    	ServerSocket listener;
    	
    	public Listener(ServerSocket listener){
    		this.listener = listener;
    	}
    	
    	public void run(){
    		try {
    			while(true)
					new Handler(listener.accept()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private long timeSinceUpdate;

        public Handler(Socket socket) {
            this.socket = socket;
            timeSinceUpdate = System.currentTimeMillis();
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                while (true) {
                    out.println("NAME");
                    name = in.readLine();
                    if (name == null) {
                        continue;
                    }
                    synchronized(names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }
                out.println("ACCEPTNAME");
                Environment.add(new Character(name));
                for(PrintWriter writer : writers){
                	writer.println("ADDNAME "+name);
                }
               	for(int i = Environment.getEntities().size()-1; i >= 0; i--){
                	Entity e = Environment.getEntities().get(i);
                	if(e instanceof Character)
                		continue;
                	out.println("SETOBJ " + e.num()+":"+e.x()+":"+e.y());
                }
                for(String nameO : names){
                	if(name.equals(nameO))
                		continue;
                	out.println("ADDNAME "+nameO);
                }
                writers.add(out);
                while (true) {
                	if(System.currentTimeMillis() > timeSinceUpdate + 1000){
                		for(int i = Environment.getEntities().size()-1; i >= 0; i--){
                			Entity e = Environment.getEntities().get(i);
                			if(e.num() != 0){
                    			out.println("SETOBJ "+e.num()+":"+e.x()+":"+e.y());
                			}
                			timeSinceUpdate = System.currentTimeMillis();
                		}
                    }
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    setPlayer(name + ":" + input);
                    for (PrintWriter writer : writers) {
                        writer.println("LOCPLYR " + name + ":" + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
			} finally {
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
    public static void setPlayer(String substring) {
		String[] data = substring.split(":");
		for(Entity e : Environment.getEntities()){
			if(e instanceof Character && e.toString().equals(data[0])){
				((Character)e).setX(Integer.parseInt(data[1]));
				((Character)e).setY(Integer.parseInt(data[2]));
			}
		}
	}
}