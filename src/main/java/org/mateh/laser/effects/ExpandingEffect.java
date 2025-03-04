package org.mateh.laser.effects;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class ExpandingEffect extends AbstractEffect {
    private BukkitRunnable task;

    public ExpandingEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            private float scale = 10.0f;
            private boolean expanding = true;

            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    scale += expanding ? 0.2f : -0.2f;
                    if (scale >= 15.0f) expanding = false;
                    if (scale <= 5.0f) expanding = true;

                    Transformation transformation = new Transformation(
                            new Vector3f(0, 0, 0),
                            new Quaternionf(),
                            new Vector3f(0.1f, scale, 0.1f),
                            new Quaternionf()
                    );

                    blockDisplay.setTransformation(transformation);
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
