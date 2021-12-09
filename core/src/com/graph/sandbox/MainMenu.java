package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Arrays;
import java.util.Objects;


public class MainMenu implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();
    private boolean settingsOpen = false;
    private boolean openGraphOpen = false;
    private final Window settingsBox;
    private final Window openGraphBox;

    final FileHandle configFile = Gdx.files.local("core/assets/config.txt");
    String text = configFile.readString();
    String[] configArray = text.split("\\r?\\n");


    public MainMenu() {
        System.out.println(Arrays.toString(configArray));



        //Image background = new Image(new Texture(Gdx.files.internal("GTIS TTIEL.png")));
        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));


        final Label errorText = new Label("*Sorry, this feature has not yet been implemented", buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth() * (0.25f) + 10, 5);

        Label versionName = new Label("Oliver Smith | V1.0", buttonSkin, "peach");
        versionName.setPosition(Gdx.graphics.getWidth() * (0.905f), 3);
        stage.addActor(versionName);

        Image title = new Image(new Texture(Gdx.files.internal("GTISTitle.png")));
        title.setHeight(Gdx.graphics.getHeight() * (0.075f));
        title.setWidth(Gdx.graphics.getHeight() * (0.075f) * (14));//
        title.setPosition(Gdx.graphics.getWidth() * (0.27f), (Gdx.graphics.getHeight() * (0.92f)));
        stage.addActor(title);


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.25f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);



        openGraphBox = new Window("Open Graph", buttonSkin, "maroon");                                                                           //Open Graph Button
        openGraphBox.setHeight(Gdx.graphics.getHeight() * (0.6f));
        openGraphBox.setWidth(Gdx.graphics.getWidth() * (0.5f));
        openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));
        stage.addActor(openGraphBox);
        openGraphBox.setVisible(false);


        TextButton openGraphClose = new TextButton("X", buttonSkin, "maroonX");
        openGraphClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openGraphBox.setVisible(false);
                openGraphOpen = false;
                openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));


            }
        });
        openGraphBox.getTitleTable().add(openGraphClose).height(Value.percentHeight(.05f, openGraphBox)).width(Value.percentWidth(.05f, openGraphBox));
        openGraphBox.align(Align.top | Align.right);

        TextButton openGraph = new TextButton("Open Graph", buttonSkin, "maroon");
        openGraph.setHeight(Gdx.graphics.getHeight() * (0.1f));
        openGraph.setWidth(Gdx.graphics.getWidth() * (0.2f));
        openGraph.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.72f)));

        openGraph.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (openGraphOpen) {
                    openGraphOpen = false;
                    openGraphBox.setVisible(false);
                    openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));
                } else {
                    openGraphOpen = true;
                    openGraphBox.setVisible(true);
                }

            }
        });
        stage.addActor(openGraph);



//--------------------------------------------------------------









        settingsBox= new Window("Settings", buttonSkin);                                                                                                                 //Settings Button
        settingsBox.setHeight(Gdx.graphics.getHeight() * (0.5f));
        settingsBox.setWidth(Gdx.graphics.getWidth() * (0.4f));
        settingsBox.setPosition((Gdx.graphics.getWidth() * (0.35f)), (Gdx.graphics.getHeight() * (0.2f)));
        settingsBox.keepWithinStage();
        stage.addActor(settingsBox);
        settingsBox.setVisible(false);
        settingsBox.getTitleTable().align(Align.top | Align.right);

        TextButton settingsClose = new TextButton("X", buttonSkin, "orange-smallX");
        settingsClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsBox.setVisible(false);
                settingsOpen = false;
                settingsBox.setPosition((Gdx.graphics.getWidth() * (0.35f)), (Gdx.graphics.getHeight() * (0.2f)));

            }
        });
        settingsBox.getTitleTable().add(settingsClose).height(Value.percentHeight(.05f, settingsBox)).width(Value.percentWidth(.05f, settingsBox));
        settingsBox.align(Align.top | Align.right);

        TextButton Settings = new TextButton("Settings", buttonSkin, "maroon");
        Settings.setHeight(Gdx.graphics.getHeight() * (0.1f));
        Settings.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Settings.setPosition((Gdx.graphics.getWidth() * (0.125f) - Gdx.graphics.getWidth() * (0.1125f)), (Gdx.graphics.getHeight() * (0.60f)));





// fullscreen;  preferred names;  Default settings;




        Label resLabel = new Label("Resolution:", buttonSkin);
        resLabel.setFontScaleX(1.25f);
        resLabel.setFontScaleY(1.25f);

        final SelectBox<String> resPicker = new SelectBox<>(buttonSkin);
        resPicker.setItems("2560 x 1440","1920 x 1080","1600 x 900","1366 x 768","1280 x 720","960 x 540","720 x 480");
        resPicker.setMaxListCount(5);



        settingsBox.align(Align.top | Align.center);
        settingsBox.add(resLabel).padTop(Value.percentHeight(0.075f, settingsBox)).padRight(Value.percentHeight(0.25f, settingsBox));
        settingsBox.add(resPicker).padTop(Value.percentHeight(0.075f, settingsBox)).padLeft(Value.percentHeight(0.25f, settingsBox)).height(Value.percentHeight(0.09f, settingsBox)).width(resPicker.getPrefWidth()*(1.2f));  //.padRight(Value.percentHeight(0.1f, settingsBox)).padTop(Value.percentHeight(0.075f, settingsBox));



        settingsBox.row();

        //fullscreen



        settingsBox.row();

        Label termLabel = new Label("Preferred Terms:", buttonSkin);
        termLabel.setFontScaleX(1.25f);
        termLabel.setFontScaleY(1.25f);
        settingsBox.add(termLabel).padTop(Value.percentHeight(0.075f, settingsBox)).padRight(Value.percentHeight(0.125f, settingsBox));


        final CheckBox prefNameVertex = new CheckBox("  Vertex", buttonSkin);
        settingsBox.add(prefNameVertex).padTop(Value.percentHeight(0.075f, settingsBox));

        final CheckBox prefNameNode = new CheckBox("  Node", buttonSkin);
        settingsBox.add(prefNameNode).padTop(Value.percentHeight(0.075f, settingsBox)).padLeft(Value.percentHeight(-0.125f, settingsBox));









        if(Objects.equals(configArray[1], "vertex")){
            prefNameVertex.toggle();
        }
        else{
            prefNameNode.toggle();
        }


        prefNameVertex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefNameVertex.setChecked(true);
                prefNameNode.setChecked(false);
                prefNameVertex.setDisabled(true);
                prefNameNode.setDisabled(false);

            }
        });

        prefNameNode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefNameNode.setChecked(true);
                prefNameVertex.setChecked(false);
                prefNameNode.setDisabled(true);
                prefNameVertex.setDisabled(false);
            }
        });










        settingsBox.row();



        TextButton applyButton = new TextButton("Apply",buttonSkin,"maroon");
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                configArray[0] = resPicker.getSelected();

                if(prefNameVertex.isChecked()){
                    configArray[1] = "vertex";
                }
                else{
                    configArray[1] = "node";
                }

                configFile.writeString(  configArray[0]+  "\n"  +  configArray[1], false);     //  add more configArray[]

            }
        });
        settingsBox.add(applyButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.3f, settingsBox));











        Settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (settingsOpen) {
                    settingsOpen = false;
                    settingsBox.setVisible(false);
                    settingsBox.setPosition((Gdx.graphics.getWidth() * (0.35f)), (Gdx.graphics.getHeight() * (0.2f)));
                } else {
                    resPicker.setSelected(configArray[0]);
                    settingsOpen = true;
                    settingsBox.setVisible(true);


                }

            }
        });





        stage.addActor(Settings);

//----------------------------------------------------------------------






        TextButton newGraph = new TextButton("New Graph", buttonSkin, "maroon");                                                                              //New Graph Button
        newGraph.setHeight(Gdx.graphics.getHeight() * (0.12f));
        newGraph.setWidth(Gdx.graphics.getWidth() * (0.225f));
        newGraph.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.84f)));

        newGraph.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Sandbox());
                errorText.setVisible(false);
            }
        });
        stage.addActor(newGraph);


        TextButton exitButton = new TextButton("Exit", buttonSkin, "maroon");                                                                               //Exit Button
        exitButton.setHeight(Gdx.graphics.getHeight() * (0.12f));
        exitButton.setWidth(Gdx.graphics.getWidth() * (0.225f));
        exitButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.04f)));

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

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
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (settingsBox.getX() <= Gdx.graphics.getWidth() * (0.25f)-1) {
            settingsBox.setX(Gdx.graphics.getWidth() * (0.25f)-1);
        }

        if (openGraphBox.getX() <= Gdx.graphics.getWidth() * (0.25f)-1) {
            openGraphBox.setX(Gdx.graphics.getWidth() * (0.25f)-1);
        }

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