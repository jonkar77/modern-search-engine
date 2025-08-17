package com.example.mod_search.domain.service.frontierQueue;

import com.example.mod_search.domain.model.UrlEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.mod_search.domain.service.frontierQueue.UrlExtractor.normalizeUrl;

@Service
public class PrioritizerService implements FrontierQueue {

    private static final Logger logger = LoggerFactory.getLogger(PrioritizerService.class);

    public List<UrlEntry> prioritizeUrls(List<String> urls) {
        logger.info("Starting URL prioritization...");

        // dummy data
        List<UrlEntry> urlList = Arrays.asList(
                new UrlEntry(urls.get(0), 0.9, 1000000, 5),
                new UrlEntry(urls.get(1), 0.4, 20000, 2),
                new UrlEntry(urls.get(2), 0.7, 300000, 4)
        );

        // Process each URL and create UrlEntry objects
        urlList.forEach(url -> {
            String plainUrl = normalizeUrl(url.getUrl(), "");
            UrlEntry urlEntry = new UrlEntry();

            urlEntry.setUrl(plainUrl);

            //TODO: Get 3rd party metrics data for URL
            // For now, using placeholder values or implement actual data fetching

            double pagerankScore = urlEntry.getPageRank(); // Already between 0 and 1
            double trafficScore = Math.log(urlEntry.getTraffic() + 1) / 15; // Scaled
            double updateScore = urlEntry.getUpdateFrequency() / 5.0; // Normalize to 0–1

            double finalScore = pagerankScore * 0.5 + trafficScore * 0.3 + updateScore * 0.2;
            urlEntry.setPriorityScore(finalScore);

            // Add to the list
//            urlEntries.add(urlEntry);
        });

        // Sort by priority score descending
        urlList.sort(Comparator.comparingDouble(UrlEntry::getPriorityScore).reversed());

        logger.info("Prioritization finished. Processed {} URLs.", urlList.size());
        return urlList;
    }
}