package com.command;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Grid {
	public int height_count;
	public int width_count;
	public int[][] testMatrix;
	
	public Grid(int x, int y) {
		width_count  = x;
		height_count = y;
		testMatrix = new int[width_count][height_count];
		for(int i = 0; i < width_count; i++) {
			for(int j = 0; j < height_count; j++) {
				testMatrix[i][j] = 0;
			}
		}
	}
	
	public void setSpace(int x, int y, int n) {
		testMatrix[x][y] = n;
	}
	
	public boolean isOcc(int x, int y) {
		return testMatrix[x][y] == 1;
	}
	
	public void print() {
		for(int r=0; r<testMatrix.length; r++) {
	       for(int c=0; c<testMatrix[r].length; c++)
	           System.out.print(testMatrix[r][c] + " ");
	       System.out.println();
	    }
	}
	
	public void draw(ShapeRenderer rendr) {
		rendr.begin(ShapeType.Line);
		rendr.setColor(Color.RED);
		for(int i = 0; i < height_count+1; i++) {
			rendr.line(0, i*Vals.GRID_SIZE, Vals.SCREEN_WIDTH, i*Vals.GRID_SIZE);
		}
		for(int i = 0; i < width_count+1; i++) {
			rendr.line(i*Vals.GRID_SIZE, 0, i*Vals.GRID_SIZE, Vals.SCREEN_HEIGHT);
		}
		for(int i = 0; i < width_count; i++) {
			for(int j = 0; j < height_count; j++) {
				if(testMatrix[i][j] == 1) {
					rendr.rect((32*i)+12, (32*j)+12, 8, 8);
				}
			}
		}
		rendr.end();
	}
}
