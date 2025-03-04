package org.mateh.laser.effects;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class HelixSpinEffect extends AbstractEffect {
    private BukkitRunnable task;
    private int step = 0;

    public HelixSpinEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    double radius = 0.5;
                    double angle = step * Math.PI / 20;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    Transformation transformation = blockDisplay.getTransformation();
                    Vector3f translation = new Vector3f((float) x, transformation.getTranslation().y, (float) z);
                    blockDisplay.setTransformation(new Transformation(
                            translation,
                            transformation.getLeftRotation(),
                            transformation.getScale(),
                            transformation.getRightRotation()));
                    step++;
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
