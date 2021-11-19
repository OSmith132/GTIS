package com.graph.sandbox.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.graph.sandbox.Main;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = true;
		config.foregroundFPS = 60;
		config.height = 900;  // Default:  1600x900
		config.width = config.height * 16 / 9;
		config.addIcon("titles 2.png", Files.FileType.Internal);
		//config.fullscreen=true;
		new LwjglApplication(new Main(), config);
	}
}