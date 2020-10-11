package ListenEvents;

import Main.BlarioPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {
    private static BlarioPlugin plugin;
    public static String plA;
    public static String[] plAS;

    public AdvancementListener(BlarioPlugin plugin){
        AdvancementListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
// Is kinda fucky
    @EventHandler
    public void onAdvancement(final PlayerAdvancementDoneEvent pl){
        Bukkit.getScheduler().runTask(plugin,
                new Runnable() {
                    public void run() {
                        plA = pl.getAdvancement().getKey().getNamespace();
                        plAS = plA.split("/");
                        Bukkit.broadcastMessage(plA);
                        if (plAS[0].equals("recipes")){
                            ;
                        } else {
                            Bukkit.broadcastMessage("[Server] Big brain");
                        }
                    }
                });
    }
}