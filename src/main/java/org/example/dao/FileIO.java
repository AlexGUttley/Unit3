package org.example.dao;

import org.example.dto.Stock;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

/**
 * A local, file-based implementation of the DataIO interface.
 */
public class FileIO implements DataIO {

    private static final String FILE_NAME = "inventory.csv";
    private static final String AUDIT_LOG = "auditLog.txt";

    /**
     * Generates an ArrayList of stock from the inventory file
     * @return the stock ArrayList
     */
    @Override
    public ArrayList<Stock> readStock() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            reader.readLine();//reads twice to ensure the first line (categories) is not used
            line = reader.readLine();
            ArrayList<Stock> inventory = new ArrayList<>();
            while (line != null) {
                String[] lineArray = line.split(", ");
                inventory.add(
                        new Stock(lineArray[0],
                        Integer.parseInt(lineArray[1]),
                        new BigDecimal(lineArray[2]),
                        Integer.parseInt(lineArray[3]))
                );
                //inventory.add(new Stock(name, id, cost, stockAmount));
                line = reader.readLine();
            }
            writeAuditLog("Successfully read data from disk.");
            return inventory;
        } catch (IOException e) {
            writeAuditLog("Reading from disk failed.");
        }
        return null;
    }

    /**
     * Writes the contents of the provided arraylist back into the storage file.
     * @param output Stock ArrayList to be written to storage.
     */
    @Override
    public void writeStock(ArrayList<Stock> output) throws IOException {
        try (PrintWriter pw = new PrintWriter(FILE_NAME)) {
         pw.println("Name, ID, Price, Stock Volume");
         for(Stock s : output) {
             String outputLine = s.getName() + ", " +
                             s.getID() +  ", " +
                             s.getCost() + ", " +
                             s.getStockAmount();
             pw.println(outputLine);
         }
         writeAuditLog("Writing to disk succeeded.");
        }
    }

    /**
     * Writes a provided summary of any event, timestamped, into the audit log file.
     * @param summary A summary of the event to be logged.
     */
    @Override
    public void writeAuditLog(String summary) throws IOException{
        LocalDateTime time = LocalDateTime.now();
        String out = time.format(ISO_DATE_TIME) + ": " + summary;
        try (FileWriter fw = new FileWriter(AUDIT_LOG, true); BufferedWriter writer = new BufferedWriter(fw)) {
            writer.newLine();
            writer.write(out);
        }
    }
}
