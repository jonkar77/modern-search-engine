package com.example.mod_search.application.controller;

import com.example.mod_search.domain.model.UrlEntry;
import com.example.mod_search.domain.service.frontierQueue.FrontierQueueService;
import com.example.mod_search.domain.service.frontierQueue.FrontierQueue;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/frontier")
public class FrontierController {

    private static final Logger logger = LoggerFactory.getLogger(FrontierController.class);
    private final FrontierQueueService frontierQueueService;
    private final FrontierQueue frontierQueue;

    public FrontierController(FrontierQueueService frontierQueueService, FrontierQueue frontierQueue) {
        this.frontierQueueService = frontierQueueService;
        this.frontierQueue = frontierQueue;
    }

    /**
     * Endpoint to get prioritized URLs.
     * Computes based on PageRank, web traffic, update frequency, etc.
     */
    @PostMapping("/queue")
    @Timed(value = "api.response.time",
            description = "Time taken to prioritize URLs",
            extraTags = {"endpoint", "queue"})
    public ResponseEntity<List<UrlEntry>> frontierQueue(@RequestBody List<String> urls) {
        try {
            logger.info("Received request to prioritize URLs for crawling.");
            List<UrlEntry> prioritizedList = frontierQueue.prioritizeUrls(urls);

            logger.info("Prioritization completed. Returning {} URLs.", prioritizedList.size());

            return ResponseEntity.ok(prioritizedList);
        } catch (Exception e) {
            logger.error("Error during URL prioritization: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to prioritize URLs", e);
        }
    }
}