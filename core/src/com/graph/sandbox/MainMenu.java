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

    String[] defaultConfigArray = {"1600 x 900", "windowed", "vertex", "edge"};



    public MainMenu() {




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


            }
        });
        settingsBox.getTitleTable().add(settingsClose).height(Value.percentHeight(.05f, settingsBox)).width(Value.percentWidth(.05f, settingsBox));
        settingsBox.align(Align.top | Align.right);

        TextButton Settings = new TextButton("Settings", buttonSkin, "maroon");
        Settings.setHeight(Gdx.graphics.getHeight() * (0.1f));
        Settings.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Settings.setPosition((Gdx.graphics.getWidth() * (0.125f) - Gdx.graphics.getWidth() * (0.1125f)), (Gdx.graphics.getHeight() * (0.60f)));


        {//--------------------------------------------------------------





        settingsBox.align(Align.top | Align.center);


        Label resLabel = new Label("Resolution:", buttonSkin);
        resLabel.setFontScaleX(1.25f);
        resLabel.setFontScaleY(1.25f);

        final SelectBox<String> resPicker = new SelectBox<>(buttonSkin);
        resPicker.setItems("2560 x 1440","1920 x 1080","1600 x 900","1366 x 768","1280 x 720","960 x 540","720 x 480");
        resPicker.setMaxListCount(5);


        settingsBox.add(resLabel).padTop(Value.percentHeight(0.075f, settingsBox)).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);
        settingsBox.add(resPicker).padTop(Value.percentHeight(0.075f, settingsBox)).padLeft(Value.percentWidth(0.125f, settingsBox)).height(Value.percentHeight(0.09f, settingsBox)).width(resPicker.getPrefWidth()*(1.2f)).colspan(2);


        settingsBox.row();


        Label fullscreenLabel = new Label("Fullscreen:",buttonSkin);
        fullscreenLabel.setFontScaleX(1.25f);
        fullscreenLabel.setFontScaleY(1.25f);
        settingsBox.add(fullscreenLabel).padTop(Value.percentHeight(0.075f, settingsBox)).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);

        final CheckBox fullscreenButton = new CheckBox("",buttonSkin,"switch-text");
        settingsBox.add(fullscreenButton).padTop(Value.percentHeight(0.075f, settingsBox)).padLeft(Value.percentWidth(0.125f, settingsBox)).colspan(2);

        fullscreenButton.setChecked(Objects.equals(configArray[1], "fullscreen"));


        settingsBox.row();


        Label termLabel = new Label("Preferred Terms:", buttonSkin);
        termLabel.setFontScaleX(1.25f);
        termLabel.setFontScaleY(1.25f);
        settingsBox.add(termLabel).padTop(Value.percentHeight(0.075f, settingsBox)).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);


        final CheckBox prefNameVertex = new CheckBox("  Vertex", buttonSkin);
        settingsBox.add(prefNameVertex).padTop(Value.percentHeight(0.075f, settingsBox));

        final CheckBox prefNameNode = new CheckBox("  Node", buttonSkin);
        settingsBox.add(prefNameNode).padTop(Value.percentHeight(0.075f, settingsBox)).padLeft(Value.percentWidth(-0.125f, settingsBox)).colspan(2);


        settingsBox.row();

        settingsBox.add();


        final CheckBox prefNameEdge = new CheckBox("  Edge", buttonSkin);
        settingsBox.add(prefNameEdge).padTop(Value.percentHeight(0.015f, settingsBox)).padLeft(Value.percentWidth(0.225f, settingsBox)).colspan(2);

        final CheckBox prefNameArc = new CheckBox("  Arc", buttonSkin);
        settingsBox.add(prefNameArc).padTop(Value.percentHeight(0.015f, settingsBox)).padLeft(Value.percentWidth(-0.139f, settingsBox)).colspan(2);



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


        prefNameEdge.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefNameEdge.setChecked(true);
                prefNameArc.setChecked(false);
                prefNameEdge.setDisabled(true);
                prefNameArc.setDisabled(false);

            }
        });

        prefNameArc.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefNameArc.setChecked(true);
                prefNameEdge.setChecked(false);
                prefNameArc.setDisabled(true);
                prefNameEdge.setDisabled(false);
            }
        });



        settingsBox.row().size(Value.percentHeight(0.25f, settingsBox));

        settingsBox.add();

        settingsBox.row();






            TextButton cancelButton = new TextButton("Cancel",buttonSkin);
            cancelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    settingsOpen = false;
                    settingsBox.setVisible(false);
                }
            });

            settingsBox.add(cancelButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.275f, settingsBox));






            TextButton defaultButton = new TextButton("Reset To Default",buttonSkin);
            defaultButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    resPicker.setSelected(defaultConfigArray[0]);

                    fullscreenButton.setChecked(Objects.equals(defaultConfigArray[1], "fullscreen"));

                    if (Objects.equals(defaultConfigArray[2], "vertex")) {
                        prefNameVertex.setChecked(true);
                        prefNameNode.setChecked(false);
                    } else {
                        prefNameNode.setChecked(true);
                        prefNameVertex.setChecked(false);
                    }

                    if (Objects.equals(defaultConfigArray[3], "edge")) {
                        prefNameEdge.setChecked(true);
                        prefNameArc.setChecked(false);
                    } else {
                        prefNameEdge.setChecked(false);
                        prefNameArc.setChecked(true);
                    }

                }


            });

            settingsBox.add(defaultButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.275f, settingsBox));






            TextButton applyButton = new TextButton("Apply",buttonSkin);
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                configArray[0] = resPicker.getSelected();

                if(fullscreenButton.isChecked()){
                    configArray[1] = "fullscreen";
                }
                else{
                    configArray[1] = "windowed";
                }

                if(prefNameVertex.isChecked()){
                    configArray[2] = "vertex";
                }
                else{
                    configArray[2] = "node";
                }

                if(prefNameEdge.isChecked()){
                    configArray[3] = "edge";
                }
                else{
                    configArray[3] = "arc";
                }

                configFile.writeString(  configArray[0]  +  "\n"  +  configArray[1]  +  "\n"  +  configArray[2]  +  "\n"  +  configArray[3], false);     //  add more configArray[]

            }
        });
        settingsBox.add(applyButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.275f, settingsBox));










            Settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (settingsOpen) {
                    settingsOpen = false;
                    settingsBox.setVisible(false);
                    settingsBox.setPosition((Gdx.graphics.getWidth() * (0.35f)), (Gdx.graphics.getHeight() * (0.2f)));
                } else {
                    resPicker.setSelected(configArray[0]);

                    fullscreenButton.setChecked(Objects.equals(configArray[1], "fullscreen"));



                    if(Objects.equals(configArray[2], "vertex")){
                        prefNameVertex.setChecked(true);
                        prefNameNode.setChecked(false);
                    }
                    else{
                        prefNameNode.setChecked(true);
                        prefNameVertex.setChecked(false);
                    }

                    if(Objects.equals(configArray[3], "edge")){
                        prefNameEdge.setChecked(true);
                        prefNameArc.setChecked(false);
                    }
                    else{
                        prefNameArc.setChecked(true);
                        prefNameEdge.setChecked(false);
                    }



                    settingsOpen = true;
                    settingsBox.setVisible(true);




                }

            }
        });





        stage.addActor(Settings);

//----------------------------------------------------------------------
}    // settings menu stuff






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