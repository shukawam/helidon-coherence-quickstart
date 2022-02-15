package me.shukawam.helidon.todo;

import com.tangosol.net.NamedMap;
import com.tangosol.util.Filter;
import com.tangosol.util.Filters;

import javax.ws.rs.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

/**
 * REST resource for todo items management.
 *
 * @author shukawam
 */
@Path("/tasks")
public class TodoResource {
    private NamedMap<UUID, Task> tasks;

    @GET
    @Produces("application/json")
    public Collection<Task> getTasks(@QueryParam("completed") Boolean completed) {
        Filter<Task> filter = completed == null
                ? Filters.always()
                : Filters.equal(Task::isCompleted, completed);
        return tasks.values(filter, Comparator.comparingLong(Task::getCreatedAt));
    }

    @POST
    @Consumes("application/json")
    public void createTask(Task task) {
        task = new Task(task.getDescription());
        tasks.put(task.getId(), task);
    }

    @DELETE
    @Path("{id}")
    public void deleteTask(@PathParam("id") UUID id) {
        tasks.remove(id);
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Task updateTask(@PathParam("id") UUID id, Task task) {
        String description = task.getDescription();
        Boolean completed = task.isCompleted();
        return tasks.compute(id, (k, v) -> {
            Objects.requireNonNull(v);
            if (description != null) {
                v.setDescription(description);
            }
            if (completed != null) {
                v.setCompleted(completed);
            }
            return v;
        });
    }
}
