package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LevelReader{

	static Map<Color, Character> colorToC = new HashMap<Color, Character>();
	static Map<BufferedImage, Color> imagetoColor = new HashMap<BufferedImage, Color>();
	static Set<Color> colors = new HashSet<Color>();
	static int level = 3;
	static int world = 4;
	public static void main(String[] args) throws IOException{
		File file = new File("C:/Users/Nick/Downloads/src/fun/mario-"+world+"-"+level+".gif");  
        FileInputStream fis = new FileInputStream(file);  
        BufferedImage image = ImageIO.read(fis); //reading the image file  
        int rows = image.getHeight()/16; //You should decide the values for rows and cols variables  
        int cols = image.getWidth()/16;  
  		
        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height  
        int chunkHeight = image.getHeight() / rows;  
        System.out.println(chunkWidth + " " + chunkHeight);
        BufferedImage imgs[][] = new BufferedImage[rows][cols]; //Image array to hold image chunks  
        for (int x = 0; x < rows; x++) {  
            for (int y = 0; y < cols; y++) {  
                //Initialize the image array with image chunks  
                imgs[x][y] = new BufferedImage(chunkWidth, chunkHeight, BufferedImage.TYPE_INT_ARGB);  
  
                // draws the image chunk  
                Graphics gr = imgs[x][y].createGraphics();  
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x+8, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight+8, null);  
                gr.dispose();  
            }  
        }  
        System.out.println("Splitting done");  
        JFrame frame = new JFrame("JFrame Source Demo");
		// Add a window listner for close button
		// This is an empty content area in the frame
		frame.setLayout(new GridLayout(rows,cols));
        //writing mini images into image files
        long hey = System.nanoTime();
        for (int i = 0; i < imgs.length; i++) {
        	for(int j = 0; j < imgs[0].length; j++){
            	//frame.add(new JLabel(new ImageIcon(imgs[i][j])));
           		walk(imgs[i][j]);
        	}
        	//System.out.println();
        }
        for(BufferedImage i : imagetoColor.keySet()){
        	frame.add(new JLabel(new ImageIcon(i)));
        }
        frame.setVisible(true);
        frame.pack();
        for(BufferedImage bl : imagetoColor.keySet()){
        	char fun = '.';
        	String ar = ((String) (JOptionPane.showInputDialog(
                    frame,
                    "Choose a screen name:",
                    "Screen name selection",
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(bl),
                    null, null)));
            if(ar != null && ar.length() > 0){
            	fun = ar.charAt(0);
            }
            colorToC.put(imagetoColor.get(bl), new Character(fun));
        }
        System.out.println((System.nanoTime()-hey)/1000000000.0);
        System.out.println("Mini images created");
        PrintWriter writer = new PrintWriter("level"+world+level+".txt", "UTF-8");
        for(int x = 0; x < imgs.length; x++){
        	for(int y = 0; y < imgs[0].length; y++){
        		char ch = colorToC.get(imageToC(imgs[x][y]));
        		writer.print(ch);
       		}
       		writer.println();
        }
        writer.close();
	}
	
	private static Color imageToC(BufferedImage bi){
		long red = 0;
		long green = 0;
		long blue = 0;
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				int rgb = bi.getRGB(i, j);
				int r = (rgb & 0xff0000) >> 16;
				int g = (rgb & 0xff00) >> 8;
				int b = rgb & 0xff;
				red += r;
				green += g;
				blue += b;
			}
		}
		red /= 16*16;
		green /= 16*16;
		blue /= 16*16;
		return new Color((int)red, (int)green, (int)blue);
	}

	private static void walk(BufferedImage bi) {
		Color bl = imageToC(bi);
		if(!colors.contains(bl)){
			imagetoColor.put(bi, bl);
		}
		colors.add(bl);
		//System.out.print(s);
	}
}