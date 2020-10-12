package ListenEvents;

import Main.BlarioPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import java.util.Random;
import static org.bukkit.Bukkit.getLogger;


public class DeathListener implements Listener {
    private static BlarioPlugin plugin;
    public FileConfiguration config;
    public String[] co_dms;
    public int coc;
    public int rando;

    public DeathListener(BlarioPlugin plugin) {
        DeathListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        config = plugin.getConfig();
    }

    public int dmLength(){
        try {
            String co = config.getString("DeathMessages");
            if (co != null) {
                co = co.replace("[", "").replace("]", "");
            }
            co_dms = co.split(",");
            coc = co_dms.length;
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return coc;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Random random = new Random();
        rando = random.nextInt(dmLength());
        if(e.getDeathMessage().contains("was blown up by")){
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("[Server] Aw man");
                }
            });
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(String.format("[Server] %s", co_dms[rando].replace("\"", "")));
                }
            });
        }
    }
}