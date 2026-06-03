package com.dpi;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SNIExtractor {

    private static final Pattern DOMAIN_PATTERN =
            Pattern.compile(
                    "([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}"
            );

    public static String extractDomain(
            byte[] payload) {

        try {

            String data =
                    new String(
                            payload,
                            StandardCharsets.UTF_8
                    );

            Matcher matcher =
                    DOMAIN_PATTERN.matcher(data);

            if (matcher.find()) {

                return matcher.group();
            }

        } catch (Exception e) {

            return null;
        }

        return null;
    }
}