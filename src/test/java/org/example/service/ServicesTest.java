package org.example.service;

import org.example.dto.Change;
import org.example.dto.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Services test.
 */
class ServicesTest {

    /**
     * The Inventory.
     */
    ArrayList<Stock> inventory;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        inventory = new ArrayList<>();
        inventory.add(new Stock("Coke", 1, new BigDecimal("2.00"), 9));
        inventory.add(new Stock("Pepsi", 2, new BigDecimal("1.80"), 1));
        inventory.add(new Stock("Sprite", 3, new BigDecimal("1.30"), 0));
    }

    /**
     * Gets inventory readout.
     */
    @Test
    void getInventoryReadout() {
        Services services = new Services(inventory);
        ArrayList<String> readout = services.getInventoryReadout();
        assertEquals("Pepsi, ID: 2, £1.80", readout.get(1));
    }

    /**
     * Gets inventory readout of stock.
     */
    @Test
    void getInventoryReadoutOfStock() {
        Services services = new Services(inventory);
        ArrayList<String> readout = services.getInventoryReadout();
        assertEquals("Sprite, ID: 3, £1.30 OUT OF STOCK", readout.get(2));
    }

    /**
     * Add and get money.
     */
    @Test
    void addAndGetMoney() {
        Services services = new Services(inventory);
        services.addMoney(new BigDecimal("5.00"));
        assertEquals(0, services.getMoney().compareTo(new BigDecimal("5.00")));
    }

    /**
     * Calculate change.
     */
    @Test
    void calculateChange() {
        Services services = new Services(inventory);
        services.addMoney(new BigDecimal("3.00"));
        Change[] c = {Change.TWO_POUND, Change.ONE_POUND};
        assertArrayEquals(c, services.calculateChange());
    }

    /**
     * Buy.
     */
    @Test
    void buy() {
        Services services = new Services(inventory);
        services.addMoney(new BigDecimal("3.00"));
        assertEquals("Coke", services.buy(1));
    }

    /**
     * Gets valid i ds.
     */
    @Test
    void getValidIDs() {
        Services services = new Services(inventory);
        int[] expected = {1,2};
        assertArrayEquals(expected, services.getValidIDs());
    }

    /**
     * Gets i ds.
     */
    @Test
    void getIDs() {
        Services services = new Services(inventory);
        int[] expected = {1,2,3};
        assertArrayEquals(expected, services.getIDs());
    }

    /**
     * Gets inventory.
     */
    @Test
    void getInventory() {
        Services services = new Services(inventory);
        assertEquals(inventory, services.getInventory());
    }
}