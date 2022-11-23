package org.example.dto;

import java.math.BigDecimal;

/**
 * Object representation of a single type of item in the vending machine model.
 */
public class Stock {

    private final String name;
    private final int ID;
    private final BigDecimal cost;
    private int stockAmount;

    /**
     * Instantiates a new Stock.
     *
     * @param name        the name
     * @param id          the id
     * @param cost        the cost
     * @param stockAmount the stock amount
     */
    public Stock (String name, int id, BigDecimal cost, int stockAmount) {

        this.name = name;
        ID = id;
        this.cost = cost;
        this.stockAmount = stockAmount;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Gets stock amount.
     *
     * @return the stock amount
     */
    public int getStockAmount() {
        return stockAmount;
    }

    /**
     * Sets stock amount.
     *
     * @param stockAmount the stock amount
     */
    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    /**
     * Reduce stock.
     */
    public void reduceStock() {
        stockAmount--;
    }

    /**
     * Provides a string implementation of the object's parameters. Does not display the stock value directly, unless it is out of stock.
     * @return A verbose string containing the object's parameters.
     */
    @Override
    public String toString() {
        String verbose =  name + ", ID: " + getID() + ", £" + cost;
        if (stockAmount < 1) {
            verbose += " OUT OF STOCK";
        }
        return verbose;
    }

}
