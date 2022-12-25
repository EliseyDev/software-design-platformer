package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle shape;

    private final GridPoint2 coordinates;

    private float rotation;

    protected GameObject(Texture texture, GridPoint2 coordinates) {
        this.texture = texture;
        this.graphics = new TextureRegion(texture);
        this.shape = createShapeBounds(graphics);
        this.coordinates = coordinates;
        this.rotation = 0f;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getShape() {
        return shape;
    }

    public Texture getTexture() {
        return texture;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    private static Rectangle createShapeBounds(TextureRegion region) {
        return new Rectangle()
                .setWidth(region.getRegionWidth())
                .setHeight(region.getRegionHeight());
    }
}
