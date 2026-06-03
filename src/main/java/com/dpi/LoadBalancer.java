package com.dpi;

import java.util.concurrent.LinkedBlockingQueue;

public class LoadBalancer {

    private final LinkedBlockingQueue<PacketData>[] workerQueues;

    @SuppressWarnings("unchecked")
    public LoadBalancer(int workerCount) {

        workerQueues =
                new LinkedBlockingQueue[workerCount];

        for (int i = 0; i < workerCount; i++) {

            workerQueues[i] =
                    new LinkedBlockingQueue<>();
        }
    }

    public void distributePacket(
            PacketData packet) {

        int workerIndex =
                Math.abs(
                        packet.getFiveTuple()
                                .hashCode()
                ) % workerQueues.length;

        workerQueues[workerIndex]
                .offer(packet);

        System.out.println(
                "LoadBalancer -> Worker "
                        + workerIndex
                        + " : "
                        + packet.getFiveTuple()
        );
    }

    public LinkedBlockingQueue<PacketData> getQueue(
            int workerId) {

        return workerQueues[workerId];
    }
}