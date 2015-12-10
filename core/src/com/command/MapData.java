package com.command;

public class MapData {
	
	char 	wall 	= 'w';
	char[] 	floors 	= {'f', 'l', 's'};
	char 	exit 	= 'e';
	
	public static String[] translate(String[] mapData) {
		int arrayLength     = mapData.length;
		int stringLength    = mapData[0].length();
		int arrayLimit      = arrayLength-1;
		int stringLimit     = stringLength-1;
		
		String[] tempArray  = new String[stringLength];
		
		int index 		    = stringLimit;
		
		for(int i = stringLimit; i >= 0; i--) {
			String tempString = "";
			for(int j = arrayLimit; j >= 0; j--) {
				tempString += mapData[j].charAt(i);
			}
			tempArray[index--] = tempString;
		}
		return tempArray;
	}
	
	public static String[] randomMap() {
		int y = Vals.GRID_Y_COUNT;
		int x = Vals.GRID_X_COUNT;
		String[] tempA = new String[y];
		for(int i=0; i<y; i++) {
			String tempS = "";
			for(int j=0; j<x; j++) {
				if(i==0 || i==y-1 || j==0 || j==x-1) {
					tempS += 'w';
				} else {
					boolean lavaDetected = false;
					int lavaP = 98;
					int r1 = (int)(Math.random() * 100);
					if(tempS.charAt(j-1) == 'l') {
						lavaP -= 20;
						lavaDetected = true;
					}
					if(tempA[i-1] != null) {
//						System.out.println("checking above " + tempA[i-1]); 
						if(tempA[i-1].charAt(j) == 'l' ) {
							lavaP -= 40;
							lavaDetected = true;
						}
					};
					if(lavaDetected) {
						System.out.println(String.valueOf(r1) + " " + String.valueOf(lavaP));	
					}
					if(r1>lavaP) {
						tempS += 'l';
					} else {
						tempS += 'f';	
					}
				}
			}
//			System.out.println(tempS);
			tempA[i] = tempS;
		}
		return tempA;
	}

	public static String[] map_01 = {
		   //0000000001111111//
		   //1234567890123456//
			"wwwwwwwwwwwwwwww", // 01
			"wlfffffffffffffw", // 02
			"wflffffffffffffw", // 03
			"wfflfffffffffffw", // 04
			"wffflffffffffffw", // 05
			"wffffffffffffffw", // 06
			"wffwfffffffffffw", // 07
			"wffwfffffffffffw", // 08
			"wffwfffffffffffw", // 09
			"wfwffffffffffffw", // 10
			"wwfffffffffffffw", // 11
			"wwffffffssfffffw", // 12
			"wwwfffffssfffffw", // 13
			"wwwwfffffffffffw", // 14
			"wwwwwffffffffffw", // 15
			"wwwwwwwwewwwwwww"  // 16
	};
	public static String[] map_02 = {
		   //000000000111111111//
		   //123456789012345678//
			"wwwwwwwwewwwwwwwww", // 01
			"wffffffffffffffffw", // 02
			"wffffffffffffffffw", // 03
			"wffffffffffffffffw", // 04
			"wffffwwwwffffffffw", // 05
			"wffffwwwwffffffffw", // 06
			"wffffwwwwffffffffw", // 07
			"wffffffffffffffffw", // 08
			"wffffffffffffffffw", // 09
			"wffffffffffffffffw", // 10
			"wffffffffwwwfffffw", // 11
			"wffffffffwwwfffffw", // 12
			"wffffffffwwwfffffw", // 13
			"wffffffffffffffffw", // 14
			"wffffffffffffffffe", // 15
			"wffffffffffffffffw", // 16
			"wwwwwwwwwwwwwwwwww"  // 17
	};
	public static String[] map_03 = {
		   //000000000111111111//
		   //123456789012345678//
			"wwwwwwwwwwwwwwwwww", // 01
			"wffffffffffffffffw", // 02
			"effffffffffffffffe", // 03
			"wffffffffffffffffw", // 04
			"wwwwwwwwwwwwwwwwww"  // 05
	};
	
}
