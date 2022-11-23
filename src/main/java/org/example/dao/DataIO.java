package org.example.dao;

import org.example.dto.Stock;

import java.util.ArrayList;

/**
 * Interface designed for use with the vending machine program, to handle all backend I/O operations.
 */
public interface DataIO {

    /**
     * Reads the stock information from storage.
     *
     * @return An ArrayList containing all stock.
     */
    ArrayList<Stock> readStock();

    /**
     * Write stock from Arraylist to storage.
     *
     * @param output Stock ArrayList to be written to storage.
     */
    void writeStock(ArrayList<Stock> output);

    /**
     * Write audit log.
     *
     * @param summary A summary of the event to be logged.
     */
    void writeAuditLog(String summary);


}
