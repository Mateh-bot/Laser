package org.mateh.laser.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.mateh.laser.Main;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private Main main;
    private String fileName;
    private FileConfiguration fileConfiguration = null;
    private File file = null;
    private String folderName;

    public CustomConfig(String fileName, String folderName, Main main) {
        this.fileName = fileName;
        this.folderName = folderName;
        this.main = main;
    }

    public void registerConfig() {
        if (folderName != null) {
            file = new File(main.getDataFolder() + File.separator + folderName, fileName);
        } else {
            file = new File(main.getDataFolder(), fileName);
        }

        if (!file.exists()) {
            if (folderName != null) {
                main.saveResource(folderName + File.separator + fileName, false);
            } else {
                main.saveResource(fileName, false);
            }
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    public boolean reloadConfig() {
        if (fileConfiguration == null) {
            if (folderName != null) {
                file = new File(main.getDataFolder() + File.separator + folderName, fileName);
            } else {
                file = new File(main.getDataFolder(), fileName);
            }

        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        if (file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.setDefaults(defConfig);
        }
        return true;
    }

    public void saveConfig() {
        if (fileConfiguration != null && file != null) {
            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not save config to " + file.getName());
            }
        }
    }
}
