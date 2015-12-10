package com.command;

public class GameObject {
	
	public int xPos;
	public int xCor;
	public int yPos;
	public int yCor;
	public boolean blocking = false;
	
	public GameObject(int x, int y) {
		xCor = x;
		xPos = x*Vals.GRID_SIZE;// + Vals.GRID_HALF;
		yCor = y;
		yPos = y*Vals.GRID_SIZE;// + Vals.GRID_HALF;
		
	}
	
	public void updatePosition() {
		xPos = xCor*Vals.GRID_SIZE;// + Vals.GRID_HALF;
		yPos = yCor*Vals.GRID_SIZE;// + Vals.GRID_HALF;
	}
	
	public void move(int x, int y) {
		xCor += x;
		yCor += y;
		updatePosition();
	}
	
}
