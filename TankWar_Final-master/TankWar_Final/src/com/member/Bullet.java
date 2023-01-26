package com.member;

public class Bullet implements Runnable{
	int bulletX;
	int bulletY;
	String bulletDirection;
	int bulletSpeed=4;
	boolean isLive = true;
	
	public Bullet(int x, int y, String direction){
		this.bulletX = x;
		this.bulletY = y;
		this.bulletDirection = direction;
	}

	public int getBulletX() {
		return bulletX;
	}

	public int getBulletY() {
		return bulletY;
	}

	public boolean getIsLive() {
		return isLive;
	}

	public void setIsLive(boolean isLive) {
		this.isLive = isLive;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(bulletDirection){
			//upward
			case "North":
				bulletY-=bulletSpeed;
				break;
			//left
			case "West":
				bulletX-=bulletSpeed;
				break;
			//right
			case "South":
				bulletY+=bulletSpeed;
				break;
			//downward
			case "East":
				bulletX+=bulletSpeed;
				break;
			}
			//System.out.println("x"+ x + "y" +y);
			//the bullet stop running
			if(bulletX<0||bulletX>600||bulletY<0||bulletY>400){
//				System.out.println(bulletX);
//				System.out.println(bulletY);
				this.isLive = false;
				break;
			}	
		}
	}
}
