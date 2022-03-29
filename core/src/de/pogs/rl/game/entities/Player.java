package de.pogs.rl.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends AbstractEntity {
    private Texture texture = new Texture(Gdx.files.internal("rakete.png"));
    private Sprite sprite;

    private float angle = 0;
    private float aimedAngle = 0;
    private float angle_response = 1;

    private float speed = 1;

    public Vector2 position = new Vector2(0, 0);

    public Player() {
        sprite = new Sprite(texture);
        sprite.setScale(0.2f);
        position.set(0, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void update(float delta, Input input) {
        updateAimedAngle();
        updateAngle(delta);
        updatePosition(delta);
        sprite.setPosition(position.x - (texture.getWidth() / 2),
                position.y + texture.getHeight() * sprite.getScaleY() / 2);
        sprite.setRotation(angle);
        // if (input.isKeyPressed(Keys.LEFT)) sprite.setPosition(sprite.getX() - speed *
        // delta, sprite.getY());

        // if (input.isKeyPressed(Keys.RIGHT)) sprite.setPosition(sprite.getX() + speed
        // * delta, sprite.getY());
    }

    private void updateAimedAngle() {
        if (Gdx.input.isTouched()) {

            aimedAngle = (float) Math
                    .toDegrees((float) (Math.atan(mouseXfromPlayer()
                            / mouseYfromPlayer())));
            if (mouseXfromPlayer() > 0 && mouseYfromPlayer() > 0) {
                aimedAngle = -180 + aimedAngle;
            }
            if (mouseXfromPlayer() < 0 && mouseYfromPlayer() > 0) {
                aimedAngle = 180 + aimedAngle;
            }

        }
    }

    private float mouseXfromPlayer() {
        return Gdx.input.getX() - (float) (Gdx.graphics.getWidth() / 2);
    }

    private float mouseYfromPlayer() {
        return (float) (Gdx.input.getY() - (float) (Gdx.graphics.getHeight() / 2));
    }

    private void updateAngle(float delta) {
        angle = angle + (angleDifferenceSmaller(aimedAngle, angle)) * delta * angle_response;
    }

    private float angleDifferenceSmaller(float angle1, float angle2) {
        float diff1 = angle1 - angle2;
        float diff2 = diff1 - 360;
        if (Math.abs(diff1) > Math.abs(diff2)) {
            return diff2;
        }
        return diff1;
    }

    private void updatePosition(float delta) {
        // position = position.add(((float) Math.cos(angle)) * delta, ((float)
        // Math.sin(angle)) * delta);
    }
}
