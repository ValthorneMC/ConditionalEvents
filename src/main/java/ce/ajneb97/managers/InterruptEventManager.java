package ce.ajneb97.managers;

import ce.ajneb97.ConditionalEvents;
import ce.ajneb97.model.internal.WaitActionTask;
import ce.ajneb97.utils.WrappedTask;
import org.bukkit.Bukkit;

import java.util.*;

public class InterruptEventManager {
    private ConditionalEvents plugin;
    private ArrayList<WaitActionTask> tasks;

    public InterruptEventManager(ConditionalEvents plugin){
        this.plugin = plugin;
        this.tasks = new ArrayList<>();
    }

    public void addTask(String playerName, String eventName, WrappedTask wrappedTask){
        tasks.add(new WaitActionTask(playerName,eventName,wrappedTask));
    }

    public void removeTask(WrappedTask wrappedTask){
        tasks.removeIf(task -> task.getTask() == wrappedTask);
    }

    // Interrupt actions for a specific event, globally or per player
    public void interruptEvent(String eventName, String playerName){
        tasks.removeIf(task -> {
            if(playerName == null){
                if(task.getEventName().equals(eventName)){
                    task.getTask().cancel();
                    return true;
                }
            }else{
                if(task.getPlayerName() != null && task.getPlayerName().equals(playerName) && task.getEventName().equals(eventName)){
                    task.getTask().cancel();
                    return true;
                }
            }
            return false;
        });
    }
}
