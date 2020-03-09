package com.adx2099.roomadx2099example;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;


    public AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor DiskIO() {
        return diskIO;
    }

    public Executor mainThread(){
        return mainThread;
    }

    public Executor NetworkIO(){
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        @Override
        public void execute(Runnable command) {

        }
    }
}
