package com.graph.sandbox.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.graph.sandbox.Main;


public class DesktopLauncher {


	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.foregroundFPS = 80;


//		config.height = 900;  // Default:  1600x900
//		config.width = config.height * 16 / 9;
		config.addIcon("titles 2.png", Files.FileType.Internal);
		config.fullscreen=false;
		new LwjglApplication(new Main(), config);
	}
}