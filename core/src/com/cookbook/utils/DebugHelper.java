package com.cookbook.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class DebugHelper {

    private static final String TAG = DebugHelper.class.getName();

    // Độ dài của cả trục
    private static final float LINE_LENGTH = 5000f;

    // Khoảng cách giữa các trục
    private static final float LINE_STEP = 20;

    // Vị trí của trục đánh dấu (để dễ nhận biết)
    private static final int MARK_STEP = 5;

    // Màu của gốc tọa độ
    private static final Color ROOT_COLOR = Color.RED;

    // Màu của trục thường
    private static final Color NORMAL_COLOR = Color.GRAY;

    // Màu của trục đánh dấu
    private static final Color MARK_COLOR = Color.YELLOW;

    private boolean enabled;

    private ShapeRenderer shapeRenderer;

    public DebugHelper() {
        init();
    }

    private void init() {
        enabled = false;
        shapeRenderer = new ShapeRenderer();
    }

    public void render(OrthographicCamera camera) {
        if (!enabled) return;

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        float haflLength = LINE_LENGTH / 2f;

        // Vẽ gốc tọa độ (màu đỏ)
        shapeRenderer.setColor(ROOT_COLOR);
        shapeRenderer.line(-haflLength, 0, haflLength, 0); // trục hoành
        shapeRenderer.line(0, -haflLength, 0, haflLength); // trục tung

        // Vẽ trục phụ và trục đánh dấu
        int numAxis = MathUtils.floor(LINE_LENGTH / LINE_STEP / 2);

        for (int i = 1; i < numAxis; i++) {
            if (i % MARK_STEP == 0) {
                shapeRenderer.setColor(MARK_COLOR);
            } else {
                shapeRenderer.setColor(NORMAL_COLOR);
            }

            // trục ngang
            shapeRenderer.line(-haflLength, -LINE_STEP * i, haflLength, -LINE_STEP * i);
            shapeRenderer.line(-haflLength, LINE_STEP * i, haflLength, LINE_STEP * i);

            // trục dọc
            shapeRenderer.line(-LINE_STEP * i, -haflLength, -LINE_STEP * i, haflLength);
            shapeRenderer.line(LINE_STEP * i, -haflLength, LINE_STEP * i, haflLength);
        }

        shapeRenderer.end();
    }

    public void toggleEnabled() {
        enabled = !enabled;
    }
}
