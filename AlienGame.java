/**
 * Created by Oskar on 2015-01-22.
  TODO:
  fixa aliens
  fixa så att väggarna kan förlora HP
  nörfa väggarna?
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AlienGame extends JComponent implements ActionListener, KeyListener {

    Bullet[] bullets = new Bullet[10];
    // Bullet theBullet = new Bullet();
    int windowX = 800;
    public int windowY = 600;
    int shipSizeX = 30;
    int shipSizeY = 10;
	public int shipX = windowX / 2 - shipSizeX / 2;
    int shipSpeedRight = 0;
    int shipSpeedLeft = 0;
    int shipSpeed = 2; //2 pixlar per 10 ms
    boolean debugMode = false;
	int nextBullet;
	Image image;
	int[] wallHP=new int[]{1,1,1,1};
	
	public AlienGame() {
		for (int i = 0; i < 10; i++) {
			bullets[i] = new Bullet();
		}
	}
    public static void main (String[] arg) {
        AlienGame theClass = new AlienGame();
        try{
            if(arg[0].equals("debug")){
               theClass.debugModeOn();
			   System.out.println("[info] Running in debug mode");
            }
        }catch (Exception e){
            if(theClass.debugCheck()){
                System.err.println("[ ex ] "+e);
            }
        }
        JFrame window = new JFrame("Alien Invaders"); // <-- Här är titeln på fönstret

        AlienGame game = new AlienGame();
        window.add(game);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        if(theClass.debugCheck()){

            System.out.println("[ ok ] All systems go");
        }
        Timer t = new Timer(10, game);
        t.start();
        window.addKeyListener(game);
    }

    public Dimension getPreferredSize() {
        return new Dimension(windowX, windowY);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Bakgrund
		g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, windowX, windowY);
		
		// Skott
		for (int i = 0; i < 10;  i++) {
			g.setColor(new Color(255, 255, 255));
			//if (bullets[i].x != null && bullets[i].y != null)
				g.fillRect(bullets[i].x, bullets[i].y, 2, 10);
		}
		
		// Ritar rymdskepp
        g.setColor(new Color(255, 2, 57));
        g.fillRect(shipX, windowY - 100, shipSizeX, shipSizeY);
		
		//ritar skydd
		ImageIcon wall1=new ImageIcon("recource\\wall"+wallHP[0]+".png");
		ImageIcon wall2=new ImageIcon("recource\\wall"+wallHP[1]+".png");
		ImageIcon wall3=new ImageIcon("recource\\wall"+wallHP[2]+".png");
		ImageIcon wall4=new ImageIcon("recource\\wall"+wallHP[3]+".png");
		if(wallHP[0]<5){
		g.drawImage(wall1.getImage(), (100-54)+0*200, windowY - 200, null);
		}
		if(wallHP[1]<5){
		System.out.println("wall 2 printed");
		g.drawImage(wall2.getImage(), (100-54)+1*200, windowY - 200, null);
		}
		if(wallHP[2]<5){
		g.drawImage(wall3.getImage(), (100-54)+2*200, windowY - 200, null);
		}
		if(wallHP[3]<5){
		g.drawImage(wall4.getImage(), (100-54)+3*200, windowY - 200, null);
		}
    }
    public void actionPerformed(ActionEvent e) {
        //AlienGame theClass=new AlienGame();
        if(shipX>=0&&shipX<=windowX-shipSizeX){
            shipX = shipX + shipSpeedRight + shipSpeedLeft;
        }else if(shipX<=0){
            if(debugMode){
                System.out.println("[info] left edge reached");
            }
            shipX++;
        }else if(shipX>=windowX-shipSizeX){
            if(debugMode){
                System.out.println("[info] right edge reached");
            }
            shipX--;
        }else{
            if(debugMode){
                System.err.println("[ ex ] Illegal position");
                System.err.println(shipX);
                System.exit(0);
            }
        }
		// Skottet rör sig
		for (int i = 0; i < 10; i++) {
			if (bullets[i].y > 0) {
				bullets[i].y = bullets[i].y - 3;
			}
		}
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shipSpeedRight = shipSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shipSpeedLeft = -shipSpeed;
        }
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			bullets[nextBullet].x = shipX;
			bullets[nextBullet].y = windowY - 100;
			if (nextBullet + 1 < 10)
				nextBullet++;
			else
				nextBullet = 0;
				
			if (debugMode) {
				System.out.println("[info] Shots fired");
			}
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shipSpeedRight = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shipSpeedLeft = 0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    public void debugModeOn(){
        debugMode = true;
    }
    public boolean debugCheck(){
        if(debugMode==true){
            return true;
        }else{
            return false;
        }
    }

}

class Bullet {
    int x;
    int y;
}
