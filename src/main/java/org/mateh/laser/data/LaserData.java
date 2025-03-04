package org.mateh.laser.data;

import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;

import java.util.UUID;

public class LaserData {
    private final UUID id;
    private final Location location;
    private final BlockDisplay blockDisplay;

    public LaserData(UUID id, Location location, BlockDisplay blockDisplay) {
        this.id = id;
        this.location = location;
        this.blockDisplay = blockDisplay;
    }

    public UUID getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }
}
