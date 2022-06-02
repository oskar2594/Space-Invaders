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
package de.pogs.rl.game.world.entities;

import java.awt.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.pogs.rl.RocketLauncher;
import de.pogs.rl.game.GameScreen;
import de.pogs.rl.utils.SpecialMath;
import de.pogs.rl.utils.SpecialMath.Vector2;

public class TankEnemy extends SimpleEnemy {

    private float hp = 20;
    private Texture aimTexture = RocketLauncher.getAssetHelper().getImage("aimbeam");
    private Sprite aimSprite = new Sprite(aimTexture);

    public TankEnemy(Vector2 position) {
        super(position, RocketLauncher.getAssetHelper().getImage("monster2"));
        shootingCoeff = 0.1f;
        bulletSpeed = 1000;
        bulletDamage = 25;
        bulletColor = new Color(30, 255, 30, 255);
        aimSprite.setPosition(position.getX(), position.getY());
        aimSprite.setOrigin(0, 0);
        aimSprite.setAlpha(0);

    }

    public TankEnemy(float x, float y) {
        this(new Vector2(x, y));
    }

    @Override
    public void addDamage(float damage, AbstractEntity source) {
        hp -= damage;
        if (hp < 0) {
            this.alive = false;
            source.killOtherEvent(this);
        }
        splashEffectSelf();
    }

    @Override
    protected void shoot(float delta) {
        if (GameScreen.getPlayer().getPosition().dst2(position) < sightRange) {
            float flightTime = GameScreen.getPlayer().getPosition().dst(position) / bulletSpeed;
            Vector2 playerPosPredicted =
                    GameScreen.getPlayer().getPosition().add(GameScreen.getPlayer().getVelocity().mul(flightTime));
            Vector2 bulletDirection = playerPosPredicted.sub(position).nor();
            aimSprite.setRotation(90 - SpecialMath.VectorToAngle(bulletDirection.mul(-1)));
            aimSprite.setAlpha(1);
            if (Math.random() < shootingCoeff * delta) {
                Bullet.createBullet(position, this, bulletDamage,
                        bulletDirection.mul(bulletSpeed).add(velocity), bulletColor, 10000);
            }
        } else {
            aimSprite.setAlpha(0);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        aimSprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        aimSprite.setPosition(position.getX(), position.getY());
    }
}
