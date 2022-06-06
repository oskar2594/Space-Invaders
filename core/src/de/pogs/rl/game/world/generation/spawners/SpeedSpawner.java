package de.pogs.rl.game.world.generation.spawners;

import java.util.LinkedList;
import de.pogs.rl.game.world.entities.AbstractEntity;
import de.pogs.rl.game.world.entities.SpeedEnemy;
import de.pogs.rl.game.world.generation.AbstractSpawner;
import de.pogs.rl.utils.SpecialMath.Vector2;

public class SpeedSpawner extends AbstractSpawner {
    public LinkedList<AbstractEntity> spawn(Vector2 chunk) {
        LinkedList<AbstractEntity> list = new LinkedList<AbstractEntity>();
        if (Math.random() < 0.002) {
            list.add(new SpeedEnemy((float) (chunk.getX() + Math.random() * 200),
                    (float) (chunk.getY() + Math.random() * 200)));
        }
        return list;
    }
}