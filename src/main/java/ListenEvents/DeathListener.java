package ListenEvents;

import Main.BlarioPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private static BlarioPlugin plugin;

    public DeathListener(BlarioPlugin plugin) {
        DeathListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getDeathMessage().contains("was blown up by")){
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("[Server] Aw man");
                }
            });
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("[Server] Noob");
                }
            });
        }
    }
}