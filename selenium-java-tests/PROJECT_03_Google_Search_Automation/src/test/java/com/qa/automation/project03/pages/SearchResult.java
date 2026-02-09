package com.qa.automation.project03.pages;

/**
 * Represents a single Google search result (title, link, snippet).
 */
public class SearchResult {
    private final String title;
    private final String link;
    private final String snippet;

    public SearchResult(String title, String link, String snippet) {
        this.title = title != null ? title : "";
        this.link = link != null ? link : "";
        this.snippet = snippet != null ? snippet : "";
    }

    public String getTitle() { return title; }
    public String getLink() { return link; }
    public String getSnippet() { return snippet; }
}
