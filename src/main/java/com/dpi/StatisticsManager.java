package com.dpi;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsManager {


  private final Map<String, Integer> domainStats =
        new HashMap<>();
        
        private final Map<AppType, Integer> applicationStats =
        new HashMap<>();
    private final Map<String, Integer> sourceIpCount =
        new HashMap<>();

private final Map<String, Integer> destinationIpCount =
        new HashMap<>();

    private final AtomicLong totalPackets =
            new AtomicLong();

    private final AtomicLong forwardedPackets =
            new AtomicLong();

    private final AtomicLong droppedPackets =
            new AtomicLong();

    private final AtomicLong totalBytes =
            new AtomicLong();

    private final AtomicLong tcpPackets =
            new AtomicLong();

private final AtomicLong udpPackets =
            new AtomicLong();


    public void incrementPackets() {

        totalPackets.incrementAndGet();
    }

    public void incrementForwarded() {

        forwardedPackets.incrementAndGet();
    }

    public void incrementDropped() {

        droppedPackets.incrementAndGet();
    }

    public void addBytes(long bytes) {

        totalBytes.addAndGet(bytes);
    }

    public long getTotalPackets() {

        return totalPackets.get();
    }

    public long getForwardedPackets() {

        return forwardedPackets.get();
    }

    public long getDroppedPackets() {

        return droppedPackets.get();
    }

    public long getTotalBytes() {

        return totalBytes.get();
    }
    public void incrementTcp() {
    tcpPackets.incrementAndGet();
}

public void incrementUdp() {
    udpPackets.incrementAndGet();
}

public long getTcpPackets() {
    return tcpPackets.get();
}

public long getUdpPackets() {
    return udpPackets.get();
}
public void trackApplication(AppType appType) {

    applicationStats.put(
            appType,
            applicationStats.getOrDefault(
                    appType,
                    0
            ) + 1
    );
}
// public long getGooglePackets() {
//     return googlePackets.get();
// }

// public long getYoutubePackets() {
//     return youtubePackets.get();
// }

// public long getTelegramPackets() {
//     return telegramPackets.get();
// }

// public long getTiktokPackets() {
//     return tiktokPackets.get();
// }


public void addDomain(String domain) {

    if (domain == null || domain.isBlank()) {
        return;
    }

    domainStats.put(
            domain,
            domainStats.getOrDefault(
                    domain,
                    0
            ) + 1
    );
}

public void trackIpTraffic(
        String srcIp,
        String dstIp) {

    sourceIpCount.put(
            srcIp,
            sourceIpCount.getOrDefault(
                    srcIp,
                    0
            ) + 1
    );

    destinationIpCount.put(
            dstIp,
            destinationIpCount.getOrDefault(
                    dstIp,
                    0
            ) + 1
    );
}
public Map<String, Integer> getSourceIpCount() {

    return sourceIpCount;
}

public Map<String, Integer> getDestinationIpCount() {

    return destinationIpCount;
}
public Map<AppType, Integer> getApplicationStats() {

    return applicationStats;
}
public Map<String, Integer> getDomainStats() {

    return domainStats;
}
}