package com.command;

public class Creature extends DrawableObject {
	
	public String name;

	public Creature(int x, int y) {
		super(x, y);
		blocking = true;
	}
	
}
