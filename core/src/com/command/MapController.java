package com.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	public boolean battle  = false;
	public boolean showInv = false;
	public BitmapFont font;
	public ScrollPane playerInv;
	public InputMultiplexer iomux;
	
	public MapController() {
		iomux = new InputMultiplexer();
		
		font 		= new BitmapFont();
		mapLoader 	= new MapLoader();
		maps 		= new Array<MapInstance>();
		maps.add(new MapInstance(mapIndex++, MapData.translate(MapData.randomMap())));
		mapLoader.setMapData(maps.get(currentMap).mapData);
		terrain   = mapLoader.generateTerrain();
		
		grid      	= new Grid(Vals.GRID_X_COUNT, Vals.GRID_Y_COUNT);
		creatures 	= new Array<Creature>();
		items 		= new Array<Item>();
		pc 			= new Player(Vals.GRID_X_COUNT/2, Vals.GRID_Y_COUNT/2, iomux);
//		playerInv   = getInventory();
		
		Monster testNPC = new Monster(7,7);
		moveNPC(testNPC, 1, 1);
		creatures.add(testNPC);
		NPC nonOgre = new NPC(5,5);
		moveNPC(nonOgre, 1, -1);
		creatures.add(nonOgre);
		
		Item testItem = new Item(4, 10, new Texture("item_test.tga"), Item.Slot.HAND);
		items.add(testItem);
		
		Item testItem2 = new Item(12, 8, new Texture("items_01.tga"), Item.Slot.LEGS);
		testItem2.name = "item 2";
		testItem2.setRegion(3, 0);
		items.add(testItem2);
		
		Array<TextureRegion> testBS = new Array<TextureRegion>();
		testBS.add(new TextureRegion(new Texture("battle_test.tga"), 0, 0, 800, 600));
		battleScreen = new BattleScreen(testNPC, pc, testBS);
		
		iomux.addProcessor(this);
		Gdx.input.setInputProcessor(iomux);
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
		if(showInv) {
//			playerInv.draw(batch, 1.0f);
			pc.charScreen.draw(batch);
		}
		batch.end();
	}
	
	public ScrollPane getInventory() {
		Pixmap  			  pm   = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
		pm.setColor(Color.WHITE);
		pm.fillRectangle(0, 0, 64, 64);
		Texture				  tex  = new Texture(pm);
		TextureRegion 		  tr   = new TextureRegion(tex, 0, 0, 64, 64);
		TextureRegionDrawable trd  = new TextureRegionDrawable(tr);
		List<Item> 			  list = new List<Item>(new List.ListStyle(font, Color.BLACK, Color.BLUE, trd));
		list.setItems(pc.inventory);
		list.setSelected(pc.inventory.get(0));
		ScrollPane 			  sp   = new ScrollPane(list);
		return sp;
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
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					if(temp.occupant instanceof Monster) {
						startBattle();	
					}
					break;
				}
				if(!temp.blocking) 
					pc.move(0,1);
				break;
			case Keys.DOWN:
			case Keys.S:
				temp = terrain.get(pc.xCor).get(pc.yCor-1);
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					if(temp.occupant instanceof Monster) {
						startBattle();	
					}
					break;
				}
				if(!temp.blocking) 
					pc.move(0,-1);
				break;
			case Keys.RIGHT:
			case Keys.D:
				temp = terrain.get(pc.xCor+1).get(pc.yCor);
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					if(temp.occupant instanceof Monster) {
						startBattle();	
					}
					break;
				}
				if(!temp.blocking) 
					pc.move(1, 0);
				break;
			case Keys.LEFT:
			case Keys.A:
				temp = terrain.get(pc.xCor-1).get(pc.yCor);
				if(temp.isExit) break;
				if(grid.isOcc(temp.xCor, temp.yCor)){ 
					if(temp.occupant instanceof Monster) {
						startBattle();	
					}
					break;
				}
				if(!temp.blocking) 
					pc.move(-1,0);
				break;
			case Keys.C:
			case Keys.I:
				showInv = !showInv;
				break;
			case Keys.E:
				for(Item item: items) {
					if(item.xCor == pc.xCor && item.yCor == pc.yCor) {
						pc.pickUpItem(item);
						items.removeValue(item, true);
//						pc.charScreen.updateInv(pc.inventory);
						break;
//						getInventory();
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
	
	public void moveNPC(NPC npc, int x, int y) {
		if(npc == null) return;
		int ox = npc.xCor;
		int oy = npc.yCor;
		grid.setSpace(ox, oy, 0);
		npc.move(x, y);
		grid.setSpace(ox+x, oy+y, 1);
		Terrain temp = terrain.get(npc.xCor).get(npc.yCor);
		if(!temp.occupied) {
			temp.setOccupant(npc);
		}
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
//		System.out.println(String.valueOf(screenX) + " " + String.valueOf(screenY));
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
//		System.out.println(String.valueOf(screenX) + " " + String.valueOf(screenY));
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
