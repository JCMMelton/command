package com.command;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Creature  {

	public Player(int xInit, int yInit) {
		super(xInit, yInit);
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(new TextureRegion(new Texture("pc_test.tga"), 0, 0, 32, 32));
	}
	
}
