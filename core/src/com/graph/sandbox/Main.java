package com.graph.sandbox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends Game {
	@Override
	public void create() {
		Gdx.graphics.setTitle("GTIS");

		System.out.println();
		setScreen(new MainMenu());
	}
}