package com.thinhnguyen.nab.service.prepaiddata.concurrent.task;


public interface Task<T> {

    T run() throws Exception;
}
