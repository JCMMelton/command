package com.command.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.command.CommandGame;
import com.command.Vals;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Vals.SCREEN_HEIGHT;
		config.width  = Vals.SCREEN_WIDTH;
		new LwjglApplication(new CommandGame(), config);
	}
}
