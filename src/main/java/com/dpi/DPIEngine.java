package com.dpi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DPIEngine {

    private final LoadBalancer loadBalancer;
    private final FastPathWorker[] workers;
    private final ExecutorService executor;

    private static final int WORKER_COUNT = 4;

    public DPIEngine() {
        workers = new FastPathWorker[WORKER_COUNT];
        loadBalancer = new LoadBalancer(WORKER_COUNT);

        executor =
                Executors.newFixedThreadPool(
                        WORKER_COUNT
                );

        for (int i = 0; i < WORKER_COUNT; i++) {

    workers[i] =
            new FastPathWorker(
                    i,
                    loadBalancer.getQueue(i)
            );

    executor.submit(workers[i]);
}

        System.out.println(
                "DPI Engine Started with "
                        + WORKER_COUNT
                        + " Workers"
        );
    }

    public void processPacket(
            PacketData packet) {

        loadBalancer.distributePacket(packet);
    }

    public void shutdown() {

    for (FastPathWorker worker : workers) {

        if (worker != null) {
            worker.stopWorker();
        }
    }

    executor.shutdownNow();
}
public FastPathWorker[] getWorkers() {

    return workers;
}
public void printWorkerStats() {

    System.out.println();
    System.out.println("WORKER PERFORMANCE");

    for (int i = 0; i < workers.length; i++) {

        System.out.println(
                "Worker " + i +
                " -> " +
                workers[i].getPacketsProcessed() +
                " packets"
        );
    }
}
}