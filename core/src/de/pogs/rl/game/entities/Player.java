package de.pogs.rl.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.pogs.rl.utils.SpecialMath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends AbstractEntity {
    private Texture texture = new Texture(Gdx.files.internal("rakete.png"));
    private Sprite sprite;

    private float scale = 0.2f;

    private float angle = 0;
    private float aimedAngle = 0;
    private float angle_response = 1;

    private float speed = 15;

    public Vector2 position = new Vector2(0, 0);

    public Player() {
        sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth() * scale, texture.getHeight() * scale);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        position.set(0, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        updateAimedAngle();
        updateAngle(delta);
        updatePosition(delta);
        // if (input.isKeyPressed(Keys.LEFT)) sprite.setPosition(sprite.getX() - speed *
        // delta, sprite.getY());

        // if (input.isKeyPressed(Keys.RIGHT)) sprite.setPosition(sprite.getX() + speed
        // * delta, sprite.getY());
        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - sprite.getHeight() / 2);
        sprite.setRotation(angle);

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

        angle = angle + (SpecialMath.angleDifferenceSmaller(aimedAngle, angle, 360)) * delta * angle_response;
        angle = SpecialMath.modulus(angle + 180, 360) - 180;

    }

    private void updatePosition(float delta) {
        position = position.add(((float) Math.cos((angle + 90) * Math.PI / 180)) * delta * speed,
                ((float) Math.sin((angle + 90) * (Math.PI / 180))) * delta * speed);
        // System.out.println(position.x);
    }
}
