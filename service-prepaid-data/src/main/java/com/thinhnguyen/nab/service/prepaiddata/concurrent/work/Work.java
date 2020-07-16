package com.thinhnguyen.nab.service.prepaiddata.concurrent.work;

import com.thinhnguyen.nab.service.prepaiddata.concurrent.rules.Rule;

public interface Work<T> {

    void start(Rule rule);

    boolean hasException();
    Exception getException();
    T getResult();

}
