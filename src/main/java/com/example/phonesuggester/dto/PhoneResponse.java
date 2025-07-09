package com.example.phonesuggester.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PhoneResponse {
    private String id;
    private String name;
    private String brand;
    private String description;
    private Double price;
    private String currency;
    private List<String> usageTypes;
    private String imageUrl;
    private LocalDateTime releaseDate;
    private Integer storageGB;
    private Integer ramGB;
    private Integer batteryCapacity;
    private Double screenSize;
    private Integer cameraCount;
    private Boolean is5G;
    private Boolean isWaterResistant;
    private Boolean hasWirelessCharging;
    private Double rating;
    private Integer reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PhoneResponse() {}

    public PhoneResponse(String id, String name, String brand, String description, Double price, 
                        String currency, List<String> usageTypes, String imageUrl, LocalDateTime releaseDate,
                        Integer storageGB, Integer ramGB, Integer batteryCapacity, Double screenSize,
                        Integer cameraCount, Boolean is5G, Boolean isWaterResistant, Boolean hasWirelessCharging,
                        Double rating, Integer reviewCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.usageTypes = usageTypes;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.storageGB = storageGB;
        this.ramGB = ramGB;
        this.batteryCapacity = batteryCapacity;
        this.screenSize = screenSize;
        this.cameraCount = cameraCount;
        this.is5G = is5G;
        this.isWaterResistant = isWaterResistant;
        this.hasWirelessCharging = hasWirelessCharging;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<String> getUsageTypes() { return usageTypes; }
    public void setUsageTypes(List<String> usageTypes) { this.usageTypes = usageTypes; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDateTime releaseDate) { this.releaseDate = releaseDate; }

    public Integer getStorageGB() { return storageGB; }
    public void setStorageGB(Integer storageGB) { this.storageGB = storageGB; }

    public Integer getRamGB() { return ramGB; }
    public void setRamGB(Integer ramGB) { this.ramGB = ramGB; }

    public Integer getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(Integer batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public Double getScreenSize() { return screenSize; }
    public void setScreenSize(Double screenSize) { this.screenSize = screenSize; }

    public Integer getCameraCount() { return cameraCount; }
    public void setCameraCount(Integer cameraCount) { this.cameraCount = cameraCount; }

    public Boolean getIs5G() { return is5G; }
    public void setIs5G(Boolean is5G) { this.is5G = is5G; }

    public Boolean getIsWaterResistant() { return isWaterResistant; }
    public void setIsWaterResistant(Boolean isWaterResistant) { this.isWaterResistant = isWaterResistant; }

    public Boolean getHasWirelessCharging() { return hasWirelessCharging; }
    public void setHasWirelessCharging(Boolean hasWirelessCharging) { this.hasWirelessCharging = hasWirelessCharging; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 