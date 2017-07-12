package com.cookbook.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {

    private static final float MOVE_DISTANCE = 500; // pixel per second

    private Vector2 position;

    private float scale;

    public CameraHelper() {
        position = new Vector2();
        scale = 1;
    }

    public void update(float deltaTime) {
        float distance = deltaTime * MOVE_DISTANCE;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            position.x -= distance;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            position.x += distance;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            position.y += distance;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            position.y -= distance;
        }
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = scale;

        camera.update();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
