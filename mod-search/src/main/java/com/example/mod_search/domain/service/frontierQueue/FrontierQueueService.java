package com.example.mod_search.domain.service.frontierQueue;

import com.example.mod_search.application.utils.AppConstants;
import com.example.mod_search.domain.model.UrlEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.mod_search.domain.service.frontierQueue.UrlExtractor.extractDomain;
import static com.example.mod_search.domain.service.frontierQueue.UrlExtractor.normalizeUrl;

@Service
public class FrontierQueueService implements FrontierQueue {

    private static final Logger logger = LoggerFactory.getLogger(FrontierQueueService.class);
    private final Map<String, Queue<UrlEntry>> domainQueues = new HashMap<>();

    public List<UrlEntry> prioritizeUrls(List<String> urls) {
        try {
            logger.info("Starting URL prioritization...");

            // dummy data
            List<UrlEntry> urlList = Arrays.asList(
                    new UrlEntry(urls.get(0), 0.9, 1000000, 5,  ""),
                    new UrlEntry(urls.get(1), 0.4, 20000, 2, ""),
                    new UrlEntry(urls.get(2), 0.7, 300000, 4, "")
            );

            // Process each URL and create UrlEntry objects
            urlList.forEach(url -> {
                String plainUrl = normalizeUrl(url.getUrl(), "");
    //            UrlEntry urlEntry = new UrlEntry();

                url.setUrl(plainUrl);

                //TODO: Get 3rd party metrics data for URL
                // For now, using placeholder values or implement actual data fetching

                double pagerankScore = url.getPageRank(); // Already between 0 and 1
                double trafficScore = Math.log(url.getTraffic() + 1) / 15; // Scaled
                double updateScore = url.getUpdateFrequency() / 5.0; // Normalize to 0–1

                double finalScore = pagerankScore * 0.5 + trafficScore * 0.3 + updateScore * 0.2;
                url.setPriorityScore(finalScore);

                // Add to the list
    //            urlEntries.add(urlEntry);
            });

            // Sort by priority score descending
            urlList.sort(Comparator.comparingDouble(UrlEntry::getPriorityScore).reversed());

            logger.info("Prioritization finished. Processed {} URLs.", urlList.size());
            enqueue(urlList);
            return urlList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void enqueue(List<UrlEntry> urls) {
        long countUrl = 0L;
        AppConstants appConstants = new AppConstants();
        long n = urls.size();

        for (UrlEntry url : urls) {
            countUrl++;

            // Percentile-based bucket assignment
            String bucketType;
            if (countUrl <= 0.3 * n) bucketType = appConstants.BucketType.getFirst();
            else if (countUrl <= 0.7 * n) bucketType = appConstants.BucketType.get(1);
            else bucketType = appConstants.BucketType.getLast();

            url.setBucketType(bucketType);

            // Domain extraction
            String domain = extractDomain(url.getUrl());

            // Initialize domain queue if not exists
            domainQueues.computeIfAbsent(domain, d -> new LinkedList<>());

            // Enqueue URL into domain queue
            domainQueues.get(domain).offer(url);

            logger.info("Enqueued URL {} into domain {} queue under bucket {}", url.getUrl(), domain, bucketType);
        }
    }

    // Round-robin fetch from domain queues
    public UrlEntry getNextUrl() {
        if (domainQueues.isEmpty()) return null;

        Iterator<Map.Entry<String, Queue<UrlEntry>>> it = domainQueues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Queue<UrlEntry>> entry = it.next();
            Queue<UrlEntry> queue = entry.getValue();
            if (!queue.isEmpty()) {
                UrlEntry nextUrl = queue.poll();
                if (queue.isEmpty()) it.remove();
                return nextUrl;
            }
        }
        return null;
    }




}