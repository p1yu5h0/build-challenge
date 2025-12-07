package com.piyush.assignment2;

import java.util.*;

public class SalesAnalyzer {
    private List<SalesItem> sales;

    public SalesAnalyzer(List<SalesItem> sales) {
        this.sales = sales;
    }

    public void showTotalRevenue() {
        double total = 0;
        for (SalesItem item : sales) {
            total += item.getRevenue();
        }
        System.out.println("1. Total Revenue: $" + String.format("%.2f", total));
    }

    public void showRegionWiseRevenue() {
        Map<String, Double> regionRevenue = new HashMap<>();

        for (SalesItem item : sales) {
            String region = item.getRegion();
            double revenue = item.getRevenue();
            regionRevenue.put(region, regionRevenue.getOrDefault(region, 0.0) + revenue);
        }

        System.out.println("2. Revenue by Region:");
        for (Map.Entry<String, Double> entry : regionRevenue.entrySet()) {
            System.out.println("   " + entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
        }
    }

    public void showTopSellingProduct() {
        SalesItem topProduct = null;
        double maxRevenue = 0;

        for (SalesItem item : sales) {
            if (item.getRevenue() > maxRevenue) {
                maxRevenue = item.getRevenue();
                topProduct = item;
            }
        }

        System.out.println("3. Top Product: " + topProduct);
    }

    public void showAveragePricePerRegion() {

        Map<String, List<Double>> regionPrices = new HashMap<>();

        for (SalesItem item : sales) {
            String region = item.getRegion();
            if (!regionPrices.containsKey(region)) {
                regionPrices.put(region, new ArrayList<>());
            }
            regionPrices.get(region).add(item.getPrice());
        }

        System.out.println("4. Average Price per Region:");

        for (Map.Entry<String, List<Double>> entry : regionPrices.entrySet()) {
            String region = entry.getKey();
            List<Double> prices = entry.getValue();

            double sum = 0;
            for (double price : prices) {
                sum += price;
            }
            double avg = sum / prices.size();
            System.out.println("   " + region + ": $" + String.format("%.2f", avg));
        }

    }

}
