package com.promineotech.ds3_armor_calc.gui;

import com.promineotech.ds3_armor_calc.entities.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ApiClient {

  /**
   * Fetches all items from the API and returns only those with item_type = "Ring".
   */
  public static List<Item> fetchRingItems() {
    try {
      // 1) GET all items from the API endpoint
      URL url = URI.create("http://localhost:8080/items").toURL(); 
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != 200) {
          throw new RuntimeException("HTTP GET failed with " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
          sb.append(line);
      }
      br.close();
      conn.disconnect();

      // 2) Configure the ObjectMapper to use snake_case.
      ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

      // 3) Convert JSON -> List<Item>
      List<Item> allItems = mapper.readValue(sb.toString(), new TypeReference<List<Item>>() {});

      // 4) Filter for rings based on item_type (ignoring case and trimming spaces)
      List<Item> rings = allItems.stream()
          .filter(item -> item.getItemType() != null 
                       && item.getItemType().trim().equalsIgnoreCase("Ring"))
          .collect(Collectors.toList());

      System.out.println("Fetched " + allItems.size() + " total items. Found " 
                         + rings.size() + " ring items.");
      return rings;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }


  public static List<Item> fetchWeaponItems() {
    try {
      URL url = URI.create("http://localhost:8080/items").toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");

      if (connection.getResponseCode() != 200) {
        throw new RuntimeException("HTTP GET Request Failed with Error code: " + connection.getResponseCode());
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder responseStrBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        responseStrBuilder.append(line);
      }
      reader.close();
      connection.disconnect();

      ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
      
      List<Item> allItems = mapper.readValue(responseStrBuilder.toString(), new TypeReference<List<Item>>() {});
      
      // Filter items to include only those with itemType "Weapon"
      List<Item> weapons = allItems.stream()
          .filter(item -> item.getItemType() != null && "weapon".equalsIgnoreCase(item.getItemType()))
          .collect(Collectors.toList());
      return weapons;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  public static List<Item> fetchShieldItems() {
    try {
      URL url = URI.create("http://localhost:8080/items").toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");

      if (connection.getResponseCode() != 200) {
        throw new RuntimeException("HTTP GET Request Failed with Error code: " + connection.getResponseCode());
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder responseStrBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        responseStrBuilder.append(line);
      }
      reader.close();
      connection.disconnect();

      ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

      List<Item> allItems = mapper.readValue(responseStrBuilder.toString(), new TypeReference<List<Item>>() {});
      
      List<Item> shields = allItems.stream()
          .filter(item -> item.getItemType() != null && "shield".equalsIgnoreCase(item.getItemType()))
          .collect(Collectors.toList());
      return shields;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }
}
