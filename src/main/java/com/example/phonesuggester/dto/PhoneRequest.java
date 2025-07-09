package com.example.phonesuggester.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

public class PhoneRequest {
    @NotBlank(message = "Phone name is required")
    @Size(min = 2, max = 100, message = "Phone name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "1000000.0", message = "Price cannot exceed 1,000,000")
    private Double price;
    
    @NotNull(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter code (e.g., USD, EUR)")
    private String currency;
    
    @NotEmpty(message = "At least one usage type is required")
    private List<String> usageTypes;
    
    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^https?://.*", message = "Image URL must be a valid HTTP/HTTPS URL")
    private String imageUrl;
    
    @NotNull(message = "Release date is required")
    private LocalDateTime releaseDate;
    
    @NotNull(message = "Storage capacity is required")
    @Min(value = 1, message = "Storage must be at least 1 GB")
    private Integer storageGB;
    
    @NotNull(message = "RAM capacity is required")
    @Min(value = 1, message = "RAM must be at least 1 GB")
    private Integer ramGB;
    
    @NotNull(message = "Battery capacity is required")
    @Min(value = 1000, message = "Battery capacity must be at least 1000 mAh")
    private Integer batteryCapacity;
    
    @NotNull(message = "Screen size is required")
    @DecimalMin(value = "3.0", message = "Screen size must be at least 3.0 inches")
    @DecimalMax(value = "10.0", message = "Screen size cannot exceed 10.0 inches")
    private Double screenSize;
    
    @NotNull(message = "Camera count is required")
    @Min(value = 1, message = "Must have at least 1 camera")
    @Max(value = 10, message = "Cannot have more than 10 cameras")
    private Integer cameraCount;
    
    private Boolean is5G = false;
    private Boolean isWaterResistant = false;
    private Boolean hasWirelessCharging = false;
    
    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private Double rating;

    // Getters and Setters
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
} 