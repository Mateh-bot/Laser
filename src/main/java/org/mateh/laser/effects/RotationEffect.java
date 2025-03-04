package org.mateh.laser.effects;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class RotationEffect extends AbstractEffect {
    private BukkitRunnable task;

    public RotationEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            private float angle = 0;

            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    Quaternionf rotation = new Quaternionf().rotateY((float) Math.toRadians(angle));
                    Transformation currentTransform = blockDisplay.getTransformation();

                    Transformation newTransform = new Transformation(
                            currentTransform.getTranslation(),
                            rotation,
                            currentTransform.getScale(),
                            currentTransform.getRightRotation()
                    );

                    blockDisplay.setTransformation(newTransform);

                    angle += 10;
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
