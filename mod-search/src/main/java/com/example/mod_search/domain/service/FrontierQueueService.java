package com.example.mod_search.domain.service;

// services/FrontierQueueService.java
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Set;
import java.util.HashSet;

@Service
public class FrontierQueueService {

    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    private final Set<String> visited = new HashSet<>();

    public synchronized void enqueue(String url) {
        if (!visited.contains(url)) {
            queue.add(url);
            visited.add(url);
        }
    }

    public String dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
