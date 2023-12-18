import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class ScreenSaver extends JFrame{
	static int backgroundCounter = 0;
	static int j = 0;
	//hold 100 circles at a time
	static Rectangle2D[] OverlapArray = new Rectangle2D[1000];
	
	
	
	
	//Constructor
	ScreenSaver(){
		//gets rid of title bar
		this.setUndecorated(true);
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				System.exit(0);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.exit(0);
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				System.exit(0);
				
			}
			
		});
		this.addMouseListener( new MouseListener(){
			//ends the program if the mouse is clicked, exits the screen or is released from click
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				System.exit(0);
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
				
			}
			//unimplemented methods
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				System.exit(0);
			}
			
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//gets the size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		//sets the size of screen to the size of the jframe
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(true);
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		
		//if the frame was resized
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				repaint();
			}
		});
		Timer timer = new Timer();
		
		//sets a small delay for each circle
		timer.scheduleAtFixedRate(new UpdateTask(), 0, 100); // Update every 2000 milliseconds (2 seconds)
	        
	}
	
	@Override
	public void paint(Graphics g) {
		
          
		//Number to change for X position
		Random Num1 = new Random();
		
		//Number to change for Y position
		Random Num2 = new Random();
		
		//Permanent radius for the circles
		int radius = 20;
		
		//Theta to increase to 360 but starts at 1
		int theta = 1;
		
		//gets random x for next circle
		int RandX = Num1.nextInt(0, 1700);
		
		//gets random y for next circle
		int RandY = Num2.nextInt(0, 1080); 
		
		//Graphics object to draw the circles
		Graphics2D g2D = (Graphics2D) g;
		
		
		if(backgroundCounter <= 0) {
			//set Background color
			
			g2D.setColor(Color.BLACK);
			
			//fill the background
	        g2D.fillRect(0, 0, getWidth(), getHeight());
			backgroundCounter ++;
		}
		
		//Circle shape
		Shape circle = new Arc2D.Double(RandX, RandY, 2*radius, 2*radius, theta, theta + 359, theta);
		
		//sets color of circles to black
		g2D.setColor(Color.white);
		
		//draw circle
		g2D.draw(circle);
		
		//get original color
		Color original = g2D.getColor();
		
		
		//gets the collisionBox of the circle
		Rectangle2D collisionBox = circle.getBounds2D();
		if(j >= 1000) {
			//set j to 0 to overwrite the old objects with new ones
			j = 0; 
			
			g2D.setColor(Color.BLACK);
			g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
			
			//clear the array
			Arrays.fill(OverlapArray, null);
			
			//clear the circles on the screen
			repaint();
		}
		OverlapArray[j] = collisionBox;
		j++;
		//checks for overlap of circles and then makes them green
		for(int i = 0; i < j; i ++) {
			//Check if circle equals itself
			if(!collisionBox.equals(Array.get(OverlapArray, i))) {
				//check if it is overlapping with a circle other than itself
				if(collisionBox.intersects((Rectangle2D) Array.get(OverlapArray, i))) {
					//Random integers to set the RGB
						int RandRed = (int)(Math.random() * 256);
						int RandGreen = (int)(Math.random() * 256);
						int RandBlue = (int)(Math.random() * 256);
					
						//set Random colors to fill circle
						g2D.setColor(new Color(RandRed, RandGreen, RandBlue));
						//fill the circle
						g2D.fill(circle);
				}
			} else {
				continue;
			}
			
		}
		
		//sets color back to the original color
		g2D.setColor(original);
		
		
		
	}

	 private class UpdateTask extends TimerTask {
	        @Override
	        public void run() {
	            repaint(); // Trigger a repaint
	          
	        }
	    }

	

	
}
