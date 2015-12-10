package com.command;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Terrain extends DrawableObject {
	
	public 	boolean 	isExit = false;
	enum 	Direction 	{NORTH, SOUTH, EAST, WEST, NONE};
	public 	Direction 	facing = Direction.NONE;
	public 	char 		type;
	public  Creature    occupant;
	public  boolean     occupied = false;
	
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
	
	public Terrain(int x, int y, TextureRegion region, boolean blocking, boolean exit, Direction facing) {
		super(x, y);
		this.isExit = exit;
		this.blocking = blocking;
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		this.facing = facing;
//		region.rotate()
		setRegion(region);
	}
	
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	
	public void setType(char type) {
		this.type = type;
	}
	
	public void setOccupant(Creature occ) {
		if(occupant != null) {
			return;
		} else {
			occupant = occ;
		}
	}
	
	public Creature removeOccupant() {
		Creature temp = occupant;
		occupant = null;
		return temp;
	}
	
}
