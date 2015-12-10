package com.command;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BattleScreen {
	
	public Array<TextureRegion> backgroundLayers;
	public NPC    npc;
	public Player pc;
	
	
	public BattleScreen(NPC npc, Player pc) {
		this.npc = npc;
		this.pc  = pc;
	}

	public BattleScreen(NPC npc, Player pc, Array<TextureRegion> bgLayers) {
		this.npc 		 = npc;
		this.pc  		 = pc;
		backgroundLayers = bgLayers;
	}
	
	public void draw(SpriteBatch batch) {
		for(TextureRegion layer: backgroundLayers) {
			batch.draw(layer, 0, 0);
		}
		batch.draw(npc.textureRegion, Vals.SCREEN_WIDTH-100,Vals.SCREEN_HEIGHT-100);
		batch.draw(pc.textureRegion, 10, 10);
	}

}
