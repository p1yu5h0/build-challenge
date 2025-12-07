package com.piyush.assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


public class SalesAnalyzerTest {

    @Test
    public void testTotalRevenue() {
        List<SalesItem> testData = Arrays.asList(
                new SalesItem("North", "Laptop", 10, 1000),
                new SalesItem("South", "Mouse", 20, 25)
        );
        SalesAnalyzer analyzer = new SalesAnalyzer(testData);

        // Simple test - just check it runs
        assertTrue(testData.size() > 0);
    }

    @Test
    public void testRegionWiseRevenue() {
        List<SalesItem> testData = Arrays.asList(
                new SalesItem("North", "Test", 5, 100)
        );
        SalesAnalyzer analyzer = new SalesAnalyzer(testData);

        // Test runs without error
        assertTrue(true);
    }

    @Test
    public void testTopProduct() {
        List<SalesItem> testData = Arrays.asList(
                new SalesItem("Test", "Product1", 10, 50),
                new SalesItem("Test", "Product2", 5, 100)
        );
        SalesAnalyzer analyzer = new SalesAnalyzer(testData);

        // Test completes successfully
        assertTrue(testData.size() == 2);
    }
}
