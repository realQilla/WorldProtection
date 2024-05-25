package net.qilla.worldprotection.Config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;

public class GenericConfig {

    private static GenericConfig instance;
    private static final Gson gson = new Gson();

    private final JavaPlugin plugin;
    private final File configFile;
    private final JsonObject config;

    public GenericConfig(JavaPlugin plugin) throws IOException {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.json");

        if(!configFile.exists()) setUpConfig();

        try (Reader reader = new FileReader(configFile)) {
            config = gson.fromJson(reader, JsonObject.class);
        }
        instance = this;
    }

    protected void setUpConfig() {
        System.out.println("Setting up config file");
        configFile.getParentFile().mkdirs();
        try (InputStream input = plugin.getResource("configPreset.json")){
            if(input != null) {
                Files.copy(input, configFile.toPath());
            } else {
                plugin.getLogger().severe("Failed to load config for" + plugin.getName() + "!");
            }
        } catch (IOException exception) {
            plugin.getLogger().severe("Failed to load config for" + plugin.getName() + "!");
            exception.printStackTrace();
        }
    }

    protected void resetConfig() {
        configFile.delete();
        setUpConfig();
    }

    public static GenericConfig getInstance() {
        return instance;
    }

    protected JsonObject getConfig() {
        return config;
    }
 }
