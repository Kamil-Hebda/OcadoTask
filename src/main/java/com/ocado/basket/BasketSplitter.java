package com.ocado.basket;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BasketSplitter {

    // Map to store delivery options for each product
    public Map<String, List<String>> deliveryOptions;

    // Constructor to initialize deliveryOptions from a JSON configuration file
    public BasketSplitter(String absolutePathToConfigFile) {
        try (FileReader reader = new FileReader(absolutePathToConfigFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
            this.deliveryOptions = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to split items in the basket into delivery groups
    public Map<String, List<String>> split(List<String> items) {

        if(items.isEmpty()) return null;
        // Map to store products grouped by delivery option
        Map<String, List<String>> deliveryProduct = new HashMap<>();
        // Map to store final delivery options with optimized grouping
        Map<String, List<String>> finalDeliveryOptions = new HashMap<>();

        // Populate the deliveryProduct map with items and their associated delivery options
        for (String item : items) {
            if (deliveryOptions.containsKey(item)) {
                for (String deliveryOption : deliveryOptions.get(item)) {
                    deliveryProduct.putIfAbsent(deliveryOption, new ArrayList<>());
                    deliveryProduct.get(deliveryOption).add(item);
                }
            }
        }

        // Optimize grouping by selecting the delivery option with the most items first
        while (!deliveryProduct.isEmpty()) {
            // Find the delivery option with the longest list of products
            String longestKey = null;
            int maxLength = 0;
            for (Map.Entry<String, List<String>> entry : deliveryProduct.entrySet()) {
                if (entry.getValue().size() > maxLength) {
                    maxLength = entry.getValue().size();
                    longestKey = entry.getKey();
                }
            }

            // If no more delivery options are available, exit the loop
            if (longestKey == null) {
                break;
            }

            // Add the delivery option with the most items to the final delivery options
            finalDeliveryOptions.put(longestKey, deliveryProduct.get(longestKey));

            // Remove the selected delivery option and its products from the remaining options
            deliveryProduct.remove(longestKey);
            for (List<String> productList : deliveryProduct.values()) {
                productList.removeAll(finalDeliveryOptions.get(longestKey));
            }
        }

        return finalDeliveryOptions;
    }
}
