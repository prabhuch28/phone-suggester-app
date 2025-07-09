package com.example.phonesuggester.controller;

import com.example.phonesuggester.dto.PhoneRequest;
import com.example.phonesuggester.dto.PhoneResponse;
import com.example.phonesuggester.service.InMemoryPhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/phones")
@Validated
@Tag(name = "Phone Management", description = "APIs for managing phone suggestions")
public class PhoneController {

    @Autowired
    private InMemoryPhoneService phoneService;

    @GetMapping
    @Operation(summary = "Get all phones", description = "Retrieve a paginated list of all phones")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved phones",
                    content = @Content(schema = @Schema(implementation = PhoneResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<Page<PhoneResponse>>> getAllPhones(
            @Parameter(description = "Page number (0-based)") 
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size") 
            @RequestParam(defaultValue = "10") @Min(1) @Positive int size,
            @Parameter(description = "Sort field") 
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") 
            @RequestParam(defaultValue = "ASC") String sortDir) {
        
        List<PhoneResponse> allPhones = phoneService.findAll();
        
        // Simple pagination implementation
        int start = page * size;
        int end = Math.min(start + size, allPhones.size());
        List<PhoneResponse> pageContent = allPhones.subList(start, end);
        
        Page<PhoneResponse> phonePage = new PageImpl<>(pageContent, PageRequest.of(page, size), allPhones.size());
        
        return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(phonePage, "Phones retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get phone by ID", description = "Retrieve a specific phone by its ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved phone"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Phone not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<PhoneResponse>> getPhoneById(
            @Parameter(description = "Phone ID") 
            @PathVariable @NotBlank String id) {
        
        Optional<PhoneResponse> phone = phoneService.findById(id);
        if (phone.isPresent()) {
            return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(phone.get(), "Phone retrieved successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(com.example.phonesuggester.dto.ApiResponse.error("Phone not found with id: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Create a new phone", description = "Add a new phone to the system")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Phone created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid phone data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<PhoneResponse>> createPhone(
            @Parameter(description = "Phone data") 
            @Valid @RequestBody PhoneRequest phoneRequest) {
        
        PhoneResponse createdPhone = phoneService.createPhone(phoneRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.example.phonesuggester.dto.ApiResponse.success(createdPhone, "Phone created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a phone", description = "Update an existing phone by ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Phone updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Phone not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid phone data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<PhoneResponse>> updatePhone(
            @Parameter(description = "Phone ID") 
            @PathVariable @NotBlank String id,
            @Parameter(description = "Updated phone data") 
            @Valid @RequestBody PhoneRequest phoneRequest) {
        
        Optional<PhoneResponse> updatedPhone = phoneService.updatePhone(id, phoneRequest);
        if (updatedPhone.isPresent()) {
            return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(updatedPhone.get(), "Phone updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(com.example.phonesuggester.dto.ApiResponse.error("Phone not found with id: " + id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a phone", description = "Delete a phone by ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Phone deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Phone not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<Void>> deletePhone(
            @Parameter(description = "Phone ID") 
            @PathVariable @NotBlank String id) {
        
        boolean deleted = phoneService.deletePhone(id);
        if (deleted) {
            return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(null, "Phone deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(com.example.phonesuggester.dto.ApiResponse.error("Phone not found with id: " + id));
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search phones", description = "Search phones by query string")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Search completed successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid search parameters"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<List<PhoneResponse>>> searchPhones(
            @Parameter(description = "Search query") 
            @RequestParam @NotBlank String query) {
        
        List<PhoneResponse> phones = phoneService.searchPhones(query);
        return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(phones, "Search completed successfully"));
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get phones by brand", description = "Retrieve all phones from a specific brand")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Phones retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid brand parameter"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<List<PhoneResponse>>> getPhonesByBrand(
            @Parameter(description = "Brand name") 
            @PathVariable @NotBlank String brand) {
        
        List<PhoneResponse> phones = phoneService.findByBrand(brand);
        return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(phones, "Phones retrieved successfully"));
    }

    @GetMapping("/type/{usageType}")
    @Operation(summary = "Get phones by usage type", description = "Retrieve phones by usage type")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Phones retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid usage type parameter"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<List<PhoneResponse>>> getPhonesByUsageType(
            @Parameter(description = "Usage type") 
            @PathVariable @NotBlank String usageType) {
        
        List<PhoneResponse> phones = phoneService.findByUsageType(usageType);
        return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(phones, "Phones retrieved successfully"));
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get phones by price range", description = "Retrieve phones within a price range")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Phones retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid price range parameters"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<List<PhoneResponse>>> getPhonesByPriceRange(
            @Parameter(description = "Minimum price") 
            @RequestParam @Min(0) Double minPrice,
            @Parameter(description = "Maximum price") 
            @RequestParam @Min(0) Double maxPrice) {
        
        if (minPrice > maxPrice) {
            return ResponseEntity.badRequest()
                    .body(com.example.phonesuggester.dto.ApiResponse.error("Minimum price cannot be greater than maximum price"));
        }
        
        List<PhoneResponse> phones = phoneService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success(phones, "Phones retrieved successfully"));
    }

    @GetMapping("/external")
    @Operation(summary = "Get external phones", description = "Retrieve phones from external API (DummyJSON)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "External phones retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<com.example.phonesuggester.dto.ApiResponse<String>> getExternalPhones(
            @Parameter(description = "Limit number of results") 
            @RequestParam(defaultValue = "100") @Min(1) @Positive int limit) {
        
        try {
            // This would be moved to a separate external service
            return ResponseEntity.ok(com.example.phonesuggester.dto.ApiResponse.success("External API response", "External phones retrieved"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(com.example.phonesuggester.dto.ApiResponse.error("Failed to fetch external phones: " + e.getMessage()));
        }
    }
}