package de.pogs.rl.game.world.entities;

import java.awt.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import de.pogs.rl.RocketLauncher;
import de.pogs.rl.game.GameScreen;
import de.pogs.rl.game.world.particles.ParticleEmitter;
import de.pogs.rl.game.world.particles.ParticleUtils;
import de.pogs.rl.utils.SpecialMath;
import de.pogs.rl.utils.SpecialMath.Vector2;

public class Bullet extends AbstractEntity {
    private Texture texture;

    private ParticleEmitter glow;
    private Sprite sprite;

    private float scale = 0.1f;
    private float width = 2f;
    private float speed;
    private float angle;

    private long deathTime;
    private AbstractEntity sender;

    private float damage;

    private Vector2 velocity;

    /**
     * Factory Methode, die ein Bullet erzeugt und registriert.
     * @param position Initiale Position des Bullet.
     * @param sender Entität, welche das Bullet verschossen hat und dadurch immun gegen das Bullet ist.
     * @param damage Schaden, den das Bullet bei einem Treffer verursachen soll.
     * @param velocity Geschwindigkeitsvektor nach dem Bullet sich bewegt.
     * @param color Farbe des Bullet.
     * @param lifeTime Zeit, nach der Bullet gelöscht wird.
     * @return Das erzeugte Bullet.
     */
    public static Bullet createBullet(Vector2 position, AbstractEntity sender, float damage,
            Vector2 velocity, Color color, long lifeTime) {
                Bullet bullet = new Bullet(position, sender, damage, velocity, color, lifeTime);
                bullet.update(0);
                EntityManager.get().addEntity(bullet);
                return bullet;
    }

    public Bullet(Vector2 position, AbstractEntity sender, float damage, Vector2 velocity,
            Color color, long lifeTime) {
        texture = ParticleUtils.generateParticleTexture(color, (int) width, (int) width * 3);
        sprite = new Sprite(texture);
        this.angle = 180 - SpecialMath.VectorToAngle(velocity);
        sprite.setSize(texture.getWidth() / texture.getHeight() * width, width);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.position = position;
        this.velocity = velocity;
        sprite.setRotation(this.angle + 90);
        this.sender = sender;
        this.damage = damage;
        this.radius = 1;
        this.deathTime = TimeUtils.millis() + lifeTime;
        sprite.setPosition(position.getX() - (sprite.getWidth() / 2),
                position.getY() - sprite.getHeight() / 2);

        glow = GameScreen.INSTANCE.particleManager.createEmitter(new ParticleEmitter(0, 0, -1, 15,
                ParticleUtils.generateParticleTexture(color, (int) width, (int) width * 4), 90, 90,
                0, 800, width, width, .2f, .1f, 0f, .0f, false));
        glow.attach(this.sprite, 0, 0, this);
    }

    @Override
    public void render(SpriteBatch batch) {

        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        updatePosition(delta);
        sprite.setPosition(position.getX() - (sprite.getWidth() / 2),
                position.getY() - sprite.getHeight() / 2);
        for (AbstractEntity entity : EntityManager.get().getCollidingEntities(this)) {
            if (entity != sender) {
                entity.addDamage(damage, sender);
                this.alive = false;
                break;
            }
        }
        if (TimeUtils.millis() > deathTime) {
            this.alive = false;
        }
    }

    private void updatePosition(float delta) {
        position = position.add(velocity.mul(delta));
    }

    @Override
    public void addDamage(float damage, AbstractEntity source) {
        this.alive = false;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}