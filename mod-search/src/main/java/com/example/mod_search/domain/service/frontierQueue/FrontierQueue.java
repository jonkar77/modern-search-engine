package com.example.mod_search.domain.service.frontierQueue;
import com.example.mod_search.domain.model.UrlEntry;

import java.util.List;

public interface FrontierQueue {
    public List<UrlEntry> prioritizeUrls(List<String> urls);
}
