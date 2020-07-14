package com.thinhnguyen.nab.service.prepaiddata.tools;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimeRunner<T> extends Thread {

    private T message;
    private final Integer timeoutInSec;
    private boolean timeout = false;
    public TimeRunner(Integer timeoutInSec) {

        this.timeoutInSec = timeoutInSec;
    }

    public void receiveMessage(T message) {
        this.message = message;
        interrupt();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * timeoutInSec);
        } catch (InterruptedException e) {
            return;
        }
        timeout = true;
    }
}
