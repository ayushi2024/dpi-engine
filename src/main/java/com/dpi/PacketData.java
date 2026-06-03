package com.dpi;

public class PacketData {

    private final FiveTuple fiveTuple;

    private final int packetLength;

    public PacketData(
            FiveTuple fiveTuple,
            int packetLength) {

        this.fiveTuple = fiveTuple;
        this.packetLength = packetLength;
    }

    public FiveTuple getFiveTuple() {
        return fiveTuple;
    }

    public int getPacketLength() {
        return packetLength;
    }

    @Override
    public String toString() {

        return "PacketData{" +
                "fiveTuple=" + fiveTuple +
                ", packetLength=" + packetLength +
                '}';
    }
}