/**
 * 
 * MIT LICENSE
 * 
 * Copyright 2022 Philip Gilde & Oskar Stanschus
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @author Philip Gilde & Oskar Stanschus
 * 
 */
package de.pogs.rl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.pogs.rl.utils.CameraShake;

/**
 * RocketCamera
 */
public class RocketCamera extends OrthographicCamera {


    public RocketCamera() {
        super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.zoom = 1f;
    }


    /**
     * Kameragröße bei Veränderung der Bildschrimgröße anpassen
     */
    public void resize(int width, int height) {
        this.viewportHeight = height;
        this.viewportWidth = width;

        this.zoom = 1f;
    }

    private float beforeZoom = 0;

    public void refresh(float delta) {
        // Position der Kamera auf den Spieler setzen
        this.position.set(GameScreen.getPlayer().getPosition().getX(),
                GameScreen.getPlayer().getPosition().getY(), 0);

        // Kamera Wackeln ausführen
        Vector2 shake = CameraShake.getShake();
        this.translate(shake);


        // Zoom Faktor ermitteln & setzen
        float playerSpeed = GameScreen.getPlayer().getVelocity().magn();
        float maxSpeed = GameScreen.getPlayer().getMaxSpeed();
        float zoom = (float) easeInOut((playerSpeed / maxSpeed)) * 0.18f + 0.9f;
        this.zoom = Math.min(zoom, 1.1f);


        // Kamera Wackeln aktivieren / deaktivieren
        if (zoom > .9f && GameScreen.getPlayer().getAccelerating()) {
            CameraShake.setStrength((zoom - .95f) * 10);
        } else {
            CameraShake.setStrength(0);
        }

        // Trigger CameraZoomm Event
        if (beforeZoom != this.zoom) {
            GameScreen.resizeZoom(
                    (int) (Gdx.graphics.getWidth() * GameScreen.getCamera().zoom),
                    (int) (Gdx.graphics.getHeight() * GameScreen.getCamera().zoom));
            beforeZoom = this.zoom;
        }
        this.update();
    }

    private double easeInOut(double number) {
        if (number < 0.5) {
            return 4 * Math.pow(number, 3);
        } else {
            return (number - 1) * (2 * number - 2) * (2 * number - 2) + 1;
        }

    }

}
