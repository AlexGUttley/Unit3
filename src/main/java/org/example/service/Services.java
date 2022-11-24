package org.example.service;

import org.example.dto.Stock;
import org.example.dto.Change;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Service layer class responsible for all financial and inventory calculations.
 */
public class Services {



    private final ArrayList<Stock> inventory;
    private BigDecimal money;
    private String summary;

    /**
     * Instantiates a new instance of the service layer.
     *
     * @param inventory the inventory to be utilised.
     */
    public Services(ArrayList<Stock> inventory){
        this.inventory = inventory;
        this.money = new BigDecimal(0);
    }

    /**
     * Gets a verbose readout of the inventory using the .toString method of the stock.
     *
     * @return the inventory readout
     */
    public ArrayList<String> getInventoryReadout () {
        ArrayList<String> stringInventory = new ArrayList<>();
        inventory.forEach(n -> stringInventory.add(n.toString()));
        return stringInventory;
    }

    /**
     * Adds the specified amount of money.
     *
     * @param moneyIn the money to be added.
     */
    public void addMoney(BigDecimal moneyIn) {
        money = money.add(moneyIn);
        summary = "£" + moneyIn + " was added to the balance. The current total is now £" + money;
    }

    /**
     * Calculate the correct change to total all remaining funds, then wipes out the current funds.
     *
     * @return the calculated change.
     */
    public Change[] calculateChange () {
        summary = "£" + money.toString() + " was dispensed in the form of change.";
        ArrayList<Change> total = new ArrayList<>();
        for (Change c : Change.values()) {
            while (money.compareTo(c.getValue()) >= 0) {
                money = money.subtract(c.getValue());
                total.add(c);
            }
        }
        return total.toArray(Change[]::new);
    }

    /**
     * Processes a request to buy an item with a given ID. Will throw exceptions if the product is out of stock, or if the user does not have sufficient funds.
     *
     * @param ID the id of the product to be purchased.
     * @return The name of the product that has been successfully bought.
     * @throws RuntimeException Either InsufficientFundsException or NoItemInventoryException, depending on the error encountered.
     */
    public String buy (int ID) throws RuntimeException{
        for(Stock s : inventory) {
            if (s.getID() == ID) {
                if (s.getStockAmount() > 0) {
                    if (s.getCost().compareTo(money) <= 0) {
                        money = money.subtract(s.getCost());
                        s.reduceStock();
                        summary = (s.getName() + " was purchased for " + s.getCost() + ". The remaining balance is now £" + money);
                        return s.getName();
                    } else {
                        throw new InsufficientFundsException("You do not have enough money to complete this transaction.");
                    }
                } else {
                    throw new NoItemInventoryException("No products with that ID are currently in stock.");
                }
            }
        }
        return null;
    }

    /**
     * Gets money.
     *
     * @return the money
     */
    public BigDecimal getMoney () {
        return money;
    }

    /**
     * Returns a list of all IDs corresponding to inventory elements that are not out of stock.
     *
     * @return An integer array representing all in-stock items.
     */
    public int[] getValidIDs () {
        ArrayList<Integer> IDs = new ArrayList<>();
        for(Stock s : inventory) {
            if (s.getStockAmount() > 0) {
                IDs.add(s.getID());
            }
        }
        int[] validIDs = new int[IDs.size()];
        int pos = 0; //Inelegant way of converting from arrayList to string. Might even be easier to iterate through stock twice. Refactor.
        for (Integer i : IDs) {
            validIDs[pos] = i;
            pos++;
        }
        return validIDs;
    }

    /**
     * Returns all IDs for all inventory elements, regardless of stock levels or other factors.
     *
     * @return An int array containing all IDs.
     */
    public int[] getIDs () {
        int[] IDs = new int[inventory.size()];
        int i = 0;
        for (Stock s : inventory) {
            IDs[i] = s.getID();
            i++;
        }
        return IDs;
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public ArrayList<Stock> getInventory () {
        return inventory;
    }

    /**
     * Gets a summary of the most recent event to have occurred.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

}
