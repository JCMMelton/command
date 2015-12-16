package com.command;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CommandGame extends ApplicationAdapter implements ApplicationListener {
	
	Stage stage;
	SpriteBatch batch;
//	Texture img;
	BitmapFont font;
	ShapeRenderer rendr;
	Array<Person> peeps;
	Array<Person> team1;
	Array<Person> team2;
	Array<Integer> testGrid;
	boolean debugMode = false;
	public Grid grid;
	public MapController map;
	public PlayerManagement pcm;
	
	@Override
	public void create () {
		Player pc = new Player(Vals.GRID_X_COUNT/2, Vals.GRID_Y_COUNT/2);
		pcm = new PlayerManagement(pc);
//		stage 	 = new Stage();
//		map      = new MapController();
		font  	 = new BitmapFont();
		rendr 	 = new ShapeRenderer();
		testGrid = new Array<Integer>();
		peeps 	 = new Array<Person>();
		team1 	 = new Array<Person>();
		team2 	 = new Array<Person>();
		batch    = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		pcm.render(deltaTime);
//		map.draw(batch);
	}
	
	public void debug(Person peep, Person enemy) {
		batch.begin();
		
		int step = 30;
		int gx 	 = 20;
		int gy   = 20;
		
		int rowOne   = gx;
		int rowTwo   = gx+step;
		int rowThree = gx+step*2;
		int rowFour  = gx+step*3;
		
		int colOne   = gy;
		int colTwo   = gy+step;
		int colThree = gy+step*3;
		int colFour  = gy+step*4;
		
		font.draw(batch, String.valueOf(peep.visionRange.overlaps(enemy.fightRange)),    rowOne,   colOne);
		font.draw(batch, String.valueOf(peep.visionRange.overlaps(enemy.renderCircle)),  rowOne,   colTwo);
		font.draw(batch, String.valueOf(peep.visionRange.overlaps(enemy.visionRange)),   rowOne,   colThree);
		font.draw(batch, "v",   rowOne, colFour);
		font.draw(batch, "    v",   rowFour, colThree);
		
//		font.draw(batch, String.valueOf(peep.fightRange.overlaps(enemy.fightRange)),     rowTwo,   colOne);
		font.draw(batch, String.valueOf(peep.canReach(enemy)),     rowTwo,   colOne);
		font.draw(batch, String.valueOf(peep.fightRange.overlaps(enemy.renderCircle)),   rowTwo,   colTwo);
		font.draw(batch, String.valueOf(peep.fightRange.overlaps(enemy.visionRange)),    rowTwo,   colThree);
		font.draw(batch, "f",   rowTwo, colFour);
		font.draw(batch, "    f",   rowFour, colOne);
		
		font.draw(batch, String.valueOf(peep.renderCircle.overlaps(enemy.fightRange)),   rowThree, colOne);
		font.draw(batch, String.valueOf(peep.renderCircle.overlaps(enemy.renderCircle)), rowThree, colTwo);
		font.draw(batch, String.valueOf(peep.renderCircle.overlaps(enemy.visionRange)),  rowThree, colThree);
		font.draw(batch, "r",   rowThree, colFour);
		font.draw(batch, "    r",   rowFour, colTwo);
		
		batch.end();
	}
	
	@Override
	public void dispose() {
		font.dispose();
		rendr.dispose();
		batch.dispose();
//		stage.dispose();
	}
	
	
}
