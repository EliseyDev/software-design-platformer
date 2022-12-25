package ru.mipt.bit.platformer.movement.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.controller.InputController;
import ru.mipt.bit.platformer.exception.WrongDirectionException;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.movement.Direction;
import ru.mipt.bit.platformer.movement.MovementProcessor;
import ru.mipt.bit.platformer.movement.Orientation;
import ru.mipt.bit.platformer.movement.TileMovement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static java.lang.String.*;
import static ru.mipt.bit.platformer.movement.Direction.*;
import static ru.mipt.bit.platformer.movement.Orientation.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public final class PlayerMovementProcessor implements MovementProcessor {
    private final GameObject player;
    private final List<GameObject> staticObjects;

    private final InputController keyboardController;

    private final TileMovement tileMovement;

    private final GridPoint2 playerDestinationTile;

    private float currentMovementProgress = 1f;

    private float movementSpeed = 0.4f;

    private static final float READY_MOVEMENT_PROGRESS = 1f;
    private static final int MOVEMENT_STEP = 1;

    public PlayerMovementProcessor(final GameObject player, List<GameObject> staticObjects, TileMovement tileMovement,
                                   InputController keyboardController, float movementSpeed) {
        this(player, staticObjects, tileMovement, keyboardController);
        this.movementSpeed = movementSpeed;
    }

    public PlayerMovementProcessor(final GameObject player, List<GameObject> staticObjects, TileMovement tileMovement,
                                   InputController keyboardController) {
        this.player = player;
        this.staticObjects = staticObjects;
        this.keyboardController = keyboardController;
        this.playerDestinationTile = new GridPoint2(player.getCoordinates().x, player.getCoordinates().y);
        this.tileMovement = tileMovement;
    }

    @Override
    public void moveGameObject() {
        float deltaTime = getDeltaTime();

        if (keyboardController.moveUp()) {
            movePlayer(NORTH, UP);
        }

        if (keyboardController.moveRight()) {
            movePlayer(EAST, RIGHT);
        }

        if (keyboardController.moveDown()) {
            movePlayer(SOUTH, DOWN);
        }

        if (keyboardController.moveLeft()) {
            movePlayer(WEST, LEFT);
        }

        tileMovement.moveRectangleBetweenTileCenters(player.getShape(), player.getCoordinates(), playerDestinationTile,
                currentMovementProgress);

        currentMovementProgress = continueProgress(currentMovementProgress, deltaTime, movementSpeed);

        if (playerCanMove()) {
            player.getCoordinates().set(playerDestinationTile);
        }
    }

    private static float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    private void changeDestinationPoint(Direction direction) {
        switch (direction) {
            case NORTH: {
                playerDestinationTile.y++;
                break;
            }
            case SOUTH: {
                playerDestinationTile.y--;
                break;
            }
            case EAST: {
                playerDestinationTile.x++;
                break;
            }
            case WEST: {
                playerDestinationTile.x--;
                break;
            }
        }
    }

    private GridPoint2 getDestinationPoint(Direction direction) {
        switch (direction) {
            case NORTH: {
                return new GridPoint2(player.getCoordinates().x, player.getCoordinates().y + MOVEMENT_STEP);
            }
            case SOUTH: {
                return new GridPoint2(player.getCoordinates().x, player.getCoordinates().y - MOVEMENT_STEP);
            }
            case EAST: {
                return new GridPoint2(player.getCoordinates().x + MOVEMENT_STEP, player.getCoordinates().y);
            }
            case WEST: {
                return new GridPoint2(player.getCoordinates().x - MOVEMENT_STEP, player.getCoordinates().y);
            }
            default: {
                throw new WrongDirectionException(format("Current direction is not implemented: %s", direction));
            }
        }
    }

    private boolean playerCanMove() {
        return isEqual(currentMovementProgress, READY_MOVEMENT_PROGRESS);
    }

    private boolean nonCollisionsOnDestinationPoint(GridPoint2 destinationCoordinates) {
        return staticObjects.stream().noneMatch(gameObject -> gameObject.getCoordinates().equals(destinationCoordinates));
    }

    private void movePlayer(Direction direction, Orientation orientation) {
        if (playerCanMove()) {
            if (nonCollisionsOnDestinationPoint(getDestinationPoint(direction))) {
                changeDestinationPoint(direction);
                currentMovementProgress = 0f;
            }
            player.setRotation(orientation.getDegree());
        }
    }
}
