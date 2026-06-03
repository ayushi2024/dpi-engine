package com.dpi;

import java.nio.charset.StandardCharsets;

public class AppClassifier {

    public static AppType classify(
            byte[] payload,
            int destinationPort) {

        String data =
                new String(payload, StandardCharsets.UTF_8)
                        .toLowerCase();

        if (data.contains("youtube")) {
            return AppType.YOUTUBE;
        }

        if (data.contains("github")) {
            return AppType.GITHUB;
        }

        if (data.contains("chatgpt")
                || data.contains("openai")) {
            return AppType.CHATGPT;
        }

        if (data.contains("google")) {
            return AppType.GOOGLE;
        }

        if (data.contains("telegram")) {
            return AppType.TELEGRAM;
        }

        if (data.contains("tiktok")) {
            return AppType.TIKTOK;
        }

        if (destinationPort == 53) {
            return AppType.DNS;
        }

        if (destinationPort == 80) {
            return AppType.HTTP;
        }

        if (destinationPort == 443) {
            return AppType.HTTPS;
        }

        return AppType.UNKNOWN;
    }

    public static AppType classifyDomain(
            String domain) {

        if (domain == null) {
            return AppType.UNKNOWN;
        }

        domain = domain.toLowerCase();

        if (domain.contains("youtube")) {
            return AppType.YOUTUBE;
        }

        if (domain.contains("google")) {
            return AppType.GOOGLE;
        }

        if (domain.contains("github")) {
            return AppType.GITHUB;
        }

        if (domain.contains("chatgpt")) {
            return AppType.CHATGPT;
        }

        if (domain.contains("openai")) {
            return AppType.OPENAI;
        }

        if (domain.contains("telegram")) {
            return AppType.TELEGRAM;
        }

        if (domain.contains("tiktok")) {
            return AppType.TIKTOK;
        }

        if (domain.contains("netflix")) {
            return AppType.NETFLIX;
        }

        return AppType.UNKNOWN;
    }
}