package com.dpi;

public class Dashboard {

    private static final String TOP     = "╔══════════════════════════════════════════════════════════╗";
    private static final String MID     = "╠══════════════════════════════════════════════════════════╣";
    private static final String BOT     = "╚══════════════════════════════════════════════════════════╝";
    private static final String DIVIDER = "╟──────────────────────────────────────────────────────────╢";
    private static final int    WIDTH   = 58; // inner content width between the ║ borders

    public static void printReport(
            StatisticsManager stats,
            ConnectionTracker tracker,
            DPIEngine dpiEngine,
            long processingTime) {

        long totalPackets = stats.getTotalPackets();

        double tcpPercent =
                totalPackets > 0
                        ? (stats.getTcpPackets() * 100.0) / totalPackets
                        : 0;

        double udpPercent =
                totalPackets > 0
                        ? (stats.getUdpPackets() * 100.0) / totalPackets
                        : 0;

        System.out.println();

        // ── Header ───────────────────────────────────────────────
        System.out.println(TOP);
        row("DEEP PACKET INSPECTION (DPI) ENGINE  v1.0");
        System.out.println(MID);

        // ── Traffic Statistics ───────────────────────────────────
        section("TRAFFIC STATISTICS");
        System.out.println(DIVIDER);
        kv("Total Packets",     String.valueOf(stats.getTotalPackets()));
        kv("Total Bytes",       String.valueOf(stats.getTotalBytes()));

        // ── Connections ──────────────────────────────────────────
        System.out.println(MID);
        section("CONNECTIONS");
        System.out.println(DIVIDER);
        kv("Total Connections", String.valueOf(tracker.getTotalConnections()));

        // ── Protocol Distribution ────────────────────────────────
        System.out.println(MID);
        section("PROTOCOL DISTRIBUTION");
        System.out.println(DIVIDER);
        kv("TCP Packets", String.format("%d (%.1f%%)", stats.getTcpPackets(), tcpPercent));
        kv("UDP Packets", String.format("%d (%.1f%%)", stats.getUdpPackets(), udpPercent));

        // ── Application Ranking ──────────────────────────────────
        System.out.println(MID);
        section("APPLICATION RANKING");
        System.out.println(DIVIDER);
        stats.getApplicationStats()
                .entrySet()
                .stream()
                .sorted(
                        (a, b) ->
                                b.getValue()
                                        .compareTo(a.getValue())
                )
                .forEach(
                        entry ->
                                kv(entry.getKey().toString(), String.valueOf(entry.getValue()))
                );

        // ── Firewall Statistics ──────────────────────────────────
        System.out.println(MID);
        section("FIREWALL STATISTICS");
        System.out.println(DIVIDER);
        kv("Forwarded Packets", String.valueOf(stats.getForwardedPackets()));
        kv("Dropped Packets",   String.valueOf(stats.getDroppedPackets()));

        // ── Worker Statistics ────────────────────────────────────
        System.out.println(MID);
        section("WORKER STATISTICS");
        System.out.println(DIVIDER);

        FastPathWorker[] workers =
                dpiEngine.getWorkers();

        for (int i = 0; i < workers.length; i++) {

            kv("Worker " + i,
                    workers[i].getPacketsProcessed() + " packets");
        }

        // ── Top Source IPs ───────────────────────────────────────
        System.out.println(MID);
        section("TOP SOURCE IPs");
        System.out.println(DIVIDER);
        stats.getSourceIpCount()
                .entrySet()
                .stream()
                .sorted(
                        (a, b) ->
                                b.getValue()
                                        .compareTo(a.getValue())
                )
                .limit(5)
                .forEach(
                        entry ->
                                kv(entry.getKey(), String.valueOf(entry.getValue()))
                );

        // ── Top Destination IPs ──────────────────────────────────
        System.out.println(MID);
        section("TOP DESTINATION IPs");
        System.out.println(DIVIDER);
        stats.getDestinationIpCount()
                .entrySet()
                .stream()
                .sorted(
                        (a, b) ->
                                b.getValue()
                                        .compareTo(a.getValue())
                )
                .limit(5)
                .forEach(
                        entry ->
                                kv(entry.getKey(), String.valueOf(entry.getValue()))
                );

        // ── Top Domains ──────────────────────────────────────────
        System.out.println(MID);
        section("TOP DOMAINS");
        System.out.println(DIVIDER);
        stats.getDomainStats()
                .entrySet()
                .stream()
                .sorted(
                        (a, b) ->
                                b.getValue()
                                        .compareTo(a.getValue())
                )
                .limit(5)
                .forEach(
                        entry ->
                                kv(entry.getKey(), String.valueOf(entry.getValue()))
                );

        // ── Performance ──────────────────────────────────────────
        System.out.println(MID);
        section("PERFORMANCE");
        System.out.println(DIVIDER);
        kv("Processing Time", processingTime + " ms");

        System.out.println(BOT);
        System.out.println();
    }

    /** Prints a centered section header row with blank lines above and below. */
    private static void section(String title) {
        String blank = padRight("", WIDTH);
        String label = "  [ " + title + " ]";
        System.out.println("║" + blank + "║");
        System.out.println("║" + padRight(label, WIDTH) + "║");
        System.out.println("║" + blank + "║");
    }

    /** Prints a key-value data row. */
    private static void kv(String key, String value) {
        String content = "    " + String.format("%-22s", key) + ": " + value;
        System.out.println("║" + padRight(content, WIDTH) + "║");
    }

    /** Prints a plain centered row. */
    private static void row(String text) {
        int pad = (WIDTH - text.length()) / 2;
        String centered = " ".repeat(Math.max(0, pad)) + text;
        System.out.println("║" + padRight(centered, WIDTH) + "║");
    }

    /** Pads or trims a string to exactly `width` chars. */
    private static String padRight(String s, int width) {
        if (s.length() >= width) return s.substring(0, width);
        return s + " ".repeat(width - s.length());
    }
}
