package com.promineotech.ds3_armor_calc.data_loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ResourcePatternResolver resourceResolver;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> errorFiles = new HashMap<>();

        loadData("classpath:armor/**/*.json",   "Armor",   errorFiles);
        loadData("classpath:weapons/**/*.json", "Weapon",  errorFiles);
        loadData("classpath:rings/*.json",      "Ring",    errorFiles);
        loadData("classpath:shields/**/*.json", "Shield",  errorFiles);

        if (!errorFiles.isEmpty()) {
            System.err.println("\nThe following files failed to load:");
            errorFiles.forEach((filename, errorMessage) -> {
                System.err.println(filename + ": " + errorMessage);
            });
        } else {
            System.out.println("\nAll files loaded successfully.");
        }
    }

    private void loadData(String resourcePattern, String category, Map<String, String> errorFiles) {
        try {
            Resource[] resources = resourceResolver.getResources(resourcePattern);
            for (Resource resource : resources) {
                try (InputStream is = resource.getInputStream()) {
                    // Copy the ObjectMapper so we can set naming strategy per category
                    ObjectMapper mapper = objectMapper.copy();

                    // For rings lowerCamelCase
                    if ("Ring".equals(category)) {
                        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
                    } else {
                        // For armor/weapons/shields  snake_case
                        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
                    }

                    // Convert JSON -> Item
                    Item item = mapper.readValue(is, Item.class);

                    // If the JSON didn't set itemType, default it
                    if (item.getItemType() == null || item.getItemType().isEmpty()) {
                        item.setItemType(category);
                    }

                    // For Weapons, infer the sub-type (e.g. "axes", "ultra_greatswords")
                    if ("Weapon".equals(category)) {
                        String subType = extractSubTypeFromResource(resource, "weapons");
                        item.setWeaponCategory(subType);
                    }
                    //Infer the shield type
                    else if ("Shield".equals(category)) {
                        String shieldType = extractSubTypeFromResource(resource, "shields");
                        item.setShieldCategory(shieldType);
                    }
                    // For Armor, infer which slot (chest/gauntlets/helm/leggings)
                    else if ("Armor".equals(category)) {
                        String slot = extractSubTypeFromResource(resource, "armor");
                        item.setArmorSlot(slot);
                    }

                    // Attempt to save the item
                    itemRepository.save(item);
                    System.out.println("Loaded " + category + ": " + item.getItemName());
                }
                catch (Exception e) {
                    String filename = resource.getFilename();
                    errorFiles.put(filename, e.getMessage());
                    System.err.println("Error loading file " + filename + ": " + e.getMessage());
                }
            }
        }
        catch (Exception e) {
            String categoryError = "Category " + category + " error";
            errorFiles.put(categoryError, e.getMessage());
            System.err.println("Error loading " + category + " resources: " + e.getMessage());
        }
    }

    /**
     * Extracts a subfolder name from the resource's URI, e.g., "chest" or "ultra_greatswords",
     * to categorize the item. If not found, returns "Unknown".
     */
    private String extractSubTypeFromResource(Resource resource, String parentFolder) {
        try {
            URI uri = resource.getURI();
            String uriStr = uri.toString();
            String marker = parentFolder + "/";
            int index = uriStr.indexOf(marker);
            if (index != -1) {
                int start = index + marker.length();
                int end = uriStr.indexOf("/", start);
                if (end > start) {
                    String rawSubType = uriStr.substring(start, end);
                    return URLDecoder.decode(rawSubType, StandardCharsets.UTF_8.name());
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error extracting sub-type from resource: " + e.getMessage());
        }
        return "Unknown";
    }
}
