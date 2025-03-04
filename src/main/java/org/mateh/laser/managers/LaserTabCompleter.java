package org.mateh.laser.managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LaserTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("color", "effect");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("color")) {
            return Arrays.asList(
                    "white",
                    "light_gray",
                    "gray",
                    "black",
                    "brown",
                    "red",
                    "orange",
                    "yellow",
                    "lime",
                    "green",
                    "cyan",
                    "light_blue",
                    "blue",
                    "purple",
                    "magenta",
                    "pink"
            );
        } else if (args.length == 2 && args[0].equalsIgnoreCase("effect")) {
            return Arrays.asList(
                    "rotation",
                    "blink",
                    "idle",
                    "color_cycle",
                    "expanding",
                    "orbiting",
                    "color_pulse",
                    "directional_wave",
                    "growth",
                    "zigzag",
                    "helix_spin"
            );
        }
        return Collections.emptyList();
    }
}
