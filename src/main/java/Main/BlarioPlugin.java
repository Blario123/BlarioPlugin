package Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ListenEvents.DeathListener;
import ListenEvents.SleepListener;
import ListenEvents.AdvancementListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BlarioPlugin extends JavaPlugin {
    File configFile = new File("plugins/BlarioPlugin", "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    private int correct;

    @Override
    public void onEnable() {
        getLogger().info("Starting Death Messages");
        initFiles();
        getConfig().options().copyDefaults(true);
        if (config.getBoolean("DeathMessage")) {
            new DeathListener(this);
        }
        if (config.getBoolean("SleepMessage")) {
            new SleepListener(this);
        }
        //Fix big brain issue
        if (config.getBoolean("AdvancementMessage")) {
            new AdvancementListener(this);
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("Stopping BlarioPlugin");
        getLogger().info("Saving configs");
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFiles() {
        String[] options = new String[]{"DeathMessage", "DeathMessages", "SleepMessage", "AdvancementMessage", "Waketime"};
        getLogger().info("Loading config...");
        if (!configFile.exists()) {
            getLogger().info("Couldn't find config! Creating it...");
            saveResource("config.yml", false);
            getLogger().info("Successfully created config!");
            //Ensure to set all options in the advancements array at the start of initFiles().
            config = new YamlConfiguration();
            config.set("DeathMessage", true);
            config.set("DeathMessages", "[\"Noob\"]");
            config.set("SleepMessage", true);
            config.set("AdvancementMessage", false);
            config.set("Waketime", 0);
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (configFile.exists()) {
            try {
                Scanner scanner = new Scanner(configFile);
                int pos = 0;
                correct = 0;
//                String[] inc = new String[]{};
                while (scanner.hasNextLine()) {
                    String setting = scanner.nextLine().split(":")[0];
                    if (options[pos].equals(setting)){
                        correct += 1;
                    } else {
                        getLogger().warning("Setting is not valid");
//                        inc = Arrays.copyOf(inc,inc.length + 1);
                    }
                    pos += 1;
                }
                scanner.close();
            } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            getLogger().info("Config successfully loaded.");
        }
    }
}