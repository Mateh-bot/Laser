package org.mateh.laser.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.mateh.laser.Main;
import org.mateh.laser.config.CustomConfig;
import org.mateh.laser.data.LaserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LaserManager {
    private final Map<UUID, LaserData> lasers = new HashMap<>();
    private final CustomConfig laserConfig;
    private final Main main;

    public LaserManager(Main main) {
        this.main = main;
        this.laserConfig = new CustomConfig("lasers.yml", null, main);
        this.laserConfig.registerConfig();
    }

    public UUID addLaser(BlockDisplay blockDisplay) {
        UUID id = UUID.randomUUID();
        Location location = blockDisplay.getLocation();
        LaserData data = new LaserData(id, location, blockDisplay);
        lasers.put(id, data);
        return id;
    }

    public LaserData getLaser(UUID id) {
        return lasers.get(id);
    }

    public boolean removeLaser(UUID id) {
        LaserData data = lasers.remove(id);
        if (data != null) {
            data.getBlockDisplay().remove();
            return true;
        }
        return false;
    }

    public Map<UUID, LaserData> getAllLasers() {
        return lasers;
    }

    public void saveLasers() {
        FileConfiguration config = laserConfig.getConfig();
        config.set("lasers", null);

        for (Map.Entry<UUID, LaserData> entry : lasers.entrySet()) {
            UUID id = entry.getKey();
            LaserData data = entry.getValue();
            Location location = data.getLocation();
            BlockDisplay blockDisplay = data.getBlockDisplay();

            String path = "lasers." + id.toString();
            config.set(path + ".world", location.getWorld().getName());
            config.set(path + ".x", location.getX());
            config.set(path + ".y", location.getY());
            config.set(path + ".z", location.getZ());
            config.set(path + ".material", blockDisplay.getBlock().getMaterial().toString());
        }

        laserConfig.saveConfig();
    }

    public void loadLasers() {
        FileConfiguration config = laserConfig.getConfig();

        if (!config.contains("lasers")) {
            return;
        }

        for (String key : config.getConfigurationSection("lasers").getKeys(false)) {
            try {
                UUID id = UUID.fromString(key);

                String worldName = config.getString("lasers." + key + ".world");
                double x = config.getDouble("lasers." + key + ".x");
                double y = config.getDouble("lasers." + key + ".y");
                double z = config.getDouble("lasers." + key + ".z");
                String materialName = config.getString("lasers." + key + ".material");

                Material material = Material.valueOf(materialName);
                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

                BlockDisplay blockDisplay = location.getWorld().spawn(location, BlockDisplay.class, display -> {
                    display.setBlock(material.createBlockData());
                    display.setTransformation(new Transformation(
                            new Vector3f(0, 0, 0),
                            new Quaternionf(),
                            new Vector3f(0.1f, 10f, 0.1f),
                            new Quaternionf()
                    ));
                });

                lasers.put(id, new LaserData(id, location, blockDisplay));
            } catch (Exception e) {
                Bukkit.getLogger().warning("Error loading laser with ID " + key + ": " + e.getMessage());
            }
        }
    }
}
