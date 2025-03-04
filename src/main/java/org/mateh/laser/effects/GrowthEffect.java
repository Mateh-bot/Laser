package org.mateh.laser.effects;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class GrowthEffect extends AbstractEffect {
    private BukkitRunnable task;
    private boolean growing = true;
    private float scale = 0.1f;

    public GrowthEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    if (growing) {
                        scale += 0.2f;
                        if (scale >= 10f) {
                            growing = false;
                        }
                    } else {
                        scale -= 0.2f;
                        if (scale <= 0.1f) {
                            growing = true;
                        }
                    }
                    Transformation transformation = blockDisplay.getTransformation();
                    Vector3f newScale = new Vector3f(0.1f, scale, 0.1f);
                    blockDisplay.setTransformation(new Transformation(
                            transformation.getTranslation(),
                            transformation.getLeftRotation(),
                            newScale,
                            transformation.getRightRotation()));
                } else {
                    cancel();
                }
            }
        };
        task.runTaskTimer(main, 0, 2);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }
}
