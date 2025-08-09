package com.example.mod_search.application.controller;

import com.example.mod_search.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;
//    @PostMapping("/push")
//    public ResponseEntity<?> push(@RequestBody String url) {
//        redisService.saveUrl(url);
//        return ResponseEntity.ok("Pushed");
//    }
//
//    @GetMapping("/pop")
//    public ResponseEntity<String> pop() {
//        String url = redisService.getNextUrl();
//        return ResponseEntity.ok(url != null ? url : "Queue empty");
//    }
}
