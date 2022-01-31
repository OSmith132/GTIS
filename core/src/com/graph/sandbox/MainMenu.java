package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class MainMenu implements Screen {
    private final Stage stage = new Stage();
    private boolean settingsOpen = false;
    private boolean openGraphOpen = false;
    private final Window settingsBox;
    private final Window openGraphBox;
    private  boolean settingsChanged;

    String[] widthHeightArray;

    FileHandle configFile = Gdx.files.local("core/assets/config.txt");
    String text = configFile.readString();
    String[] configArray = text.split("\\r?\\n");

    String[] defaultConfigArray = {"1600 x 900", "windowed", "vertex", "edge", "medium"};


// code stacks and queues for pp6

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


        TextButton newGraph = new TextButton("New Graph", buttonSkin, "maroon");                                                                              //New Graph Button
        newGraph.setHeight(Gdx.graphics.getHeight() * (0.12f));
        newGraph.setWidth(Gdx.graphics.getWidth() * (0.225f));
        newGraph.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.84f)));

        newGraph.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game) Gdx.app.getApplicationListener()).setScreen(new Sandbox("New_graph",true));

            }
        });
        stage.addActor(newGraph);


































        openGraphBox = new Window("Open Graph", buttonSkin, "maroon");                                                                           //Open Graph Button
        openGraphBox.setHeight(Gdx.graphics.getHeight() * (0.6f));
        openGraphBox.setWidth(Gdx.graphics.getWidth() * (0.5f));
        openGraphBox.setPosition((Gdx.graphics.getWidth() * (0.3f)), (Gdx.graphics.getHeight() * (0.25f)));
        openGraphBox.setVisible(false);
        stage.addActor(openGraphBox);


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
        openGraphBox.align(Align.top | Align.center);








        File folder = new File("core/assets/Saved Graphs");
        final File[] listOfFiles = folder.listFiles();


        final ArrayList<String> listOfFileNames = new ArrayList<>();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile() && listOfFile.getName().endsWith(".graph"))
                listOfFileNames.add(listOfFile.getName());
        }






        final List<Object> openGraphList = new List<>(buttonSkin, "dimmed");
        final ScrollPane openGraphActualList = new ScrollPane(openGraphList, buttonSkin,"android");
        openGraphBox.add(openGraphActualList).height(Value.percentHeight(.75f, openGraphBox)).width(Value.percentWidth(.6f, openGraphBox)).padTop(Value.percentWidth(.025f, openGraphBox)).colspan(3);
        openGraphList.setTypeToSelect(true);


        openGraphList.setItems(listOfFileNames.toArray());      // NOTE: Could make it so that I can select different pages in the list using a list.size()










        openGraphBox.row();




        final Window deleteFileBox = new Window("Are you sure you want to delete this file?",buttonSkin);
        deleteFileBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        deleteFileBox.setWidth(Gdx.graphics.getWidth() * (0.25f));
        deleteFileBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        deleteFileBox.setModal(true);
        deleteFileBox.setMovable(false);
        deleteFileBox.getTitleLabel().setAlignment(1);
        deleteFileBox.align(Align.center);
        deleteFileBox.setVisible(false);

        stage.addActor(deleteFileBox);








        TextButton deleteFileNo = new TextButton(("Don't delete"), buttonSkin);
        deleteFileBox.add(deleteFileNo).height(Value.percentHeight(0.35f, deleteFileBox)).width(Value.percentWidth(0.3f, deleteFileBox)).pad(Value.percentWidth(0.01f,deleteFileBox));

        deleteFileNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                deleteFileBox.setVisible(false);
            }
        });

        TextButton deleteFileYes = new TextButton(("Delete"), buttonSkin);
        deleteFileBox.add(deleteFileYes).height(Value.percentHeight(0.35f, deleteFileBox)).width(Value.percentWidth(0.3f, deleteFileBox)).pad(Value.percentWidth(0.01f,deleteFileBox));

        deleteFileYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                Gdx.files.local(("core/assets/Saved Graphs/" + openGraphList.getSelected())).delete();


                listOfFileNames.clear();
                for (File listOfFile : listOfFiles) {

                    if (listOfFile.isFile() && listOfFile.getName().endsWith(".graph"))
                        listOfFileNames.add(listOfFile.getName());
                }

                openGraphList.setItems(listOfFileNames.toArray());


                deleteFileBox.setVisible(false);

            }
        });




        TextButton deleteGraphButton = new TextButton("Delete", buttonSkin,"maroon");
        openGraphBox.add(deleteGraphButton).height(Value.percentHeight(.06f, openGraphBox)).width(Value.percentWidth(.2f, openGraphBox)).pad(Value.percentWidth(.025f, openGraphBox));

        deleteGraphButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {



                deleteFileBox.setVisible(true);
                deleteFileBox.toFront();
            }
        });





        TextButton editGraphButton = new TextButton("Edit", buttonSkin,"maroon");
        openGraphBox.add(editGraphButton).height(Value.percentHeight(.06f, openGraphBox)).width(Value.percentWidth(.2f, openGraphBox)).pad(Value.percentWidth(.025f, openGraphBox));

        editGraphButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (listOfFileNames.size() > 0) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Sandbox((String) openGraphList.getSelected(),false));
                }



            }
        });





        TextButton openGraphButton = new TextButton("Open", buttonSkin,"maroon");
        openGraphBox.add(openGraphButton).height(Value.percentHeight(.06f, openGraphBox)).width(Value.percentWidth(.2f, openGraphBox)).pad(Value.percentWidth(.025f, openGraphBox));

        openGraphButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Here Goes the code for opening a file
                errorText.setVisible(true);
            }
        });













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











































        //settingsBox Initialized above^
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

         float windowTopPadding = (Gdx.graphics.getHeight() * (0.03f));

        settingsBox.align(Align.top | Align.center);


        Label resLabel = new Label("Resolution:", buttonSkin);
        resLabel.setFontScaleX(Gdx.graphics.getHeight() / 600f);
        resLabel.setFontScaleY(Gdx.graphics.getHeight() / 600f);

        final SelectBox<String> resPicker = new SelectBox<>(buttonSkin);
        resPicker.setItems("2560 x 1440","1920 x 1080","1600 x 900","1366 x 768","1280 x 720","960 x 540","720 x 480");
        resPicker.setMaxListCount(5);

            resPicker.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    settingsChanged = true;
                }
            });


        settingsBox.add(resLabel).padTop(windowTopPadding).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);
        settingsBox.add(resPicker).padTop(windowTopPadding).height(Gdx.graphics.getHeight() * (0.045f)).width(Gdx.graphics.getWidth() * (0.1f)).colspan(2);


        settingsBox.row();



        Label fullscreenLabel = new Label("Fullscreen:",buttonSkin);
        fullscreenLabel.setFontScaleX(Gdx.graphics.getHeight() / 600f);
        fullscreenLabel.setFontScaleY(Gdx.graphics.getHeight() / 600f);
        settingsBox.add(fullscreenLabel).padTop(windowTopPadding).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);


        final CheckBox fullscreenButton = new CheckBox("",buttonSkin,"switch-text");

        settingsBox.add(fullscreenButton).padTop(windowTopPadding).colspan(2);

        fullscreenButton.setChecked(Objects.equals(configArray[1], "fullscreen"));

            fullscreenButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   settingsChanged = true;
                }
            });

        settingsBox.row();









        Label termLabel = new Label("Preferred Terms:", buttonSkin);
        termLabel.setFontScaleX(Gdx.graphics.getHeight() / 600f);
        termLabel.setFontScaleY(Gdx.graphics.getHeight() / 600f);
        settingsBox.add(termLabel).padTop(windowTopPadding).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);



        final CheckBox prefNameVertex = new CheckBox("  Vertex", buttonSkin);
        settingsBox.add(prefNameVertex).padTop(windowTopPadding).left();

        final CheckBox prefNameNode = new CheckBox("  Node", buttonSkin);
        settingsBox.add(prefNameNode).padTop(windowTopPadding).left().padLeft(Value.percentWidth(-0.15f, settingsBox));




        settingsBox.row();




        Label invislabel = new Label("", buttonSkin);
        settingsBox.add(invislabel).padTop(Value.percentHeight(0.015f, settingsBox)).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);




        final CheckBox prefNameEdge = new CheckBox("  Edge", buttonSkin);
        settingsBox.add(prefNameEdge).padTop(Value.percentHeight(0.015f, settingsBox)).left();

        final CheckBox prefNameArc = new CheckBox("  Arc", buttonSkin);
        settingsBox.add(prefNameArc).padTop(Value.percentHeight(0.015f, settingsBox)).left().padLeft(Value.percentWidth(-0.15f, settingsBox));











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


        settingsBox.row();




        final Label vertexSizeLabel = new Label("Vertex Size:   " + configArray[4], buttonSkin);
        vertexSizeLabel.setFontScaleX(Gdx.graphics.getHeight() / 600f);
        vertexSizeLabel.setFontScaleY(Gdx.graphics.getHeight() / 600f);
        settingsBox.add(vertexSizeLabel).padTop(windowTopPadding).padRight(Value.percentWidth(0.125f, settingsBox)).colspan(2);

        final Slider vertexSizeSlider = new Slider(0,2,1,false,buttonSkin);

        if (Objects.equals(configArray[4], "small")){
            vertexSizeSlider.setValue(0);
        }
        else if (Objects.equals(configArray[4], "medium")){
            vertexSizeSlider.setValue(1);
        }
        else{
            vertexSizeSlider.setValue(2);
        }

        settingsBox.add(vertexSizeSlider).padTop(windowTopPadding).padRight(Value.percentWidth(0.075f, settingsBox)).height(Gdx.graphics.getHeight() * (0.045f)).width(Gdx.graphics.getWidth() * (0.1f)).colspan(2);



            vertexSizeSlider.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (vertexSizeSlider.getValue() == 0){
                        vertexSizeLabel.setText("Vertex Size:  small");
                    }
                    else if (vertexSizeSlider.getValue() == 1){
                        vertexSizeLabel.setText("Vertex Size:   medium");
                    }
                    else{
                        vertexSizeLabel.setText("Vertex Size:   large");
                    }


                }
                                         });




        settingsBox.row().size(Value.percentHeight(0.25f, settingsBox));










            TextButton cancelButton = new TextButton("Cancel",buttonSkin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsOpen = false;
                settingsBox.setVisible(false);
            }
        });
        settingsBox.add(cancelButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.275f, settingsBox)).bottom().padBottom(Value.percentHeight(0.01f, settingsBox)).expand();







        TextButton defaultButton = new TextButton("Reset To Default",buttonSkin);
        defaultButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resPicker.setSelected(defaultConfigArray[0]);

                settingsChanged = true;

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

        settingsBox.add(defaultButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.275f, settingsBox)).bottom().padBottom(Value.percentHeight(0.01f, settingsBox)).expand();











            TextButton applyButton = new TextButton("Apply",buttonSkin);
        applyButton.addListener(new ClickListener() {
            @Override

            public void clicked(InputEvent event, float x, float y) {


                configArray[0] = resPicker.getSelected();



                if(fullscreenButton.isChecked() && settingsChanged){
                    configArray[1] = "fullscreen";

                }
                else if (!fullscreenButton.isChecked() && settingsChanged){
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



                if (vertexSizeSlider.getValue() == 0){
                    configArray[4] = "small";
                    vertexSizeLabel.setText("Vertex Size:  small");
                }
                else if (vertexSizeSlider.getValue() == 1){
                    configArray[4] = "medium";
                    vertexSizeLabel.setText("Vertex Size:   medium");
                }
                else{
                    configArray[4] = "large";
                    vertexSizeLabel.setText("Vertex Size:   large");
                }




                configFile.writeString(  configArray[0]  +  "\n"  +  configArray[1]  +  "\n"  +  configArray[2]  +  "\n"  +  configArray[3] +  "\n"  +  configArray[4],false);     //  add more configArray[]


                widthHeightArray = configArray[0].split(" ");


                if(fullscreenButton.isChecked() && settingsChanged){
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                }
                else if (!fullscreenButton.isChecked() && settingsChanged){
                    Gdx.graphics.setWindowedMode(Integer.parseInt(widthHeightArray[0]),Integer.parseInt(widthHeightArray[2]));
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                }




            }
        });
        settingsBox.add(applyButton).height(Value.percentHeight(0.125f, settingsBox)).width(Value.percentWidth(0.275f, settingsBox)).bottom().padBottom(Value.percentHeight(0.01f, settingsBox)).expand();










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


//        settingsBox.debug();






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