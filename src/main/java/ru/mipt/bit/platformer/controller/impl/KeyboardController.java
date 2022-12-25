package ru.mipt.bit.platformer.controller.impl;

import ru.mipt.bit.platformer.controller.InputController;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardController implements InputController {
    private boolean isLeft;
    private boolean isRight;
    private boolean isUp;
    private boolean isDown;

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;

        switch (keycode) {
            case LEFT:
            case A:
                isLeft = true;
                keyProcessed = true;
                break;
            case RIGHT:
            case D:
                isRight = true;
                keyProcessed = true;
                break;
            case UP:
            case W:
                isUp = true;
                keyProcessed = true;
                break;
            case DOWN:
            case S:
                isDown = true;
                keyProcessed = true;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;

        switch (keycode) {
            case LEFT:
            case A:
                isLeft = false;
                keyProcessed = true;
                break;
            case RIGHT:
            case D:
                isRight = false;
                keyProcessed = true;
                break;
            case UP:
            case W:
                isUp = false;
                keyProcessed = true;
                break;
            case DOWN:
            case S:
                isDown = false;
                keyProcessed = true;
        }
        return keyProcessed;
    }

    @Override
    public boolean moveLeft() {
        return this.isLeft;
    }

    @Override
    public boolean moveRight() {
        return this.isRight;
    }

    @Override
    public boolean moveUp() {
        return this.isUp;
    }

    @Override
    public boolean moveDown() {
        return this.isDown;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
