package com.halfhour.tasks.event;

import com.halfhour.tasks.model.Task;

/**
 * Created by 年高 on 2015/5/25.
 */
public class TaskEvent {
    private int type;
    private Task task;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
