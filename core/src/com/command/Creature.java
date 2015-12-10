package com.command;

import com.badlogic.gdx.utils.Array;

public class Creature extends DrawableObject {
	
	public String name;
	public Array<Item> inventory;

	public Creature(int x, int y) {
		super(x, y);
		blocking  = true;
		inventory = new Array<Item>();
	}
	
}
