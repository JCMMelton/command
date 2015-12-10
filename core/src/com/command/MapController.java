package com.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MapController implements InputProcessor {

	public Grid grid;
	public Array<Item> items;
	public Array<Creature> creatures;
	public Array<Array<Terrain>> terrain;
	public Array<MapInstance> maps;
	public int mapIndex = 0;
	public int currentMap = 0;
	public MapLoader mapLoader;
	public Player pc;
	public int xOffset = 0;
	public int yOffset = 0;
	public BattleScreen battleScreen;
	public boolean battle = false;
	
	public MapController() {
		mapLoader 	= new MapLoader();
		grid      	= new Grid(Vals.GRID_X_COUNT, Vals.GRID_Y_COUNT);
		creatures 	= new Array<Creature>();
		items 		= new Array<Item>();
		pc 			= new Player(Vals.GRID_X_COUNT/2, Vals.GRID_Y_COUNT/2);
		NPC testNPC = new NPC(7,7);
		creatures.add(testNPC);
		grid.setSpace(7, 7, 1);
		Item testItem = new Item(4, 10, new Texture("item_test.tga"));
		items.add(testItem);
		maps = new Array<MapInstance>();
		maps.add(new MapInstance(mapIndex++, MapData.translate(MapData.randomMap())));
		mapLoader.setMapData(maps.get(currentMap).mapData);
		terrain   = mapLoader.generateTerrain();
		Array<TextureRegion> testBS = new Array<TextureRegion>();
		testBS.add(new TextureRegion(new Texture("battle_test.tga"), 0, 0, 800, 600));
		battleScreen = new BattleScreen(testNPC, pc, testBS);
		Gdx.input.setInputProcessor(this);
	}
	
	public void draw(SpriteBatch batch) {
		batch.begin();
		if(battle) {
			battleScreen.draw(batch);
		} else {
			for(Array<Terrain> row: terrain) {
				for(Terrain terr: row) {
					terr.draw(batch);
				}
			}
			for(Creature creature: creatures) {
				creature.draw(batch);
			}
			for(Item item: items) {
				item.draw(batch);
			}
			pc.draw(batch);	
		}
		batch.end();
	}
	
	public void battleInput(int keycode) {
		switch(keycode) {
			case Keys.SPACE:
				battle = false;
				break;
		}
	}
	
	public void movementInput(int keycode) {

		Terrain temp;
		switch(keycode) {
			case Keys.UP:
			case Keys.W:
				temp = terrain.get(pc.xCor).get(pc.yCor+1);
//				temp = terrain.get(pc.xCor+xOffset).get(pc.yCor+yOffset-1);
//				System.out.println(String.valueOf(temp.blocking) + " " + String.valueOf(temp.type));
//				System.out.println(String.valueOf(temp.xCor) + " " + String.valueOf(temp.yCor));
//				System.out.println(String.valueOf(pc.xCor) + " " + String.valueOf(pc.yCor));
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					startBattle();
					break;
				}
				if(!temp.blocking) 
					pc.move(0,1);
				break;
			case Keys.DOWN:
			case Keys.S:
				temp = terrain.get(pc.xCor).get(pc.yCor-1);
//				temp = terrain.get(pc.xCor+xOffset).get(pc.yCor+yOffset+1);
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					startBattle();
					break;
				}
				if(!temp.blocking) 
					pc.move(0,-1);
				break;
			case Keys.RIGHT:
			case Keys.D:
				temp = terrain.get(pc.xCor+1).get(pc.yCor);
//				temp = terrain.get(pc.xCor+xOffset-1).get(pc.yCor+yOffset);
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					startBattle();
					break;
				}
				if(!temp.blocking) 
					pc.move(1, 0);
				break;
			case Keys.LEFT:
			case Keys.A:
				temp = terrain.get(pc.xCor-1).get(pc.yCor);
//				temp = terrain.get(pc.xCor+xOffset+1).get(pc.yCor+yOffset);
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					startBattle();
					break;
				}
				if(!temp.blocking) 
					pc.move(-1,0);
				break;
			case Keys.E:
				for(Item item: items) {
					if(item.xCor == pc.xCor && item.yCor == pc.yCor) {
						pc.pickUpItem(item);
						items.removeValue(item, true);
					}
				}
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(battle) {
			battleInput(keycode);
		} else {
			movementInput(keycode);
		}
		return false;
	}
	
	public void startBattle() {
		battle = true;
	}
	
	public void pcMove(int x, int y) {
		xOffset += x;
		yOffset += y;
		for(Array<Terrain> row: terrain) {
			for(Terrain terr: row) {
				terr.move(x,y);
			}
		}
	}
	
	public void nextMap() {
		
	}
	
	public class MapLoader {
		public String path = "terrain_02.tga";
		public Texture t;
		public Array<TextureRegion>  regions;
		public Array<Terrain> 		 temp;
		public Array<Array<Terrain>> tempA;
		public String[] mapData;
		public int mapX;
		public int mapY;
		
		public MapLoader() {
			t 		= new Texture(path);
			regions = new Array<TextureRegion>();
			int gs  = Vals.GRID_SIZE;
			regions.add(new TextureRegion(t, 0,    0,  gs, gs));
			regions.add(new TextureRegion(t, gs,   0,  gs, gs));
			regions.add(new TextureRegion(t, 0,    gs, gs, gs));
			regions.add(new TextureRegion(t, gs,   gs, gs, gs));
			regions.add(new TextureRegion(t, gs*2, 0,  gs, gs));
		}
		
		public void setMapData(String[] mapData) {
			this.mapData = mapData;
			 mapY = mapData.length;
			 mapX = mapData[0].length();
		}
		
		public Array<Array<Terrain>> generateTerrain() {
			 tempA = new Array<Array<Terrain>>();
			 for(int i = 0; i < mapY; i++) {
				 temp  = new Array<Terrain>();
				 char[] tempC = mapData[i].toCharArray();
				 for(int j = 0; j < mapX; j++) {
					 char type = tempC[j];
					 int  xT   = i;
					 int  yT   = j;
					 Terrain nt;
					 switch(type) {
						 case 'w':
							 nt = new Terrain(xT, yT, regions.get(0), true);
							 nt.setType(type);
							 temp.add(nt);
							 break;
						 case 'f':
							 nt = new Terrain(xT, yT, regions.get(1));
							 nt.setType(type);
							 temp.add(nt);
							 break;
						 case 'l':
							 nt = new Terrain(xT, yT, regions.get(2), true);
							 nt.setType(type);
							 temp.add(nt);
							 break;
						 case 's':
							 nt = new Terrain(xT, yT, regions.get(3));
							 nt.setType(type);
							 temp.add(nt);
							 break;
						 case 'e':
							 nt = new Terrain(xT, yT, regions.get(4), false, true, Terrain.Direction.NONE);
							 nt.setType(type);
							 temp.add(nt);
							 break;
					 }
				 }
				 tempA.add(temp);
			 }
			 return tempA;
		}
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
