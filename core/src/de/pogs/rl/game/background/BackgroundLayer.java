package de.pogs.rl.game.background;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.pogs.rl.game.GameScreen;
import de.pogs.rl.utils.FastNoiseLite;
import de.pogs.rl.utils.PerlinNoiseGenerator;

public final class BackgroundLayer {
    public static BackgroundLayer INSTANCE;

    private int radius;

    public ChunkManager chunkManager;

    private double seed;

    public BackgroundLayer() {
        INSTANCE = this;
        radius = 50;
        seed = new Random().nextGaussian() * 255;
        chunkManager = new ChunkManager(radius, seed);

    }

    public BitmapFont font = new BitmapFont();

    public void render(float delta, final SpriteBatch batch) {
        long startTime = System.nanoTime();

        chunkManager.render(delta, batch);
        long endTime = System.nanoTime();
        // lights.update(batch);
        // stars.update(batch);
        long duration = (endTime - startTime);

        // System.out.println(duration / 1000000);
        font.draw(batch,
                Gdx.graphics.getFramesPerSecond() + " | " + (duration / 1000000) + "ms",
                GameScreen.INSTANCE.camera.position.x - Gdx.graphics.getWidth() / 3,
                GameScreen.INSTANCE.camera.position.y - Gdx.graphics.getHeight() / 3);

        // starSprite.draw(batch);
        // lightSprite.draw(batch);
    }

    public void update() {
        chunkManager.update();
    }

    private double distance(Vector2 start, Vector2 end) {
        return Math.sqrt((end.y - start.y) * (end.y - start.y) + (end.x - start.x) * (end.x - start.x));
    }
}
