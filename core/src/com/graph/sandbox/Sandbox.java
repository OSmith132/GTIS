package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


public class Sandbox implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    private boolean saved = true;


     String vertexName = "Node";  // Node or Vertex
     String edgeName = "Arc";     // Arc or Edge





//I made this long line for no reason other than to show Kamil this line when he asks what the longest line in my code is for the fourth time. It doesn't matter as his longest line will be longer than this one anyway; and probably by a fair margin.

    public Sandbox() {

        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        final Label errorText = new Label("*Sorry, this feature has not yet been implemented", buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth() * (0.2f) + 10, 5);
        //errorText.getFontScaleX()






        final Window saveBox = new Window("Would you like to save and exit?",buttonSkin,"maroon");
        saveBox.setHeight(Gdx.graphics.getHeight() * (0.15f));
        saveBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        saveBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        saveBox.setModal(true);
        saveBox.setMovable(false);
        saveBox.getTitleLabel().setAlignment(1);


        TextButton yesButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        saveBox.add(yesButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.4f, saveBox)).pad(Value.percentWidth(0.05f,saveBox));

        TextButton noButton = new TextButton(("Save"), buttonSkin, "maroon");
        saveBox.add(noButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.4f, saveBox)).pad(Value.percentWidth(0.05f,saveBox));

        saveBox.align(Align.center);
        stage.addActor(saveBox);
        saveBox.setVisible(false);












        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);


        TextButton newVertex = new TextButton(("New " + vertexName), buttonSkin, "maroon");
        newVertex.setHeight(Gdx.graphics.getHeight() * (0.1f));
        newVertex.setWidth(Gdx.graphics.getWidth() * (0.08f));
        newVertex.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newVertex);

        newVertex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saved = false;

                stage.addActor(errorText);

            }
        });


        TextButton newEdge = new TextButton(("New " + edgeName), buttonSkin, "maroon");
        newEdge.setHeight(Gdx.graphics.getHeight() * (0.1f));
        newEdge.setWidth(Gdx.graphics.getWidth() * (0.08f));
        newEdge.setPosition((Gdx.graphics.getWidth() * (0.105f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newEdge);

        newEdge.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saved = false;

                stage.addActor(errorText);

            }
        });










        TextButton saveButton = new TextButton(("Save"), buttonSkin, "maroon");
        saveButton.setHeight(Gdx.graphics.getHeight() * (0.1f));
        saveButton.setWidth(Gdx.graphics.getWidth() * (0.08f));
        saveButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.16f)));
        stage.addActor(saveButton);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saved = true;

            }
        });


        TextButton saveAsButton = new TextButton(("Save As"), buttonSkin, "maroon");
        saveAsButton.setHeight(Gdx.graphics.getHeight() * (0.1f));
        saveAsButton.setWidth(Gdx.graphics.getWidth() * (0.08f));
        saveAsButton.setPosition((Gdx.graphics.getWidth() * (0.105f)), (Gdx.graphics.getHeight() * (0.16f)));
        stage.addActor(saveAsButton);

        saveAsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                saved = true;

            }
        });




        TextButton mainMenu = new TextButton(("Main Menu"), buttonSkin, "maroon");
        mainMenu.setHeight(Gdx.graphics.getHeight() * (0.1f));
        mainMenu.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        mainMenu.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.04f)));
        stage.addActor(mainMenu);

        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (saved){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    errorText.remove();
                }
                else{
                    saveBox.setVisible(true);
                }


            }


        });

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        stage.act();
        stage.draw();




        drawSaveBackground();




    }


    private void drawSaveBackground() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.6f,0.6f,0.6f,0.3f);
        sr.rect(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }           // Draw the save background










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
        sr.dispose();
    }
}