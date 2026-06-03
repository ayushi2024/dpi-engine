package com.dpi;

import java.util.Objects;

public class FiveTuple {

    private final String sourceIp;
    private final int sourcePort;

    private final String destinationIp;
    private final int destinationPort;

    private final String protocol;

    public FiveTuple(
            String sourceIp,
            int sourcePort,
            String destinationIp,
            int destinationPort,
            String protocol) {

        this.sourceIp = sourceIp;
        this.sourcePort = sourcePort;
        this.destinationIp = destinationIp;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        FiveTuple other = (FiveTuple) obj;

        return sourcePort == other.sourcePort
                && destinationPort == other.destinationPort
                && Objects.equals(sourceIp, other.sourceIp)
                && Objects.equals(destinationIp, other.destinationIp)
                && Objects.equals(protocol, other.protocol);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                sourceIp,
                sourcePort,
                destinationIp,
                destinationPort,
                protocol);
    }

    @Override
    public String toString() {

        return sourceIp + ":" + sourcePort
                + " -> "
                + destinationIp + ":" + destinationPort
                + " [" + protocol + "]";
    }
}