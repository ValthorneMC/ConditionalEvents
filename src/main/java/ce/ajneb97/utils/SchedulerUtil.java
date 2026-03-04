package ce.ajneb97.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Scheduler utility that provides cross-compatible scheduling methods for both
 * Bukkit/Spigot/Paper and Folia servers.
 *
 * On Folia: uses GlobalRegionScheduler for sync tasks and AsyncScheduler for async tasks.
 * On Bukkit/Spigot/Paper: uses the standard BukkitScheduler.
 */
public class SchedulerUtil {

    private static final boolean IS_FOLIA;

    static {
        boolean folia;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (ClassNotFoundException e) {
            folia = false;
        }
        IS_FOLIA = folia;
    }

    public static boolean isFolia() {
        return IS_FOLIA;
    }

    /**
     * Run a task on the global region thread (Folia) or main thread (Bukkit).
     */
    public static WrappedTask runTask(Plugin plugin, Runnable runnable) {
        if (IS_FOLIA) {
            return FoliaScheduler.runTask(plugin, runnable);
        }
        Object task = Bukkit.getScheduler().runTask(plugin, runnable);
        return new WrappedTask(task);
    }

    /**
     * Run a task after a delay on the global region thread (Folia) or main thread (Bukkit).
     */
    public static WrappedTask runTaskLater(Plugin plugin, Runnable runnable, long delayTicks) {
        if (IS_FOLIA) {
            return FoliaScheduler.runTaskLater(plugin, runnable, delayTicks);
        }
        Object task = Bukkit.getScheduler().runTaskLater(plugin, runnable, delayTicks);
        return new WrappedTask(task);
    }

    /**
     * Run a repeating task on the global region thread (Folia) or main thread (Bukkit).
     */
    public static WrappedTask runTaskTimer(Plugin plugin, Runnable runnable, long delayTicks, long periodTicks) {
        if (IS_FOLIA) {
            return FoliaScheduler.runTaskTimer(plugin, runnable, delayTicks, periodTicks);
        }
        Object task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, delayTicks, periodTicks);
        return new WrappedTask(task);
    }

    /**
     * Run a task asynchronously.
     */
    public static WrappedTask runAsync(Plugin plugin, Runnable runnable) {
        if (IS_FOLIA) {
            return FoliaScheduler.runAsync(plugin, runnable);
        }
        Object task = Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        return new WrappedTask(task);
    }

    /**
     * Run a repeating task asynchronously.
     */
    public static WrappedTask runAsyncTimer(Plugin plugin, Runnable runnable, long delayTicks, long periodTicks) {
        if (IS_FOLIA) {
            return FoliaScheduler.runAsyncTimer(plugin, runnable, delayTicks, periodTicks);
        }
        Object task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delayTicks, periodTicks);
        return new WrappedTask(task);
    }
}
