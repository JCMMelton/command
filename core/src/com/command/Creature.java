package com.command;

import com.badlogic.gdx.utils.Array;

public class Creature extends DrawableObject {
	
	public String name;
	public Array<Item> inventory;
	public Array<Item> equipment;
	
	// Attributes
	public int health;
	public int actionPoints;
	
	public int attackPower;
	public int modifiedAttackPower;
	
	public int speed;
	public int modifiedSpeed;
	
	public int strength;
	public int modifiedStrength;
	
	public int dexterity;
	public int modifiedDexterity;
	
	public int endurance;
	public int modifiedEndurance;
	
	public int defence;
	public int modifiedDefence;
	

	public Creature(int x, int y) {
		super(x, y);
		blocking  = true;
		inventory = new Array<Item>();
		equipment = new Array<Item>();
	}
	
	public void pickUpItem(Item item) {
		item.inventoryIndex = inventory.size;
		inventory.add(item);
		updateStats();
	}
	
	public Item dropItem(int itemId) {
		for(Item item: inventory) {
			if(item.id == itemId) {
				inventory.removeIndex(item.inventoryIndex);
				item.inventoryIndex = 0;
				return item;
			}
		}
		return null;
	}
	
	public void updateItemIds() {
		int nDex = 0;
		for(Item item: inventory) {
			item.inventoryIndex = nDex++;
		}
	}
	
	public void updateStats() {
		for(Item item: equipment) {
			modifiedAttackPower = attackPower + item.attackPower;
			modifiedDefence 	= defence 	  + item.defence;
			modifiedSpeed		= speed		  + item.speed;
			modifiedStrength 	= strength 	  + item.strength;
			modifiedDexterity	= dexterity	  + item.dexterity;
			modifiedEndurance	= endurance   + item.endurance;
		}
	}
	
}
