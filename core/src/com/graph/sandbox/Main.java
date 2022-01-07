package com.graph.sandbox;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Objects;
import java.util.Scanner;


public class Main extends Game {




	@Override
	public void create() {

		FileHandle configFile = Gdx.files.internal("config.txt");
		String text = configFile.readString();
		String[] configArray = text.split("\\r?\\n");
		String[] widthHeightArray = configArray[0].split(" ");
		Gdx.graphics.setTitle("GTIS");

		if (Objects.equals(configArray[1], "fullscreen")) {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
		else{
			Gdx.graphics.setWindowedMode(Integer.parseInt(widthHeightArray[0]), Integer.parseInt(widthHeightArray[2]));
		}
		setScreen(new MainMenu());
		//((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
	}
}