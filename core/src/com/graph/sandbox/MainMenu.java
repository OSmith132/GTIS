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


public class MainMenu implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();
    boolean settingsOpen = false;
    boolean openGraphOpen = false;



    public MainMenu() {
        //Image background = new Image(new Texture(Gdx.files.internal("GTIS TTIEL.png")));
        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));



        final Label errorText = new Label("*Sorry, this feature has not yet been implemented",buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth()*(0.25f)+10, 5);

        Label versionName = new Label("Oliver Smith | V1.0",buttonSkin, "peach");
        versionName.setPosition(Gdx.graphics.getWidth()*(0.905f), 3);
        stage.addActor(versionName);

        Image title = new Image(new Texture(Gdx.files.internal("GTISTitle.png")));
        title.setHeight(Gdx.graphics.getHeight()*(0.075f));
        title.setWidth(Gdx.graphics.getHeight()*(0.075f)*(14));//
        title.setPosition(Gdx.graphics.getWidth()*(0.27f), (Gdx.graphics.getHeight() * (0.92f)));
        stage.addActor(title);



        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.25f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);



       /* final Image settingsBox = new Image(new Texture(Gdx.files.internal("settings box.png")));
        settingsBox.setHeight(Gdx.graphics.getHeight()*(2/3f));
        settingsBox.setWidth(Gdx.graphics.getWidth() * (0.5f));
        settingsBox.setPosition((Gdx.graphics.getWidth() * (0.25f)), (Gdx.graphics.getHeight() * (0.25f)));*/







        final Window settingsBox = new Window("Settings", buttonSkin);
        settingsBox.setHeight(Gdx.graphics.getHeight()*(0.6f));
        settingsBox.setWidth(Gdx.graphics.getWidth() * (0.5f));
        settingsBox.setPosition((Gdx.graphics.getWidth() * (0.35f)), (Gdx.graphics.getHeight() * (0.2f)));
        stage.addActor(settingsBox);
        settingsBox.setVisible(false);
        settingsBox.getTitleTable().align(Align.top|Align.right);

        TextButton settingsClose = new TextButton("X", buttonSkin,"orange-smallX");
        settingsClose.setHeight(Gdx.graphics.getHeight()*(1f));
        settingsClose.setWidth(Gdx.graphics.getWidth() * (1f));
        settingsClose.addListener (new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsBox.setVisible(false);
                settingsOpen = false;
                settingsBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));

            }
        });
        settingsBox.getTitleTable().add(settingsClose).height(Value.percentHeight(.05f, settingsBox)).width(Value.percentWidth(.05f, settingsBox));
        settingsBox.align(Align.top|Align.right);








        final Window openGraphBox = new Window("Open Graph", buttonSkin,"maroon");
        openGraphBox.setHeight(Gdx.graphics.getHeight()*(0.6f));
        openGraphBox.setWidth(Gdx.graphics.getWidth() * (0.5f));
        openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));
        stage.addActor(openGraphBox);
        openGraphBox.setVisible(false);
        openGraphBox.getTitleTable().align(Align.top|Align.right);

        TextButton openGraphClose = new TextButton("X", buttonSkin,"maroonX");
        openGraphClose.setHeight(Gdx.graphics.getHeight()*(1f));
        openGraphClose.setWidth(Gdx.graphics.getWidth() * (1f));
        openGraphClose.addListener (new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openGraphBox.setVisible(false);
                openGraphOpen = false;
                openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));

            }
        });
        openGraphBox.getTitleTable().add(openGraphClose).height(Value.percentHeight(.05f, openGraphBox)).width(Value.percentWidth(.05f, openGraphBox));
        openGraphBox.align(Align.top|Align.right);








        TextButton newGraph = new TextButton("New Graph", buttonSkin, "maroon");
        newGraph.setHeight(Gdx.graphics.getHeight() * (0.12f));
        newGraph.setWidth(Gdx.graphics.getWidth() * (0.225f));
        newGraph.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.84f)));

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
        openGraph.setPosition((Gdx.graphics.getWidth() * (0.0125f)),(Gdx.graphics.getHeight() * (0.72f)));

        openGraph.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (openGraphOpen){
                    openGraphOpen = false;
                    openGraphBox.setVisible(false);
                    openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));
                }

                else{
                    openGraphOpen = true;
                    openGraphBox.setVisible(true);
                }

            }
        });
        stage.addActor(openGraph);



        TextButton Settings = new TextButton("Settings", buttonSkin, "maroon");
        Settings.setHeight(Gdx.graphics.getHeight()*(0.1f));
        Settings.setWidth(Gdx.graphics.getWidth()*(0.2f));
        Settings.setPosition((Gdx.graphics.getWidth()*(0.125f)-Gdx.graphics.getWidth()*(0.1125f)),(Gdx.graphics.getHeight() * (0.60f)));

        Settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if (settingsOpen){
                    settingsOpen = false;
                    settingsBox.setVisible(false);
                    settingsBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));
                }

                else{
                    settingsOpen = true;
                    settingsBox.setVisible(true);
                }


            }
        });
        stage.addActor(Settings);




        TextButton exitButton = new TextButton("Exit", buttonSkin, "maroon");
        exitButton.setHeight(Gdx.graphics.getHeight()*(0.12f));
        exitButton.setWidth(Gdx.graphics.getWidth()*(0.225f));
        exitButton.setPosition((Gdx.graphics.getWidth()*(0.0125f)),(Gdx.graphics.getHeight() * (0.04f)));

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
