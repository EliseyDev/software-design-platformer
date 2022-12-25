package ru.mipt.bit.platformer.movement;

public enum Orientation {
    UP(90f),
    DOWN(-90f),
    LEFT(-180f),
    RIGHT(0f),
    NONE(0f);

    private final float degree;

    Orientation(float degree) {
        this.degree = degree;
    }

    public float getDegree() {
        return this.degree;
    }


}
