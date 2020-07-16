package com.thinhnguyen.nab.service.prepaiddata.concurrent;

import com.thinhnguyen.nab.service.prepaiddata.concurrent.rules.Rule;
import com.thinhnguyen.nab.service.prepaiddata.concurrent.rules.TimeRule;
import com.thinhnguyen.nab.service.prepaiddata.concurrent.task.Task;
import com.thinhnguyen.nab.service.prepaiddata.concurrent.work.RuledWork;
import com.thinhnguyen.nab.service.prepaiddata.concurrent.work.Work;
import com.thinhnguyen.nab.service.prepaiddata.exception.RequestException;
import com.thinhnguyen.nab.service.prepaiddata.exception.ServiceRuntimeException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Play a role as a person with the official task of overseeing the work of a person or group.
 * He monitors someone to make sure they comply with rules or other requirements set for the them.
 *
 * He define rule and instruction and manage the result
 *
 * @param <T>
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Superviser<T> {
    Task<T> task;
    int timeoutInSec;
    Consumer<T> failInstruction;


    public ResponseEntity<T> overseeing() {
        TimeRule<T> rule = new TimeRule<>(timeoutInSec, failInstruction);
        Work<T> tWork = new RuledWork<T>(task);
        Rule.Result result = rule.apply(tWork);
        switch (result){
            //Fail the time rule --> timeout
            case FAIL: return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            //Status is OK but result is null have to check
            case PASS:
                T entity = tWork.getResult();
                if(Objects.isNull(entity)){
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                }
                return ResponseEntity.ok(tWork.getResult());
             //it can be the client error (Bad request) or server error (Server is down or Internal Error)
            case ERROR:
                Exception e = tWork.getException();
                if(e instanceof RequestException){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                if(e instanceof ServiceRuntimeException) {
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                }
            }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
