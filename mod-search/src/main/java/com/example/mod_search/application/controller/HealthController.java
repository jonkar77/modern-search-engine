package com.example.mod_search.application.controller;

import com.example.mod_search.infra.redis.RedisService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {
    private final RedisService redisService;

    @GetMapping("/infra")
    public Map<String, Object> healthCheck() {
        return Map.of(
                "redis", redisService.isRedisHealthy()
//                "meiliSearch", meiliSearchService.isHealthy()
        );
    }
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(Map.of("status", "app server UP"));
    }
}
