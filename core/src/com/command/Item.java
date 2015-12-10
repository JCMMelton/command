package com.command;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Item extends DrawableObject {

	enum 	Slot {HEAD, BODY, LEGS, FEET, HAND, NONE};
	public 	Slot slot = null;
	public  int  inventoryIndex;
	public  int  id;
	public  int  handsNeeded;
	
	public Item(int x, int y) {
		super(x, y);
	}
	
	public Item(int x, int y, Texture texture) {
		this(x, y);
		this.texture = texture;
		this.textureRegion = new TextureRegion(texture, 0, 0, 32, 32);
	}
	
	public Item(int x, int y, Texture texture, Slot slot) {
		this(x, y, texture);
		this.slot = slot;
	}
	
	public Item(int x, int y, Texture texture, Slot slot, int id) {
		this(x, y, texture, slot);
		this.id = id;
	}
	
	public Item(int x, int y, Texture texture, Slot slot, int id,  int inventoryIndex) {
		this(x, y, texture, slot, id);
		this.inventoryIndex = inventoryIndex;
	}

}
