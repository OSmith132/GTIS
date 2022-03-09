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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Sandbox implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
    BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("whiteFont.fnt"));
    GlyphLayout layout = new GlyphLayout();


    private boolean showVertexNumbers = true;
    private boolean showEdgeWeights = true;



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




    TextField populateVInputField;
    Slider vPopSlider;

    TextField populateEInputField;
    Slider ePopSlider;


    int maxVertices;
    int maxEdges = 20;



    TextButton confirmEdgeWeight;
    final TextField edgeWeightInputField;
    final Window edgeWeightBox;

    Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
    final Label savedLabel = new Label("Graph Saved Successfully.", buttonSkin, "error");


//I made this long line for no reason other than to show Kamil this line when he asks what the longest line in my code is for the fourth time. It doesn't matter as his longest line will be longer than this one anyway; and probably by a fair margin.kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk


    private String existingFileNameChanger(String graph_name, int i) {



        FileHandle file = Gdx.files.local("core/assets/Saved Graphs/" + graph_name + ".graph");

        if (file.exists()) {




            if ((graph_name.charAt(graph_name.length() - 1)) == ')') {

                String[] parts = graph_name.split("\\(" );
                String[] intPart = parts[parts.length-1].split("\\)");
                graph_name = graph_name.substring(0, graph_name.length() -( 3 +    (int)Math.floor(       (  String.valueOf(Integer.parseInt(intPart[0])).length() -1  ))        )        ) + "(" + i + ")";



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

        if (Objects.equals(configArray[1], "fullscreen")) {
            resolutionW = Gdx.graphics.getWidth();
            resolutionH = Gdx.graphics.getHeight();
        }


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
            whiteFont.getData().setScale(0.4f,0.4f);
            maxVertices = 400;
        } else if (Objects.equals(configArray[4], "medium")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.015f));
            maxVertices = 100;
            whiteFont.getData().setScale(0.8f,0.8f);
        } else {
            vertexSize = (Gdx.graphics.getWidth() * (0.025f));
            font.getData().setScale(5f/3f,5f/3f);
            whiteFont.getData().setScale(4f/3f,4f/3f);
            maxVertices = 50;
        }




        final Label errorText = new Label("*Sorry, this feature has not yet been implemented", buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth() * (0.2f) + 10, 5);
        //errorText.getFontScaleX()


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle2.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f)+50);
        Rectangle.setPosition(-50, -1);
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























        final Window populateVertexBox = new Window("Populate Vertex", buttonSkin, "maroon");
        populateVertexBox.setHeight(Gdx.graphics.getHeight() * (0.2f));
        populateVertexBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        populateVertexBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        populateVertexBox.setModal(true);
        populateVertexBox.setMovable(false);
        populateVertexBox.getTitleLabel().setAlignment(1);
        populateVertexBox.setVisible(false);
        stage.addActor(populateVertexBox);





        populateVInputField = new TextField(Integer.toString(maxVertices /2), buttonSkin,"spinner");
        populateVInputField.setAlignment(1);


        populateVertexBox.add(populateVInputField).height(Value.percentHeight(0.2f, populateVertexBox)).width(Value.percentWidth(0.2f, populateVertexBox)).colspan(2).padTop(Value.percentWidth(0.01f, populateVertexBox));

        populateVertexBox.row();

        vPopSlider = new Slider(0, maxVertices,1,false,buttonSkin);
        vPopSlider.setValue((int)(maxVertices /2f));

        vPopSlider.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                populateVInputField.setText(String.valueOf((int) vPopSlider.getValue()));

            }
        });




        populateVertexBox.add(vPopSlider).height(Value.percentHeight(0.1f, populateVertexBox)).width(Value.percentWidth(0.7f, populateVertexBox)).colspan(2).pad(Value.percentWidth(0.02f, populateVertexBox));

        populateVertexBox.row();





        TextButton cancelVPopulate = new TextButton(("Cancel"), buttonSkin, "maroon");
        populateVertexBox.add(cancelVPopulate).height(Value.percentHeight(0.2f, populateVertexBox)).width(Value.percentWidth(0.35f, populateVertexBox)).pad(Value.percentWidth(0.025f, populateVertexBox));
        cancelVPopulate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                populateVertexBox.setVisible(false);
                modalBoxVisible = false;
            }
        });

        TextButton confirmVPopulate = new TextButton(("Ok"), buttonSkin, "maroon");
        populateVertexBox.add(confirmVPopulate).height(Value.percentHeight(0.2f, populateVertexBox)).width(Value.percentWidth(0.35f, populateVertexBox)).pad(Value.percentWidth(0.025f, populateVertexBox));
        confirmVPopulate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


               populateVertex((int)vPopSlider.getValue());

                populateVertexBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        final TextButton populateVertexButton = new TextButton(("V-Populate"), buttonSkin, "maroon");
        populateVertexButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        populateVertexButton.setWidth(Gdx.graphics.getWidth() * (0.08f));
        populateVertexButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.73f)));
        stage.addActor(populateVertexButton);

        populateVertexButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                //do stuff

                populateVertexBox.setVisible(true);
                modalBoxVisible = true;
            }
        });

























        final Window populateEdgeBox = new Window("Populate Edge", buttonSkin, "maroon");
        populateEdgeBox.setHeight(Gdx.graphics.getHeight() * (0.2f));
        populateEdgeBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        populateEdgeBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        populateEdgeBox.setModal(true);
        populateEdgeBox.setMovable(false);
        populateEdgeBox.getTitleLabel().setAlignment(1);
        populateEdgeBox.setVisible(false);
        stage.addActor(populateEdgeBox);





        populateEInputField = new TextField(Integer.toString(maxEdges), buttonSkin,"spinner");
        populateEInputField.setAlignment(1);

        final CheckBox popEFloatButton = new CheckBox(" Int", buttonSkin, "radio");
        popEFloatButton.setChecked(true);

        populateEdgeBox.add(populateEInputField).height(Value.percentHeight(0.2f, populateEdgeBox)).width(Value.percentWidth(0.2f, populateEdgeBox)).colspan(1).padTop(Value.percentWidth(0.01f, populateEdgeBox)).padRight( - (popEFloatButton.getPrefWidth() + populateEInputField.getPrefWidth()/2f + Gdx.graphics.getWidth() * (0.005f)));
        populateEdgeBox.add(popEFloatButton).padRight(-popEFloatButton.getPrefWidth());


        populateEdgeBox.row();

        ePopSlider = new Slider(1, maxEdges,1,false,buttonSkin);
        ePopSlider.setValue(maxEdges);

        ePopSlider.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                populateEInputField.setText(String.valueOf((int) ePopSlider.getValue()));

            }
        });


        populateEdgeBox.add(ePopSlider).height(Value.percentHeight(0.1f, populateEdgeBox)).width(Value.percentWidth(0.7f, populateEdgeBox)).colspan(3).pad(Value.percentWidth(0.02f, populateEdgeBox));



        populateEdgeBox.row();





        TextButton cancelEPopulate = new TextButton(("Cancel"), buttonSkin, "maroon");
        populateEdgeBox.add(cancelEPopulate).height(Value.percentHeight(0.2f, populateEdgeBox)).width(Value.percentWidth(0.35f, populateEdgeBox)).pad(Value.percentWidth(0.025f, populateEdgeBox));
        cancelEPopulate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                populateEdgeBox.setVisible(false);
                modalBoxVisible = false;
            }
        });

        TextButton confirmEPopulate = new TextButton(("Ok"), buttonSkin, "maroon");
        populateEdgeBox.add(confirmEPopulate).height(Value.percentHeight(0.2f, populateEdgeBox)).width(Value.percentWidth(0.35f, populateEdgeBox)).pad(Value.percentWidth(0.025f, populateEdgeBox));
        confirmEPopulate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (vertexCoordsX.size() > 1)
                    populateEdge((int)ePopSlider.getValue(), !popEFloatButton.isChecked());

                populateEdgeBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        final TextButton populateEdgeButton = new TextButton(("E-Populate"), buttonSkin, "maroon");
        populateEdgeButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        populateEdgeButton.setWidth(Gdx.graphics.getWidth() * (0.08f));
        populateEdgeButton.setPosition((Gdx.graphics.getWidth() * (0.105f)), (Gdx.graphics.getHeight() * (0.73f)));
        stage.addActor(populateEdgeButton);

        populateEdgeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                //do stuff

                populateEdgeBox.setVisible(true);
                modalBoxVisible = true;
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






















        final Window popupBox = new Window("", buttonSkin, "maroon");
        popupBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        popupBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        popupBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        popupBox.setModal(true);
        popupBox.setMovable(false);
        popupBox.getTitleLabel().setAlignment(1);
        popupBox.setVisible(false);


        TextButton popupClose = new TextButton("X", buttonSkin, "maroonX");
        popupClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupBox.setVisible(false);
                modalBoxVisible = false;
            }
        });

        popupBox.getTitleTable().add(popupClose).height(Value.percentHeight(.05f, popupBox)).width(Value.percentWidth(.05f, popupBox));
        popupBox.getTitleTable().align(Align.top | Align.right);


        final Label popupLabel = new Label("Please fully connect the graph first",buttonSkin,"error");
        popupBox.add(popupLabel).padTop(Value.percentWidth(0.05f, popupBox));

        popupBox.row();

        final TextButton popupCloseButton = new TextButton("Ok",buttonSkin,"maroon");
        popupCloseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupBox.setVisible(false);
                modalBoxVisible = false;
            }
        });
        popupBox.add(popupCloseButton).height(Value.percentHeight(0.2f, popupBox)).width(Value.percentWidth(0.4f, popupBox)).pad(Value.percentWidth(0.05f, popupBox));





        stage.addActor(popupBox);

















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
                    algorithmsBox.setVisible(false);

                    if (vertexCoordsX.size() >= 2 && graphConnected()) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new AlgorithmExecutor(currentGraphName + ".graph"));


                    } else if (vertexCoordsX.size() < 2) {

                        algorithmsBox.setVisible(false);
                        if (Objects.equals(vertexName, "vertex"))
                            popupLabel.setText("Please add more vertices");
                        else
                            popupLabel.setText("Please add more nodes");

                        popupBox.setVisible(true);


                    } else if (!graphConnected()) {
                        algorithmsBox.setVisible(false);
                        popupLabel.setText("Please fully connect the graph first");

                        popupBox.setVisible(true);

                    }
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


                if (saved && !firstTimeSave && vertexCoordsX.size() >= 2 && graphConnected()) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new AlgorithmExecutor(currentGraphName + ".graph"));

                } else if (!saved) {

                    algorithmsBox.setVisible(true);
                    modalBoxVisible = true;

                } else if (vertexCoordsX.size() < 2) {
                    algorithmsBox.setVisible(false);

                    if (Objects.equals(vertexName, "vertex"))
                        popupLabel.setText("Please add more vertices");
                    else
                        popupLabel.setText("Please add more nodes");

                    popupBox.setVisible(true);


                } else if (!graphConnected()) {
                    algorithmsBox.setVisible(false);
                    popupLabel.setText("Please fully connect the graph first");
                    popupBox.setVisible(true);
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
                populateVertexButton.setTouchable(Touchable.enabled);
                populateEdgeButton.setTouchable(Touchable.enabled);
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
                populateVertexButton.setTouchable(Touchable.enabled);
                populateEdgeButton.setTouchable(Touchable.enabled);
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
            populateVertexButton.setTouchable(Touchable.disabled);
            populateEdgeButton.setTouchable(Touchable.disabled);
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





        if (Gdx.input.isKeyJustPressed(Input.Keys.V) && !modalBoxVisible) {
            newEdgeClicked = false;
            newVertexClicked = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && !modalBoxVisible) {
            newVertexClicked = false;
            lastVertexClicked = -1;
            edgeWeightInputField.setText("");
            newEdgeClicked = true;
        }


        if ((Objects.equals(edgeWeightInputField.getText(), "")))
            confirmEdgeWeight.setText("---");


        else {
            try {
                if (Float.parseFloat(edgeWeightInputField.getText()) > 0)
                    confirmEdgeWeight.setText("Ok");
                else
                    confirmEdgeWeight.setText("---");
            } catch (NumberFormatException e) {
                confirmEdgeWeight.setText("---");
            }

        }




        if (populateVInputField.isVisible() && !Objects.equals(Integer.toString((int)vPopSlider.getValue()), populateVInputField.getText())){

            try {
                    vPopSlider.setValue(Integer.parseInt(populateVInputField.getText()));
                    if (Integer.parseInt(populateVInputField.getText()) > maxVertices - vertexCoordsX.size())
                        populateVInputField.setText(String.valueOf(maxVertices - vertexCoordsX.size()));
                    else if (Integer.parseInt(populateVInputField.getText()) < 0)
                        populateVInputField.setText("0");
                    vPopSlider.setRange(0,maxVertices - vertexCoordsX.size());


            }
            catch (NumberFormatException e) {
                vPopSlider.setValue(0);
               // populateVInputField.setText(Integer.toString((maxVertices - vertexCoordsX.size()) /2));
            }
        }







        if (populateEInputField.isVisible() && !Objects.equals(Integer.toString((int)ePopSlider.getValue()), populateEInputField.getText())){

            try {
                ePopSlider.setValue(Integer.parseInt(populateEInputField.getText()));
                if (Integer.parseInt(populateEInputField.getText()) > maxEdges - edgeListTo.size())
                    populateEInputField.setText(String.valueOf(maxEdges - edgeListTo.size()));
                else if (Integer.parseInt(populateEInputField.getText()) < 0)
                    populateEInputField.setText("0");
                ePopSlider.setRange(1,maxEdges - edgeListTo.size());


            }
            catch (NumberFormatException e) {
                ePopSlider.setValue((maxEdges - edgeListTo.size()) /2f);
            //    populateEInputField.setText(Integer.toString((maxEdges - edgeListTo.size()) /2));
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






        placeNewEdge();

        drawExistingEdge();


        if (graphIsDigraph)
            drawDigraphArrows();

        if (showEdgeWeights)
            drawEdgeValues();

        drawExistingVertex();

        placeNewVertex();

        binAnimation();


        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !modalBoxVisible) {
            System.out.println("Well Done! You've found the debug button!");
        }



        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && !modalBoxVisible) {
            eraseVertex();
        }

        savedLabel.setVisible(saved && !firstTimeSave && vertexCoordsX.size() != 0);

        stage.draw();
        stage.act();



    }


    private void drawExistingVertex() {

        if (showVertexNumbers) {
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

            if (lastVertexClicked != -1 && showVertexNumbers) {
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
        }

        else{
            for (int i = 0; i < vertexCoordsX.size(); i++) {

                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(0.07f, 0.07f, 0.07f, 1);
                sr.circle(vertexCoordsX.get(i), vertexCoordsY.get(i), vertexSize);
                sr.end();
            }
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

        if (showVertexNumbers) {
            String letter = String.valueOf(vertexCoordsX.size());
            //letter = String.valueOf((char)(65+vertexCoordsX.size()));
            layout.setText(whiteFont, letter);   // add label here
            float fontWidth = layout.width;
            float fontHeight = layout.height;
            batch.begin();
            whiteFont.draw(batch, letter, Gdx.input.getX() - 0.5f * fontWidth, (Gdx.graphics.getHeight() - Gdx.input.getY()) + 0.5f * fontHeight);
            batch.end();
        }
    }  //letter in here

    private void drawMovingEdge(int vertex) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);

        sr.rectLine((vertexCoordsX.get(vertex)), (vertexCoordsY.get(vertex)), Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()), vertexSize / 3);

        if (graphIsDigraph) {
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


        }
            sr.end();
            sr.identity();

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
                edgeWeightList.remove(edgeWeightList.size() -1);    // this make break in future
                i -= 1;
            } else {

                for (int k = 0; k < edgeListFrom.size(); k++) {                              //This works. Don't touch it.
                    for (int j = k; j < edgeListFrom.size(); j++) {

                        if (k != j && Objects.equals(edgeListFrom.get(k), edgeListFrom.get(j)) && Objects.equals(edgeListTo.get(k), edgeListTo.get(j))) {
                            edgeListFrom.remove(j);
                            edgeListTo.remove(j);
                            edgeWeightList.remove(edgeWeightList.size() -1);
                            j -= 1;
                        }

                    }
                }


                for (int k = 0; k < edgeListFrom.size(); k++) {                              //This also works. Don't touch it either.
                    for (int j = k; j < edgeListFrom.size(); j++) {

                        if (k != j && Objects.equals(edgeListFrom.get(k), edgeListTo.get(j)) && Objects.equals(edgeListTo.get(k), edgeListFrom.get(j))) {
                            edgeListFrom.remove(j);
                            edgeListTo.remove(j);
                            edgeWeightList.remove(edgeWeightList.size() -1);
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
        populateVInputField.setText(Integer.toString((int)((maxVertices - vertexCoordsX.size()) /2f)));
        populateEInputField.setText(Integer.toString((int)((maxEdges - edgeListTo.size()) /2f)));

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

    private void drawEdgeValues() {



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

    private void populateVertex(int number) {
        saved = false;

        if (number + vertexCoordsX.size() > maxVertices && maxVertices - vertexCoordsX.size() > 0)
            number = maxVertices - vertexCoordsX.size();
        else if (maxVertices - vertexCoordsX.size() == 0)
            number = 0;


        for (int i = 0; i < number; i++) {

            int x = (int) (Gdx.graphics.getWidth() * 0.2f + (int) (Math.random() * (Gdx.graphics.getWidth() * 0.8f)));
            int y = (int) (Math.random() * Gdx.graphics.getHeight());


            for (int k = 0; k < vertexCoordsX.size(); k++) {

                if (Math.pow(x - vertexCoordsX.get(k), 2) + Math.pow(y - vertexCoordsY.get(k), 2) <= vertexSize * vertexSize * 4 + 5 || x > Gdx.graphics.getWidth() - (2 * vertexSize) || x < 0.2f * Gdx.graphics.getWidth() + (2 * vertexSize) || y > Gdx.graphics.getHeight() - (2 * vertexSize) || y < 2 * vertexSize) {
                    x = (int) (Gdx.graphics.getWidth() * 0.2f + (int) (Math.random() * (Gdx.graphics.getWidth() * 0.8f)));
                    y = (int) (Math.random() * Gdx.graphics.getHeight());
                    k = -1;
                }
            }

            vertexCoordsX.add(x);
            vertexCoordsY.add(y);

        }

        populateVInputField.setText(Integer.toString((int) ((maxVertices - vertexCoordsX.size()) / 2f)));
    }

    private void populateEdge(int maxWeight, boolean floatWeight) {
        saved = false;
        Random rand = new Random();


        undirectedEdgeListFrom.clear();
        undirectedEdgeListTo.clear();

        undirectedEdgeListTo.addAll(edgeListTo);
        undirectedEdgeListTo.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListTo);


        for (int i = 0; i < vertexCoordsX.size(); i++) {


            int to = getNearbyVertex(i);

            float weight = ThreadLocalRandom.current().nextInt(1, maxWeight + 1);

            if (floatWeight)
                weight -= ThreadLocalRandom.current().nextInt(0, 99 + 1) / 100f;


            edgeListFrom.add(i);
            edgeListTo.add(to);
            edgeWeightList.add(weight);


        }


        while (!graphConnected()) {


            int from = unvisitedVertices().get(rand.nextInt(unvisitedVertices().size()));
            int to = getNearbyVertex(from);


            float weight = ThreadLocalRandom.current().nextInt(1, maxWeight + 1);

            if (floatWeight)
                weight -= ThreadLocalRandom.current().nextInt(0, 99 + 1) / 100f;


            edgeListFrom.add(from);
            edgeListTo.add(to);
            edgeWeightList.add(weight);


        }


    }

    private int getNearbyVertex(int xIndex) {

        Random rand = new Random();


        int xCoord = vertexCoordsX.get(xIndex);
        int yCoord = vertexCoordsY.get(xIndex);

        int random;
        int x;
        int y;

        for (float i = 0; i < Gdx.graphics.getWidth() + vertexSize; i += 1) {    // change this


            random = rand.nextInt(vertexCoordsX.size());
            x = vertexCoordsX.get(random);
            y = vertexCoordsY.get(random);

            boolean duplicateEdge = false;
            for (int k = 0; k < undirectedEdgeListFrom.size(); k++) {


                if (xIndex == k && random == undirectedEdgeListTo.get(k)) {

                    duplicateEdge = true;
                    break;
                }


            }



            if (x < xCoord + i && x > xCoord - i && y < yCoord + i && y > yCoord - i && xCoord != x && yCoord != y && !duplicateEdge) {

                return random;

            }


        }

        return 0;

    }

    ArrayList<Integer> undirectedEdgeListTo = new ArrayList<>();
    ArrayList<Integer> undirectedEdgeListFrom = new ArrayList<>();

    private boolean graphConnected() {


        ArrayList<Integer> visited = new ArrayList<>();

        undirectedEdgeListFrom.clear();
        undirectedEdgeListTo.clear();

        undirectedEdgeListTo.addAll(edgeListTo);
        undirectedEdgeListTo.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListTo);

        return dfs(0, visited).size() == vertexCoordsX.size();                            //what is happening here??


    }

    private ArrayList<Integer> dfs(int currentVertex, ArrayList<Integer> visited) {


        visited.add(currentVertex);


        ArrayList<Integer> connections = new ArrayList<>();
        for (int i = 0; i < undirectedEdgeListFrom.size(); i++) {
            if (undirectedEdgeListFrom.get(i) == currentVertex && !connections.contains(undirectedEdgeListTo.get(i)))
                connections.add(undirectedEdgeListTo.get(i));
        }



        for (Integer connection : connections) {
            if (!visited.contains(connection)) {

                dfs(connection, visited);
            }
        }



        return visited;
    }

    private ArrayList<Integer> unvisitedVertices() {

        ArrayList<Integer> visited = new ArrayList<>();
        undirectedEdgeListFrom.clear();
        undirectedEdgeListTo.clear();

        undirectedEdgeListTo.addAll(edgeListTo);
        undirectedEdgeListTo.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListTo);


        ArrayList<Integer> allVertices = new ArrayList<>();
        for (int i = 0; i < vertexCoordsX.size(); i++) {
            allVertices.add(i);
        }


        ArrayList<Integer> visitedVertices = new ArrayList<>(dfs(0, visited));


        allVertices.removeAll(visitedVertices);



        return allVertices;
    }




    public void eraseVertex() {

        int vertex = findClickedVertex();

        if (vertex != -1 && !newVertexClicked && !newEdgeClicked) {

            vertexCoordsX.remove(vertex);
            vertexCoordsY.remove(vertex);

            for (int i = 0; i < edgeListFrom.size(); i++) {

                if (edgeListFrom.get(i) == vertex  ||  edgeListTo.get(i) == vertex){
                    edgeListFrom.remove(i);
                    edgeListTo.remove(i);
                    edgeWeightList.remove(i);

                    i -= 1;

                }

            }

            for (int i = 0; i < edgeListFrom.size(); i++) {

                if (edgeListFrom.get(i) > vertex)
                    edgeListFrom.set(i,edgeListFrom.get(i)-1);

                if (edgeListTo.get(i) > vertex)
                    edgeListTo.set(i,edgeListTo.get(i)-1);
            }

            lastVertexClicked = -1;
            saved = false;


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