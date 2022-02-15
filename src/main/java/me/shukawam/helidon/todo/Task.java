package me.shukawam.helidon.todo;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Data class representing single ToDo task.
 *
 * @author shukawam
 */
public class Task implements Serializable {
    private UUID id;
    private String description;
    private Boolean completed;

    @JsonbTransient
    private long createdAt;

    public Task() {
    }

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.completed = completed;
        this.createdAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
