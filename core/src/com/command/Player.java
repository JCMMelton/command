package com.command;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Player extends Creature  {

	public Item head;
	public Item feet;
	public Item body;
	public Item legs;
	public Array<Item> hands;
	public int handLimit = 2;
	
	public Player(int xInit, int yInit) {
		super(xInit, yInit);
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(new TextureRegion(new Texture("pc_test.tga"), 0, 0, 32, 32));
		hands = new Array<Item>();
	}
	
	public void pickUpItem(Item item) {
		item.inventoryIndex = inventory.size;
		inventory.add(item);
	}
	
	public void dropItem(int itemId) {
		for(Item item: inventory) {
			if(item.id == itemId) {
				inventory.removeIndex(item.inventoryIndex);
			}
		}
	}
	
	public void equipItem(Item item) {
		switch(item.slot) {
			case HEAD:
				inventory.add(head);
				head = item;
				break;
			case FEET:
				inventory.add(feet);
				feet = item;
				break;
			case LEGS:
				inventory.add(legs);
				legs = item;
				break;
			case BODY:
				inventory.add(body);
				body = item;
				break;
			case HAND:
				if(hands.size == handLimit) break;
				if(hands.size == 1 && item.handsNeeded > 1) break;
				for(Item hand: hands) {
					inventory.add(hand);	
				}
				hands.add(item);
				break;
			case NONE:
				break;
		}
	}
	
}
