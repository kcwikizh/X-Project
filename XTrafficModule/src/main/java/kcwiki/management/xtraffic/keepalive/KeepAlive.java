/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.keepalive;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;

/**
 *
 * @author iHaru
 */
public class KeepAlive {
    private final ScheduledExecutorService KEEP_ALIVE_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private boolean shutdown = false;
    
    public static KeepAlive newTask(Runnable task, int period){
        KeepAlive keepAliveTask = new KeepAlive();
        keepAliveTask.startKeepAliveTask(task, period);
        return keepAliveTask;
    }
    
    public void startKeepAliveTask(Runnable task, int period){
        this.KEEP_ALIVE_EXECUTOR
            .scheduleWithFixedDelay(task, 10, period, TimeUnit.SECONDS);
    }
    
    public void stopKeepAliveTask(){
        if(shutdown)
            return;
        shutdown = true;
        this.KEEP_ALIVE_EXECUTOR.shutdown();
    }
    
    public void forceStopKeepAliveTask(){
        if(shutdown)
            return;
        shutdown = true;
        this.KEEP_ALIVE_EXECUTOR.shutdownNow();
    }
    
    @PreDestroy
    public void destroyMethod() {
        forceStopKeepAliveTask();
    }
            
}
