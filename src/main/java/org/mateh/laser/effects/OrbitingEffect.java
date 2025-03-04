package org.mateh.laser.effects;

import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class OrbitingEffect extends AbstractEffect {
    private BukkitRunnable task;

    public OrbitingEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            private double angle = 0;
            private final Location center = blockDisplay.getLocation().clone();

            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    angle += Math.toRadians(10);
                    Vector offset = new Vector(Math.cos(angle), 0, Math.sin(angle)).multiply(2);
                    blockDisplay.teleport(center.clone().add(offset));
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
