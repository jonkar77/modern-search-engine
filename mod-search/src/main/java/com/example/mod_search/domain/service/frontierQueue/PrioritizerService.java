package com.example.mod_search.domain.service.frontierQueue;

import com.example.mod_search.domain.model.UrlEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrioritizerService {

    private static final Logger logger = LoggerFactory.getLogger(PrioritizerService.class);

    public List<UrlEntry> prioritizeUrls() {
        logger.info("Starting URL prioritization...");

        // Example: Mocked data
        List<UrlEntry> urls = Arrays.asList(
                new UrlEntry("https://apple.com", 0.9, 1000000, 5),
                new UrlEntry("https://forum.applefans.com/post123", 0.4, 20000, 2),
                new UrlEntry("https://news.apple.com", 0.7, 300000, 4)
        );

        // Compute a composite score
        urls.forEach(url -> {
            double pagerankScore = url.getPageRank(); // Already between 0 and 1
            double trafficScore = Math.log(url.getTraffic() + 1) / 15; // Scaled
            double updateScore = url.getUpdateFrequency() / 5.0; // Normalize to 0–1

            double finalScore = pagerankScore * 0.5 + trafficScore * 0.3 + updateScore * 0.2;
            url.setPriorityScore(finalScore);
        });

        // Sort descending
        urls.sort(Comparator.comparingDouble(UrlEntry::getPriorityScore).reversed());

        logger.info("Prioritization finished.");
        return urls;
    }
}
