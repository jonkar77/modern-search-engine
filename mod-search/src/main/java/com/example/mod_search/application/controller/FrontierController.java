package com.example.mod_search.application.controller;

import com.example.mod_search.domain.model.UrlEntry;
import com.example.mod_search.domain.service.frontierQueue.PrioritizerService;
import lombok.*;
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
    private final PrioritizerService prioritizerService;

    public FrontierController(PrioritizerService prioritizerService) {
        this.prioritizerService = prioritizerService;
    }

    /**
     * Endpoint to get prioritized URLs.
     * Computes based on PageRank, web traffic, update frequency, etc.
     */
    @GetMapping("/queue")
    public ResponseEntity<List<UrlEntry>> frontierQueue() {
        logger.info("Received request to prioritize URLs for crawling.");

        List<UrlEntry> prioritizedList = prioritizerService.prioritizeUrls();

        logger.info("Prioritization completed. Returning {} URLs.", prioritizedList.size());

        return ResponseEntity.ok(prioritizedList);
    }
}
