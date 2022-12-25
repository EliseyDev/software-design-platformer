package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.controller.InputController;
import ru.mipt.bit.platformer.controller.impl.KeyboardController;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Player;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.movement.MovementProcessor;
import ru.mipt.bit.platformer.movement.Orientation;
import ru.mipt.bit.platformer.movement.TileMovement;
import ru.mipt.bit.platformer.movement.impl.PlayerMovementProcessor;

import java.util.List;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TanksGame implements ApplicationListener {
    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private GameObject player;
    private List<GameObject> staticSceneObjects;

    private MovementProcessor playerMovementProcessor;

    private static final String PLAYER_TEXTURE = "images/tank_blue.png";
    private static final String TREE_TEXTURE = "images/greenTree.png";
    private static final String LEVEL_MAP = "level.tmx";

    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load(LEVEL_MAP);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);

        InputController playerController = new KeyboardController();
        input.setInputProcessor(playerController);


        player = new Player(new Texture(PLAYER_TEXTURE), new GridPoint2(1, 1));

        staticSceneObjects = List.of(
                new Tree(new Texture(TREE_TEXTURE), new GridPoint2(1, 3)),
                new Tree(new Texture(TREE_TEXTURE), new GridPoint2(3, 6)
                ));
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.linear);

        playerMovementProcessor = new PlayerMovementProcessor(player, staticSceneObjects, tileMovement, playerController, DEFAULT_MOVEMENT_SPEED);

        placeStaticObjectsOnScene(groundLayer);

    }

    @Override
    public void render() {
        cleanScreen();

        playerMovementProcessor.moveGameObject();

        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        renderSceneObjects();

        // submit all drawing requests
        batch.end();
    }

    private void cleanScreen() {
        gl.glClearColor(0f, 0f, 0.2f, 1f);
        gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        player.getTexture().dispose();
        staticSceneObjects.forEach(gameObject -> gameObject.getTexture().dispose());
        level.dispose();
        batch.dispose();
    }

    private void renderSceneObjects() {
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getShape(), player.getRotation());

        staticSceneObjects.forEach(gameObject -> drawTextureRegionUnscaled(batch, gameObject.getGraphics(), gameObject.getShape(),
                Orientation.NONE.getDegree()));
    }

    private void placeStaticObjectsOnScene(TiledMapTileLayer groundLayer) {
        staticSceneObjects.forEach(gameObject -> moveRectangleAtTileCenter(groundLayer, gameObject.getShape(),
                gameObject.getCoordinates()));
    }

}
