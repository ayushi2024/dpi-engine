package com.dpi;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;

public class App {

    public static void main(String[] args) {

            long startTime = System.currentTimeMillis();
        String pcapFile = "capture.pcap";
        DPIEngine dpiEngine = new DPIEngine();
        ConnectionTracker tracker = new ConnectionTracker();
        RuleManager ruleManager = new RuleManager();
        StatisticsManager stats =new StatisticsManager();



        try {

            PcapHandle handle = Pcaps.openOffline(pcapFile);

            Packet packet;

            System.out.println("Reading PCAP File...");
            System.out.println("--------------------------------");

            while ((packet = handle.getNextPacket()) != null) {

                IpPacket ipPacket = packet.get(IpPacket.class);

                if (ipPacket == null) {
                    continue;
                }

                String srcIp =
                        ipPacket.getHeader()
                                .getSrcAddr()
                                .getHostAddress();

                String dstIp =
                        ipPacket.getHeader()
                                .getDstAddr()
                                .getHostAddress();

stats.trackIpTraffic(
        srcIp,
        dstIp
);
                String protocol = "OTHER";

                int srcPort = 0;
                int dstPort = 0;

                TcpPacket tcpPacket = packet.get(TcpPacket.class);

                UdpPacket udpPacket = packet.get(UdpPacket.class);

                if (tcpPacket != null) {

                    protocol = "TCP";

                    srcPort =
                            tcpPacket.getHeader()
                                    .getSrcPort()
                                    .valueAsInt();

                    dstPort =
                            tcpPacket.getHeader()
                                    .getDstPort()
                                    .valueAsInt();

                } else if (udpPacket != null) {

                    protocol = "UDP";

                    srcPort =
                            udpPacket.getHeader()
                                    .getSrcPort()
                                    .valueAsInt();

                    dstPort =
                            udpPacket.getHeader()
                                    .getDstPort()
                                    .valueAsInt();
                }

                FiveTuple tuple = new FiveTuple(
                        srcIp,
                        srcPort,
                        dstIp,
                        dstPort,
                        protocol
                );





if ("TCP".equals(protocol)) {

    stats.incrementTcp();

}

else if ("UDP".equals(protocol)) {

    stats.incrementUdp();
}






                byte[] payload = packet.getRawData();

                String domain =
                        SNIExtractor.extractDomain(payload);
                stats.addDomain(domain);
                AppType appType;

                if (tracker.containsConnection(tuple)) {

                    appType =
                            tracker.getApplication(tuple);

                } else {

                    appType =
                            AppClassifier.classifyDomain(domain);

                    if (appType == AppType.UNKNOWN) {

                        appType =
                                AppClassifier.classify(
                                        payload,
                                        dstPort
                                );
                    }

                    if (appType != AppType.UNKNOWN) {

                        tracker.addConnection(
                                tuple,
                                appType
                        );
                    }
                }
stats.trackApplication(appType);
                PacketStatus status;

if (ruleManager.isBlocked(domain)) {

    status = PacketStatus.DROPPED;

} else {

    status = PacketStatus.FORWARDED;
}
stats.incrementPackets();

stats.addBytes(packet.length());

if (status == PacketStatus.DROPPED) {

    stats.incrementDropped();

} else {

    stats.incrementForwarded();
}

System.out.println(
        tuple
        + " | Domain = "
        + domain
        + " | Application = "
        + appType
        + " | Status = "
        + status
);
                PacketData packetData =
        new PacketData(
                tuple,
                packet.length()
        );

dpiEngine.processPacket(
        packetData
);
            }

            System.out.println();
           System.out.println();

System.out.println(
        "Total Connections Tracked = "
        + tracker.getTotalConnections()
);

System.out.println(
        "Total Packets Processed = "
        + stats.getTotalPackets()
);

System.out.println(
        "Total Bytes Analyzed = "
        + stats.getTotalBytes()
);

System.out.println(
        "Packets Forwarded = "
        + stats.getForwardedPackets()
);

System.out.println(
        "Packets Dropped = "
        + stats.getDroppedPackets()
);

handle.close();

dpiEngine.shutdown();

long endTime = System.currentTimeMillis();

long processingTime =
        endTime - startTime;

Dashboard.printReport(
        stats,
        tracker,
        dpiEngine,
        processingTime
);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}