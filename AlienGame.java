/**
 * Created by Oskar on 2015-01-22.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AlienGame extends JComponent implements ActionListener, KeyListener {


    //Bullet[] bullets = Bullet[10];
    Bullet theBullet = new Bullet();
    int shipX = 400-15;
    int windowX = 800;
    int windowY = 600;
    int shipSizeX = 30;
    int shipSizeY = 10;
    int shipSpeedRight = 0;
    int shipSpeedLeft = 0;
    int shipSpeed = 2; //2 pixlar per 10 ms
    boolean debugMode = true;

    public static void main (String[] arg) {
        AlienGame theClass = new AlienGame();
        try{
            if(arg[0].equals("debug")){
               theClass.debugModeOn();
            }
        }catch (Exception e){
            if(theClass.debugCheck()==true){
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
        if(theClass.debugCheck()==true){

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
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, windowX, windowY);
		
		g.setColor(new Color(255, 255, 255));
		
		
        g.setColor(new Color(255, 2, 57));
        g.fillRect(shipX, windowY - 100, shipSizeX, shipSizeY);
    }
    public void actionPerformed(ActionEvent e) {
        //AlienGame theClass=new AlienGame();
        if(shipX>=0&&shipX<=windowX-shipSizeX){
            shipX = shipX + shipSpeedRight + shipSpeedLeft;
        }else if(shipX<=0){
            if(debugMode==true){
                System.out.println("[info] left edge reached");
            }
            shipX++;
        }else if(shipX>=windowX-shipSizeX){
            if(debugMode==true){
                System.out.println("[info] right edge reached");
            }
            shipX--;
        }else{
            if(debugMode==true){
                System.err.println("[ ex ] Illegal position");
                System.err.println(shipX);
                System.exit(0);
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
    int x = 500;
    int y;
}
