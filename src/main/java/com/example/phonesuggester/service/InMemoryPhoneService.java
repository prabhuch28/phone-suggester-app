package com.example.phonesuggester.service;

import com.example.phonesuggester.dto.PhoneRequest;
import com.example.phonesuggester.dto.PhoneResponse;
import com.example.phonesuggester.model.Phone;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class InMemoryPhoneService {

    private final Map<String, Phone> phones = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public InMemoryPhoneService() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample phones data
        List<Phone> samplePhones = Arrays.asList(
            createPhone("iPhone 15 Pro", "Apple", "Latest iPhone with A17 Pro chip", 999.0, "USD", 
                Arrays.asList("Gaming", "Photography", "Business"), "https://example.com/iphone15pro.jpg",
                LocalDate.of(2023, 9, 22), 256, 8, 4441, 6.1, 3, true, true, true, 4.8),
            
            createPhone("Samsung Galaxy S24 Ultra", "Samsung", "Premium Android flagship", 1199.0, "USD",
                Arrays.asList("Gaming", "Photography", "Business"), "https://example.com/s24ultra.jpg",
                LocalDate.of(2024, 1, 17), 512, 12, 5000, 6.8, 4, true, true, true, 4.7),
            
            createPhone("Google Pixel 8 Pro", "Google", "Best camera phone with AI features", 999.0, "USD",
                Arrays.asList("Photography", "Business"), "https://example.com/pixel8pro.jpg",
                LocalDate.of(2023, 10, 4), 256, 12, 4950, 6.7, 3, true, true, true, 4.6),
            
            createPhone("OnePlus 12", "OnePlus", "Fast performance with great value", 799.0, "USD",
                Arrays.asList("Gaming", "Photography"), "https://example.com/oneplus12.jpg",
                LocalDate.of(2024, 1, 23), 256, 16, 5400, 6.82, 3, true, true, false, 4.5),
            
            createPhone("Xiaomi 14 Ultra", "Xiaomi", "Professional photography phone", 1299.0, "USD",
                Arrays.asList("Photography", "Gaming"), "https://example.com/xiaomi14ultra.jpg",
                LocalDate.of(2024, 2, 22), 512, 16, 5000, 6.73, 4, true, true, true, 4.4)
        );

        for (Phone phone : samplePhones) {
            phones.put(phone.getId(), phone);
        }
    }

    private Phone createPhone(String name, String brand, String description, Double price, String currency,
                            List<String> usageTypes, String imageUrl, LocalDate releaseDate, Integer storageGB,
                            Integer ramGB, Integer batteryCapacity, Double screenSize, Integer cameraCount,
                            Boolean is5G, Boolean isWaterResistant, Boolean hasWirelessCharging, Double rating) {
        Phone phone = new Phone();
        phone.setId(UUID.randomUUID().toString());
        phone.setName(name);
        phone.setBrand(brand);
        phone.setDescription(description);
        phone.setPrice(price);
        phone.setCurrency(currency);
        phone.setUsageTypes(usageTypes);
        phone.setImageUrl(imageUrl);
        phone.setReleaseDate(releaseDate.atStartOfDay());
        phone.setStorageGB(storageGB);
        phone.setRamGB(ramGB);
        phone.setBatteryCapacity(batteryCapacity);
        phone.setScreenSize(screenSize);
        phone.setCameraCount(cameraCount);
        phone.setIs5G(is5G);
        phone.setIsWaterResistant(isWaterResistant);
        phone.setHasWirelessCharging(hasWirelessCharging);
        phone.setRating(rating);
        phone.setReviewCount(random.nextInt(1000) + 100);
        phone.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
        phone.setUpdatedAt(LocalDateTime.now());
        return phone;
    }

    @Cacheable(value = "phones", key = "#id")
    public Optional<PhoneResponse> findById(String id) {
        return Optional.ofNullable(phones.get(id))
                .map(this::convertToResponse);
    }

    @Cacheable(value = "phones", key = "'all'")
    public List<PhoneResponse> findAll() {
        return phones.values()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Page<PhoneResponse> findAllPaginated(Pageable pageable) {
        List<PhoneResponse> allPhones = findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allPhones.size());
        
        if (start > allPhones.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, allPhones.size());
        }
        
        List<PhoneResponse> pageContent = allPhones.subList(start, end);
        return new PageImpl<>(pageContent, pageable, allPhones.size());
    }

    @Cacheable(value = "phones", key = "'brand:' + #brand")
    public List<PhoneResponse> findByBrand(String brand) {
        return phones.values()
                .stream()
                .filter(phone -> phone.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "phones", key = "'type:' + #usageType")
    public List<PhoneResponse> findByUsageType(String usageType) {
        return phones.values()
                .stream()
                .filter(phone -> phone.getUsageTypes().stream()
                        .anyMatch(type -> type.toLowerCase().contains(usageType.toLowerCase())))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "phones", key = "'price:' + #minPrice + '-' + #maxPrice")
    public List<PhoneResponse> findByPriceRange(Double minPrice, Double maxPrice) {
        return phones.values()
                .stream()
                .filter(phone -> phone.getPrice() >= minPrice && phone.getPrice() <= maxPrice)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "phones", key = "'search:' + #query")
    public List<PhoneResponse> searchPhones(String query) {
        String lowerQuery = query.toLowerCase();
        return phones.values()
                .stream()
                .filter(phone -> phone.getName().toLowerCase().contains(lowerQuery) ||
                        phone.getBrand().toLowerCase().contains(lowerQuery) ||
                        phone.getDescription().toLowerCase().contains(lowerQuery))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "phones", allEntries = true)
    public PhoneResponse createPhone(PhoneRequest phoneRequest) {
        Phone phone = convertToEntity(phoneRequest);
        phone.setId(UUID.randomUUID().toString());
        phone.setCreatedAt(LocalDateTime.now());
        phone.setUpdatedAt(LocalDateTime.now());
        phone.setReviewCount(0);
        
        phones.put(phone.getId(), phone);
        return convertToResponse(phone);
    }

    @CacheEvict(value = "phones", allEntries = true)
    public Optional<PhoneResponse> updatePhone(String id, PhoneRequest phoneRequest) {
        Phone existingPhone = phones.get(id);
        if (existingPhone != null) {
            updatePhoneFromRequest(existingPhone, phoneRequest);
            existingPhone.setUpdatedAt(LocalDateTime.now());
            phones.put(id, existingPhone);
            return Optional.of(convertToResponse(existingPhone));
        }
        return Optional.empty();
    }

    @CacheEvict(value = "phones", allEntries = true)
    public boolean deletePhone(String id) {
        return phones.remove(id) != null;
    }

    private Phone convertToEntity(PhoneRequest request) {
        Phone phone = new Phone();
        phone.setName(request.getName());
        phone.setBrand(request.getBrand());
        phone.setDescription(request.getDescription());
        phone.setPrice(request.getPrice());
        phone.setCurrency(request.getCurrency());
        phone.setUsageTypes(request.getUsageTypes());
        phone.setImageUrl(request.getImageUrl());
        phone.setReleaseDate(request.getReleaseDate());
        phone.setStorageGB(request.getStorageGB());
        phone.setRamGB(request.getRamGB());
        phone.setBatteryCapacity(request.getBatteryCapacity());
        phone.setScreenSize(request.getScreenSize());
        phone.setCameraCount(request.getCameraCount());
        phone.setIs5G(request.getIs5G());
        phone.setIsWaterResistant(request.getIsWaterResistant());
        phone.setHasWirelessCharging(request.getHasWirelessCharging());
        phone.setRating(request.getRating());
        return phone;
    }

    private void updatePhoneFromRequest(Phone phone, PhoneRequest request) {
        phone.setName(request.getName());
        phone.setBrand(request.getBrand());
        phone.setDescription(request.getDescription());
        phone.setPrice(request.getPrice());
        phone.setCurrency(request.getCurrency());
        phone.setUsageTypes(request.getUsageTypes());
        phone.setImageUrl(request.getImageUrl());
        phone.setReleaseDate(request.getReleaseDate());
        phone.setStorageGB(request.getStorageGB());
        phone.setRamGB(request.getRamGB());
        phone.setBatteryCapacity(request.getBatteryCapacity());
        phone.setScreenSize(request.getScreenSize());
        phone.setCameraCount(request.getCameraCount());
        phone.setIs5G(request.getIs5G());
        phone.setIsWaterResistant(request.getIsWaterResistant());
        phone.setHasWirelessCharging(request.getHasWirelessCharging());
        phone.setRating(request.getRating());
    }

    private PhoneResponse convertToResponse(Phone phone) {
        return new PhoneResponse(
                phone.getId(),
                phone.getName(),
                phone.getBrand(),
                phone.getDescription(),
                phone.getPrice(),
                phone.getCurrency(),
                phone.getUsageTypes(),
                phone.getImageUrl(),
                phone.getReleaseDate(),
                phone.getStorageGB(),
                phone.getRamGB(),
                phone.getBatteryCapacity(),
                phone.getScreenSize(),
                phone.getCameraCount(),
                phone.getIs5G(),
                phone.getIsWaterResistant(),
                phone.getHasWirelessCharging(),
                phone.getRating(),
                phone.getReviewCount(),
                phone.getCreatedAt(),
                phone.getUpdatedAt()
        );
    }
} 