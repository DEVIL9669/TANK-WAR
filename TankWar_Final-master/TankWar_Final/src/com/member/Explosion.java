package com.member;

public class Explosion {
	int x, y;
	int time = 6;
	boolean isLive = true;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Explosion(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getTime() {
		return time;
	}

	public void timeDecrease(){
		if(time>0){
			time--;
		}else{
			this.isLive = false;
		}
	}
}
