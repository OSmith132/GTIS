package com.graph.sandbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenu implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    public MainMenu(){
        //Image background = new Image(new Texture(Gdx.files.internal("GTIS TTIEL.png")));
        //background.setHeight(720);background.setWidth(1280);
        //stage.addActor(background);
        Image Rectangle = new Image(new Texture(Gdx.files.internal("Rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight()+2);
        Rectangle.setWidth(Gdx.graphics.getWidth()/5);
        Rectangle.setPosition(-15,-1);
        stage.addActor(Rectangle);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.95f,0.95f,0.95f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // sr.begin(ShapeRenderer.ShapeType.Filled);
        // sr.rect(0,0,Gdx.graphics.getWidth()/5f,Gdx.graphics.getHeight());
        //  sr.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
