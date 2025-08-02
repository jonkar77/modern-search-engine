package ApilLayer.controller;

import Cache.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

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
