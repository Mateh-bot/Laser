package org.mateh.laser.effects;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;

import java.util.Arrays;
import java.util.List;

public class ColorCycleEffect extends AbstractEffect {
    private BukkitRunnable task;
    private final List<BlockData> colors;
    private int colorIndex = 0;

    public ColorCycleEffect(BlockDisplay blockDisplay, Main main) {
        super(blockDisplay, main);
        this.colors = Arrays.asList(
                Material.RED_CONCRETE.createBlockData(),
                Material.BLUE_CONCRETE.createBlockData(),
                Material.GREEN_CONCRETE.createBlockData(),
                Material.YELLOW_CONCRETE.createBlockData(),
                Material.ORANGE_CONCRETE.createBlockData(),
                Material.PURPLE_CONCRETE.createBlockData(),
                Material.WHITE_CONCRETE.createBlockData(),
                Material.LIGHT_GRAY_CONCRETE.createBlockData(),
                Material.GRAY_CONCRETE.createBlockData(),
                Material.BLACK_CONCRETE.createBlockData(),
                Material.BROWN_CONCRETE.createBlockData(),
                Material.LIME_CONCRETE.createBlockData(),
                Material.CYAN_CONCRETE.createBlockData(),
                Material.LIGHT_BLUE_CONCRETE.createBlockData(),
                Material.MAGENTA_CONCRETE.createBlockData(),
                Material.PINK_CONCRETE.createBlockData()
        );
    }

    @Override
    public void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (blockDisplay.isValid()) {
                    blockDisplay.setBlock(colors.get(colorIndex));
                    colorIndex = (colorIndex + 1) % colors.size();
                } else {
                    cancel();
                }
            }
        };

        task.runTaskTimer(main, 0, 20);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }
}
