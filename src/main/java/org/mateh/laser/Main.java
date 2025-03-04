package org.mateh.laser;

import org.bukkit.plugin.java.JavaPlugin;
import org.mateh.laser.commands.LaserCommand;
import org.mateh.laser.managers.LaserManager;
import org.mateh.laser.managers.LaserTabCompleter;

public final class Main extends JavaPlugin {
    private LaserManager laserManager;

    @Override
    public void onEnable() {
        laserManager = new LaserManager(this);
        getCommand("laser").setExecutor(new LaserCommand(this));
        getCommand("laser").setTabCompleter(new LaserTabCompleter());
        laserManager.loadLasers();
    }

    @Override
    public void onDisable() {
        laserManager = new LaserManager(this);
        laserManager.saveLasers();
    }

    public LaserManager getLaserManager() {
        return laserManager;
    }
}
