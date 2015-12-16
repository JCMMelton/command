package com.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Player extends Creature  {

	public Item head;
	public Item feet;
	public Item body;
	public Item legs;
	public Array<Item> hands;
	public int handLimit = 2;
	public InventoryScreen invScreen;
	public CharacterScreen charScreen;
	
	public Player(int xInit, int yInit) {
		super(xInit, yInit);
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(new TextureRegion(new Texture("pc_test.tga"), 0, 0, 32, 32));
		hands  = new Array<Item>();
		health = 100;
	}
	
	public Player(int xInit, int yInit, InputMultiplexer iomux) {
		super(xInit, yInit);
		setDimentions(Vals.GRID_SIZE, Vals.GRID_SIZE);
		setRegion(new TextureRegion(new Texture("pc_test.tga"), 0, 0, 32, 32));
		hands  = new Array<Item>();
		health = 100;
//		invScreen  = new InventoryScreen(inventory);
		charScreen = new CharacterScreen();
		iomux.addProcessor(charScreen);
		charScreen.setIomux(iomux);
	}
	
	@Override
	public void pickUpItem(Item item) {
		super.pickUpItem(item);
		charScreen.updateInv(inventory);
	}
	
	public Item unequipItem(int itemId) {
		for(Item item: inventory) {
			if(item.id == itemId) {
				equipment.removeIndex(item.inventoryIndex);
				return item;
			}
		}
		return null;
	}
	
	public void equipItem(Item item) {
		switch(item.slot) {
			case HEAD:
				inventory.add(unequipItem(head.id));
				head = item;
				equipment.add(head);
				break;
			case FEET:
				inventory.add(unequipItem(feet.id));
				feet = item;
				equipment.add(feet);
				break;
			case LEGS:
				inventory.add(unequipItem(legs.id));
				legs = item;
				equipment.add(legs);
				break;
			case BODY:
				inventory.add(unequipItem(body.id));
				body = item;
				equipment.add(body);
				break;
			case HAND:
				if(hands.size == handLimit) break;
				if(hands.size == 1 && item.handsNeeded > 1) break;
				for(Item hand: hands) {
					inventory.add(unequipItem(hand.id));
				}
				hands.add(item);
				equipment.add(item);	
				break;
			case NONE:
				break;
		}
	}
	
	public class CharacterScreen implements InputProcessor {
		
		int w = Vals.GRID_SIZE*2;
		int x = Vals.SCREEN_WIDTH-w;
		int h = Vals.GRID_SIZE*4;
		int y = 0;
		Array<Item>   items;
		TextureRegion tr;
		BitmapFont    font;
		int index = 0;
		int max   = 4;
		
		public CharacterScreen() {
			font = new BitmapFont();
			font.setColor(Color.BLACK);
			items = new Array<Item>();
			initTR();
		}
		
		public void setIomux(InputMultiplexer iomux) {
			Gdx.input.setInputProcessor(iomux);
//			Gdx.input.removeInput
		}
		
		public void updateInv(Array<Item> items) {
			this.items = items;
		}
		
		public void draw(SpriteBatch batch) {
			if(tr == null) System.out.println("no texture region");
			if(batch == null) System.out.println("no batch");
			batch.draw(tr, x, y);
			int localMax = max > items.size ? items.size : max;
			localMax += index;
			for(int i=index; i<localMax; i++) {
				int px = x + w/4;
				int py = h - ((1+i)*Vals.GRID_SIZE);
//				font.draw(batch, items.get(i).name, px, py);
				batch.draw(items.get(i).textureRegion, px, py);
			}
		}
		
		public void initTR() {
			Pixmap pm = new Pixmap(w, h, Pixmap.Format.RGBA8888);
			pm.setColor(Color.WHITE);
			pm.fillRectangle(0, 0, w, h);
			Texture t = new Texture(pm);
			tr = new TextureRegion(t, 0, 0, w, h);
		}
		
		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			System.out.println("charScreen " + String.valueOf(screenX) + " " + String.valueOf(screenY));
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	public class InventoryScreen implements InputProcessor {
		
		int w = Vals.GRID_SIZE*2;
		int x = Vals.SCREEN_WIDTH-w;
		int h = Vals.GRID_SIZE*4;
		int y = 0;
		Array<Item>   items;
		TextureRegion tr;
		BitmapFont    font;
		int index = 0;
		int max   = 4;
		
		public InventoryScreen(Array<Item> items) {
			Gdx.input.setInputProcessor(this);
			font = new BitmapFont();
			font.setColor(Color.BLACK);
			this.items = items;
			initTR();
		}
		
		public void updateInv(Array<Item> items) {
			this.items = items;
		}
		
		public void draw(SpriteBatch batch) {
			batch.draw(tr, x, y);
			int localMax = max > items.size ? items.size : max;
			localMax += index;
			for(int i=index; i<localMax; i++) {
				int px = x + w/4;
				int py = h - (i*Vals.GRID_SIZE) - Vals.GRID_HALF;
				font.draw(batch, items.get(i).name, px, py);
			}
		}
		
		public void initTR() {
			Pixmap pm = new Pixmap(w, h, Pixmap.Format.RGBA8888);
			pm.setColor(Color.WHITE);
			pm.fillRectangle(0, 0, w, h);
			Texture t = new Texture(pm);
			tr = new TextureRegion(t, 0, 0, w, h);
		}

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			System.out.println(String.valueOf(screenX) + " " + String.valueOf(screenY));
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
}
