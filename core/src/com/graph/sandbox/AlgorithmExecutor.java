package com.graph.sandbox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class AlgorithmExecutor implements Screen {
    private final Stage stage = new Stage();
    private final ShapeRenderer sr = new ShapeRenderer();

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
    BitmapFont smallFont = new BitmapFont(Gdx.files.internal("font.fnt"));
    BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("whiteFont.fnt"));
    BitmapFont greenFont = new BitmapFont(Gdx.files.internal("greenFont.fnt"));
    BitmapFont redFont = new BitmapFont(Gdx.files.internal("redFont.fnt"));
    GlyphLayout layout = new GlyphLayout();


    private final boolean showVertexNumbers = true;
    private final boolean showEdgeWeights = true;


    private boolean modalBoxVisible;
    private final boolean graphIsDigraph;

    private boolean runningPrims = false;
    private boolean runningKruskals = false;
    private boolean runningDijkstras = false;
    private boolean runningCpa = false;


    private boolean primsButtonClicked = false;
    private boolean kruskalsButtonClicked = false;
    private boolean dijkstrasButtonClicked = false;
    private boolean cpaButtonClicked = false;

    private boolean firstClickDijkstra = false;


    ArrayList<Integer> vertexCoordsX = new ArrayList<>();
    ArrayList<Integer> vertexCoordsY = new ArrayList<>();

    ArrayList<Integer> edgeListFrom = new ArrayList<>();
    ArrayList<Integer> edgeListTo = new ArrayList<>();
    ArrayList<Float> edgeWeightList = new ArrayList<>();

    final Window popupBox;
    final Label popupLabel;


    final FileHandle configFile = Gdx.files.local("core/assets/config.txt");
    String text = configFile.readString();
    String[] configArray = text.split("\\r?\\n");

    Integer resolutionW = Integer.parseInt(configArray[0].split(" ")[0]);
    Integer resolutionH = Integer.parseInt(configArray[0].split(" ")[2]);
    float vertexSize;

    String currentGraphName;


    public AlgorithmExecutor(final String graph_name) {

        if (Objects.equals(configArray[1], "fullscreen")) {
            resolutionW = Gdx.graphics.getWidth();
            resolutionH = Gdx.graphics.getHeight();
        }


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


        float XScale = resolutionW / 1600f;
        float YScale = resolutionH / 900f;

        if (Objects.equals(configArray[4], "small")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.0075f));
            font.getData().setScale(0.5f * XScale, 0.5f * YScale);
            smallFont.getData().setScale(0.25f * XScale,0.25f * YScale);
            whiteFont.getData().setScale(0.4f * XScale, 0.4f * YScale);
        } else if (Objects.equals(configArray[4], "medium")) {
            vertexSize = (Gdx.graphics.getWidth() * (0.015f));
            smallFont.getData().setScale(0.5f * XScale,0.5f * YScale);
            whiteFont.getData().setScale(0.8f * XScale, 0.8f * YScale);
            font.getData().setScale(XScale, YScale);
        } else {
            vertexSize = (Gdx.graphics.getWidth() * (0.025f));
            font.getData().setScale(5f / 3f * XScale, 5f / 3f * YScale);
            smallFont.getData().setScale(5f / 3f * XScale,5f / 3f * YScale);
            whiteFont.getData().setScale(4f / 3f * XScale, 4f / 3f * YScale);
        }









        Skin buttonSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));


        Image Rectangle = new Image(new Texture(Gdx.files.internal("rectangle2.png")));
        Rectangle.setHeight(Gdx.graphics.getHeight() + 1);
        Rectangle.setWidth(Gdx.graphics.getWidth() * (0.2f) + 50);
        Rectangle.setPosition(-50, -1);
        stage.addActor(Rectangle);


        final TextButton dijkstraButton = new TextButton(("Dijkstra's"), buttonSkin, "maroon");                                           //copy this for primm's and Kruskal's
        dijkstraButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        dijkstraButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        dijkstraButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.86f)));
        stage.addActor(dijkstraButton);

        dijkstraButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (!runningDijkstras)
                    resetAlg();

                runningPrims = false;
                runningKruskals = false;
                runningDijkstras = true;
                runningCpa = false;

                dijkstrasButtonClicked = true;
                firstClickDijkstra = false;

                startVertex = -1;
                endVertex = -1;

                visitedEdgeListFrom.clear();
                visitedEdgeListTo.clear();

            }
        });


        final TextButton primsButton = new TextButton(("Prim's"), buttonSkin, "maroon");                                           //copy this for primm's and Kruskal's
        primsButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        primsButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        primsButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.75f)));
        stage.addActor(primsButton);

        primsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (!runningPrims)
                    resetAlg();

                runningPrims = true;
                runningKruskals = false;
                runningDijkstras = false;
                runningCpa = false;


                primsButtonClicked = true;

            }
        });


        final TextButton kruskalButton = new TextButton(("Kruskal's"), buttonSkin, "maroon");
        kruskalButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        kruskalButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        kruskalButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.64f)));
        stage.addActor(kruskalButton);

        kruskalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (!runningKruskals) {
                    startVertex = -1;
                    endVertex = -1;
                }

                runningPrims = false;
                runningKruskals = true;
                runningDijkstras = false;
                runningCpa = false;


                kruskalsButtonClicked = true;


            }
        });


        final TextButton cpaButton = new TextButton(("CPA"), buttonSkin, "maroon");
        cpaButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        cpaButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        cpaButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.53f)));
        stage.addActor(cpaButton);

        cpaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (graphIsDigraph) {
                    if (!runningCpa)
                        resetAlg();

                    runningPrims = false;
                    runningKruskals = false;
                    runningDijkstras = false;
                    runningCpa = true;

                    cpaButtonClicked = true;

                    startVertex = -1;
                    endVertex = -1;

                    visitedEdgeListFrom.clear();
                    visitedEdgeListTo.clear();

                }
                else{

                    popupBox.getTitleLabel().setText("Critical Path Analysis:");
                    popupLabel.setText("CPA only available for digraphs");
                    popupBox.setVisible(true);

                }
            }
        });


        popupBox = new Window("", buttonSkin, "maroon");
        popupBox.setHeight(Gdx.graphics.getHeight() * (0.2f));
        popupBox.setWidth(Gdx.graphics.getWidth() * (0.19f));
        popupBox.setPosition(Gdx.graphics.getWidth() * (0.005f), Gdx.graphics.getHeight() * (0.28f));
        popupBox.getTitleLabel().setAlignment(-1);
        popupBox.setVisible(false);


        TextButton popupClose = new TextButton("X", buttonSkin, "maroonX");
        popupClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupBox.setVisible(false);
                modalBoxVisible = false;
                resetAlg();
            }
        });


        popupBox.getTitleTable().add(popupClose).height(Value.percentHeight(.05f, popupBox)).width(Value.percentWidth(.05f, popupBox));
        //popupBox.getTitleTable().align(Align.top | Align.right);


        popupLabel = new Label("If you're seeing this there has been an error.", buttonSkin, "error");
        popupBox.add(popupLabel).padTop(Value.percentWidth(0.05f, popupBox));

        popupBox.row();

        final TextButton popupCloseButton = new TextButton("Ok", buttonSkin, "maroon");
        popupCloseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupBox.setVisible(false);
                modalBoxVisible = false;
                resetAlg();
            }
        });
        popupBox.add(popupCloseButton).height(Value.percentHeight(0.15f, popupBox)).width(Value.percentWidth(0.3f, popupBox)).pad(Value.percentWidth(0.075f, popupBox)).padBottom(Value.percentHeight(-0.05f, popupBox));


        stage.addActor(popupBox);


        final TextButton sandboxButton = new TextButton(("Edit Graph"), buttonSkin, "maroon");
        sandboxButton.setHeight(Gdx.graphics.getHeight() * (0.09f));
        sandboxButton.setWidth(Gdx.graphics.getWidth() * (0.1725f));
        sandboxButton.setPosition((Gdx.graphics.getWidth() * (0.0125f)), (Gdx.graphics.getHeight() * (0.16f)));
        stage.addActor(sandboxButton);

        sandboxButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game) Gdx.app.getApplicationListener()).setScreen(new Sandbox(currentGraphName + ".graph", false));

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

        if (graphIsDigraph && !runningKruskals && !runningPrims)
            drawDigraphArrows();

        drawExistingEdge();

        if (visitedEdgeListFrom.size() > 0)
            drawFinishedAlgEdges();

        if (showEdgeWeights)
            drawFloatValues();

        drawExistingVertex();


        if (startVertex != -1)
            drawFinishedAlgVertices();


        if (runningDijkstras && dijkstrasButtonClicked) {


            if (!firstClickDijkstra) {
                vertexSelectPointer(true);

                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && findClickedVertex() != -1) {
                    startVertex = findClickedVertex();
                    firstClickDijkstra = true;
                }
            } else {
                vertexSelectPointer(false);

                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && findClickedVertex() != -1 && findClickedVertex() != startVertex) {
                    endVertex = findClickedVertex();
                    dijkstraAlg(startVertex, endVertex);
                    popupBox.setVisible(true);
                    dijkstrasButtonClicked = false;
                }
            }

        }


        if (runningCpa && cpaButtonClicked) {

            vertexSelectPointer(true);

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && findClickedVertex() != -1) {
                    startVertex = findClickedVertex();
                    cpaAlg(startVertex);
                    cpaButtonClicked = false;
                    popupBox.setVisible(true);
                }

        }


        if (runningPrims && primsButtonClicked) {

            vertexSelectPointer(true);

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && findClickedVertex() != -1) {
                primsAlg(findClickedVertex());
                startVertex = findClickedVertex();
                primsButtonClicked = false;
                popupBox.setVisible(true);

            }
        }


        if (runningKruskals && kruskalsButtonClicked) {

            kruskalAlg();
            startVertex = -1;
            kruskalsButtonClicked = false;
            popupBox.setVisible(true);


        }

        if (runningCpa && visitedEdgeListFrom.size() > 0)
            drawCpaTimes();


        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !modalBoxVisible) {
            System.out.println("Well Done! You've found the debug button!");
            cpaAlg(0);
        }


        stage.draw();
        stage.act();



    }


    private void drawExistingVertex() {

        for (int i = 0; i < vertexCoordsX.size(); i++) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(0.07f, 0.07f, 0.07f, 1);
            sr.circle(vertexCoordsX.get(i), vertexCoordsY.get(i), vertexSize);
            sr.end();

            if (showVertexNumbers) {
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
        return -1;
    }

    private void vertexSelectPointer(boolean startVertex) {
        batch.begin();

        if (startVertex) {
            layout.setText(font, "Start");
            greenFont.draw(batch, "Start", Gdx.input.getX() + 15, (Gdx.graphics.getHeight() - Gdx.input.getY()));
        } else {
            layout.setText(font, "End");
            redFont.draw(batch, "End", Gdx.input.getX() + 15, (Gdx.graphics.getHeight() - Gdx.input.getY()));
        }

        batch.end();

    }

    private void drawFinishedAlgEdges() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.LIME);


        for (int i = 0; i < visitedEdgeListFrom.size(); i++) {
            sr.rectLine(vertexCoordsX.get(visitedEdgeListFrom.get(i)), vertexCoordsY.get(visitedEdgeListFrom.get(i)), vertexCoordsX.get(visitedEdgeListTo.get(i)), vertexCoordsY.get(visitedEdgeListTo.get(i)), vertexSize / 3);
        }


        sr.setColor(0.95f, 0.95f, 0.95f, 1);
        for (int i = 0; i < edgeListFrom.size(); i++) {
            if (edgeWeightList.get(i) == 0 && runningCpa) {    //needs to be runningCpa

                float x1 = vertexCoordsX.get(edgeListFrom.get(i));
                float y1 = vertexCoordsY.get(edgeListFrom.get(i));
                float x2 = vertexCoordsX.get(edgeListTo.get(i));
                float y2 = vertexCoordsY.get(edgeListTo.get(i));

                float divisor = (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
                float vecX = ((x2 - x1) / divisor) * vertexSize;
                float vecY = ((y2 - y1) / divisor) * vertexSize;


                for (int k = 0; k < divisor / (Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2))); k += 2) {
                    sr.rectLine(x1 + k * vecX, y1 + k * vecY, x1 + (k + 1) * vecX, y1 + (k + 1) * vecY, vertexSize / 3);
                }



                sr.setColor(Color.RED);
                float midpointX = (vertexCoordsX.get(edgeListFrom.get(i)) + vertexCoordsX.get(edgeListTo.get(i))) * 0.5f;
                float midpointY = (vertexCoordsY.get(edgeListFrom.get(i)) + vertexCoordsY.get(edgeListTo.get(i))) * 0.5f;

                float x11 = 0 - vertexSize;
                float y11 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
                float x22 = 0 + vertexSize;
                float y22 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
                float x33 = 0;
                float y33 = (float) (0 + 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
                double rotationAngle;

                if (vertexCoordsX.get(edgeListTo.get(i)) < vertexCoordsX.get(edgeListFrom.get(i)))
                    rotationAngle = (0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(edgeListTo.get(i)) - vertexCoordsY.get(edgeListFrom.get(i))) / (vertexCoordsX.get(edgeListTo.get(i)) - vertexCoordsX.get(edgeListFrom.get(i))));
                else
                    rotationAngle = -(0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(edgeListTo.get(i)) - vertexCoordsY.get(edgeListFrom.get(i))) / (vertexCoordsX.get(edgeListTo.get(i)) - vertexCoordsX.get(edgeListFrom.get(i))));


                sr.identity();
                sr.translate(midpointX, midpointY, 0);
                sr.rotate(0, 0, 1, (float) Math.toDegrees(rotationAngle));
                sr.triangle(x11, y11, x22, y22, x33, y33);
            }
        }
        sr.end();


        if (graphIsDigraph && !runningKruskals && !runningPrims || runningCpa) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.LIME);


            for (int i = 0; (i < visitedEdgeListFrom.size()); i++) {


                float midpointX = (vertexCoordsX.get(visitedEdgeListFrom.get(i)) + vertexCoordsX.get(visitedEdgeListTo.get(i))) * 0.5f;
                float midpointY = (vertexCoordsY.get(visitedEdgeListFrom.get(i)) + vertexCoordsY.get(visitedEdgeListTo.get(i))) * 0.5f;

                float x1 = 0 - vertexSize;
                float y1 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
                float x2 = 0 + vertexSize;
                float y2 = (float) (0 - 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
                float x3 = 0;
                float y3 = (float) (0 + 0.5f * Math.sqrt((2 * vertexSize * 2 * vertexSize) - (vertexSize * vertexSize)));
                double rotationAngle;

                if (vertexCoordsX.get(visitedEdgeListTo.get(i)) < vertexCoordsX.get(visitedEdgeListFrom.get(i)))
                    rotationAngle = (0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(visitedEdgeListTo.get(i)) - vertexCoordsY.get(visitedEdgeListFrom.get(i))) / (vertexCoordsX.get(visitedEdgeListTo.get(i)) - vertexCoordsX.get(visitedEdgeListFrom.get(i))));
                else
                    rotationAngle = -(0.5f * Math.PI) + Math.atan((double) (vertexCoordsY.get(visitedEdgeListTo.get(i)) - vertexCoordsY.get(visitedEdgeListFrom.get(i))) / (vertexCoordsX.get(visitedEdgeListTo.get(i)) - vertexCoordsX.get(visitedEdgeListFrom.get(i))));


                sr.identity();
                sr.translate(midpointX, midpointY, 0);
                sr.rotate(0, 0, 1, (float) Math.toDegrees(rotationAngle));
                sr.triangle(x1, y1, x2, y2, x3, y3);


            }

            sr.end();
            sr.identity();
        }

    }

    private void drawFinishedAlgVertices() {

        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (startVertex != -1) {
            sr.setColor(Color.GREEN);
            sr.circle(vertexCoordsX.get(startVertex), vertexCoordsY.get(startVertex), vertexSize);
        }

        if (endVertex != -1) {
            sr.setColor(Color.FIREBRICK);
            sr.circle(vertexCoordsX.get(endVertex), vertexCoordsY.get(endVertex), vertexSize);
        }

        sr.end();


        if (showVertexNumbers && startVertex != -1) {

            String letter = String.valueOf(startVertex);
            //letter = String.valueOf((char)(65+i));

            layout.setText(whiteFont, letter);   // add label here
            float fontWidth = layout.width;
            float fontHeight = layout.height;
            batch.begin();
            whiteFont.draw(batch, letter, vertexCoordsX.get(startVertex) - 0.5f * fontWidth, vertexCoordsY.get(startVertex) + 0.5f * fontHeight);
            batch.end();
        }

        if (showVertexNumbers && endVertex != -1) {

            String letter = String.valueOf(endVertex);
            //letter = String.valueOf((char)(65+i));

            layout.setText(whiteFont, letter);   // add label here
            float fontWidth = layout.width;
            float fontHeight = layout.height;
            batch.begin();
            whiteFont.draw(batch, letter, vertexCoordsX.get(endVertex) - 0.5f * fontWidth, vertexCoordsY.get(endVertex) + 0.5f * fontHeight);
            batch.end();
        }


    }

    private void resetAlg() {
        visitedEdgeListFrom.clear();
        visitedEdgeListTo.clear();
        startVertex = -1;
        endVertex = -1;
        runningPrims = false;
        runningKruskals = false;
        runningDijkstras = false;
        runningCpa = false;
        firstClickDijkstra = false;

    }



    ArrayList<Integer> undirectedEdgeListFrom = new ArrayList<>();
    ArrayList<Integer> undirectedEdgeListTo = new ArrayList<>();

    ArrayList<Float> undirectedEdgeWeightList = new ArrayList<>();

    ArrayList<Integer> visitedEdgeListFrom = new ArrayList<>();
    ArrayList<Integer> visitedEdgeListTo = new ArrayList<>();
    int startVertex = -1;
    int endVertex = -1;

    boolean graphHasCycle;
    ArrayList<Integer> newVisitedEdgeListFrom = new ArrayList<>();
    ArrayList<Integer> newVisitedEdgeListTo = new ArrayList<>();

    ArrayList<Integer> shortestPathList = new ArrayList<>();
    ArrayList<Integer> sortedConnections = new ArrayList<>();
    ArrayList<Float> sortedConnectionWeight = new ArrayList<>();

    ArrayList<Float> tempLabels = new ArrayList<>();
    ArrayList<Float> permLabels = new ArrayList<>();
    ArrayList<Integer> prevVertexList = new ArrayList<>();

    int criticalVertex;

    ArrayList<Float> earliestEventTimeList = new ArrayList<>();
    ArrayList<Float> latestEventTimeList = new ArrayList<>();
    ArrayList<Integer> sortedPreviousVertex = new ArrayList<>();

    private void primsAlg(int startVertex) {


        undirectedEdgeListFrom.clear();
        undirectedEdgeListTo.clear();


        undirectedEdgeListTo.addAll(edgeListTo);
        undirectedEdgeListTo.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListTo);

        visitedEdgeListFrom.clear();
        visitedEdgeListTo.clear();

        float totalWeight = 0;
        ArrayList<Integer> vertexList = new ArrayList<>();

        vertexList.add(startVertex);


        for (int i = 0; i < vertexCoordsX.size() - 1; i++) {
            ArrayList<Float> primsArray = new ArrayList<>(findClosestVertexPrims(vertexList));

            visitedEdgeListFrom.add((int) (float) primsArray.get(0));
            visitedEdgeListTo.add((int) (float) primsArray.get(1));
            totalWeight += primsArray.get(2);
            vertexList.add((int) (float) primsArray.get(1));
        }


        String disTotalWeight = Float.toString(totalWeight);

        if (totalWeight == (int)totalWeight)
            disTotalWeight = Integer.toString((int)totalWeight);

        String dispVertexList = vertexList.toString();
        dispVertexList = dispVertexList.replace("[", "");
        dispVertexList = dispVertexList.replace("]", "");

        layout.setText(whiteFont, dispVertexList);

        if (layout.width > Gdx.graphics.getWidth() * 0.2f)
            dispVertexList = dispVertexList.substring(0, 15) + "...";

        popupBox.getTitleLabel().setText("Prim's Algorithm:");
        popupLabel.setText("Path: " + dispVertexList + "\n" + "Total Weight: " + disTotalWeight);




    }

    private ArrayList<Float> findClosestVertexPrims(ArrayList<Integer> vertexList) {


        int closestVertex = -1;


        ArrayList<Integer> connectionsTo = new ArrayList<>();


        for (int k = 0; k < vertexList.size(); k++) {
            int currentVertex = vertexList.get(k);


            for (int i = 0; i < undirectedEdgeListFrom.size(); i++) {


                if (undirectedEdgeListFrom.get(i) == currentVertex && !connectionsTo.contains(undirectedEdgeListTo.get(i)) && !vertexList.contains(undirectedEdgeListTo.get(i))) {
                    connectionsTo.add(undirectedEdgeListTo.get(i));

                }


            }


        }


        for (int currentVertex : vertexList) {


            for (Integer connection : connectionsTo) {

                int weightIndex = findWeightIndex(currentVertex, connection);


                if (closestVertex == -1 || (weightIndex != -1 && (edgeWeightList.get(weightIndex) < edgeWeightList.get(closestVertex)))) {
                    closestVertex = weightIndex;
                }

            }

        }

        ArrayList<Float> returnList = new ArrayList<>();

        if (vertexList.contains(edgeListFrom.get(closestVertex))) {
            returnList.add((float) edgeListFrom.get(closestVertex));
            returnList.add((float) edgeListTo.get(closestVertex));
        } else {
            returnList.add((float) edgeListTo.get(closestVertex));
            returnList.add((float) edgeListFrom.get(closestVertex));
        }

        returnList.add(edgeWeightList.get(closestVertex));

        return returnList;  // from: 0    To:   1    Weight:   2


    }

    private void kruskalAlg() {


        undirectedEdgeListFrom.clear();
        undirectedEdgeListTo.clear();

        undirectedEdgeListTo.addAll(edgeListTo);
        undirectedEdgeListTo.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListFrom);
        undirectedEdgeListFrom.addAll(edgeListTo);

        undirectedEdgeWeightList.clear();
        undirectedEdgeWeightList.addAll(edgeWeightList);
        undirectedEdgeWeightList.addAll(edgeWeightList);

        visitedEdgeListFrom.clear();
        visitedEdgeListTo.clear();

        ArrayList<Integer> vertexList = new ArrayList<>();
        float totalWeight = 0;


        for (int i = 0; i < vertexCoordsX.size() - 1; i++) {
            ArrayList<Float> kruskalArray = new ArrayList<>(findNextEdgeKruskals(vertexList));

            visitedEdgeListFrom.add((int) (float) kruskalArray.get(0));
            visitedEdgeListTo.add((int) (float) kruskalArray.get(1));
            totalWeight += kruskalArray.get(2);


            for (float vertex : kruskalArray) {
                if (!vertexList.contains((int) vertex) && kruskalArray.get(2) != vertex)
                    vertexList.add((int) vertex);

            }

        }

        String disTotalWeight = Float.toString(totalWeight);

        if (totalWeight == (int)totalWeight)
            disTotalWeight = Integer.toString((int)totalWeight);

        String dispVertexList = vertexList.toString();
        dispVertexList = dispVertexList.replace("[", "");
        dispVertexList = dispVertexList.replace("]", "");

        layout.setText(whiteFont, dispVertexList);

        if (layout.width > Gdx.graphics.getWidth() * 0.2f)
            dispVertexList = dispVertexList.substring(0, 15) + "...";


        popupBox.getTitleLabel().setText("Kruskal's Algorithm:");
        popupLabel.setText("Path: " + dispVertexList + "\n" + "Total Weight: " + disTotalWeight);


        System.out.println("Path: " + vertexList);
        System.out.println("      " + visitedEdgeListFrom + "\n      " + visitedEdgeListTo);
        System.out.println("Total Weight: " + totalWeight);


    }

    private ArrayList<Float> findNextEdgeKruskals(ArrayList<Integer> vertexList) {


        int smallestEdgeIndex = -1;


        for (int i = 0; i < undirectedEdgeWeightList.size(); i++) {


            boolean edgeExists = false;
            for (int k = 0; k < visitedEdgeListFrom.size(); k++) {
                if ((Objects.equals(visitedEdgeListFrom.get(k), undirectedEdgeListFrom.get(i)) && Objects.equals(visitedEdgeListTo.get(k), undirectedEdgeListTo.get(i))) || (Objects.equals(visitedEdgeListFrom.get(k), undirectedEdgeListTo.get(i)) && Objects.equals(visitedEdgeListTo.get(k), undirectedEdgeListFrom.get(i)))) {
                    edgeExists = true;
                    break;
                }
            }


            if ((smallestEdgeIndex == -1 || undirectedEdgeWeightList.get(i) < undirectedEdgeWeightList.get(smallestEdgeIndex)) && !edgeExists && !graphHasCycle(i))
                smallestEdgeIndex = i;


        }


        int smallestEdgeFrom = undirectedEdgeListFrom.get(smallestEdgeIndex);
        int smallestEdgeTo = undirectedEdgeListTo.get(smallestEdgeIndex);
        float smallestWeight = undirectedEdgeWeightList.get(smallestEdgeIndex);


        ArrayList<Float> returnList = new ArrayList<>();

        returnList.add((float) smallestEdgeFrom);
        returnList.add((float) smallestEdgeTo);
        returnList.add(smallestWeight);

        return returnList;  // from: 0    To:   1    Weight:   2

    }

    private boolean graphHasCycle(int edgeIndexAdded) {

        newVisitedEdgeListFrom.clear();
        newVisitedEdgeListTo.clear();

        newVisitedEdgeListFrom.addAll(visitedEdgeListFrom);
        newVisitedEdgeListTo.addAll(visitedEdgeListTo);
        newVisitedEdgeListTo.addAll(visitedEdgeListFrom);
        newVisitedEdgeListFrom.addAll(visitedEdgeListTo);

        newVisitedEdgeListFrom.add(undirectedEdgeListFrom.get(edgeIndexAdded));
        newVisitedEdgeListTo.add(undirectedEdgeListTo.get(edgeIndexAdded));
        newVisitedEdgeListTo.add(undirectedEdgeListFrom.get(edgeIndexAdded));
        newVisitedEdgeListFrom.add(undirectedEdgeListTo.get(edgeIndexAdded));


        graphHasCycle = false;


        for (int i = 0; i < newVisitedEdgeListFrom.size(); i++) {

            dfsCycle(i, new ArrayList<Integer>(), -1);

        }

        return graphHasCycle;

    }

    private void dfsCycle(int currentVertex, ArrayList<Integer> visited, int lastVertex) {


        visited.add(currentVertex);


        ArrayList<Integer> connections = new ArrayList<>();
        for (int i = 0; i < newVisitedEdgeListFrom.size(); i++) {
            if (newVisitedEdgeListFrom.get(i) == currentVertex && !connections.contains(newVisitedEdgeListTo.get(i)))
                connections.add(newVisitedEdgeListTo.get(i));
        }


        for (Integer connection : connections) {
            if (!visited.contains(connection)) {
                dfsCycle(connection, visited, currentVertex);
            } else if (connection != lastVertex) {
                graphHasCycle = true;
                break;
            }

        }


    }

    private int findWeightIndex(int from, int to){
        if (!graphIsDigraph || runningKruskals  ||  runningPrims) {
            for (int j = 0; j < edgeListFrom.size(); j++) {
                if ((from == edgeListFrom.get(j) && Objects.equals(to, edgeListTo.get(j))) || (from == edgeListTo.get(j) && Objects.equals(to, edgeListFrom.get(j)))) {
                    return j;
                }
            }
            return -1;
        }

        else{
            for (int j = 0; j < edgeListFrom.size(); j++) {
                if ((from == edgeListFrom.get(j) && Objects.equals(to, edgeListTo.get(j)))) {
                    return j;
                }
            }
            return -1;
        }

    }

    private void dijkstraAlg(int startVertex, int endVertex) {


        undirectedEdgeListFrom.clear();
        undirectedEdgeListTo.clear();
        undirectedEdgeWeightList.clear();

        undirectedEdgeWeightList.addAll(edgeWeightList);
        undirectedEdgeWeightList.addAll(edgeWeightList);

        undirectedEdgeListTo.addAll(edgeListTo);
        undirectedEdgeListFrom.addAll(edgeListFrom);
        if (!graphIsDigraph) {
            undirectedEdgeListTo.addAll(edgeListFrom);
            undirectedEdgeListFrom.addAll(edgeListTo);
        }

        System.out.println(undirectedEdgeListFrom + "  " + undirectedEdgeListTo);

        sortedConnectionWeight.clear();
        sortedConnections.clear();


        shortestPathList.clear();

        visitedEdgeListFrom.clear();
        visitedEdgeListTo.clear();

        tempLabels.clear();
        permLabels.clear();
        prevVertexList.clear();
        criticalVertex = -1;

        ArrayList<Float> negativeArray = new ArrayList<>();
        for (int i = 0; i < vertexCoordsX.size(); i++) {
            negativeArray.add(0f);
            prevVertexList.add(-1);
        }


        tempLabels.addAll(negativeArray);
        permLabels.addAll(negativeArray);


        bfsDijkstra(new ArrayList<Integer>(), -1, startVertex, endVertex);


        Collections.reverse(shortestPathList);
        float totalWeight = permLabels.get(endVertex);

        String disTotalWeight = Float.toString(totalWeight);

        if (totalWeight == (int)totalWeight)
            disTotalWeight = Integer.toString((int)totalWeight);


        if (shortestPathList.size() > 0) {


            for (int vertex : shortestPathList) {

                if (vertex == shortestPathList.get(0)) {
                    visitedEdgeListFrom.add(vertex);
                } else if (vertex == shortestPathList.get(shortestPathList.size() - 1)) {
                    visitedEdgeListTo.add(vertex);
                } else {
                    visitedEdgeListFrom.add(vertex);
                    visitedEdgeListTo.add(vertex);
                }

            }


            String dispVertexList = shortestPathList.toString();
            dispVertexList = dispVertexList.replace("[", "");
            dispVertexList = dispVertexList.replace("]", "");

            layout.setText(whiteFont, dispVertexList);

            if (layout.width > Gdx.graphics.getWidth() * 0.2f)
                dispVertexList = dispVertexList.substring(0, 15) + "...";


            popupBox.getTitleLabel().setText("Dijkstra's Algorithm:");
            popupLabel.setText("Path: " + dispVertexList + "\n" + "Total Weight: " + disTotalWeight);


        } else {

            popupBox.getTitleLabel().setText("Dijkstra's Algorithm:");
            popupLabel.setText("No Path Found");

            System.out.println("No Path Found");

        }

    }

    private void bfsDijkstra(ArrayList<Integer> visited, int lastVertex, int currentVertex, int endVertex) {


        visited.add(currentVertex);
        if (lastVertex == -1) {
            tempLabels.set(currentVertex, 0f);
            permLabels.set(currentVertex, 0f);
        } else {
            permLabels.set(currentVertex, tempLabels.get(currentVertex));
            sortedConnections.remove(0);
            sortedConnectionWeight.remove(0);

        }


        if (currentVertex == endVertex) {
            shortestPathList.add(currentVertex);
            criticalVertex = prevVertexList.get(currentVertex);
            shortestPathList.add(criticalVertex);
            return;
        }


        ArrayList<Integer> connections = new ArrayList<>();
        ArrayList<Float> connectionWeight = new ArrayList<>();
        for (int i = 0; i < undirectedEdgeListFrom.size(); i++) {
            if (undirectedEdgeListFrom.get(i) == currentVertex && !connections.contains(undirectedEdgeListTo.get(i)) && !visited.contains(undirectedEdgeListTo.get(i)) && !sortedConnections.contains(undirectedEdgeListTo.get(i))) {
                connections.add(undirectedEdgeListTo.get(i));
                connectionWeight.add(undirectedEdgeWeightList.get(i) + permLabels.get(currentVertex));
            }
        }





        if (sortedConnectionWeight.size() > 0) {

            for (int i = 0; i < connectionWeight.size(); i++) {

                for (int j = 0; j < sortedConnectionWeight.size(); j++) {
                    if (connectionWeight.get(i) < sortedConnectionWeight.get(j)) {
                        sortedConnectionWeight.add(j, connectionWeight.get(i));
                        sortedConnections.add(j, connections.get(i));
                        break;
                    }
                }

                if (!sortedConnections.contains(connections.get(i))) {
                    sortedConnectionWeight.add(connectionWeight.get(i));
                    sortedConnections.add(connections.get(i));
                }

            }

        } else {

            sortedConnectionWeight.addAll(connectionWeight);
            Collections.sort(sortedConnectionWeight);

            for (float connection : sortedConnectionWeight) {
                sortedConnections.add(connections.get(connectionWeight.indexOf(connection)));
                connectionWeight.set(connectionWeight.indexOf(connection), -1f);
            }

        }


        for (int connection : sortedConnections) {


            if (lastVertex == -1 || tempLabels.get(connection) == 0) {
                tempLabels.set(connection, tempLabels.get(currentVertex) + edgeWeightList.get(findWeightIndex(currentVertex, connection)));
                prevVertexList.set(connection, currentVertex);
            } else {
                for (int vertex : visited) {
                    int weightIndex = findWeightIndex(vertex, connection);

                    System.out.println("weight " + vertex + " " + connection + " " + weightIndex);

                    if (weightIndex != -1) {
                        if (tempLabels.get(connection) > tempLabels.get(vertex) + undirectedEdgeWeightList.get(weightIndex)) {
                            tempLabels.set(connection, tempLabels.get(vertex) + undirectedEdgeWeightList.get(weightIndex));

                            prevVertexList.set(connection, vertex);
                        }
                    }

                }
            }
        }


        if (sortedConnections.size() != 0) {

            bfsDijkstra(visited, currentVertex, sortedConnections.get(0), endVertex);

            if (criticalVertex == currentVertex && prevVertexList.get(currentVertex) != -1) {
                criticalVertex = prevVertexList.get(currentVertex);
                shortestPathList.add(criticalVertex);
            }
        }

    }

    private void cpaAlg(int startVertex) {

         undirectedEdgeListFrom.clear();
         undirectedEdgeListTo.clear();
         undirectedEdgeWeightList.clear();

         undirectedEdgeWeightList.addAll(edgeWeightList);
         undirectedEdgeWeightList.addAll(edgeWeightList);

         undirectedEdgeListTo.addAll(edgeListTo);
         undirectedEdgeListFrom.addAll(edgeListFrom);


         earliestEventTimeList.clear();
         latestEventTimeList.clear();


         visitedEdgeListFrom.clear();
         visitedEdgeListTo.clear();

        sortedConnections.clear();
        sortedConnectionWeight.clear();


        sortedPreviousVertex.clear();


        ArrayList<Float> negativeArray = new ArrayList<>();
        for (int i = 0; i < vertexCoordsX.size(); i++) {
            negativeArray.add(-1f);
        }

        earliestEventTimeList.addAll(negativeArray);
        latestEventTimeList.addAll(negativeArray);


        bfsForwardPass(new ArrayList<Integer>(), -1, startVertex);



        if (!graphHasCycle && !earliestEventTimeList.contains(-1f)) {

            undirectedEdgeListFrom.clear();
            undirectedEdgeListTo.clear();

            undirectedEdgeListTo.addAll(edgeListFrom);
            undirectedEdgeListFrom.addAll(edgeListTo);

            sortedConnections.clear();
            sortedConnectionWeight.clear();


            bfsBackwardPass(new ArrayList<Integer>(), -1, earliestEventTimeList.indexOf(Collections.max(earliestEventTimeList)));


            if (!graphHasCycle && !latestEventTimeList.contains(-1f)) {


                ArrayList<Integer> from = new ArrayList<>(visitedEdgeListFrom);
                ArrayList<Integer> to = new ArrayList<>(visitedEdgeListTo);

                Collections.reverse(from);
                Collections.reverse(to);

                String dispVertexList = from + ", " + to.get(to.size()-1).toString();

                dispVertexList = dispVertexList.replace("[", "");
                dispVertexList = dispVertexList.replace("]", "");

                layout.setText(whiteFont, dispVertexList);

                if (layout.width > Gdx.graphics.getWidth() * 0.2f)
                    dispVertexList = dispVertexList.substring(0, 15) + "...";


                popupLabel.setText("Critical Vertices: " + dispVertexList   + "\n"  +  "Minimum Completion Time: " + earliestEventTimeList.get(earliestEventTimeList.size()-1));






            }


            else if (graphHasCycle){
                popupLabel.setText("Graph Has Cycle!");
            }
            else{
                popupLabel.setText("Graph Is Not Suitable For CPA!");
            }

        }
        else if (graphHasCycle){
            popupLabel.setText("Graph Has Cycle!");
        }
        else{
            popupLabel.setText("Graph Is Not Suitable For CPA!");
        }

        popupBox.getTitleLabel().setText("Critical Path Analysis:");





    }

    private void bfsForwardPass(ArrayList<Integer> visited, int lastVertex, int currentVertex) {


        visited.add(currentVertex);


        if (lastVertex == -1)
            earliestEventTimeList.set(currentVertex, 0f);
        else{
            sortedConnections.remove(0);
            sortedConnectionWeight.remove(0);
        }


        ArrayList<Integer> connections = new ArrayList<>();
        ArrayList<Float> connectionWeight = new ArrayList<>();
        for (int i = 0; i < undirectedEdgeListFrom.size(); i++) {
            if (undirectedEdgeListFrom.get(i) == currentVertex && !connections.contains(undirectedEdgeListTo.get(i))) {  // && !visited.contains(undirectedEdgeListTo.get(i))   needs cycle detectino
                connections.add(undirectedEdgeListTo.get(i));
                connectionWeight.add(undirectedEdgeWeightList.get(i) + earliestEventTimeList.get(currentVertex));
            }
        }


        sortedConnections.addAll(connections);
        sortedConnectionWeight.addAll(connectionWeight);


        for (int connection : sortedConnections){
            if (sortedConnectionWeight.get(sortedConnections.indexOf(connection)) > earliestEventTimeList.get(connection))
                earliestEventTimeList.set(connection,sortedConnectionWeight.get(sortedConnections.indexOf(connection)));
        }


        System.out.println("connections   " + sortedConnections + "  " + sortedConnectionWeight);
        System.out.println("times   " + earliestEventTimeList);

        if (sortedConnections.size() != 0) {

          //  for (Integer sortedConnection : sortedConnections) {
            for (int i=0; i< sortedConnections.size(); i++) {

                if (visited.size() <= edgeListFrom.size() * edgeListFrom.size()){
                    bfsForwardPass(visited, currentVertex, sortedConnections.get(0));
                }
                else {
                    System.out.println(visited.size() + " " + edgeListFrom.size());
                    System.out.println(visited + "    " + edgeListFrom);
                    graphHasCycle = true;
                    return;
                }

            }
        }

    }

    private void bfsBackwardPass(ArrayList<Integer> visited, int lastVertex, int currentVertex) {


        visited.add(currentVertex);


        if (lastVertex == -1) {
            latestEventTimeList.set(currentVertex, earliestEventTimeList.get(currentVertex));
           // visitedEdgeListTo.add(currentVertex);
        }
        else{
            sortedConnections.remove(0);
            sortedConnectionWeight.remove(0);
            sortedPreviousVertex.remove(0);
        }


        ArrayList<Integer> previousVertex = new ArrayList<>();
        ArrayList<Integer> connections = new ArrayList<>();
        ArrayList<Float> connectionWeight = new ArrayList<>();
        for (int i = 0; i < undirectedEdgeListFrom.size(); i++) {
            if (undirectedEdgeListFrom.get(i) == currentVertex && !connections.contains(undirectedEdgeListTo.get(i))) {  // && !visited.contains(undirectedEdgeListTo.get(i))   needs cycle detectino
                connections.add(undirectedEdgeListTo.get(i));
                connectionWeight.add(latestEventTimeList.get(currentVertex) - undirectedEdgeWeightList.get(i));
                previousVertex.add(currentVertex);
            }
        }


        sortedConnections.addAll(connections);
        sortedConnectionWeight.addAll(connectionWeight);
        sortedPreviousVertex.addAll(previousVertex);

        for (int connection : sortedConnections){
            if (latestEventTimeList.get(connection) == -1  ||  sortedConnectionWeight.get(sortedConnections.indexOf(connection)) < latestEventTimeList.get(connection)) {
                latestEventTimeList.set(connection, sortedConnectionWeight.get(sortedConnections.indexOf(connection)));
//                System.out.println("latest con " +latestEventTimeList.get(connection));
//                System.out.println("earlys con " +earliestEventTimeList.get(connection));


                System.out.println(sortedConnections + " --- " + currentVertex);


                if (Objects.equals(latestEventTimeList.get(connection), earliestEventTimeList.get(connection))){
                    visitedEdgeListTo.add(sortedPreviousVertex.get(sortedConnections.indexOf(connection)));
                    visitedEdgeListFrom.add(connection);

                }
            }
        }


        System.out.println("connections   " + sortedConnections + "  " + sortedConnectionWeight);
        System.out.println("times   " + earliestEventTimeList);

        if (sortedConnections.size() != 0) {

            //  for (Integer sortedConnection : sortedConnections) {
            for (int i=0; i< sortedConnections.size(); i++) {

                if (visited.size() <= edgeListFrom.size() * edgeListFrom.size()){
                    bfsBackwardPass(visited, currentVertex, sortedConnections.get(0));
                }
                else {
                    graphHasCycle = true;
                    return;
                }

            }
        }

    }



    private void drawCpaTimes(){

        sr.begin(ShapeRenderer.ShapeType.Filled);


        for(int i=0; i<vertexCoordsX.size();i++) {

            sr.setColor(Color.WHITE);
            sr.rect(vertexCoordsX.get(i) - vertexSize * 2, vertexCoordsY.get(i) - vertexSize, vertexSize * 4, vertexSize * 2);


            sr.setColor(Color.BLACK);

            float lineSize = vertexSize/10;

            float leftX = vertexCoordsX.get(i) - vertexSize*2;
            float topY = vertexCoordsY.get(i) - vertexSize;
            float rightX = vertexCoordsX.get(i) + vertexSize*2;
            float bottomY = vertexCoordsY.get(i) + vertexSize;


            sr.rectLine(leftX,topY,rightX,topY,lineSize);
            sr.rectLine(leftX,bottomY,rightX,bottomY,lineSize);
            sr.rectLine(leftX,bottomY,leftX,topY,lineSize);
            sr.rectLine(rightX,bottomY,rightX,topY,lineSize);
            sr.rectLine(vertexCoordsX.get(i),bottomY,vertexCoordsX.get(i),topY,lineSize);
        }

        sr.end();


        batch.begin();
        for(int i=0; i<vertexCoordsX.size();i++) {

            String early;
            String late;

            if (earliestEventTimeList.get(i) == (int) (float) earliestEventTimeList.get(i)) {
                early = Integer.toString((int) (float) earliestEventTimeList.get(i));
            } else {
                early = earliestEventTimeList.get(i).toString();
            }
            if (latestEventTimeList.get(i) == (int) (float) latestEventTimeList.get(i)) {
                late = Integer.toString((int) (float) latestEventTimeList.get(i));
            } else {
                late = latestEventTimeList.get(i).toString();
            }


            layout.setText(smallFont, early);
            if (layout.width > vertexSize * 2) {
                early = early.substring(0, 3) + "...";
                layout.setText(smallFont, early);
                if (layout.width > vertexSize * 2) {
                    early = "...";
                    layout.setText(smallFont, early);
                }
            }
            smallFont.draw(batch, early, vertexCoordsX.get(i) - vertexSize - layout.width * 0.5f, vertexCoordsY.get(i) + layout.height * 0.5f);

            layout.setText(smallFont, late);
            if (layout.width > vertexSize * 2){
                late = late.substring(0, 3) + "...";
                layout.setText(smallFont, late);
                if (layout.width > vertexSize * 2){
                    late = "...";
                    layout.setText(smallFont, early);
                }
            }
            smallFont.draw(batch, late, vertexCoordsX.get(i) + vertexSize - layout.width * 0.5f, vertexCoordsY.get(i) + layout.height * 0.5f);

        }
        batch.end();
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


//https://www.geeksforgeeks.org/union-find/