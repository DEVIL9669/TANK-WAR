package com.member;

public class Node {
	int x;
	int y;
	String direction;
	public Node(int x, int y, String direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getDirection() {
		return direction;
	}
}
