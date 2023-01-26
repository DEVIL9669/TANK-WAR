package com.member;

import java.util.Vector;

import java.util.Random;

public class Enemy extends Tank implements Runnable{
	int bulletTimeInterval;
	Vector<Enemy> enemies = new Vector<Enemy>();
	private Vector<Bullet> enemyBullets = new Vector<Bullet>();
	int select;
	String[] directionSet = {"North","West","South","East"};
	Random random = new Random();
	//constructor
	public Enemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	//get the enemyBullets vector
	public Vector<Bullet> getEnemyBullets() {
		return enemyBullets;
	}
	//receive the enemy vector from game panel
	public void setEnemies(Vector<Enemy> enemies){
		this.enemies = enemies;
	}
	//determine if tanks touch other tanks
	public boolean isTouchOthers(){
		boolean touch = false;
		switch(this.getDirection()){
		
		//If this tank is in north
		case "North":
//			System.out.println(enemies.size());
			for(int enemyIndex=0; enemyIndex<enemies.size();enemyIndex++){
				Enemy enemy = enemies.get(enemyIndex);
				if(enemy!=this){
					//other tanks in north or south
					if(enemy.getDirection() == "North" || enemy.getDirection() == "South" ){
						if(this.getX()>= enemy.getX() && this.getX()<=enemy.getX()+20 && this.getY()>=enemy.getY() && this.getY()<=enemy.getY()+30){
//							System.out.println("nn");
							return true;
						}
						if(this.getX()>=enemy.getX() && this.getX()<=enemy.getX()+20 && this.getY()+20>=enemy.getY() && this.getY()+20<=enemy.getY()+30){
//							System.out.println("ns");
							return true;
						}
					}
					//other tanks in west or east
					if(enemy.getDirection() == "West" || enemy.getDirection() =="East"){
						if(this.getX()>=enemy.getX() && this.getX()<=enemy.getX()+30 && this.getY()>=enemy.getY() && this.getY()<=enemy.getY()+20){
//							System.out.println("nw");
							return true;
						}
						if(this.getX()>=enemy.getX() && this.getX()<=enemy.getX()+30 && this.getY()+20>=enemy.getY() && this.getY()+20<=enemy.getY()+20){
//							System.out.println("ne");
							return true;
						}
					}
				}
			}
			break;
		//If this tank is in south
		case "South":
			for(int enemyIndex=0; enemyIndex<enemies.size();enemyIndex++){
				Enemy enemy = enemies.get(enemyIndex);
				if(enemy!=this){
					//others tank in north or south
					if(enemy.getDirection() == "North" || enemy.getDirection() == "South" ){
						if(this.getX() >= enemy.getX() && this.getX() <= enemy.getX()+20 && this.getY()+30 >=enemy.getY() && this.getY()+30 <=enemy.getY()+30){
//							System.out.println("sn");
							return true;
						}
						if(this.getX()+20 >= enemy.getX() && this.getX()+20 <=enemy.getX()+20 && this.y+30 >=enemy.getY() && this.y+30 <=enemy.getY()+30){
//							System.out.println("ss");
							return true;
						}
					}
					if(enemy.getDirection() == "West" || enemy.getDirection() =="East"){
						if(this.getX() >= enemy.getX() && this.getX() <= enemy.getX()+30 && this.getY()+30 >=enemy.getY() && this.getY()+30 <=enemy.getY()+20){
//							System.out.println("sw");
							return true;
						}
						if(this.getX()+20>=enemy.getX() && this.getX()+20 <=enemy.getX()+30 && this.getY()+30 >=enemy.getY() && this.getY()+30 <=enemy.getY()+20){
//							System.out.println("se");
							return true;
						}
					}
				}
			}
			break;
		//If this tank is in west
		case "West":
			for(int enemyIndex=0; enemyIndex<enemies.size();enemyIndex++){
				Enemy enemy = enemies.get(enemyIndex);
				if(enemy!=this){
					//other tanks in north or south
					if(enemy.getDirection() == "North" || enemy.getDirection() == "South" ){
						if(this.getX() >= enemy.getX() && this.getX() <= enemy.getX()+20 && this.getY()>=enemy.getY() && this.getY()<=enemy.getY()+30){
							return true;
						}
						if(this.getX()>=enemy.getX() && this.getX() <=enemy.getX()+20 && this.getY()+20>=enemy.getY() && this.getY()+20<=enemy.getY()+30){
							return true;
						}
					}
					//other tanks in west or east
					if(enemy.getDirection() == "West" || enemy.getDirection() =="East"){
						if(this.getX() >= enemy.getX() && this.getX() <= enemy.getX()+30 && this.getY()>=enemy.getY() && this.getY()<=enemy.getY()+20){
							return true;
						}
						if(this.getX()>=enemy.getX() && this.getX() <=enemy.getX()+30 && this.getY()+20>=enemy.getY() && this.getY()+20<=enemy.getY()+20){
							return true;
						}
					}
				}
			}
			break;
		//If this tank is in east
		case "East":
			for(int enemyIndex=0; enemyIndex<enemies.size();enemyIndex++){
				Enemy enemy = enemies.get(enemyIndex);
				if(enemy!=this){
					//other tanks in north or south
					if(enemy.getDirection() == "North" || enemy.getDirection() == "South" ){
						if(this.getX()+30 >= enemy.getX() && this.getX()+30 <= enemy.getX()+20 && this.getY()>=enemy.getY() && this.getY()<=enemy.getY()+30){
							return true;
						}
						if(this.getX()+30 >= enemy.getX() && this.getX()+30 <=enemy.getX()+20 && this.getY()+20>=enemy.getY() && this.getY()+20<=enemy.getY()+30){
							return true;
						}
					}
					//other tanks in west or east
					if(enemy.getDirection() == "West" || enemy.getDirection() =="East"){
						if(this.getX()+30 >= enemy.getX() && this.getX()+30 <= enemy.getX()+30 && this.getY() >=enemy.getY() && this.getY() <=enemy.getY()+20){
							return true;
						}
						if(this.getX()+30>=enemy.getX() && this.getX()+30 <=enemy.getX()+30 && this.getY()+20 >=enemy.getY() && this.getY()+20 <=enemy.getY()+20){
							return true;
						}
					}
				}
			}
			break;
		}
		return touch;
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
//			System.out.println(this.isTouchOthers());
			switch(this.getDirection()){
			case "North":
				for(int i = 0; i<30;i++){
					if(y>0 && !this.isTouchOthers()){
						y-=speed;
					}
					//set the time interval between each move
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "West":
				for(int i = 0; i<30;i++){
					if(x>5 && !this.isTouchOthers()){
						x-=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "South":
				for(int i = 0; i<30;i++){
					if(y<370 && !this.isTouchOthers()){
						y+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "East":
				for(int i = 0; i<30;i++){
					if(x<560 && !this.isTouchOthers()){
						x+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
			this.bulletTimeInterval++;
			if(bulletTimeInterval%2==0){
				//determine if the enemy tank need to reload
				if(this.getIsLive()){
					if(enemyBullets.size()<2){
						Bullet enemyBullet = null;
						//reload new bullet
						switch(this.getDirection()){
						case "North":
							enemyBullet = new Bullet(this.getX()+10,this.getY(),"North");
							enemyBullets.add(enemyBullet);
							break;
						case "West":
							enemyBullet = new Bullet(this.getX()-5, this.getY()+15,"West");
							enemyBullets.add(enemyBullet);
							break;
						case "South":
							enemyBullet = new Bullet(this.getX()+10, this.getY()+25,"South");
							enemyBullets.add(enemyBullet);
							break;
						case "East":
							enemyBullet = new Bullet(this.getX()+25, this.getY()+15,"East");
							enemyBullets.add(enemyBullet);
							break;
						}
						
						//start the bullet thread
						Thread enemyBulletThread = new Thread(enemyBullet);
						enemyBulletThread.start();
					}
				}
				
			}
			
			//Randomly choose the direction of enemy tank
			select = random.nextInt(directionSet.length);
			this.direction = directionSet[select];
			//if the enemy tanks are destroyed then get out the thread
			if(this.getIsLive() == false){
				break;
			}
		}
	}
	
//	public void setEnemies(Vector<Enemy> enemies){
//		this.enemies = enemies;
//	}
}
