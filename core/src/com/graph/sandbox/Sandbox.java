package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Objects;


public class Sandbox implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    private boolean newVertexClicked = false;
    private boolean newEdgeClicked = false;

    private int lastVertexClicked = -1;
    private boolean firstVertexClicked = false;

    private boolean saved = true;

    String vertexName = "Node";  // Node or Vertex
    String edgeName = "Arc";     // Arc or Edge



    ArrayList<Integer> vertexCoordsX = new ArrayList<>();
    ArrayList<Integer> vertexCoordsY = new ArrayList<>();

    ArrayList<Integer> edgeListFrom = new ArrayList<>();
    ArrayList<Integer> edgeListTo = new ArrayList<>();






//I made this long line for no reason other than to show Kamil this line when he asks what the longest line in my code is for the fourth time. It doesn't matter as his longest line will be longer than this one anyway; and probably by a fair margin.

    public Sandbox() {


        vertexCoordsX.add(400);
        vertexCoordsY.add(500);
        vertexCoordsX.add(800);
        vertexCoordsY.add(200);

        edgeListFrom.add(0);
        edgeListTo.add(1);

        System.out.println(vertexCoordsX + " " + vertexCoordsY);


        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        final Label errorText = new Label("*Sorry, this feature has not yet been implemented", buttonSkin, "error");
        errorText.setPosition(Gdx.graphics.getWidth() * (0.2f) + 10, 5);
        //errorText.getFontScaleX()


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);




        TextButton newVertex = new TextButton(("New " + vertexName), buttonSkin, "maroon");                 //New Vertex
        newVertex.setHeight(Gdx.graphics.getHeight() * (0.1f));
        newVertex.setWidth(Gdx.graphics.getWidth() * (0.08f));
        newVertex.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.85f)));
        stage.addActor(newVertex);

        newVertex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saved = false;
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
                saved = false;

                newEdgeClicked = true;
                newVertexClicked = false;

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





        final Window saveBox = new Window("Would you like to save and exit?",buttonSkin,"maroon");
        saveBox.setHeight(Gdx.graphics.getHeight() * (0.15f));
        saveBox.setWidth(Gdx.graphics.getWidth() * (0.2f));
        saveBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        saveBox.setModal(true);
        saveBox.setMovable(false);
        saveBox.getTitleLabel().setAlignment(1);




        TextButton noButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        saveBox.add(noButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.4f, saveBox)).pad(Value.percentWidth(0.05f,saveBox));
        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveBox.setVisible(false);
            }
        });


        TextButton yesButton = new TextButton(("Save"), buttonSkin, "maroon");
        saveBox.add(yesButton).height(Value.percentHeight(0.35f, saveBox)).width(Value.percentWidth(0.4f, saveBox)).pad(Value.percentWidth(0.05f,saveBox));
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveBox.setVisible(false);
                saved = true;

                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                errorText.remove();


                //save the doc here
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





        placeNewEdge();
        placeNewVertex();

        if (edgeListFrom.size() == edgeListTo.size()) {
            removeDuplicateEdges();
        }

        System.out.println(edgeListFrom);
        System.out.println(edgeListTo);


        drawExistingEdge();
        drawExistingVertex();



        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (lastVertexClicked != -1) && !newEdgeClicked) {
            vertexCoordsX.set(lastVertexClicked,Gdx.input.getX());
            vertexCoordsY.set(lastVertexClicked,(Gdx.graphics.getHeight() - Gdx.input.getY()));
        }




        stage.draw();
        stage.act();

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



    private void drawExistingVertex() {


        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.07f, 0.07f, 0.07f,1);


        for(int i = 0; i < vertexCoordsX.size();i++) {

            sr.circle(vertexCoordsX.get(i),vertexCoordsY.get(i),Gdx.graphics.getWidth() * (0.015f));
        }
        sr.end();
    }

    private void drawExistingEdge(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);


        for(int i = 0; i < vertexCoordsX.size();i++) {

            if (vertexCoordsY.get(i) < (Gdx.graphics.getWidth() * (0.015f))) {                                  // this keeps the vertex in bounds
                vertexCoordsY.set(i, (int) (Gdx.graphics.getWidth() * (0.015f)));
            } else if (vertexCoordsY.get(i) > (Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() * (0.015f)))) {
                vertexCoordsY.set(i, (int) (Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() * (0.015f))));
            }

            if (vertexCoordsX.get(i) < (Gdx.graphics.getWidth() * (0.215f))) {
                vertexCoordsX.set(i, (int) (Gdx.graphics.getWidth() * (0.215f)));
            } else if (vertexCoordsX.get(i) > (Gdx.graphics.getWidth() * (0.985f))) {
                vertexCoordsX.set(i, (int) (Gdx.graphics.getWidth() * (0.985f)));
            }
        }


        for(int i = 0; (i < edgeListFrom.size()) && (i < edgeListTo.size()) ;i++) {

            sr.rectLine(vertexCoordsX.get(edgeListFrom.get(i)), vertexCoordsY.get(edgeListFrom.get(i)),  vertexCoordsX.get(edgeListTo.get(i)),  vertexCoordsY.get(edgeListTo.get(i)),Gdx.graphics.getWidth() * 0.005f);

        }



        sr.end();

    }

    private void drawMovingVertex(){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);


        sr.circle(Gdx.input.getX(),(Gdx.graphics.getHeight() - Gdx.input.getY()),Gdx.graphics.getWidth() * (0.015f));

        sr.end();
}

    private void drawMovingEdge(int vertex){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);

        sr.rectLine((vertexCoordsX.get(vertex)), (vertexCoordsY.get(vertex)),Gdx.input.getX(),(Gdx.graphics.getHeight() - Gdx.input.getY()), Gdx.graphics.getWidth() * 0.005f);


        sr.end();
    }

    private int findClickedVertex(){
        for (int i = 0; i < vertexCoordsX.size(); i++) {

            if ( ((Gdx.input.getX() <=(vertexCoordsX.get(i) + Gdx.graphics.getWidth() * (0.015f)) ) && (Gdx.input.getX() >=(vertexCoordsX.get(i) - Gdx.graphics.getWidth() * (0.015f))))  &&  ((Gdx.graphics.getHeight() - Gdx.input.getY()) <= (vertexCoordsY.get(i) + (Gdx.graphics.getWidth() * (0.015f))))  && ((Gdx.graphics.getHeight() - Gdx.input.getY()) >= (vertexCoordsY.get(i) - (Gdx.graphics.getWidth() * (0.015f))))) {
               // System.out.println(vertexCoordsX + " " + vertexCoordsY + " " + (Gdx.input.getX()) + " " + (Gdx.graphics.getHeight() - Gdx.input.getY()) );

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

                drawMovingEdge(lastVertexClicked);


                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !firstVertexClicked){
                    firstVertexClicked = true;
                    edgeListFrom.add(lastVertexClicked);

                }
                else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && firstVertexClicked){
                    edgeListTo.add(lastVertexClicked);
                    firstVertexClicked = false;
                    newEdgeClicked = false;
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
        return ((Gdx.input.getX() < (Gdx.graphics.getWidth() * (0.985f)))  &&  (Gdx.input.getY() < (Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() * (0.015f))))  && (Gdx.input.getY() > (Gdx.graphics.getWidth() * (0.015f))));
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