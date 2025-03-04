package org.mateh.laser.effects;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

public class BlinkEffect extends AbstractEffect {
    private BukkitRunnable task;
    private BlockData originalBlockData;

    public BlinkEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
        this.originalBlockData = blockDisplay.getBlock();
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            private boolean visible = true;

            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    if (visible) {
                        blockDisplay.setBlock(originalBlockData);
                    } else {
                        blockDisplay.setBlock(Material.AIR.createBlockData());
                    }
                    visible = !visible;
                } else {
                    cancel();
                }
            }
        };

        task.runTaskTimer(main, 0, 10);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
        blockDisplay.setBlock(originalBlockData);
    }
}
