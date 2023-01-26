package com.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.member.Bullet;
import com.member.Enemy;
import com.member.Explosion;
import com.member.Node;
import com.member.Player;
import com.member.Recorder;
import com.member.Tank;


public class View implements ActionListener{

	JFrame jf;
	StartPanel sp = null;
	JMenuBar jmb,jmb2;
	JMenu jm1,jm2;
	JMenuItem jmt1, jmt2, jmt3,jmt4,jmt5;
	GamePanel gamePanel = null;
	StartPanel startPanel = null;
	
	public View(){
		jf = new JFrame("Tank War");
		jmb = new JMenuBar();
		//create the first menu to menu bar
		jm1 = new JMenu("File(F)");
		jm1.setMnemonic('f');
		jmt1 = new JMenuItem("New");
		jmt1.addActionListener(this);
		jmt1.setActionCommand("New");
		jmt2 = new JMenuItem("Continue");
		jmt2.addActionListener(this);
		jmt2.setActionCommand("Continue");
		jmt3 = new JMenuItem("Save and Exit");
		jmt3.addActionListener(this);
		jmt3.setActionCommand("Save and Exit");
		jmt4 = new JMenuItem("Exit");
		jmt4.addActionListener(this);
		jmt4.setActionCommand("Exit");
		jm1.add(jmt1);
		jm1.addSeparator();
		jm1.add(jmt2);
		jm1.addSeparator();
		jm1.add(jmt3);
		jm1.addSeparator();
		jm1.add(jmt4);
		jm1.addSeparator();
		jmb.add(jm1);
		//create the second menu to menu bar
		jm2 = new JMenu("Help(H)");
		jm2.setMnemonic('h');
		jmt5 = new JMenuItem("Doc");
		jm2.add(jmt5);
		jmb.add(jm2);
		//create start panel
		startPanel = new StartPanel();
		//create thread to call the run function
		//in start class
		Thread startPanelThread = new Thread(startPanel);
		startPanelThread.start();
		//set up the Java frame
		jf.setJMenuBar(jmb);
		jf.add(startPanel);
		jf.setLocation(600, 300);
		jf.setResizable(false);
		jf.setSize(600, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new View();
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		
		//if click the new game
		if(a.getActionCommand().equals("New")){
			gamePanel = new GamePanel("New");
			Thread gamePanelThread = new Thread(gamePanel);
			gamePanelThread.start();
			jf.remove(startPanel);
			jf.add(gamePanel);
			jf.addKeyListener(gamePanel);
			jf.setVisible(true);
		}else if(a.getActionCommand().equals("Continue")){
			//create the battle panel
			gamePanel = new GamePanel("Continue");
			//active thread of panel
			Thread gamePanelThread = new Thread(gamePanel);
			gamePanelThread.start();
			jf.remove(startPanel);
			jf.add(gamePanel);
			jf.addKeyListener(gamePanel);
			//refresh the JFrame
			jf.setVisible(true);
		}else if(a.getActionCommand().equals("Save and Exit")){
			Recorder info = new Recorder();
			info.setEnemies(gamePanel.enemies);
			info.saveInfo();
			System.exit(0);
		}else if(a.getActionCommand().equals("Exit")){
			System.exit(0);
		}
	}

}

//the start panel is to start the game
class StartPanel extends JPanel implements Runnable{
	int time = 0;
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 400);
		//set the time interval to display the welcome string
		if(time%2==0){
			g.setColor(Color.white);
			Font myFont = new Font("Serif",Font.BOLD, 30);
			g.setFont(myFont);
			g.drawString("Welcome To War Zone", 130, 200);
		}	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time ++;
			this.repaint();
		}
	}
}

//The game panel is for running game
class GamePanel extends JPanel implements KeyListener, Runnable{
	JFrame jf;
	Player player = null;

	Vector<Enemy> enemies= new Vector<Enemy>();
	//define the exploration
	Vector<Explosion> explosions = new Vector<Explosion>();
	//define the recording node to save
	Vector<Node> nodes = new Vector<Node>();
	//set enemies initial amount
	int enemiesAmount = 8;
	//define the explosion pictures
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	
	//set the flag to determine the functionalities
	public GamePanel(String flag){
		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/pictures/explore1.jpg"));
		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/pictures/explore2.jpg"));
		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/pictures/explore3.jpg"));
		player = new Player(400,300);
		//if flag is new then create enemies and enemy bullets
		if(flag.equals("New")){
			for(int i = 0; i<enemiesAmount; i++){
				Enemy enemy = new Enemy((i+1)*50,20);
				enemy.setDirection("South");
				//give the enemy tank vector from game panel to enemy class
				enemy.setEnemies(enemies);
				Thread enemyThread = new Thread(enemy);
				enemyThread.start();
				Bullet enemyBullet = new Bullet(enemy.getX()+10,enemy.getY()+30,"South");
				enemy.getEnemyBullets().add(enemyBullet);
				Thread enemyBulletThread = new Thread(enemyBullet);
				enemyBulletThread.start();
				enemies.add(enemy);
			}
		}else if(flag.equals("Continue")){
			nodes = new Recorder().readInfo();
			for(int i = 0; i<nodes.size(); i++){
				Node node = nodes.get(i);
				//create enemy tank and add them to vector
				Enemy enemy=new Enemy(node.getX(), node.getY());
				enemy.setDirection(node.getDirection());
				//give the my panel enemy tank vector to enemy tank
				enemy.setEnemies(enemies);
				Thread t = new Thread(enemy);
				t.start();
				Bullet bullet = new Bullet(enemy.getX()+10, enemy.getY()+30, "South");
				enemy.getEnemyBullets().add(bullet);
				Thread bulletThread = new Thread(bullet);
				bulletThread.start();
				enemies.add(enemy);
//				System.out.println(this.getSize());
				
			}
		}
	}
	
	//function of paint need JPanel.
	public void paint(Graphics g){
		super.paint(g);
		//draw the score board
		this.showScore(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 400);
		//draw player tank
		if(player.getIsLive()==true){
			this.drawTank(g, player.getX(), player.getY(), player.getDirection(), "Player");
			
		}else{
//			Thread gamePanelThread = new Thread(this);
//			gamePanelThread.interrupt();
//			Thread.currentThread().getThreadGroup().list();
		}
		
		//draw the player bullets
		for(int playerBulletIndex = 0; playerBulletIndex<player.getPlayerBullets().size();playerBulletIndex++){
			Bullet playerBullet = player.getPlayerBullets().get(playerBulletIndex);
			//draw the bullet
			if(playerBullet!=null && playerBullet.getIsLive()==true){
				g.setColor(Color.WHITE);
				g.draw3DRect(playerBullet.getBulletX(), playerBullet.getBulletY(), 2, 1, false);
			}
			if(playerBullet.getIsLive() == false){
				player.getPlayerBullets().remove(playerBullet);
			}
		}
		
		//draw the enemy tank
		for(int enemyindex =0; enemyindex<enemies.size();enemyindex++){
			Enemy enemy = enemies.get(enemyindex);
			if(enemy.getIsLive()){
				this.drawTank(g, enemy.getX(), enemy.getY(), enemy.getDirection(), "Enemy");
				for(int enemyBulletIndex  = 0; enemyBulletIndex<enemy.getEnemyBullets().size();enemyBulletIndex++){
					Bullet enemyBullet = enemy.getEnemyBullets().get(enemyBulletIndex);
					if(enemyBullet.getIsLive()){
						g.setColor(Color.RED);
						g.draw3DRect(enemyBullet.getBulletX(), enemyBullet.getBulletY(), 2, 1, false);
					}else{
						enemy.getEnemyBullets().remove(enemyBullet);
					}
				}
			}else{
				enemies.remove(enemyindex);
			}
		}
		//draw the exploration
		for(int explosionIndex = 0; explosionIndex<explosions.size();explosionIndex++){
			Explosion explosion = explosions.get(explosionIndex);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(explosion.getTime()>4){
				g.drawImage(image3, explosion.getX(), explosion.getY(), 30,30,this);

			}else if(explosion.getTime()>2){
				g.drawImage(image2, explosion.getX(), explosion.getY(), 30,30,this);

			}else{
				g.drawImage(image1, explosion.getX(), explosion.getY(), 30,30,this);

			}
			//Exploration time is getting decreased
			explosion.timeDecrease();
			
			//when the exploration time is 0, remove the exploration
			if(explosion.getTime()==0){
				explosions.remove(explosion);
			}
		}
	}
	
	//function to render all tanks
	public void drawTank(Graphics g, int x, int y, String direction, String type){
		//determine if the type is player or enemy
		switch(type){
		case "Player":
			g.setColor(Color.cyan);
			break;
		case "Enemy":
			g.setColor(Color.yellow);
			break;
		}
		
		switch(direction){
		case "North":
			//draw tanks left foot
			g.fill3DRect(x, y, 5, 30,false);
			//draw the tanks right foot
			g.fill3DRect(x+15, y, 5, 30,false);
			//draw the body of the tank
			g.fill3DRect(x+5, y+5, 10, 20, true);
			//draw the weapon room
			g.setColor(Color.red);
			g.fillOval(x+5, y+10, 10, 10);
			//draw the weapon
			g.drawLine(x+10, y+15, x+10, y);
			break;
		case "West":
			//draw right foot tank
			g.fill3DRect(x+10-15, y+15-10, 30, 5,false);
			//draw left foot
			g.fill3DRect(x+10-15, y+15+5, 30, 5,false);
			//draw the body of the tank
			g.fill3DRect(x+10-10, y+15-5, 20, 10, true);
			//draw the weapon room
			g.setColor(Color.red);
			g.fillOval(x+10-5, y+15-5, 10, 10);
			//draw the weapon
			g.drawLine(x+10, y+15, x+10-15, y+15);
			break;
		case "South":
			//draw right foot
			g.fill3DRect(x, y, 5, 30,false);
			//draw left foot
			g.fill3DRect(x+15, y, 5, 30,false);
			//draw the body of the tank
			g.fill3DRect(x+5, y+5, 10, 20, true);
			//draw the weapon room
			g.setColor(Color.red);
			g.fillOval(x+5, y+10, 10, 10);
			//draw the weapon
			g.drawLine(x+10, y+15, x+10, y+25);
			break;
		case "East":
			//draw left foot
			g.fill3DRect(x+10-15, y+15-10, 30, 5,false);
			//draw right foot
			g.fill3DRect(x+10-15, y+15+5, 30, 5,false);
			//draw the body of the tank
			g.fill3DRect(x+10-10, y+15-5, 20, 10, true);
			//draw the weapon room
			g.setColor(Color.red);
			g.fillOval(x+10-5, y+15-5, 10, 10);
			//draw the weapon
			g.drawLine(x+10, y+15, x+10+15, y+15);
			break;
		}
	}
	
	//determine if the tank get shot by bullet type means who should got shot
		public void hitTank(Bullet bullet, Tank tank, String type) {
			switch(tank.getDirection()){
			//when tank get shot in north or south direction
			case "North":
			case "South":
				if(bullet.getBulletX()<=tank.getX()+20 && bullet.getBulletX()>=tank.getX() && 
				bullet.getBulletY()<tank.getY()+30 && bullet.getBulletY()>=tank.getY()){
					bullet.setIsLive(false);
					tank.setIsLive(false);
					if(type.equals("Enemy")){
						Recorder.reduceTotalEnemies();
						Recorder.addPlayerScore();
					}else if(type.equals("Player")){
						Recorder.reducePlayerLives();
						if(Recorder.getPlayerLives()>0){
							player.setIsLive(true);
							player.setX(400);
							player.setY(300);
							player.setDirection("North");
						}else{
							player.setIsLive(false);
						}
					}
					//create the exploration
					Explosion explosion = new Explosion(tank.getX(),tank.getY());
					//add it to the exploration vector
					explosions.add(explosion);
				}
				break;
			//when tank get shot in west or east direction
			case "West":
			case "East":
				if(bullet.getBulletX()<=tank.getX()+30 && bullet.getBulletX()>=tank.getX() &&
				bullet.getBulletY()<=tank.getY()+20 && bullet.getBulletY()>=tank.getY()){
					bullet.setIsLive(false);
					tank.setIsLive(false);
					if(type.equals("Enemy")){
						Recorder.reduceTotalEnemies();
						Recorder.addPlayerScore();
					}else if(type.equals("Player")){
						Recorder.reducePlayerLives();
						if(Recorder.getPlayerLives()>0){
							player.setIsLive(true);
							player.setX(400);
							player.setY(300);
							player.setDirection("North");
						}else{
							player.setIsLive(false);
						}
					}
					//create the exploration
					Explosion explosion = new Explosion(tank.getX(),tank.getY());
					//add it to the exploration vector
					explosions.add(explosion);
				}
				break;
				
			}
			
		}
	
	//determine if player's bullet hit enemy tank
	public void hitEnemy(){
		for(int playerBulletIndex = 0; playerBulletIndex< player.getPlayerBullets().size();playerBulletIndex++){
			Bullet playerBullet = player.getPlayerBullets().get(playerBulletIndex);
			if(playerBullet.getIsLive()){
				for(int enemyIndex=0;enemyIndex<enemies.size();enemyIndex++){
					Enemy enemy = enemies.get(enemyIndex);
					if(enemy.getIsLive()){
						this.hitTank(playerBullet, enemy, "Enemy");
					}
				}
			}
		}
	}
	
	//determine if enemy's bullet hit player tank
	public void hitPlayer(){
		for(int enemyIndex = 0; enemyIndex<this.enemies.size();enemyIndex++){
			Enemy enemy = enemies.get(enemyIndex);
			for(int enemyBulletIndex=0; enemyBulletIndex<enemy.getEnemyBullets().size();enemyBulletIndex++){
				Bullet enemyBullet = enemy.getEnemyBullets().get(enemyBulletIndex);
				if(player.getIsLive()){
					this.hitTank(enemyBullet, player, "Player");					
				}
			}
		}
	}
	
	//draw the score board
	public void showScore(Graphics g){
		//draw a enemy tank as icon to indicate the number of the rest enemy
		this.drawTank(g, 20, 420, "North", "Enemy");
		g.setColor(Color.black);
		g.drawString(Recorder.getTotalEnemies()+"", 50, 440);

		
		//draw a player tank as icon to indicate the number of the rest blood
		this.drawTank(g, 20, 480, "North", "Player");
		g.setColor(Color.black);
		g.drawString(Recorder.getPlayerLives()+"", 50, 500);
		
		//draw the score of player
		g.setColor(Color.black);
		Font f = new Font("Serif",Font.BOLD, 20);
		g.setFont(f);
		g.drawString("Your Score", 100, 470);
		g.setColor(Color.red);
		g.drawString(Recorder.getPlayerScore()+" ", 200, 470);
	}

	

	//this will control the move of player tank and it need key listener
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		if(k.getKeyCode()==KeyEvent.VK_W){
			player.setDirection("North");
			player.moveUp();
			
		}
		if(k.getKeyCode()==KeyEvent.VK_A){
			player.setDirection("West");
			player.moveLeft();
		}
		if(k.getKeyCode()==KeyEvent.VK_S){
			player.setDirection("South");
			player.moveDown();
		}
		if(k.getKeyCode()==KeyEvent.VK_D){
			player.setDirection("East");
			player.moveRight();
		}
		//if press key "J" then player will shoot
		if(k.getKeyCode() == KeyEvent.VK_J){
			this.player.shoot();
		}
		//after pressed the key, Panel will refresh to show the new move of player
		this.repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//determine if enemy tank got hit
			this.hitEnemy();
			//determine if player tank got hit
			this.hitPlayer();
			//call repaint every 100 ms to refresh the game panel
//			if(pause == false){
//				System.out.println("Pause is " + pause);
			this.repaint();
//			}
		}
	}
}

