package com.example.auth.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/**
 * Pagination request parameters
 */
public class PageRequest {
    
    @Min(value = 0, message = "Page number must be 0 or greater")
    private int page = 0;
    
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    private int size = 10;
    
    private String sortBy = "id";
    private String sortDirection = "asc"; // asc, desc
    
    public PageRequest() {}
    
    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
    
    public PageRequest(int page, int size, String sortBy, String sortDirection) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }
    
    // Getters and Setters
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
    
    public boolean isAscending() {
        return "asc".equalsIgnoreCase(sortDirection);
    }
    
    public boolean isDescending() {
        return "desc".equalsIgnoreCase(sortDirection);
    }
}
