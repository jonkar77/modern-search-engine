package com.example.mod_search.ApilLayer.controller;

import com.example.mod_search.Cache.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
//@RequestMapping("/api/health")
public class HealthController {
    private final RedisService redisService;
    public HealthController(RedisService redisService) {
        this.redisService = redisService;
    }
    @GetMapping("/api/health/redis")
    public ResponseEntity<?> checkRedis() {
        boolean healthy = redisService.isRedisHealthy();
        return healthy
                ? ResponseEntity.ok(Map.of("status", "UP"))
                : ResponseEntity.status(500).body(Map.of("status", "DOWN"));
    }
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
