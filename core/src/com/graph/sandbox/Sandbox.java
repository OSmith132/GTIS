package com.graph.sandbox;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class Sandbox implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();


    private boolean newVertexClicked = false;
    private boolean newEdgeClicked = false;

    private int lastVertexClicked = -1;
    private boolean firstVertexClicked = false;
    private boolean allowVertexMove = true;

    private boolean saved = true;
    private boolean modalBoxVisible;

    private boolean graphIsDigraph;


    ArrayList<Integer> vertexCoordsX = new ArrayList<>();
    ArrayList<Integer> vertexCoordsY = new ArrayList<>();

    ArrayList<Integer> edgeListFrom = new ArrayList<>();
    ArrayList<Integer> edgeListTo = new ArrayList<>();


    final FileHandle configFile = Gdx.files.local("core/assets/config.txt");
    String text = configFile.readString();
    String[] configArray = text.split("\\r?\\n");

    String vertexName = configArray[2];  // Node or Vertex
    String edgeName = configArray[3];     // Arc or Edge
    float vertexSize;


    Image binOpen = new Image(new Texture(Gdx.files.internal("binOpen.png")));
    Image binClosed = new Image(new Texture(Gdx.files.internal("binClosed.png")));




//I made this long line for no reason other than to show Kamil this line when he asks what the longest line in my code is for the fourth time. It doesn't matter as his longest line will be longer than this one anyway; and probably by a fair margin.







    private void save(boolean graphIsDigraph, String graph_name, Boolean graph_new, Boolean saveAs) {


        

        if (saveAs && graphIsDigraph) {
            FileHandle file = Gdx.files.local("core/assets/Saved Graphs/" + graph_name);
            file.writeString("digraph\n", false);

        }



    }


    public Sandbox(final String graph_name, final Boolean graph_new) {
        //         graph / digraph          "New Graph"         true / false






















//        vertexCoordsX.add(400);
//        vertexCoordsY.add(500);
//        vertexCoordsX.add(800);
//        vertexCoordsY.add(200);
//
//        edgeListFrom.add(0);
//        edgeListTo.add(1);
//
//        System.out.println(vertexCoordsX + " " + vertexCoordsY);








        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        final Window graphTypeBox = new Window("Graph Type:", buttonSkin, "maroon");
        graphTypeBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        graphTypeBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        graphTypeBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        graphTypeBox.setModal(true);
        graphTypeBox.setMovable(false);
        graphTypeBox.getTitleLabel().setAlignment(1);
        graphTypeBox.setVisible(false);
        stage.addActor(graphTypeBox);

        TextButton graphButton = new TextButton(("Graph"), buttonSkin, "maroon");
        graphTypeBox.add(graphButton).height(Value.percentHeight(0.35f, graphTypeBox)).width(Value.percentWidth(0.35f, graphTypeBox)).pad(Value.percentWidth(0.01f, graphTypeBox));
        graphButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                graphIsDigraph = false;
                graphTypeBox.setVisible(false);
                modalBoxVisible = false;

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
            }
        });









        if (graph_new) {
            graphTypeBox.setVisible(true);
            modalBoxVisible = true;
        }
        else{
            modalBoxVisible = false;

            FileHandle graphFile = Gdx.files.local("core/assets/Saved Graphs/" + graph_name);
            String text = graphFile.readString();
            String[] graphFileArray = text.split("\\r?\\n");


            graphIsDigraph = Objects.equals(graphFileArray[0], "digraph");
        }


















        if (Objects.equals(configArray[4], "small")){
            vertexSize = ( Gdx.graphics.getWidth() * (0.0075f));
        }
        else if (Objects.equals(configArray[4], "medium")){
            vertexSize = ( Gdx.graphics.getWidth() * (0.015f));
        }
        else{
            vertexSize = ( Gdx.graphics.getWidth() * (0.025f));
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


        TextButton newVertex = new TextButton(("New " + vertexName), buttonSkin, "maroon");                 //New Vertex
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

        TextButton newEdge = new TextButton(("New " + edgeName), buttonSkin, "maroon");
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











        final Window saveAsBox = new Window("Graph Type:", buttonSkin, "maroon");
        saveAsBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        saveAsBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        saveAsBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        saveAsBox.setModal(true);
        saveAsBox.setMovable(false);
        saveAsBox.getTitleLabel().setAlignment(1);
        saveAsBox.setVisible(false);
        stage.addActor(saveAsBox);



        TextField nameInputField = new TextField(graph_name,buttonSkin);
        saveAsBox.add(nameInputField).height(Value.percentHeight(0.3f, saveAsBox)).width(Value.percentWidth(0.7f, saveAsBox)).pad(Value.percentWidth(0.01f, saveAsBox)).bottom();



        saveAsBox.row();

        TextButton cancelNameButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        saveAsBox.add(cancelNameButton).height(Value.percentHeight(0.2f, saveAsBox)).width(Value.percentWidth(0.35f, saveAsBox)).pad(Value.percentWidth(0.01f, saveAsBox)).bottom();
        cancelNameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                errorText.setVisible(true);

            }
        });

        TextButton ConfirmNameButton = new TextButton(("Ok"), buttonSkin, "maroon");
        saveAsBox.add(ConfirmNameButton).height(Value.percentHeight(0.2f, saveAsBox)).width(Value.percentWidth(0.35f, saveAsBox)).pad(Value.percentWidth(0.01f, saveAsBox)).bottom();
        ConfirmNameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                errorText.setVisible(true);

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
                save(graphIsDigraph, graph_name, graph_new, false);
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

                saveAsBox.setVisible(true);
                save(graphIsDigraph, graph_name, graph_new, true);
                saved = true;

            }
        });




        final Window saveBox = new Window("Would you like to save and exit?",buttonSkin,"maroon");
        saveBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        saveBox.setWidth(Gdx.graphics.getWidth() * (0.25f));
        saveBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        saveBox.setModal(true);
        saveBox.setMovable(false);
        saveBox.getTitleLabel().setAlignment(1);




        TextButton cancelButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        saveBox.add(cancelButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.3f, saveBox)).pad(Value.percentWidth(0.01f,saveBox));
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        TextButton noButton = new TextButton(("Don't Save"), buttonSkin, "maroon");
        saveBox.add(noButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.3f, saveBox)).pad(Value.percentWidth(0.01f,saveBox));
        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });


        TextButton yesButton = new TextButton(("Save"), buttonSkin, "maroon");
        saveBox.add(yesButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.3f, saveBox)).pad(Value.percentWidth(0.01f,saveBox));
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {




                File file = new File("core/assets/Saved Graphs/" + graph_name);




                if (file.exists() && graph_name != "New_graph.txt") {
                    save(graphIsDigraph, graph_name, graph_new, false);
                }
                else {
                    save(graphIsDigraph, graph_name, graph_new,true);
                }




                saveBox.setVisible(false);
                saved = true;

                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());

            }
        });




        saveBox.align(Align.center);
        stage.addActor(saveBox);
        saveBox.setVisible(false);















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
                    modalBoxVisible = true;


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





        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            lastVertexClicked = findClickedVertex();
        }

        if (modalBoxVisible){
            allowVertexMove = false;
        }

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !allowVertexMove && !modalBoxVisible ) {
            allowVertexMove = true;
        }



        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (lastVertexClicked != -1) && !newEdgeClicked  &&  allowVertexMove) {
            vertexCoordsX.set(lastVertexClicked,Gdx.input.getX());
            vertexCoordsY.set(lastVertexClicked,(Gdx.graphics.getHeight() - Gdx.input.getY()));
            saved = false;
        }



        placeNewEdge();


        if (edgeListFrom.size() == edgeListTo.size()) {
            removeDuplicateEdges();
        }

//        System.out.println(edgeListFrom);
//        System.out.println(edgeListTo);


        drawExistingEdge();
        drawExistingVertex();

        placeNewVertex();

        binAnimation();




        stage.draw();
        stage.act();


    }





    private void drawExistingVertex() {


        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.07f, 0.07f, 0.07f,1);


        for(int i = 0; i < vertexCoordsX.size();i++) {

            sr.circle(vertexCoordsX.get(i),vertexCoordsY.get(i),vertexSize);
        }
        sr.end();
    }

    private void drawExistingEdge(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);


        for(int i = 0; i < vertexCoordsX.size();i++) {

            if (vertexCoordsY.get(i) < vertexSize) {                                  // this keeps the vertex in bounds
                vertexCoordsY.set(i, (int) vertexSize);
            } else if (vertexCoordsY.get(i) > (Gdx.graphics.getHeight() - vertexSize)) {
                vertexCoordsY.set(i, (int) (Gdx.graphics.getHeight() - vertexSize));
            }

            if (vertexCoordsX.get(i) < (Gdx.graphics.getWidth() * (0.2f))  +  vertexSize) {
                vertexCoordsX.set(i, (int) ((Gdx.graphics.getWidth() * (0.2f))  +  vertexSize));
            } else if (vertexCoordsX.get(i) > (Gdx.graphics.getWidth() - vertexSize)) {
                vertexCoordsX.set(i, (int) (Gdx.graphics.getWidth() - vertexSize));
            }
        }


        for(int i = 0; (i < edgeListFrom.size()) && (i < edgeListTo.size()) ;i++) {

            sr.rectLine(vertexCoordsX.get(edgeListFrom.get(i)), vertexCoordsY.get(edgeListFrom.get(i)),  vertexCoordsX.get(edgeListTo.get(i)),  vertexCoordsY.get(edgeListTo.get(i)),vertexSize/3);

        }



        sr.end();

    }

    private void drawMovingVertex(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);


        sr.circle(Gdx.input.getX(),(Gdx.graphics.getHeight() - Gdx.input.getY()),vertexSize);

        sr.end();
}

    private void drawMovingEdge(int vertex){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);

        sr.rectLine((vertexCoordsX.get(vertex)), (vertexCoordsY.get(vertex)),Gdx.input.getX(),(Gdx.graphics.getHeight() - Gdx.input.getY()), vertexSize/3);


        sr.end();
    }

    private int findClickedVertex(){
        for (int i = 0; i < vertexCoordsX.size(); i++) {
            float x=Gdx.input.getX();
            float y=Gdx.graphics.getHeight()-Gdx.input.getY();

            if(Math.pow(x-vertexCoordsX.get(i),2)+Math.pow(y-vertexCoordsY.get(i),2)<=vertexSize * vertexSize){
                return i;
            }
        }
        return -1;
    }

    private void placeNewVertex(){

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

    private void placeNewEdge(){


        {
            if (newEdgeClicked   &&  (lastVertexClicked != -1)){

                allowVertexMove = false;

                drawMovingEdge(lastVertexClicked);


                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !firstVertexClicked){
                    firstVertexClicked = true;
                    edgeListFrom.add(lastVertexClicked);



                }
                else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && firstVertexClicked){
                    edgeListTo.add(lastVertexClicked);
                    firstVertexClicked = false;
                    newEdgeClicked = false;
                    saved = false;


                }






            }
            else if(newEdgeClicked && firstVertexClicked){
                edgeListFrom.remove(edgeListFrom.size() - 1);
                firstVertexClicked = false;
                newEdgeClicked = false;
            }

        }
    }

    private void removeDuplicateEdges(){
        for(int i = 0; i < edgeListFrom.size();i++){

            if (Objects.equals(edgeListFrom.get(i), edgeListTo.get(i))){  // Same Vertex
                edgeListFrom.remove(i);
                edgeListTo.remove(i);
                i -= 1;
            }

            else{

                for(int k =0; k < edgeListFrom.size(); k++) {                              //This works. Don't touch it.
                    for (int j = k; j < edgeListFrom.size(); j++) {

                         if (k != j  && Objects.equals(edgeListFrom.get(k), edgeListFrom.get(j)) && Objects.equals(edgeListTo.get(k), edgeListTo.get(j))) {
                             edgeListFrom.remove(j);
                             edgeListTo.remove(j);
                             j -= 1;
                        }

                    }
                }


                for(int k =0; k < edgeListFrom.size(); k++) {                              //This also works. Don't touch it either.
                    for (int j = k; j < edgeListFrom.size(); j++) {

                        if (k != j  && Objects.equals(edgeListFrom.get(k), edgeListTo.get(j)) && Objects.equals(edgeListTo.get(k), edgeListFrom.get(j))) {
                            edgeListFrom.remove(j);
                            edgeListTo.remove(j);
                            j -= 1;
                        }

                    }
                }

            }
        }
    }

    private boolean mouseLookValid(){
        return ((Gdx.input.getX() < Gdx.graphics.getWidth())  && (Gdx.input.getY() < Gdx.graphics.getHeight())  && (Gdx.input.getY() > 0));
    }

    private boolean mousePlaceValid(){
        return ((Gdx.input.getX() < (Gdx.graphics.getWidth() * (0.985f)))  &&  (Gdx.input.getY() < (Gdx.graphics.getHeight() - vertexSize))  && (Gdx.input.getY() > vertexSize));
    }

    private void binAnimation(){
        if (!modalBoxVisible && (Gdx.input.getX() >= (Gdx.graphics.getWidth() * (0.955f))) && ((Gdx.graphics.getHeight() - Gdx.input.getY()) <=(200 * (Gdx.graphics.getHeight() / (2700f))))){
            binOpen.setVisible(true);
            binClosed.setVisible(false);
        }
        else{
            binOpen.setVisible(false);
            binClosed.setVisible(true);
        }




    }

    private void clearAll(){
        vertexCoordsX.clear();
        vertexCoordsY.clear();
        edgeListFrom.clear();
        edgeListTo.clear();
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
    }
}