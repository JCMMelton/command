package com.command;

public class MapInstance {

	public String[] mapData;
	public int id;
	
	
	public MapInstance(int id) {
		this.id = id;
	}
	
	public MapInstance(int id, String[] mapData) {
		this.id 	 = id;
		this.mapData = mapData;
	}
	
}
