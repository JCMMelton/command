package com.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerManagement {
	
	public Texture texture;
	public Pixmap pixmap;
	public SpriteBatch batch;
	public Stage  stage;
	public Player pc;
	
	public PlayerManagement(Player pc) {
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
//		stage.toScreenCoordinates(coords, transformMatrix)
		Gdx.input.setInputProcessor(stage);
		pixmap = new Pixmap(Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT, Pixmap.Format.RGBA8888);
		// Equipment and Stats
		pixmap.setColor(Color.BLUE);
		pixmap.fillRectangle(Vals.GRID_HALF, Vals.GRID_HALF, Vals.SCREEN_WIDTH/2 - Vals.GRID_HALF, Vals.SCREEN_HEIGHT/2 - Vals.GRID_SIZE);
		// Inventory and item descriptions
		pixmap.setColor(Color.WHITE);
		pixmap.fillRectangle(Vals.GRID_HALF, Vals.SCREEN_HEIGHT/2, Vals.SCREEN_WIDTH/2 - Vals.GRID_SIZE*6, Vals.SCREEN_HEIGHT/2 - Vals.GRID_SIZE);
		pixmap.setColor(Color.GRAY);
		pixmap.fillRectangle(Vals.GRID_HALF + Vals.SCREEN_WIDTH/2 - Vals.GRID_SIZE*6, Vals.SCREEN_HEIGHT/2, Vals.GRID_SIZE*6 - Vals.GRID_HALF, Vals.SCREEN_HEIGHT/2 - Vals.GRID_SIZE );
		
		texture = new Texture(pixmap);
		Table table = new Table();
		for(Item item: pc.inventory) {
			table.add(new ItemWidget(item));
		}
		ScrollPane inventoryView = new ScrollPane(table);
		stage.addActor(inventoryView);
		
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stage.act(delta);
		batch.begin();
		batch.draw(new TextureRegion(texture), 0, 0);
//		batch.draw(new TextureRegion(texture), Vals.GRID_HALF, -Vals.SCREEN_HEIGHT/2);
//		stage.draw();
		batch.end();
	}
	
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}
	
	public class ItemWidget extends Button {
		
		public Item item;
		
		public ItemWidget(Item item) {
			super((Drawable)item.textureRegion);
			this.item = item;
		}
		
	}

}
