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

	boolean shipAlive = true;
	int alienSak;
	int maxAlienBullets = 20;
	AlienBullet[] alienBullets = new AlienBullet[maxAlienBullets];
	int alienBulletSpeed = 8;
	int maxWalls = 4;
	Wall[] walls = new Wall[maxWalls];
 int maxAliens = 8;
 Alien[] aliens = new Alien[maxAliens];
 int maxBullets = 200;
    Bullet[] bullets = new Bullet[maxBullets];
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
 int alienNextBullet;
 int bulletSpeed = 3;
 int shootOrNot;
 int alienShootOrNot;
 int moveAlienOrNot;
 boolean spaceDown;
 boolean upDown;
 int points;
 int fireSpeed = 25;
 int alienFireSpeed = 100;
 boolean superMode = false;
 Image image;
 boolean gameOver;
 int gameOverCount;


public AlienGame() { //initera gojs här typ
 for (int i = 0; i < maxBullets; i++) {
  bullets[i] = new Bullet();
 }
 for (int i = 0; i < maxAliens; i++) {
  aliens[i] = new Alien();
  aliens[i].x = i * 100;
 }
 for(int i=0;i<walls.length;i++){
	 	walls[i]=new Wall();
	 	walls[i].x=(100-walls[0].sizeX/2)+i*200;
 		}	
for (int i = 0; i < maxAlienBullets; i++) {
  alienBullets[i] = new AlienBullet();
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
 if (superMode){
  if (moveAlienOrNot % 2 == 0)
  g.setColor(new Color(155, 200, 0));
  else
   g.setColor(new Color(155, 50, 255));
 }
 else
  g.setColor(new Color(0, 0, 0));
 
       g.fillRect(0, 0, windowX, windowY);
 
 if (superMode) {
  g.setColor(new Color(255, 29, 100, 125));
  g.fillRect(shipX + shipSizeX / 2 - 1, 0, 1, 500);
 }
 //drawing walls

 
 for(int i=0;i<maxWalls;i++){
 ImageIcon wall=new ImageIcon("recource\\wall"+walls[i].HP+".png");
 if(walls[i].HP>0){
		g.drawImage(wall.getImage(), walls[i].x, walls[i].y, null);
		}
 }
 
 if (gameOver)
	if (gameOverCount < 99) {
	g.setColor(new Color(100, 100, 100));
	g.setFont(new Font("Arial", Font.BOLD,gameOverCount));
	g.drawString("GAME OVER", windowX / 2 - 300, windowY / 2);
	}
	else {
	g.setColor(new Color(100, 100, 100));
	g.setFont(new Font("Arial", Font.BOLD,16));
	g.drawString("Credits: Oskar Söderberg Natur/Natur och Lukas Tonneman Brommalallare", windowX / 2 - 300, windowY / 2);
	}
 
 //poängen
 g.setColor(new Color(100, 237, 106));
 g.setFont(new Font("Arial", Font.BOLD,34));
 g.drawString("Score: "+points, 5, windowY-10);
 
 // Skott
 g.setColor(new Color(255, 255, 255));
 for (int i = 0; i < maxBullets;  i++) {
  if (bullets[i].alive)
   g.fillRect(bullets[i].x, bullets[i].y, bullets[i].sizeX, bullets[i].sizeY);
 }
 
 // alienBullets
 g.setColor(new Color(255, 255, 255));
 for (int i = 0; i < maxAlienBullets;  i++) {
	//System.out.println(i);
  if (alienBullets[i].alive)
   g.fillRect(alienBullets[i].x, alienBullets[i].y, alienBullets[i].sizeX, alienBullets[i].sizeY);
 }
 
 // Aliens
 g.setColor(new Color(55, 111, 60));
 for (int i = 0; i < maxAliens; i++) {
  if (aliens[i].alive)
   g.fillRect(aliens[i].x, aliens[i].y, aliens[i].sizeX, aliens[i].sizeY);
 }
 
 // Ritar rymdskepp
	if (shipAlive) {
       g.setColor(new Color(255, 2, 57));
       g.fillRect(shipX, windowY - 100, shipSizeX, shipSizeY);
 g.fillRect(shipX + shipSizeX / 2 - 2, windowY - 108, 4, 8);
	}
 
   }
   public void actionPerformed(ActionEvent e) {
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
 for (int i = 0; i < maxBullets; i++) {
  bullets[i].y = bullets[i].y - bulletSpeed;
  if (bullets[i].y < 0)
   bullets[i].alive = false;
 }

 for (int i = 0; i < maxAlienBullets; i++) {
  alienBullets[i].y = alienBullets[i].y + alienBulletSpeed;
  if (alienBullets[i].y < 0)
   alienBullets[i].alive = false;
 }
 
 // Aliens rör sig
 if (moveAlienOrNot > 1000)
  moveAlienOrNot = 0;
 
 for (int i = 0; i < maxAliens && moveAlienOrNot % 15 ==  0; i++) {
  aliens[i].y = aliens[i].y + 1;
 }
 moveAlienOrNot += 1;
 if (spaceDown) {
  if (shootOrNot == fireSpeed) { // Ändra hur fort den ska skjuta här
   bullets[nextBullet].x = shipX + shipSizeX / 2 - bullets[nextBullet].sizeX;
   bullets[nextBullet].y = windowY - 100;
   bullets[nextBullet].alive = true;
   if (nextBullet + 1 < maxBullets)
    nextBullet++;
   else
    nextBullet = 0;
   shootOrNot = 0;
  }
  
 }
 
 if ((int)(Math.random()*20) == 1) {
	if (alienShootOrNot == alienFireSpeed) { // Ändra hur fort den ska skjuta här
		alienSak = (int)(Math.random()*maxAliens);
		if (aliens[alienSak].alive) {
			alienBullets[alienNextBullet].x = aliens[alienSak].x + aliens[alienSak].sizeX / 2 - alienBullets[alienNextBullet].sizeX;
			alienBullets[alienNextBullet].y = aliens[alienNextBullet].y;
			alienBullets[alienNextBullet].alive = true;
			   if (alienNextBullet + 1 < maxAliens)
				alienNextBullet++;
			   else
				alienNextBullet = 0;
			alienShootOrNot = 0;
			}
		}
  }
 
 if (shootOrNot < fireSpeed) { // och här
  shootOrNot++;
 }
 
 if (alienShootOrNot < alienFireSpeed) { // och här
  alienShootOrNot++;
 }
 for (int i = 0; i < maxBullets; i++)
  for (int j = 0; j < maxAliens; j++) {
   if (bullets[i].x + bullets[i].sizeX >= aliens[j].x &&
        bullets[i].x <= aliens[j].x + aliens[j].sizeX &&
        bullets[i].y + bullets[i].sizeY >= aliens[j].y &&
        bullets[i].y <= aliens[j].y + aliens[j].sizeY &&
        aliens[j].alive && bullets[i].alive) {
    aliens[j].alive = false;
    bullets[i].alive = false;
    points += 10;
    System.out.println("Points: " + points);
  }
  }
  
  for(int i=0;i<maxBullets;i++){
	for (int j = 0; j < maxWalls; j++) {
		if(bullets[i].x + bullets[i].sizeX >= walls[j].x &&
			bullets[i].x <= walls[j].x + walls[j].sizeX &&
			bullets[i].y + bullets[i].sizeY >= walls[j].y &&
			bullets[i].y <= walls[j].y + walls[j].sizeY &&
			walls[j].HP > 0 && bullets[i].alive){
				bullets[i].alive=false;
				walls[j].HP--;
				System.out.println(walls[j].HP);
				if(debugMode){
					System.out.println("[info] Wall "+i+" hit");
			}
		}
	}

  }
  
  // Alienskott träffar skeppet
  for(int i=0;i<maxAlienBullets;i++){
		if(alienBullets[i].x + alienBullets[i].sizeX >= shipX &&
			alienBullets[i].x <= shipX + shipSizeX &&
			alienBullets[i].y + alienBullets[i].sizeY >= windowY - 100 &&
			alienBullets[i].y <= windowY - 100 + shipSizeY &&
			alienBullets[i].alive){
				gameOver = true;
				shipAlive = false;
			}
		}
  
  // Aliens hit walls
  for(int i=0;i<maxAlienBullets;i++){
	for (int j = 0; j < maxWalls; j++) {
		if(alienBullets[i].x + alienBullets[i].sizeX >= walls[j].x &&
			alienBullets[i].x <= walls[j].x + walls[j].sizeX &&
			alienBullets[i].y + alienBullets[i].sizeY >= walls[j].y &&
			alienBullets[i].y <= walls[j].y + walls[j].sizeY &&
			walls[j].HP > 0 && alienBullets[i].alive){
				alienBullets[i].alive=false;
				walls[j].HP--;
				System.out.println(walls[j].HP);
				if(debugMode){
					System.out.println("[info] Wall "+i+" hit");
			}
		}
	}

  }
  
  /*if (allDead(aliens))
	newWave();*/
  
  if (gameOverCount > 300)
	System.exit(0);
 
 if (upDown && spaceDown) {
	/*if (superMode) {
		superMode = false;
		fireSpeed = 25;
		shipSpeed = 2;
		bulletSpeed = 8;
		shootOrNot = 0;
	}
	else {*/
	  superMode = true;
	  fireSpeed = 1;
	  shipSpeed = 6;
	  bulletSpeed = 3;
	  shootOrNot = 0;
  }
 //}
 
 if (gameOver)
	gameOverCount++;
 
 repaint();
   }
   

   
   public boolean allDead(Alien[] lista) {
	boolean shit = true;
	for (int i = 0; i < aliens.length; i++)
		if (aliens[i].alive)
			shit = false;
	return shit;
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
  spaceDown = true;   
  if (debugMode) {
   System.out.println("[actn] Space hit");
   }
 }
 if (e.getKeyCode() == KeyEvent.VK_UP) {
  upDown = true;
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
 if (e.getKeyCode() == KeyEvent.VK_SPACE) {
  spaceDown = false;
 }
 if (e.getKeyCode() == KeyEvent.VK_UP) {
  upDown = false;
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

class AlienBullet {
	int x = 900;
	int y;
	int sizeX = 2;
	int sizeY = 10;
	boolean alive = true;
}

class Wall{
int y=400;
int HP=4;
int sizeX=108;
int sizeY = 44;
int x;
}

class Bullet {
   int x = 1000;
   int y;
int sizeX = 2;
int sizeY = 10;
boolean alive = true;
}
class Alien {
int x = 100;
int y;
int sizeX = 30;
int sizeY = 30;
boolean alive = true;
}