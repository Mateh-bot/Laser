package org.mateh.laser.effects;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.scheduler.BukkitRunnable;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class ColorPulseEffect extends AbstractEffect {
    private BukkitRunnable task;
    private int step = 0;

    public ColorPulseEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    int brightness = (int) (8 + Math.sin(step * Math.PI / 10) * 7);
                    brightness = Math.max(0, Math.min(15, brightness));
                    blockDisplay.setBrightness(new Display.Brightness(brightness, 0));
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
