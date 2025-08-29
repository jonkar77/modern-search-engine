package com.example.mod_search.domain.service.frontierQueue;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UrlExtractor {

    // Tracking params we want to strip
    private static final Set<String> TRACKING_PARAMS = new HashSet<>(Arrays.asList(
            "utm_source", "utm_medium", "utm_campaign", "utm_term", "utm_content", "fbclid", "gclid"
    ));

    /**
     * Normalize and resolve URL.
     *
     * @param baseUrl The page's base URL (where link is found).
     * @param link The raw link extracted (could be relative, absolute, malformed).
     * @return Cleaned absolute URL, or null if invalid.
     */
    public static String normalizeUrl(String baseUrl, String link) {
        try {
            // Resolve relative links
            URI baseUri = new URI(baseUrl);
            URI resolved = baseUri.resolve(link);

            // Remove fragment (#something)
            URI noFragment = new URI(
                    resolved.getScheme(),
                    resolved.getAuthority(),
                    resolved.getPath(),
                    resolved.getQuery(),
                    null // fragment removed
            );

            // Clean query params (strip tracking)
            String cleanQuery = cleanQuery(noFragment.getQuery());

            URI cleanUri = new URI(
                    noFragment.getScheme(),
                    noFragment.getAuthority(),
                    noFragment.getPath(),
                    cleanQuery,
                    null
            );

            return cleanUri.toString();

        } catch (URISyntaxException e) {
            System.err.println("Invalid URL: " + baseUrl + " + " + link);
            return null;
        }
    }

    /**
     * Extract domain from URL (without www).
     */
    public static String extractDomain(String url) {
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            if (host.startsWith("www.")) {
                host = host.substring(4);
            }
            return host;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Strip tracking params from query string.
     */
    private static String cleanQuery(String query) {
        if (query == null || query.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();
        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String key = pair.split("=")[0];
            if (!TRACKING_PARAMS.contains(key)) {
                if (sb.length() > 0) sb.append("&");
                sb.append(pair);
            }
        }
        return sb.length() > 0 ? sb.toString() : null;
    }
}
