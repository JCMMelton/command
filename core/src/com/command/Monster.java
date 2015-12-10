package com.command;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Monster extends NPC {

	Monster(int x, int y) {
		super(x, y);
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(new TextureRegion(new Texture("ogre_01.tga"), 0, 0, 32, 32));
	}
	
}
