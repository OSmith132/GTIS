package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Objects;


public class AlgorithmExecutor implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
    BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("whiteFont.fnt"));
    GlyphLayout layout = new GlyphLayout();

    private boolean modalBoxVisible;
    private boolean graphIsDigraph;

    private int lastVertexClicked = -1;

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







    public AlgorithmExecutor(final String graph_name){

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



        if (Objects.equals(configArray[4], "small")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.0075f));
        } else if (Objects.equals(configArray[4], "medium")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.015f));
        } else {
            vertexSize = (Gdx.graphics.getWidth() * (0.025f));
        }



        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));






















        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle1.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f));
        Rectangle.setPosition(0, -1);
        stage.addActor(Rectangle);








        final TextButton dijkstraButton = new TextButton(("Dijkstra's"), buttonSkin, "maroon");                                           //copy this for primm's and Kruskal's
        dijkstraButton.setHeight(Gdx.graphics.getHeight() * (0.1f));
        dijkstraButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        dijkstraButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.84f)));
        stage.addActor(dijkstraButton);

        dijkstraButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                //Run Dijkstra's

            }
        });







        final TextButton sandboxButton = new TextButton(("Edit Graph"), buttonSkin, "maroon");
        sandboxButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        sandboxButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        sandboxButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.16f)));
        stage.addActor(sandboxButton);

        sandboxButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game) Gdx.app.getApplicationListener()).setScreen(new Sandbox(currentGraphName + ".graph",false));

            }


        });





        final Window exitBox = new Window("Are you sure you want to exit?", buttonSkin, "maroon");
        exitBox.setHeight(Gdx.graphics.getHeight() * (0.16f));
        exitBox.setWidth(Gdx.graphics.getWidth() * (0.25f));
        exitBox.setPosition(Gdx.graphics.getWidth() * (0.4f), Gdx.graphics.getHeight() * (0.5f));
        exitBox.setModal(true);
        exitBox.setMovable(false);
        exitBox.getTitleLabel().setAlignment(1);


        TextButton noExitButton = new TextButton(("Cancel"), buttonSkin, "maroon");
        exitBox.add(noExitButton).height(Value.percentHeight(0.35f, exitBox)).width(Value.percentWidth(0.3f, exitBox)).pad(Value.percentWidth(0.01f, exitBox));
        noExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitBox.setVisible(false);
                modalBoxVisible = false;
            }
        });


        TextButton exitButton = new TextButton(("Exit"), buttonSkin, "maroon");
        exitBox.add(exitButton).height(Value.percentHeight(0.35f, exitBox)).width(Value.percentWidth(0.3f, exitBox)).pad(Value.percentWidth(0.01f, exitBox));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });

        exitBox.align(Align.center);
        stage.addActor(exitBox);
        exitBox.setVisible(false);


        final TextButton mainMenu = new TextButton(("Main Menu"), buttonSkin, "maroon");
        mainMenu.setHeight(Gdx.graphics.getHeight() * (0.1f));
        mainMenu.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        mainMenu.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.04f)));
        stage.addActor(mainMenu);

        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                exitBox.setVisible(true);
                modalBoxVisible = true;


            }


        });



    }






    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        findClickedVertex();


        if (graphIsDigraph)
            drawDigraphArrows();

        drawExistingEdge();

        drawFloatValues();

        drawExistingVertex();





        stage.draw();
        stage.act();
    }



    private void drawExistingVertex() {





        for (int i = 0; i < vertexCoordsX.size(); i++) {
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
    public void resize(int i, int i1) {

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
