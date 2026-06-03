package com.dpi;

import java.util.HashMap;
import java.util.Map;

public class ConnectionTracker {

    private final Map<FiveTuple, AppType> connectionMap =
            new HashMap<>();

    public void addConnection(
            FiveTuple tuple,
            AppType appType) {

        connectionMap.put(tuple, appType);
    }

    public AppType getApplication(
            FiveTuple tuple) {

        return connectionMap.get(tuple);
    }

    public boolean containsConnection(
            FiveTuple tuple) {

        return connectionMap.containsKey(tuple);
    }

    public int getTotalConnections() {

        return connectionMap.size();
    }
}