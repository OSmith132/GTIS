package com.graph.sandbox;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;




public class Main extends Game {




	@Override
	public void create() {
		Gdx.graphics.setTitle("GTIS");

		setScreen(new MainMenu());
		//((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
	}
}