package ce.ajneb97.tasks;

import ce.ajneb97.ConditionalEvents;
import ce.ajneb97.utils.SchedulerUtil;
import ce.ajneb97.utils.WrappedTask;

public class PlayerDataSaveTask {

	private ConditionalEvents plugin;
	private WrappedTask scheduledTask;
	public PlayerDataSaveTask(ConditionalEvents plugin) {
		this.plugin = plugin;
	}
	
	public void end() {
		if (scheduledTask != null) {
			scheduledTask.cancel();
			scheduledTask = null;
		}
	}
	
	public void start(int minutes) {
		long ticks = minutes*60*20;
		scheduledTask = SchedulerUtil.runAsyncTimer(plugin, this::execute, ticks, ticks);
	}
	
	public void execute() {
		plugin.getConfigsManager().getPlayerConfigsManager().saveConfigs();
	}
}
