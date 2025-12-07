package com.piyush.assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalesApp {
    public static void main(String[] args) {
        System.out.println("=== Sales Data Analysis ===\n");

        List<SalesItem> sales = readSalesData("src/main/resources/sales.csv");
        SalesAnalyzer analyzer = new SalesAnalyzer(sales);

        analyzer.showTotalRevenue();
        analyzer.showRegionWiseRevenue();
        analyzer.showTopSellingProduct();
        analyzer.showAveragePricePerRegion();
    }

    private static List<SalesItem> readSalesData(String filename) {
        List<SalesItem> sales = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    SalesItem item = new SalesItem(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim()),
                            Double.parseDouble(parts[3].trim())
                    );
                    sales.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return sales;
    }
}
