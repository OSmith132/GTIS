package com.graph.sandbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MainMenu implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    public MainMenu() {
        //Image background = new Image(new Texture(Gdx.files.internal("GTIS TTIEL.png")));
        //background.setHeight(720);background.setWidth(1280);
        //stage.addActor(background);
        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.25f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);


        TextButton newGraph = new TextButton("New Graph", buttonSkin, "maroon");
        newGraph.setHeight(Gdx.graphics.getHeight() * (0.12f));
        newGraph.setWidth(Gdx.graphics.getWidth() * (0.225f));
        newGraph.setPosition((Gdx.graphics.getWidth() * (0.125f) - Gdx.graphics.getWidth() * (0.1125f)), (Gdx.graphics.getHeight() * (0.96f) - Gdx.graphics.getHeight() * (0.12f)));

        newGraph.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Make graph");
            }
        });
        stage.addActor(newGraph);


        TextButton openGraph = new TextButton("Open Graph", buttonSkin, "maroon");
        openGraph.setHeight(Gdx.graphics.getHeight() * (0.1f));
        openGraph.setWidth(Gdx.graphics.getWidth() * (0.2f));
        openGraph.setPosition((Gdx.graphics.getWidth() * (0.125f) - Gdx.graphics.getWidth() * (0.1125f)), (Gdx.graphics.getHeight() * (0.96f) - Gdx.graphics.getHeight() * (0.24f)));


        openGraph.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Open graph");
            }
        });
        stage.addActor(openGraph);



        TextButton presetGraphs = new TextButton("Preset Graphs", buttonSkin, "maroon");
        presetGraphs.setHeight(Gdx.graphics.getHeight()*(0.1f));
        presetGraphs.setWidth(Gdx.graphics.getWidth()*(0.2f));
        presetGraphs.setPosition((Gdx.graphics.getWidth()*(0.125f)-Gdx.graphics.getWidth()*(0.1125f)),(Gdx.graphics.getHeight()*(0.96f)-Gdx.graphics.getHeight()*(0.36f)));


        presetGraphs.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //System.out.println("Preset graph");
            }
        });
        stage.addActor(presetGraphs);





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
