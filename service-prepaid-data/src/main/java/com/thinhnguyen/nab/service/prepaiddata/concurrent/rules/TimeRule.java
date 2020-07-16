package com.thinhnguyen.nab.service.prepaiddata.concurrent.rules;

import com.thinhnguyen.nab.service.prepaiddata.concurrent.work.Work;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Consumer;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeRule<T> implements Rule<T> {

    static final Logger LOGGER = LoggerFactory.getLogger(TimeRule.class);

    boolean isDone = false;
    int second = 1000;
    int timeoutInSec;

    Consumer<T> failInstruction;

    public TimeRule(int timeoutInSec, Consumer<T> failInstruction) {
        this.timeoutInSec = timeoutInSec;
        this.failInstruction = failInstruction;
    }


    public Result apply(Work work) {
        work.start(this);
        while (!isDone && timeoutInSec > 0) {
            timeoutInSec --;
            try {
                Thread.sleep(second);
            } catch (InterruptedException e) {
                LOGGER.error("Rule has been interrupted", e);
            }
        }
        if(timeoutInSec <= 0) {
            return Result.FAIL;
        }
        if(work.hasException()) {
            return Result.ERROR;
        }
        return Result.PASS;
    }

    @Override
    public void hasFinished(T t) {
        isDone =true;
        if(timeoutInSec <= 0 && !Objects.isNull(t)) {
            failInstruction.accept(t);
        }
    }
}
