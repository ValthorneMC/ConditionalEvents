package ce.ajneb97.utils;

/**
 * A wrapper for scheduled tasks that works with both Bukkit (BukkitTask) and Folia (ScheduledTask).
 * Uses reflection internally to avoid class loading issues on servers that don't have Folia classes.
 */
public class WrappedTask {

    private final Object task;

    WrappedTask(Object task) {
        this.task = task;
    }

    public void cancel() {
        if (task == null) return;
        try {
            task.getClass().getMethod("cancel").invoke(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getTask() {
        return task;
    }
}
