package com.example.phonesuggester.service;

import com.example.phonesuggester.model.Category;
import com.example.phonesuggester.model.Phone;
import com.example.phonesuggester.model.User;
import com.example.phonesuggester.repository.CategoryRepository;
import com.example.phonesuggester.repository.PhoneRepository;
import com.example.phonesuggester.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            List<Category> categories = Arrays.asList(
                new Category("Budget", "Affordable smartphones for everyone"),
                new Category("Flagship", "Top-of-the-line premium phones"),
                new Category("Gaming", "Phones optimized for gaming"),
                new Category("Camera", "Best camera phones"),
                new Category("Foldable", "Latest foldable phones")
            );
            categoryRepository.saveAll(categories);
        }
        if (phoneRepository.count() == 0) {
            List<Phone> phones = new ArrayList<>();
            // Example phone data (repeat and vary for 100+ entries)
            String[] brands = {"Samsung", "Apple", "Xiaomi", "OnePlus", "Realme", "Vivo", "Oppo", "Motorola", "Google", "Nothing", "iQOO", "Tecno", "Infinix", "Lava"};
            String[] categories = {"Budget", "Flagship", "Gaming", "Camera", "Foldable"};
            String[] images = {
                "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-s24-ultra-5g.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-15-pro-max.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/xiaomi-14-ultra.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/oneplus-12.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/realme-12-pro-plus.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/vivo-x100-pro.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/oppo-find-x7-ultra.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/motorola-edge-50-pro.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/google-pixel-8-pro.jpg",
                "https://fdn2.gsmarena.com/vv/bigpic/nothing-phone-2a.jpg"
            };
            Random rand = new Random();
            for (int i = 1; i <= 100; i++) {
                String brand = brands[rand.nextInt(brands.length)];
                String category = categories[rand.nextInt(categories.length)];
                String name = brand + " Model " + (1000 + i);
                String description = "The " + name + " is a great phone for " + category.toLowerCase() + " users.";
                double price = 8000 + rand.nextInt(90000);
                String currency = "INR";
                List<String> usageTypes = Collections.singletonList(category);
                String imageUrl = images[rand.nextInt(images.length)];
                LocalDateTime releaseDate = LocalDateTime.now().minusDays(rand.nextInt(1000));
                int storageGB = 32 * (1 + rand.nextInt(8));
                int ramGB = 2 * (1 + rand.nextInt(8));
                int battery = 3000 + rand.nextInt(3000);
                double screen = 5.0 + rand.nextDouble() * 2.5;
                int cameras = 1 + rand.nextInt(4);
                double rating = 2.5 + rand.nextDouble() * 2.5;
                Phone phone = new Phone(name, brand, description, price, currency, usageTypes, imageUrl, releaseDate, storageGB, ramGB, battery, screen, cameras, rating);
                phone.setIs5G(rand.nextBoolean());
                phone.setIsWaterResistant(rand.nextBoolean());
                phone.setHasWirelessCharging(rand.nextBoolean());
                phone.setReviewCount(rand.nextInt(1000));
                phones.add(phone);
            }
            phoneRepository.saveAll(phones);
        }
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("testuser");
            user.setEmail("testuser@example.com");
            user.setPassword(passwordEncoder.encode("password123"));
            user.setRoles(Set.of("USER"));
            userRepository.save(user);
        }
    }
} 