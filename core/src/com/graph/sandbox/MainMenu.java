package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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




        final Label errorText = new Label("*Sorry, this feature has not yet been implemented",buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth()*(0.25f)+10, 5);



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
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Sandbox());
                errorText.remove();
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
                stage.addActor(errorText);

            }
        });
        stage.addActor(openGraph);



        TextButton Settings = new TextButton("Settings", buttonSkin, "maroon");
        Settings.setHeight(Gdx.graphics.getHeight()*(0.1f));
        Settings.setWidth(Gdx.graphics.getWidth()*(0.2f));
        Settings.setPosition((Gdx.graphics.getWidth()*(0.125f)-Gdx.graphics.getWidth()*(0.1125f)),(Gdx.graphics.getHeight()*(0.96f)-Gdx.graphics.getHeight()*(0.36f)));

        Settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addActor(errorText);


            }
        });
        stage.addActor(Settings);



        TextButton exitButton = new TextButton("Exit", buttonSkin, "maroon");
        exitButton.setHeight(Gdx.graphics.getHeight()*(0.11f));
        exitButton.setWidth(Gdx.graphics.getWidth()*(0.22f));
        exitButton.setPosition((Gdx.graphics.getWidth()*(0.125f)-Gdx.graphics.getWidth()*(0.1125f)),(Gdx.graphics.getHeight()*(0.96f)-Gdx.graphics.getHeight()*(0.92f)));

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);




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
