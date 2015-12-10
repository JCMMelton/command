package com.command;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Terrain extends DrawableObject {
	
	public boolean 	isExit = false;
	public char 	facing = 'n';
	public char 	type;
	
	public Terrain(int x, int y) {
		super(x, y);
	}
	
	public Terrain(int x, int y, TextureRegion region) {
		super(x, y);
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(region);
	}
	
	public Terrain(int x, int y, TextureRegion region, boolean blocking) {
		super(x, y);
		this.blocking = blocking;
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(region);
	}
	
	public Terrain(int x, int y, TextureRegion region, boolean blocking, boolean exit) {
		super(x, y);
		this.isExit = exit;
		this.blocking = blocking;
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(region);
	}
	
	public void setFacing(char facing) {
		this.facing = facing;
	}
	
	public void setType(char type) {
		this.type = type;
	}
	
}
