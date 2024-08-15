package pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TodoPojo {
    private String description;
    private UUID userUuid;
    private long timestamp;
    private boolean completed;

    public TodoPojo(String description, UUID userUuid) {
        this.description = description;
        this.userUuid = userUuid;
        this.timestamp = System.currentTimeMillis();
        this.completed = false;
    }


	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date(timestamp));
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}