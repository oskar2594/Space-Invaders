package de.pogs.rl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxNativesLoader;
import de.pogs.rl.utils.CameraShake;

/**
 * RocketCamera
 */
public class RocketCamera extends OrthographicCamera {

    public RocketCamera() {
        super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.zoom = 1f;
    }

    public void resize(int width, int height) {
        this.viewportHeight = height;
        this.viewportWidth = width;
    }

    public void render(float delta) {
        this.position.set(GameScreen.INSTANCE.player.getPosition().x,
                GameScreen.INSTANCE.player.getPosition().y, 0);
        Vector2 shake = CameraShake.getShake();
        this.translate(shake);

        float playerSpeed = GameScreen.INSTANCE.player.getSpeed();
        float maxSpeed = GameScreen.INSTANCE.player.getMaxSpeed();
        float zoom = (float) easeInOut((playerSpeed / maxSpeed)) * 0.18f + 0.9f;
        this.zoom = Math.min(zoom, 1.1f);

        if (zoom > .95f && GameScreen.INSTANCE.player.isAccelerating()) {
            CameraShake.activate((zoom - .9f) * 20);
        } else {
            CameraShake.deactivate();
        }

        GameScreen.INSTANCE.resizeZoom(
                (int) (Gdx.graphics.getWidth() * GameScreen.INSTANCE.camera.zoom),
                (int) (Gdx.graphics.getHeight() * GameScreen.INSTANCE.camera.zoom));
    }

    private double easeInOut(double number) {
        if (number < 0.5) {
            return 4 * Math.pow(number, 3);
        } else {
            return (number - 1) * (2 * number - 2) * (2 * number - 2) + 1;
        }

    }

}
