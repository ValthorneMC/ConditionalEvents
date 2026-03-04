package ce.ajneb97.model.internal;

import ce.ajneb97.utils.WrappedTask;

public class WaitActionTask {
    private String playerName;
    private String eventName;
    private WrappedTask task;

    public WaitActionTask(String playerName, String eventName, WrappedTask task) {
        this.playerName = playerName;
        this.eventName = eventName;
        this.task = task;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public WrappedTask getTask() {
        return task;
    }

    public void setTask(WrappedTask task) {
        this.task = task;
    }
}
