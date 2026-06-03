package com.dpi;

import java.util.concurrent.LinkedBlockingQueue;

public class FastPathWorker implements Runnable {

    private final int workerId;
    private volatile boolean running = true;
    private volatile long packetsProcessed = 0;
    private final LinkedBlockingQueue<PacketData> queue;
    public void stopWorker() {
        running = false;
}
    public FastPathWorker(
            int workerId,
            LinkedBlockingQueue<PacketData> queue) {

        this.workerId = workerId;
        this.queue = queue;
    }

    @Override
    public void run() {

        System.out.println(
                "Worker "
                        + workerId
                        + " started..."
        );

        while (running) {

            try {

                PacketData packet =
                        queue.take();
        packetsProcessed++;
                System.out.println(
                        "Worker "
                                + workerId
                                + " processing -> "
                                + packet.getFiveTuple()
                );





            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                break;
            }
        }
    }
    public long getPacketsProcessed() {

    return packetsProcessed;
}
}