package com.command;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawableObject extends GameObject {

	public Texture texture;
	public TextureRegion textureRegion;
	public int width;
	public int height;
	
	public DrawableObject(int x, int y) {
		super(x, y);
	}
	
	public DrawableObject(int x, int y, Texture t) {
		super(x, y);
		texture = t;
	}
	
	public DrawableObject(int x, int y, Texture t, int w, int h) {
		super(x, y);
		texture = t;
		setDimentions(w, h);
	}
	
	public DrawableObject(int x, int y, String path) {
		super(x, y);
		setTexture(path);
	}
	
	public DrawableObject(int x, int y, String path, int w, int h) {
		super(x, y);
		setTexture(path);
		setDimentions(w, h);
	}

	public void setTexture(String path) {
		texture  = new Texture(path);
	}
	
	public void setDimentions(int w, int h) {
		width  = w;
		height = h;
	}
	
	public void setRegion(int x, int y) {
		textureRegion = new TextureRegion(texture, width*x, height*y);
	}
	
	public void setRegion(TextureRegion reg) {
		textureRegion = reg;
	}
	
	public void draw(SpriteBatch batch) {
		if(textureRegion != null) {
			batch.draw(textureRegion, xPos, yPos);
		} else
	    if(texture != null) {
	    	batch.draw(texture, xPos, yPos);
	    }
	}
}
