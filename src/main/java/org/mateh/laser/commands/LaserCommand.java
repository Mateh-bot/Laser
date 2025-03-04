package org.mateh.laser.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.mateh.laser.Main;
import org.mateh.laser.abstracts.AbstractEffect;
import org.mateh.laser.effects.*;
import org.mateh.laser.managers.LaserGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LaserCommand implements CommandExecutor {
    private final Main main;
    private final Map<UUID, AbstractEffect> activeEffects = new HashMap<>();

    public LaserCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ChatColor.AQUA + "Comandos disponibles:");
            player.sendMessage(ChatColor.YELLOW + "/laser color <color>");
            player.sendMessage(ChatColor.YELLOW + "/laser effect <effect>");
            return true;
        }

        try {
            switch (args[0].toLowerCase()) {
                case "color" -> {
                    return handleColorSubcommand(player, args);
                }
                case "effect" -> {
                    return handleEffectSubcommand(player, args);
                }
                default -> {
                    player.sendMessage(ChatColor.RED + "Comando desconocido: " + args[0]);
                    player.sendMessage(ChatColor.AQUA + "Uso: /laser color <color> o /laser effect <effect>");
                    return true;
                }
            }
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Ocurrió un error al ejecutar el comando.");
            e.printStackTrace();
            return true;
        }
    }

    private boolean handleColorSubcommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /laser color <color>");
            return true;
        }

        String color = args[1].toLowerCase();
        Material concreteMaterial = getConcreteMaterial(color);
        if (concreteMaterial == null) {
            player.sendMessage(ChatColor.RED + "Color inválido: " + color);
            return true;
        }

        LaserGenerator.generateLaser(player.getLocation(), concreteMaterial);
        player.sendMessage(ChatColor.GREEN + "Láser generado con el color: " + color);
        return true;
    }

    private boolean handleEffectSubcommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /laser effect <idle | rotation | blink>");
            return true;
        }

        String effectType = args[1].toLowerCase();
        BlockDisplay targetLaser = findLaserNearPlayer(player);
        if (targetLaser == null) {
            player.sendMessage(ChatColor.RED + "¡No se encontró un láser cercano!");
            return true;
        }

        AbstractEffect previousEffect = activeEffects.getOrDefault(targetLaser.getUniqueId(), null);
        if (previousEffect != null) {
            previousEffect.stop();
        }

        AbstractEffect effect;
        switch (effectType) {
            case "rotation" -> effect = new RotationEffect(targetLaser, main);
            case "blink" -> effect = new BlinkEffect(targetLaser, main);
            case "idle" -> effect = new IdleEffect(targetLaser, main);
            case "color_cycle" -> effect = new ColorCycleEffect(targetLaser, main);
            case "expanding" -> effect = new ExpandingEffect(targetLaser, main);
            case "orbiting" -> effect = new OrbitingEffect(targetLaser, main);
            case "color_pulse" -> effect = new ColorPulseEffect(targetLaser, main);
            case "directional_wave" -> effect = new DirectionalWaveEffect(targetLaser, main);
            case "growth" -> effect = new GrowthEffect(targetLaser, main);
            case "zigzag" -> effect = new ZigzagEffect(targetLaser, main);
            case "helix_spin" -> effect = new HelixSpinEffect(targetLaser, main);

            default -> {
                player.sendMessage(ChatColor.RED + "Efecto desconocido: " + effectType);
                return true;
            }
        }

        effect.start();
        activeEffects.put(targetLaser.getUniqueId(), effect);

        player.sendMessage(ChatColor.GREEN + "Efecto " + effectType + " aplicado al láser.");
        return true;
    }



    private BlockDisplay findLaserNearPlayer(Player player) {
        return player.getWorld().getEntitiesByClass(BlockDisplay.class).stream()
                .filter(e -> e.getLocation().distance(player.getLocation()) < 5)
                .findFirst().orElse(null);
    }

    private Material getConcreteMaterial(String color) {
        return switch (color) {
            case "red" -> Material.RED_CONCRETE;
            case "blue" -> Material.BLUE_CONCRETE;
            case "green" -> Material.GREEN_CONCRETE;
            case "yellow" -> Material.YELLOW_CONCRETE;
            case "orange" -> Material.ORANGE_CONCRETE;
            case "purple" -> Material.PURPLE_CONCRETE;
            case "white" -> Material.WHITE_CONCRETE;
            case "light_gray" -> Material.LIGHT_GRAY_CONCRETE;
            case "gray" -> Material.GRAY_CONCRETE;
            case "black" -> Material.BLACK_CONCRETE;
            case "brown" -> Material.BROWN_CONCRETE;
            case "lime" -> Material.LIME_CONCRETE;
            case "cyan" -> Material.CYAN_CONCRETE;
            case "light_blue" -> Material.LIGHT_BLUE_CONCRETE;
            case "magenta" -> Material.MAGENTA_CONCRETE;
            case "pink" -> Material.PINK_CONCRETE;
            default -> null;
        };
    }
}
