package com.graph.sandbox;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


import java.util.ArrayList;
import java.util.Objects;


public class Sandbox implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
    BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("whiteFont.fnt"));
    GlyphLayout layout = new GlyphLayout();


    private boolean newVertexClicked = false;
    private boolean newEdgeClicked = false;

    private int lastVertexClicked = -1;
    private boolean firstVertexClicked = false;
    private boolean allowVertexMove = true;

    private boolean saved = true;
    private boolean modalBoxVisible;

    private boolean graphIsDigraph;
    private boolean firstTimeSave;


    ArrayList<Integer> vertexCoordsX = new ArrayList<>();
    ArrayList<Integer> vertexCoordsY = new ArrayList<>();

    ArrayList<Integer> edgeListFrom = new ArrayList<>();
    ArrayList<Integer> edgeListTo = new ArrayList<>();
    ArrayList<Float> edgeWeightList = new ArrayList<>();


    final FileHandle configFile = Gdx.files.local("core/assets/config.txt");
    String text = configFile.readString();
    String[] configArray = text.split("\\r?\\n");

    Integer resolutionW = Integer.parseInt(configArray[0].split(" ")[0]);
    Integer resolutionH = Integer.parseInt(configArray[0].split(" ")[2]);
    String vertexName = configArray[2];  // Node or Vertex
    String edgeName = configArray[3];     // Arc or Edge
    float vertexSize;

    String currentGraphName;

    Image binOpen = new Image(new Texture(Gdx.files.internal("binOpen.png")));
    Image binClosed = new Image(new Texture(Gdx.files.internal("binClosed.png")));

    TextButton confirmEdgeWeight;
    final TextField edgeWeightInputField;
    final Window edgeWeightBox;

    Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
    final Label savedLabel = new Label("Graph Saved Successfully.", buttonSkin, "error");


//I made this long line for no reason other than to show Kamil this line when he asks what the longest line in my code is for the fourth time. It doesn't matter as his longest line will be longer than this one anyway; and probably by a fair margin.kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk


    private String existingFileNameChanger(String graph_name, int i) {

        FileHandle file = Gdx.files.local("core/assets/Saved Graphs/" + graph_name + ".graph");

        if (file.exists()) {



            System.out.println((int)Math.floor((i/10f)));
            if ((graph_name.charAt(graph_name.length() - 1)) == ')') {

                String[] parts = graph_name.split("\\(" );
                String[] intPart = parts[parts.length-1].split("\\)");
                System.out.println(intPart[0]);
                System.out.println(Integer.parseInt(intPart[0]));
                graph_name = graph_name.substring(0, graph_name.length() -( 3 +    (int)Math.floor(       (Integer.parseInt(intPart[0]))/10f)        )        ) + "(" + i + ")";



            } else {
                graph_name += "(" + i + ")";
            }





            return (existingFileNameChanger(graph_name, i + 1));

        } else {
            return (graph_name);
        }
    }


    private void save(boolean graphIsDigraph, String graph_name, Boolean saveAs) {


        firstTimeSave = false;

        if (saveAs) {

            FileHandle file = Gdx.files.local("core/assets/Saved Graphs/" + existingFileNameChanger(graph_name, 1) + ".graph");
            currentGraphName = existingFileNameChanger(graph_name, 1);

            if (graphIsDigraph) {
                file.writeString("digraph\n", false);
            } else {
                file.writeString("graph\n", false);
            }


            for (Integer coordsX : vertexCoordsX) {
                file.writeString((((float) coordsX / resolutionW) * 1600) + " ", true);
            }
            file.writeString("\n", true);
            for (Integer coordsY : vertexCoordsY) {
                file.writeString((((float) coordsY / resolutionH) * 900) + " ", true);
            }
            file.writeString("\n", true);
            for (Integer listFrom : edgeListFrom) {
                file.writeString(listFrom + " ", true);
            }
            file.writeString("\n", true);
            for (Integer listTo : edgeListTo) {
                file.writeString(listTo + " ", true);
            }
            file.writeString("\n", true);
            for (Float Weights : edgeWeightList) {
                file.writeString(Weights + " ", true);
            }


        } else {

            FileHandle file = Gdx.files.local("core/assets/Saved Graphs/" + graph_name + ".graph");

            if (graphIsDigraph) {
                file.writeString("digraph\n", false);
            } else {
                file.writeString("graph\n", false);
            }


            for (Integer coordsX : vertexCoordsX) {
                file.writeString((((float) coordsX / resolutionW) * 1600) + " ", true);
            }
            file.writeString("\n", true);
            for (Integer coordsY : vertexCoordsY) {
                file.writeString((((float) coordsY / resolutionH) * 900) + " ", true);
            }
            file.writeString("\n", true);
            for (Integer listFrom : edgeListFrom) {
                file.writeString(listFrom + " ", true);
            }
            file.writeString("\n", true);
            for (Integer listTo : edgeListTo) {
                file.writeString(listTo + " ", true);
            }
            file.writeString("\n", true);
            for (Float Weights : edgeWeightList) {
                file.writeString(Weights + " ", true);
            }


        }

    }


    public Sandbox(final String graph_name, final Boolean graph_new) {
        //         "New Graph"         true / false


        firstTimeSave = graph_new;

        if (graph_new)
            currentGraphName = "New_Graph";

        else {

            saved = true;
            currentGraphName = graph_name.substring(0, graph_name.length() - 6);

            FileHandle graphFile = Gdx.files.local("core/assets/Saved Graphs/" + currentGraphName + ".graph");  //  maybe change back to graph_name
            String text = graphFile.readString();
            String[] graphFileArray = text.split("\\r?\\n");

            graphIsDigraph = Objects.equals(graphFileArray[0], "digraph");


            if (graphFileArray.length > 1) {


                String[] vertexXStringList = graphFileArray[1].split(" ");
                Float[] vertexXIntegerList = new Float[vertexXStringList.length];

                String[] vertexYStringList = graphFileArray[2].split(" ");
                Float[] vertexYIntegerList = new Float[vertexYStringList.length];

                for (int i = 0; i < vertexXStringList.length; i++) {

                    vertexXIntegerList[i] = (Float.parseFloat(vertexXStringList[i]) / 1600) * resolutionW;
                    vertexCoordsX.add(Math.round(vertexXIntegerList[i]));

                    System.out.println(vertexXIntegerList[i]);

                    vertexYIntegerList[i] = (Float.parseFloat(vertexYStringList[i]) / 900) * resolutionH;
                    vertexCoordsY.add(Math.round(vertexYIntegerList[i]));

                }


                if (graphFileArray.length > 3) {

                    String[] edgeFromStringList = graphFileArray[3].split(" ");
                    Integer[] edgeFromIntegerList = new Integer[edgeFromStringList.length];

                    String[] edgeToStringList = graphFileArray[4].split(" ");
                    Integer[] edgeToIntegerList = new Integer[edgeToStringList.length];

                    String[] edgeWeightStringList = graphFileArray[5].split(" ");
                    Float[] edgeWeightIntegerList = new Float[edgeWeightStringList.length];


                    for (int i = 0; i < edgeToStringList.length; i++) {

                        edgeToIntegerList[i] = Integer.parseInt(edgeToStringList[i]);
                        edgeListTo.add(edgeToIntegerList[i]);

                        edgeFromIntegerList[i] = Integer.parseInt(edgeFromStringList[i]);
                        edgeListFrom.add(edgeFromIntegerList[i]);

                        edgeWeightIntegerList[i] = Float.parseFloat(edgeWeightStringList[i]);
                        edgeWeightList.add(edgeWeightIntegerList[i]);

                    }


                }
            }
        }


        final Window graphTypeBox = new Window("Graph Type:", buttonSkin, "maroon");
        graphTypeBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        graphTypeBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        graphTypeBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        graphTypeBox.setModal(true);
        graphTypeBox.setMovable(false);
        graphTypeBox.getTitleLabel().setAlignment(1);
        graphTypeBox.setVisible(false);
        stage.addActor(graphTypeBox);


        if (Objects.equals(configArray[4], "small")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.0075f));
            font.getData().setScale(0.5f,0.5f);
            whiteFont.getData().setScale(0.5f,0.5f);
        } else if (Objects.equals(configArray[4], "medium")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.015f));
        } else {
            vertexSize = (Gdx.graphics.getWidth() * (0.025f));
            font.getData().setScale(5f/3f,5f/3f);
            whiteFont.getData().setScale(5f/3f,5f/3f);
        }


        final Label errorText = new Label("*Sorry, this feature has not yet been implemented", buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth() * (0.2f) + 10, 5);
        //errorText.getFontScaleX()


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);


        final Window clearAllBox = new Window("Are you sure you want to clear all?", buttonSkin, "maroon");
        clearAllBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        clearAllBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        clearAllBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        clearAllBox.setModal(true);
        clearAllBox.setMovable(false);
        clearAllBox.getTitleLabel().setAlignment(1);
        clearAllBox.setVisible(false);
        stage.addActor(clearAllBox);


        TextButton noClearButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        clearAllBox.add(noClearButton).height(Value.percentHeight(0.35f, clearAllBox)).width(Value.percentWidth(0.35f, clearAllBox)).pad(Value.percentWidth(0.01f, clearAllBox));
        noClearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                clearAllBox.setVisible(false);
                modalBoxVisible = false;

            }
        });


        TextButton clearButton = new TextButton(("Clear All"), buttonSkin, "maroon");
        clearAllBox.add(clearButton).height(Value.percentHeight(0.35f, clearAllBox)).width(Value.percentWidth(0.35f, clearAllBox)).pad(Value.percentWidth(0.01f, clearAllBox));
        clearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearAll();
                saved = false;
                clearAllBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        //Bin closed initialized above^
        binClosed.setScale(Gdx.graphics.getHeight() / (2700f));
        binClosed.setPosition(Gdx.graphics.getWidth() * (0.955f), Gdx.graphics.getHeight() * (0.01f));
        stage.addActor(binClosed);

        //Bin open initialized above^
        binOpen.setScale(Gdx.graphics.getHeight() / (2700f));
        binOpen.setPosition(Gdx.graphics.getWidth() * (0.955f), Gdx.graphics.getHeight() * (0.01f));
        binOpen.setVisible(false);
        stage.addActor(binOpen);

        binOpen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearAllBox.setVisible(true);
                modalBoxVisible = true;

            }
        });


        final TextButton newVertex = new TextButton(("New " + vertexName), buttonSkin, "maroon");                 //New Vertex
        newVertex.setHeight(Gdx.graphics.getHeight() * (0.1f));
        newVertex.setWidth(Gdx.graphics.getWidth() * (0.08f));
        newVertex.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newVertex);

        newVertex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                newVertexClicked = true;
                newEdgeClicked = false;


            }
        });

        final TextButton newEdge = new TextButton(("New " + edgeName), buttonSkin, "maroon");
        newEdge.setHeight(Gdx.graphics.getHeight() * (0.1f));
        newEdge.setWidth(Gdx.graphics.getWidth() * (0.08f));
        newEdge.setPosition((Gdx.graphics.getWidth() * (0.105f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newEdge);

        newEdge.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                newEdgeClicked = true;
                newVertexClicked = false;

            }
        });



        
        
        
        
        






        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        


        // savedLabel initialized above
        savedLabel.setPosition(Gdx.graphics.getWidth() * (0.2f) + 10, 5);
        stage.addActor(savedLabel);
        savedLabel.setVisible(false);


        final Window saveAsBox = new Window("File Name:", buttonSkin, "maroon");
        saveAsBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        saveAsBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        saveAsBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        saveAsBox.setModal(true);
        saveAsBox.setMovable(false);
        saveAsBox.getTitleLabel().setAlignment(1);
        saveAsBox.setVisible(false);
        stage.addActor(saveAsBox);


        final TextField nameInputField = new TextField(currentGraphName, buttonSkin);
        saveAsBox.add(nameInputField).height(Value.percentHeight(0.3f, saveAsBox)).width(Value.percentWidth(0.7f, saveAsBox)).colspan(2);


        saveAsBox.row();

        TextButton cancelNameButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        saveAsBox.add(cancelNameButton).height(Value.percentHeight(0.2f, saveAsBox)).width(Value.percentWidth(0.35f, saveAsBox)).pad(Value.percentWidth(0.025f, saveAsBox)).padTop(Value.percentWidth(0.05f, saveAsBox));
        cancelNameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                saveAsBox.setVisible(false);
                modalBoxVisible = false;
            }
        });

        TextButton ConfirmNameButton = new TextButton(("Ok"), buttonSkin, "maroon");
        saveAsBox.add(ConfirmNameButton).height(Value.percentHeight(0.2f, saveAsBox)).width(Value.percentWidth(0.35f, saveAsBox)).pad(Value.percentWidth(0.025f, saveAsBox)).padTop(Value.percentWidth(0.05f, saveAsBox));
        ConfirmNameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                saveAsBox.setVisible(false);
                modalBoxVisible = false;

                currentGraphName = nameInputField.getText();
                save(graphIsDigraph, currentGraphName, true);


            }
        });


        final TextButton saveButton = new TextButton(("Save"), buttonSkin, "maroon");
        saveButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        saveButton.setWidth(Gdx.graphics.getWidth() * (0.08f));
        saveButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.27f)));
        stage.addActor(saveButton);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (firstTimeSave) {
                    saveAsBox.setVisible(true);
                } else {
                    save(graphIsDigraph, currentGraphName, false);
                }

                saved = true;


            }
        });


        final TextButton saveAsButton = new TextButton(("Save As"), buttonSkin, "maroon");
        saveAsButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        saveAsButton.setWidth(Gdx.graphics.getWidth() * (0.08f));
        saveAsButton.setPosition((Gdx.graphics.getWidth() * (0.105f)), (Gdx.graphics.getHeight() * (0.27f)));
        stage.addActor(saveAsButton);

        saveAsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                saveAsBox.setVisible(true);

                saved = true;
                modalBoxVisible = true;

            }
        });


        final Window saveBox = new Window("Would you like to save and exit?", buttonSkin, "maroon");
        saveBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        saveBox.setWidth(Gdx.graphics.getWidth() * (0.25f));
        saveBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        saveBox.setModal(true);
        saveBox.setMovable(false);
        saveBox.getTitleLabel().setAlignment(1);


        TextButton cancelButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        saveBox.add(cancelButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.3f, saveBox)).pad(Value.percentWidth(0.01f, saveBox));
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        TextButton noButton = new TextButton(("Don't Save"), buttonSkin, "maroon");
        saveBox.add(noButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.3f, saveBox)).pad(Value.percentWidth(0.01f, saveBox));
        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });


        TextButton yesButton = new TextButton(("Save"), buttonSkin, "maroon");
        saveBox.add(yesButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.3f, saveBox)).pad(Value.percentWidth(0.01f, saveBox));
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (firstTimeSave) {
                    saveAsBox.setVisible(true);
                } else {
                    save(graphIsDigraph, currentGraphName, false);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                }


                saveBox.setVisible(false);


            }
        });


        saveBox.align(Align.center);
        stage.addActor(saveBox);
        saveBox.setVisible(false);









        final Window algorithmsBox = new Window("You will need to the save the graph first", buttonSkin, "maroon");
        algorithmsBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        algorithmsBox.setWidth(Gdx.graphics.getWidth() * (0.25f));
        algorithmsBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        algorithmsBox.setModal(true);
        algorithmsBox.setMovable(false);
        algorithmsBox.getTitleLabel().setAlignment(1);


        TextButton noExitButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        algorithmsBox.add(noExitButton).height(Value.percentHeight(0.35f, algorithmsBox)).width(Value.percentWidth(0.3f, algorithmsBox)).pad(Value.percentWidth(0.01f, algorithmsBox));
        noExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                algorithmsBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        TextButton exitButton = new TextButton(("Save"), buttonSkin, "maroon");
        algorithmsBox.add(exitButton).height(Value.percentHeight(0.35f, algorithmsBox)).width(Value.percentWidth(0.3f, algorithmsBox)).pad(Value.percentWidth(0.01f, algorithmsBox));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (firstTimeSave) {
                    saveAsBox.setVisible(true);
                    algorithmsBox.setVisible(false);
                } else {
                    save(graphIsDigraph, currentGraphName, false);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new AlgorithmExecutor(currentGraphName + ".graph"));
                }

                saved = true;



            }
        });

        algorithmsBox.align(Align.center);
        stage.addActor(algorithmsBox);
        algorithmsBox.setVisible(false);







        final TextButton algorithmExecutor = new TextButton(("Run Algorithms"), buttonSkin, "maroon");
        algorithmExecutor.setHeight(Gdx.graphics.getHeight() * (0.09f));
        algorithmExecutor.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        algorithmExecutor.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.16f)));
        stage.addActor(algorithmExecutor);

        algorithmExecutor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (saved && !firstTimeSave) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new AlgorithmExecutor(currentGraphName + ".graph"));
                } else {

                    algorithmsBox.setVisible(true);
                    modalBoxVisible = true;


                }



            }


        });




























        final TextButton mainMenu = new TextButton(("Main Menu"), buttonSkin, "maroon");
        mainMenu.setHeight(Gdx.graphics.getHeight() * (0.1f));
        mainMenu.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        mainMenu.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.04f)));
        stage.addActor(mainMenu);

        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (saved) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    errorText.remove();
                } else {

                    saveBox.setVisible(true);
                    modalBoxVisible = true;


                }


            }


        });


        TextButton graphButton = new TextButton(("Graph"), buttonSkin, "maroon");
        graphTypeBox.add(graphButton).height(Value.percentHeight(0.35f, graphTypeBox)).width(Value.percentWidth(0.35f, graphTypeBox)).pad(Value.percentWidth(0.01f, graphTypeBox));
        graphButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                graphIsDigraph = false;
                graphTypeBox.setVisible(false);


                modalBoxVisible = false;
                newVertex.setTouchable(Touchable.enabled);
                newEdge.setTouchable(Touchable.enabled);
                saveButton.setTouchable(Touchable.enabled);
                saveAsButton.setTouchable(Touchable.enabled);
                algorithmExecutor.setTouchable(Touchable.enabled);
                mainMenu.setTouchable(Touchable.enabled);

            }
        });

        TextButton digraphButton = new TextButton(("Digraph"), buttonSkin, "maroon");
        graphTypeBox.add(digraphButton).height(Value.percentHeight(0.35f, graphTypeBox)).width(Value.percentWidth(0.35f, graphTypeBox)).pad(Value.percentWidth(0.01f, graphTypeBox));
        digraphButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                graphIsDigraph = true;
                graphTypeBox.setVisible(false);


                modalBoxVisible = false;
                newVertex.setTouchable(Touchable.enabled);
                newEdge.setTouchable(Touchable.enabled);
                saveButton.setTouchable(Touchable.enabled);
                saveAsButton.setTouchable(Touchable.enabled);
                algorithmExecutor.setTouchable(Touchable.enabled);
                mainMenu.setTouchable(Touchable.enabled);
            }
        });













        edgeWeightBox = new Window(edgeName + " Weight:", buttonSkin, "maroon");
        edgeWeightBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        edgeWeightBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        edgeWeightBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        edgeWeightBox.setModal(true);
        edgeWeightBox.setMovable(false);
        edgeWeightBox.getTitleLabel().setAlignment(1);
        edgeWeightBox.setVisible(false);
        stage.addActor(edgeWeightBox);


        edgeWeightInputField = new TextField("", buttonSkin);
        edgeWeightBox.add(edgeWeightInputField).height(Value.percentHeight(0.3f, edgeWeightBox)).width(Value.percentWidth(0.7f, edgeWeightBox)).colspan(2);


        edgeWeightBox.row();

        TextButton cancelEdgeWeight = new TextButton(("Cancel"), buttonSkin, "maroon");
        edgeWeightBox.add(cancelEdgeWeight).height(Value.percentHeight(0.2f, edgeWeightBox)).width(Value.percentWidth(0.35f, edgeWeightBox)).pad(Value.percentWidth(0.025f, edgeWeightBox)).padTop(Value.percentWidth(0.05f, edgeWeightBox));
        cancelEdgeWeight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                edgeWeightBox.setVisible(false);
                modalBoxVisible = false;
                edgeWeightInputField.setText("");

                edgeListTo.remove(edgeListTo.size() -1);
                edgeListFrom.remove(edgeListFrom.size() -1);

            }
        });


        confirmEdgeWeight = new TextButton(("Ok"), buttonSkin, "maroon");
        edgeWeightBox.add(confirmEdgeWeight).height(Value.percentHeight(0.2f, edgeWeightBox)).width(Value.percentWidth(0.35f, edgeWeightBox)).pad(Value.percentWidth(0.025f, edgeWeightBox)).padTop(Value.percentWidth(0.05f, edgeWeightBox));
        confirmEdgeWeight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {





                if((Objects.equals(edgeWeightInputField.getText(), ""))) {
                    confirmEdgeWeight.setText("---");

                }

                else{
                    try{

                        if (Float.parseFloat(edgeWeightInputField.getText()) > 0) {

                            edgeWeightList.add(Float.parseFloat(edgeWeightInputField.getText()));
                            confirmEdgeWeight.setText("Ok");
                            edgeWeightInputField.setText("");

                            edgeWeightBox.setVisible(false);
                            modalBoxVisible = false;
                            saved = false;
                        }



                    }catch(NumberFormatException e){
                        confirmEdgeWeight.setText("---");
                    }

                }

            }
        });





















        if (graph_new) {
            graphTypeBox.setVisible(true);
            modalBoxVisible = true;
            newVertex.setTouchable(Touchable.disabled);
            newEdge.setTouchable(Touchable.disabled);
            saveButton.setTouchable(Touchable.disabled);
            saveAsButton.setTouchable(Touchable.disabled);
            algorithmExecutor.setTouchable(Touchable.disabled);
            mainMenu.setTouchable(Touchable.disabled);
        }



    }










    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(Gdx.input.isKeyJustPressed(Input.Keys.V) && !modalBoxVisible){
            newEdgeClicked = false;
            newVertexClicked = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E) && !modalBoxVisible) {
            newVertexClicked = false;
            lastVertexClicked = -1;
            edgeWeightInputField.setText("");
            newEdgeClicked = true;
        }




        if((Objects.equals(edgeWeightInputField.getText(), "")))
            confirmEdgeWeight.setText("---");


        else{
            try{
                if (Float.parseFloat(edgeWeightInputField.getText()) > 0)
                    confirmEdgeWeight.setText("Ok");
                else
                    confirmEdgeWeight.setText("---");
            }catch(NumberFormatException e){
                confirmEdgeWeight.setText("---");
            }

        }







        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            lastVertexClicked = findClickedVertex();
        }

        if (modalBoxVisible) {
            allowVertexMove = false;
        }

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !allowVertexMove && !modalBoxVisible) {
            allowVertexMove = true;
        }


        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (lastVertexClicked != -1) && !newEdgeClicked && allowVertexMove) {

            vertexCoordsX.set(lastVertexClicked, Gdx.input.getX());
            vertexCoordsY.set(lastVertexClicked, (Gdx.graphics.getHeight() - Gdx.input.getY()));

            saved = false;
        }



        if (edgeListFrom.size() == edgeListTo.size()) {
            removeDuplicateEdges();
        }

//        System.out.println(edgeListFrom);
//        System.out.println(edgeListTo);


        placeNewEdge();

        drawExistingEdge();


        if (graphIsDigraph)
            drawDigraphArrows();


        drawFloatValues();

        drawExistingVertex();

        placeNewVertex();

        binAnimation();


        savedLabel.setVisible(saved && !firstTimeSave && vertexCoordsX.size() != 0);

        stage.draw();
        stage.act();



    }


    private void drawExistingVertex() {

        for (int i = 0; i < vertexCoordsX.size(); i++) {

            if (i != lastVertexClicked) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(0.07f, 0.07f, 0.07f, 1);
                sr.circle(vertexCoordsX.get(i), vertexCoordsY.get(i), vertexSize);
                sr.end();

                String letter = String.valueOf(i);
                //letter = String.valueOf((char)(65+i));

                layout.setText(whiteFont, letter);   // add label here
                float fontWidth = layout.width;
                float fontHeight = layout.height;
                batch.begin();
                whiteFont.draw(batch, letter, vertexCoordsX.get(i) - 0.5f * fontWidth, vertexCoordsY.get(i) + 0.5f * fontHeight);
                batch.end();
            }
        }

        if (lastVertexClicked != -1) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(0.07f, 0.07f, 0.07f, 1);
            sr.circle(vertexCoordsX.get(lastVertexClicked), vertexCoordsY.get(lastVertexClicked), vertexSize);
            sr.end();


            String letter = String.valueOf(lastVertexClicked);
            //letter = String.valueOf((char)(65+lastVertexClicked));

            layout.setText(whiteFont, letter);   // add label here
            float fontWidth = layout.width;
            float fontHeight = layout.height;
            batch.begin();
            whiteFont.draw(batch, letter, vertexCoordsX.get(lastVertexClicked) - 0.5f * fontWidth, vertexCoordsY.get(lastVertexClicked) + 0.5f * fontHeight);
            batch.end();

        }



    }  //letter in here

    private void drawExistingEdge() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);


        for (int i = 0; i < vertexCoordsX.size(); i++) {

            if (vertexCoordsY.get(i) < vertexSize) {                                  // this keeps the vertex in bounds
                vertexCoordsY.set(i, (int) vertexSize);
            } else if (vertexCoordsY.get(i) > (Gdx.graphics.getHeight() - vertexSize)) {
                vertexCoordsY.set(i, (int) (Gdx.graphics.getHeight() - vertexSize));
            }

            if (vertexCoordsX.get(i) < (Gdx.graphics.getWidth() * (0.2f)) + vertexSize) {
                vertexCoordsX.set(i, (int) ((Gdx.graphics.getWidth() * (0.2f)) + vertexSize));
            } else if (vertexCoordsX.get(i) > (Gdx.graphics.getWidth() - vertexSize)) {
                vertexCoordsX.set(i, (int) (Gdx.graphics.getWidth() - vertexSize));
            }
        }


        for (int i = 0; (i < edgeListFrom.size()) && (i < edgeListTo.size()); i++) {

            sr.rectLine(vertexCoordsX.get(edgeListFrom.get(i)), vertexCoordsY.get(edgeListFrom.get(i)), vertexCoordsX.get(edgeListTo.get(i)), vertexCoordsY.get(edgeListTo.get(i)), vertexSize / 3);

        }


        sr.end();

    }

    private void drawMovingVertex() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);

        sr.circle(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()), vertexSize);

        sr.end();


        String letter = String.valueOf(vertexCoordsX.size());
        //letter = String.valueOf((char)(65+vertexCoordsX.size()));

        layout.setText(whiteFont, letter);   // add label here
        float fontWidth = layout.width;
        float fontHeight = layout.height;
        batch.begin();
        whiteFont.draw(batch, letter, Gdx.input.getX() - 0.5f * fontWidth, (Gdx.graphics.getHeight() - Gdx.input.getY()) + 0.5f * fontHeight);
        batch.end();
    }  //letter in here

    private void drawMovingEdge(int vertex) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);

        sr.rectLine((vertexCoordsX.get(vertex)), (vertexCoordsY.get(vertex)), Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()), vertexSize / 3);


        float midpointX = (vertexCoordsX.get(vertex) + Gdx.input.getX()) * 0.5f;
        float midpointY = (vertexCoordsY.get(vertex) + (Gdx.graphics.getHeight() - Gdx.input.getY())) * 0.5f;

        float x1 = 0 - vertexSize;
        float y1 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
        float x2 = 0 + vertexSize;
        float y2 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
        float x3 = 0;
        float y3 = (float) (0 + 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
        double rotationAngle;

        if (Gdx.input.getX() < vertexCoordsX.get(vertex))
            rotationAngle = (0.5f * Math.PI) + Math.atan((double) ((Gdx.graphics.getHeight() - Gdx.input.getY()) - vertexCoordsY.get(vertex)) / (Gdx.input.getX() - vertexCoordsX.get(vertex)));
        else
            rotationAngle = -(0.5f * Math.PI) + Math.atan((double) ((Gdx.graphics.getHeight() - Gdx.input.getY()) - vertexCoordsY.get(vertex)) / (Gdx.input.getX() - vertexCoordsX.get(vertex)));


        sr.identity();
        sr.translate(midpointX, midpointY, 0);
        sr.rotate(0, 0, 1, (float) Math.toDegrees(rotationAngle));
        sr.triangle(x1, y1, x2, y2, x3, y3);


        sr.end();
        sr.identity();


        sr.end();
    }

    private int findClickedVertex() {
        if (!modalBoxVisible) {
            for (int i = 0; i < vertexCoordsX.size(); i++) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (Math.pow(x - vertexCoordsX.get(i), 2) + Math.pow(y - vertexCoordsY.get(i), 2) <= vertexSize * vertexSize) {
                    return i;
                }
            }
            return -1;
        }
        return lastVertexClicked;
    }

    private void placeNewVertex() {

        if (newVertexClicked && mouseLookValid() && !newEdgeClicked) {
            drawMovingVertex();

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {


                if (Gdx.input.getX() > (Gdx.graphics.getWidth() * (0.215f)) && mousePlaceValid()) {
                    newVertexClicked = false;

                    vertexCoordsX.add(Gdx.input.getX());
                    vertexCoordsY.add(Gdx.graphics.getHeight() - Gdx.input.getY());

                    saved = false;
                }

                if (Gdx.input.getX() < (Gdx.graphics.getWidth() * (0.2f))) {
                    newVertexClicked = false;
                }
            }
        }

    }

    private void placeNewEdge() {


        {
            if (newEdgeClicked && (lastVertexClicked != -1)) {

                allowVertexMove = false;

                drawMovingEdge(lastVertexClicked);


                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !firstVertexClicked) {
                    firstVertexClicked = true;
                    edgeListFrom.add(lastVertexClicked);


                } else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && firstVertexClicked && (lastVertexClicked != edgeListFrom.get(edgeListFrom.size() - 1))) {
                    boolean notDuplicateEdge = true;


                    for (int i = 0; i < edgeListFrom.size() - 1; i++) {

                        if (edgeListTo.size() != 0 && (Objects.equals(edgeListFrom.get(edgeListFrom.size() - 1), edgeListFrom.get(i)) && edgeListTo.get(i) == lastVertexClicked) || (Objects.equals(edgeListTo.get(i), edgeListFrom.get(edgeListFrom.size() - 1)) && edgeListFrom.get(i) == lastVertexClicked)) {
                            notDuplicateEdge = false;
                            break;
                        }


                    }


                    if (notDuplicateEdge) {
                        edgeWeightBox.setVisible(true);
                        modalBoxVisible = true;

                        edgeListTo.add(lastVertexClicked);
                        firstVertexClicked = false;
                        newEdgeClicked = false;
                    } else {
                        lastVertexClicked = -1;
                    }


                }


            } else if (newEdgeClicked && firstVertexClicked) {
                edgeListFrom.remove(edgeListFrom.size() - 1);
                firstVertexClicked = false;
                newEdgeClicked = false;
            }


        }
    }

    private void removeDuplicateEdges() {
        for (int i = 0; i < edgeListFrom.size(); i++) {

            if (Objects.equals(edgeListFrom.get(i), edgeListTo.get(i))) {  // Same Vertex
                edgeListFrom.remove(i);
                edgeListTo.remove(i);
                i -= 1;
            } else {

                for (int k = 0; k < edgeListFrom.size(); k++) {                              //This works. Don't touch it.
                    for (int j = k; j < edgeListFrom.size(); j++) {

                        if (k != j && Objects.equals(edgeListFrom.get(k), edgeListFrom.get(j)) && Objects.equals(edgeListTo.get(k), edgeListTo.get(j))) {
                            edgeListFrom.remove(j);
                            edgeListTo.remove(j);
                            j -= 1;
                        }

                    }
                }


                for (int k = 0; k < edgeListFrom.size(); k++) {                              //This also works. Don't touch it either.
                    for (int j = k; j < edgeListFrom.size(); j++) {

                        if (k != j && Objects.equals(edgeListFrom.get(k), edgeListTo.get(j)) && Objects.equals(edgeListTo.get(k), edgeListFrom.get(j))) {
                            edgeListFrom.remove(j);
                            edgeListTo.remove(j);
                            j -= 1;
                        }

                    }
                }

            }
        }
    }

    private boolean mouseLookValid() {
        return ((Gdx.input.getX() < Gdx.graphics.getWidth()) && (Gdx.input.getY() < Gdx.graphics.getHeight()) && (Gdx.input.getY() > 0));
    }

    private boolean mousePlaceValid() {
        return ((Gdx.input.getX() < (Gdx.graphics.getWidth() * (0.985f))) && (Gdx.input.getY() < (Gdx.graphics.getHeight() - vertexSize)) && (Gdx.input.getY() > vertexSize));
    }

    private void binAnimation() {
        if (!modalBoxVisible && (Gdx.input.getX() >= (Gdx.graphics.getWidth() * (0.955f))) && ((Gdx.graphics.getHeight() - Gdx.input.getY()) <= (200 * (Gdx.graphics.getHeight() / (2700f))))) {
            binOpen.setVisible(true);
            binClosed.setVisible(false);
        } else {
            binOpen.setVisible(false);
            binClosed.setVisible(true);
        }


    }

    private void clearAll() {
        vertexCoordsX.clear();
        vertexCoordsY.clear();
        edgeListFrom.clear();
        edgeListTo.clear();
        edgeWeightList.clear();
    }

    private void drawDigraphArrows() {

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);


        for (int i = 0; (i < edgeListFrom.size()) && (i < edgeListTo.size()); i++) {


            float midpointX = (vertexCoordsX.get(edgeListFrom.get(i)) + vertexCoordsX.get(edgeListTo.get(i))) * 0.5f;
            float midpointY = (vertexCoordsY.get(edgeListFrom.get(i)) + vertexCoordsY.get(edgeListTo.get(i))) * 0.5f;

            float x1 = 0 - vertexSize;
            float y1 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
            float x2 = 0 + vertexSize;
            float y2 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
            float x3 = 0;
            float y3 = (float) (0 + 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
            double rotationAngle;

            if (vertexCoordsX.get(edgeListTo.get(i)) < vertexCoordsX.get(edgeListFrom.get(i)))
                rotationAngle = (0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(edgeListTo.get(i)) - vertexCoordsY.get(edgeListFrom.get(i))) / (vertexCoordsX.get(edgeListTo.get(i)) - vertexCoordsX.get(edgeListFrom.get(i))));
            else
                rotationAngle = -(0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(edgeListTo.get(i)) - vertexCoordsY.get(edgeListFrom.get(i))) / (vertexCoordsX.get(edgeListTo.get(i)) - vertexCoordsX.get(edgeListFrom.get(i))));


            sr.identity();
            sr.translate(midpointX, midpointY, 0);
            sr.rotate(0, 0, 1, (float) Math.toDegrees(rotationAngle));
            sr.triangle(x1, y1, x2, y2, x3, y3);


        }

        sr.end();
        sr.identity();

    }

    private void drawFloatValues() {

        //System.out.println(edgeListFrom + " " + edgeListTo + " " + edgeWeightList);


        if (edgeWeightList.size() != edgeListFrom.size() || (edgeListTo.size() != edgeListFrom.size())) {

            for (int i = 0; (i < edgeListFrom.size() - 1); i++) {

                float midpointX = (vertexCoordsX.get(edgeListFrom.get(i)) + vertexCoordsX.get(edgeListTo.get(i))) * 0.5f;
                float midpointY = (vertexCoordsY.get(edgeListFrom.get(i)) + vertexCoordsY.get(edgeListTo.get(i))) * 0.5f;

                double rotationAngle = (0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(edgeListTo.get(i)) - vertexCoordsY.get(edgeListFrom.get(i))) / (vertexCoordsX.get(edgeListTo.get(i)) - vertexCoordsX.get(edgeListFrom.get(i))));


                float addY = (float) (2 * vertexSize * Math.sin(rotationAngle));
                float addX = (float) (2 * vertexSize * Math.cos(rotationAngle));


                Float weight = edgeWeightList.get(i);
                String weightText;
                if (weight == Math.round(weight))
                    weightText = Integer.toString(Math.round(weight));
                else
                    weightText = Float.toString(weight);


                layout.setText(font, weightText);
                float fontWidth = layout.width;
                float fontHeight = layout.height;


                batch.begin();
                if (graphIsDigraph)
                    font.draw(batch, weightText, midpointX - 0.5f * fontWidth + addX, midpointY + 0.5f * fontHeight + addY);
                else
                    font.draw(batch, weightText, midpointX - 0.5f * fontWidth + 0.75f * addX, midpointY + 0.5f * fontHeight + 0.75f * addY);

                batch.end();
            }

        } else {

            for (int i = 0; (i < edgeListFrom.size()) && (i < edgeListTo.size()); i++) {

                float midpointX = (vertexCoordsX.get(edgeListFrom.get(i)) + vertexCoordsX.get(edgeListTo.get(i))) * 0.5f;
                float midpointY = (vertexCoordsY.get(edgeListFrom.get(i)) + vertexCoordsY.get(edgeListTo.get(i))) * 0.5f;

                double rotationAngle = (0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(edgeListTo.get(i)) - vertexCoordsY.get(edgeListFrom.get(i))) / (vertexCoordsX.get(edgeListTo.get(i)) - vertexCoordsX.get(edgeListFrom.get(i))));


                float addY = (float) (2 * vertexSize * Math.sin(rotationAngle));
                float addX = (float) (2 * vertexSize * Math.cos(rotationAngle));


                Float weight = edgeWeightList.get(i);
                String weightText;
                if (weight == Math.round(weight))
                    weightText = Integer.toString(Math.round(weight));
                else
                    weightText = Float.toString(weight);

                layout.setText(font, weightText);
                float fontWidth = layout.width;
                float fontHeight = layout.height;


                batch.begin();

                if (graphIsDigraph)
                    font.draw(batch, weightText, midpointX - 0.5f * fontWidth + addX, midpointY + 0.5f * fontHeight + addY);
                else
                    font.draw(batch, weightText, midpointX - 0.5f * fontWidth + 0.75f * addX, midpointY + 0.5f * fontHeight + 0.75f * addY);



                batch.end();
            }

        }

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
        sr.dispose();
        batch.dispose();
    }

}