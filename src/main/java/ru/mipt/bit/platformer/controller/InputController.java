package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.InputProcessor;

public interface InputController extends InputProcessor {
    boolean moveLeft();
    boolean moveRight();
    boolean moveUp();
    boolean moveDown();
}
