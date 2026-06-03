package com.dpi;

import java.util.HashSet;
import java.util.Set;

public class RuleManager {

    private final Set<String> blockedDomains =
            new HashSet<>();

    public RuleManager() {

        blockedDomains.add("youtube.com");
        blockedDomains.add("www.youtube.com");

        blockedDomains.add("tiktok.com");
        blockedDomains.add("www.tiktok.com");

        blockedDomains.add("web.telegram.org");
    }

    public boolean isBlocked(String domain) {

        if (domain == null) {
            return false;
        }

        return blockedDomains.contains(
                domain.toLowerCase()
        );
    }
}