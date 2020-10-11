package Main;

import CommandEvents.BlarioToggle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ListenEvents.DeathListener;
import ListenEvents.SleepListener;
import ListenEvents.AdvancementListener;

import java.io.File;
import java.io.IOException;

public class BlarioPlugin extends JavaPlugin {
    File configFile = new File("plugins/DeathMessages", "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    @Override
    public void onEnable() {
        getLogger().info("Starting Death Messages");
        initFiles();
        getConfig().options().copyDefaults(true);
        if (config.getBoolean("Main")) {
            new DeathListener(this);
        }
        if (config.getBoolean("SleepMessage")) {
            new SleepListener(this);
        }
        //Fix big brain issue
        if (config.getBoolean("AdvancementMessage")) {
            new AdvancementListener(this);
        }
//        this.getCommand("bltoggle").setExecutor(new BlarioToggle());
    }
    @Override
    public void onDisable() {
        getLogger().info("Stopping Death Messages");
    }

    private void initFiles() {
        String[] advancements = new String[]{"Main", "SleepMessage", "AdvancementMessage", "Waketime"};
        getLogger().info("Loading config...");
        if (!configFile.exists()) {
            getLogger().info("Couldn't find config! Creating it...");
            saveResource("config.yml", false);
            getLogger().info("Successfully created config!");
            //Ensure to set all options in the advancements array at the start of initFiles().
            config = new YamlConfiguration();
            config.set("Main", true);
            config.set("SleepMessage", true);
            config.set("AdvancementMessage", true);
            config.set("Waketime", 0);

            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getLogger().info("Config successfully loaded.");
        }
    }
}