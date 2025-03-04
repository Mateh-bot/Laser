package org.mateh.laser.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class LaserGenerator {
    public static void generateLaser(Location location, Material material) {
        World world = location.getWorld();
        if (world == null) return;

        BlockDisplay blockDisplay = world.spawn(location, BlockDisplay.class);
        blockDisplay.setBlock(material.createBlockData());

        Vector3f origin = new Vector3f(0, 0, 0);
        Vector3f scale = new Vector3f(0.1f, 10.0f, 0.1f);
        Quaternionf leftRotation = new Quaternionf();
        Quaternionf rightRotation = new Quaternionf();

        Transformation transformation = new Transformation(origin, leftRotation, scale, rightRotation);
        blockDisplay.setTransformation(transformation);
        blockDisplay.setBrightness(new Display.Brightness(15, 0));
    }
}
