package ce.ajneb97.managers;

import ce.ajneb97.ConditionalEvents;
import ce.ajneb97.model.CEEvent;
import ce.ajneb97.model.EventType;
import ce.ajneb97.model.internal.ConditionEvent;
import ce.ajneb97.utils.SchedulerUtil;
import ce.ajneb97.utils.WrappedTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RepetitiveManager {

    private ConditionalEvents plugin;
    private CEEvent ceEvent;
    private long ticks;
    private boolean mustEnd;
    private boolean started;
    private WrappedTask scheduledTask;

    public RepetitiveManager(ConditionalEvents plugin,CEEvent ceEvent,long ticks){
        this.plugin = plugin;
        this.ceEvent = ceEvent;
        this.ticks = ticks;
    }

    public boolean isStarted() {
        return started;
    }

    public void end() {
        this.mustEnd = true;
        this.started = false;
        if (scheduledTask != null) {
            scheduledTask.cancel();
            scheduledTask = null;
        }
    }

    public void start(){
        this.mustEnd = false;
        this.started = true;
        scheduledTask = SchedulerUtil.runAsyncTimer(plugin, () -> {
            if(mustEnd || !execute()){
                if (scheduledTask != null) {
                    scheduledTask.cancel();
                    scheduledTask = null;
                }
            }
        }, 0L, ticks);
    }

    public boolean execute(){
        if(ceEvent == null){
            return false;
        }

        EventsManager eventsManager = plugin.getEventsManager();
        if(ceEvent.getEventType().equals(EventType.REPETITIVE)){
            for(Player player : Bukkit.getOnlinePlayers()){
                ConditionEvent conditionEvent = new ConditionEvent(plugin, player, null, EventType.REPETITIVE, null);
                conditionEvent.setAsync(true);
                eventsManager.checkSingularEvent(conditionEvent,ceEvent);
            }
        }else{
            //Repetitive server
            ConditionEvent conditionEvent = new ConditionEvent(plugin, null, null, EventType.REPETITIVE_SERVER, null);
            conditionEvent.setAsync(true);
            eventsManager.checkSingularEvent(conditionEvent,ceEvent);
        }
        return true;
    }
}
