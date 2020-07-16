package com.thinhnguyen.nab.service.prepaiddata.concurrent.rules;

import com.thinhnguyen.nab.service.prepaiddata.concurrent.work.Work;

/**
 * Define and examining the work is in order or violated
 * @param <T>
 */
public interface Rule<T> {

    enum Result {
        PASS, ERROR, FAIL
    }

    Result apply(Work work);

    void hasFinished(T t);
}
