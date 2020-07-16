package com.thinhnguyen.nab.service.prepaiddata.concurrent.work;

import com.thinhnguyen.nab.service.prepaiddata.concurrent.task.Task;
import com.thinhnguyen.nab.service.prepaiddata.concurrent.rules.Rule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuledWork<T> implements Work<T> {

    Task<T> task;
    T result;
    Exception exception;

    public RuledWork(Task<T> task) {
        this.task = task;
    }

    @Override
    public void start(Rule rule) {
        new Thread(() -> {
            try{
                this.result = task.run();
                rule.hasFinished(result);
            } catch (Exception e) {
                rule.hasFinished(null);
                this.exception = e;
            }
        }).start();
    }

    @Override
    public boolean hasException() {
        return !Objects.isNull(exception);
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public T getResult() {
        return this.result;
    }
}
