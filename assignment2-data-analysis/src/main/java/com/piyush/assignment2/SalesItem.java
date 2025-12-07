package com.piyush.assignment2;

public class SalesItem {
    private final String region;
    private final String product;
    private final int units;
    private final double price;

    public SalesItem(String region, String product, int units, double price) {
        this.region = region;
        this.product = product;
        this.units = units;
        this.price = price;
    }

    public double getRevenue() {
        return units * price;
    }

    public String getRegion() { return region; }
    public String getProduct() { return product; }
    public int getUnits() { return units; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%s - %s: %d units @ $%.2f (Revenue: $%.2f)",
                region, product, units, price, getRevenue());
    }
}
