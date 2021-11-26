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


public class Sandbox implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    String vertexName = "Node";  // Node or Vertex
    String edgeName = "Arc";     // Arc or Edge


    public Sandbox() {

        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));



        final Label errorText = new Label("*Sorry, this feature has not yet been implemented",buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth()*(0.2f)+10, 5);
        //errorText.getFontScaleX()


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);





        TextButton newVertex = new TextButton(("New "+vertexName), buttonSkin, "maroon");
        newVertex.setHeight(Gdx.graphics.getHeight()*(0.1f));
        newVertex.setWidth(Gdx.graphics.getWidth()*(0.08f));
        newVertex.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newVertex);

        newVertex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addActor(errorText);

            }
        });



        TextButton newEdge = new TextButton(("New "+edgeName), buttonSkin, "maroon");
        newEdge.setHeight(Gdx.graphics.getHeight()*(0.1f));
        newEdge.setWidth(Gdx.graphics.getWidth()*(0.08f));
        newEdge.setPosition((Gdx.graphics.getWidth() * (0.105f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newEdge);

        newEdge.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addActor(errorText);

            }
        });



        TextButton mainMenu = new TextButton(("Main Menu"), buttonSkin, "maroon");
        mainMenu.setHeight(Gdx.graphics.getHeight()*(0.1f));
        mainMenu.setWidth(Gdx.graphics.getWidth()*(0.1725f));
        mainMenu.setPosition((Gdx.graphics.getWidth()*(0.0125f)),(Gdx.graphics.getHeight() * (0.04f)));
        stage.addActor(mainMenu);

        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                errorText.remove();

            }
        });

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