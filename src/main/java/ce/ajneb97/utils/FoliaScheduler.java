package ce.ajneb97.utils;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * Folia-specific scheduler implementation. This class is only loaded at runtime
 * when Folia is detected, so on Spigot/Paper servers it will never be class-loaded
 * and its Folia-specific imports will not cause issues.
 */
class FoliaScheduler {

    static WrappedTask runTask(Plugin plugin, Runnable runnable) {
        ScheduledTask task = Bukkit.getGlobalRegionScheduler().run(plugin, t -> runnable.run());
        return new WrappedTask(task);
    }

    static WrappedTask runTaskLater(Plugin plugin, Runnable runnable, long delayTicks) {
        if (delayTicks <= 0) {
            return runTask(plugin, runnable);
        }
        ScheduledTask task = Bukkit.getGlobalRegionScheduler().runDelayed(plugin, t -> runnable.run(), delayTicks);
        return new WrappedTask(task);
    }

    static WrappedTask runTaskTimer(Plugin plugin, Runnable runnable, long delayTicks, long periodTicks) {
        ScheduledTask task = Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, t -> runnable.run(),
                Math.max(delayTicks, 1), periodTicks);
        return new WrappedTask(task);
    }

    static WrappedTask runAsync(Plugin plugin, Runnable runnable) {
        ScheduledTask task = Bukkit.getAsyncScheduler().runNow(plugin, t -> runnable.run());
        return new WrappedTask(task);
    }

    static WrappedTask runAsyncTimer(Plugin plugin, Runnable runnable, long delayTicks, long periodTicks) {
        long delayMs = Math.max(delayTicks * 50, 1);
        long periodMs = periodTicks * 50;
        ScheduledTask task = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, t -> runnable.run(),
                delayMs, periodMs, TimeUnit.MILLISECONDS);
        return new WrappedTask(task);
    }
}
