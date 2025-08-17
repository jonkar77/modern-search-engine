package com.example.mod_search.domain.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.net.MalformedURLException;


public class UrlEntry {


    private String url;
    private double pageRank;
    private long traffic;
    private int updateFrequency; // updates/week
    private double priorityScore;

    Logger logger = LoggerFactory.getLogger(UrlEntry.class);

    public UrlEntry() {
    }

    public UrlEntry(String url, double pageRank, long traffic, int updateFrequency) {
        this.url = url;
        this.pageRank = pageRank;
        this.traffic = traffic;
        this.updateFrequency = updateFrequency;
    }

    public String getUrl() {
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            return url;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public double getPageRank() { return pageRank; }
    public long getTraffic() { return traffic; }
    public int getUpdateFrequency() { return updateFrequency; }
    public double getPriorityScore() { return priorityScore; }
    public void setPriorityScore(double priorityScore) { this.priorityScore = priorityScore; }
}
