package ListenEvents;

import Main.BlarioPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.jetbrains.annotations.NotNull;

public class SleepListener implements Listener {
    private static BlarioPlugin plugin;
    public static World world;
    boolean morning;
    boolean cancel;
    int sleepTask;
    String cond = "OK";

    public SleepListener( BlarioPlugin plugin) {
        SleepListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSleep(@NotNull final PlayerBedEnterEvent bedEnter){
        morning = false;
        final Player player = bedEnter.getPlayer();
        world = player.getWorld();
        int players = plugin.getServer().getOnlinePlayers().size();
        cancel = false;
        if(!IsNightOrThunder(world)){
            player.sendMessage("Must be night to sleep.");
            cancel = true;
        }

        if(world.getName().equals("world") && !cancel && players >= 1 && IsNightOrThunder(world)){
            morning = false;
            if (bedEnter.getBedEnterResult().toString().equals(cond)) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                    public void run() {
                        Bukkit.broadcastMessage(String.format(ChatColor.YELLOW + "%s is now sleeping", (ChatColor.YELLOW + convertToString(bedEnter.getPlayer()))));
                    }
                });
                sleepTask = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        morning = true;
                        setDayTime(world);
//                        player.
                    }
                }, 110);
            }
        }
    }

    @EventHandler
    public void onBedExit(@NotNull final PlayerBedLeaveEvent bedLeave) {
        int players = plugin.getServer().getOnlinePlayers().size();
        if (!morning && players > 1) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(String.format(ChatColor.YELLOW + "%s is no longer sleeping", convertToString(bedLeave.getPlayer())));
                }
            });
            if ((Bukkit.getScheduler().isCurrentlyRunning(sleepTask)) || Bukkit.getScheduler().isQueued((sleepTask))) Bukkit.getScheduler().cancelTask(sleepTask);
        }

        Bukkit.getScheduler().cancelTask(sleepTask);
    }

    public String convertToString(Player player){
        return player.toString().split("=")[1].split("}")[0];
    }

    public void setDayTime(@NotNull World world){
        if(world.hasStorm()) world.setStorm(false);
        if(world.isThundering()) world.setThundering(false);
        int waketime = plugin.getConfig().getInt("waketime");
        long Relative_time = (24000 - world.getTime()) - waketime;
        world.setFullTime(world.getFullTime() + Relative_time);
        if (morning) Bukkit.broadcastMessage(ChatColor.YELLOW + "Good Morning.");
    }

    public static boolean IsNightOrThunder(@NotNull World w){
        long time = (w.getFullTime() % 24000);
        if(time >= 12542) {
            return true;
        } else if (w.isThundering()){
            return true;
        } else {
            return false;
        }
    }
}
