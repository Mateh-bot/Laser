package org.mateh.laser.abstracts;

import org.bukkit.entity.BlockDisplay;
import org.mateh.laser.Main;

public abstract class AbstractEffect {
    protected final BlockDisplay blockDisplay;
    protected final Main main;

    public AbstractEffect(BlockDisplay blockDisplay, Main main) {
        this.blockDisplay = blockDisplay;
        this.main = main;
    }

    public abstract void start();

    public abstract void stop();
}
