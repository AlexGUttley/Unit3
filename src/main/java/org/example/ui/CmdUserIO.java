package org.example.ui;

import org.example.dto.Change;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Command line/console user I/O for the vending machine program.
 */
public class CmdUserIO implements UserIO {

    Scanner scanner = new Scanner(System.in);
    boolean auditErrorThisSession;

    public CmdUserIO(){
        auditErrorThisSession = false;
    }

    /**
     * Welcomes the user, provides a list of available stock, and gives them the option to add currency or exit.
     *
     * @param inventory An ArrayList containing the string representation of all stock in the inventory.
     * @return true if the user wishes to add currency, false if they wish to exit.
     */
    @Override
    public boolean welcome(ArrayList<String> inventory) {
        System.out.println("Welcome to the vending machine. The current products in stock are as follows:");
        inventory.forEach(System.out::println);
        System.out.println("Your current balance is £0.00");
        while (true) {
            System.out.println("Please choose from one of the following options:");
            System.out.println("1. Add currency");
            System.out.println("2. Exit");
            String response = scanner.nextLine().trim();
            if (response.equals("1") ||
                    response.equalsIgnoreCase("Add") ||
                    response.equalsIgnoreCase("Add currency") ||
                    response.equalsIgnoreCase("1. Add currency") ||
                    response.equalsIgnoreCase("currency")
            ) {
                return true;
            } else if (response.equals("2") ||
                    response.equalsIgnoreCase("Exit") ||
                    response.equalsIgnoreCase("2. Exit")
            ) {
                System.out.println("Goodbye");
                return false;
            } else {
                System.out.println("Your input has not been recognised. Please try again.");
            }
        }
    }

    /**
     * Poll the user for an amount of money to be added.
     * @return The money to be added, in BigDecimal format.
     */
    @Override
    public BigDecimal addMoney() {
        String cleanMoney = "";
        boolean sanitary = false;
        while (!sanitary) {
            System.out.println("Please specify the amount you wish to add to your balance, in pounds and pennies. (e.g. £5.00)");
            //Basic regex work; ensures all non-numeric character except '.' are removed, then checks it's in the right format.
            cleanMoney = scanner.nextLine().trim().replaceAll("[^0-9.]", "");
            if (cleanMoney.matches("[0-9]+\\.[0-9][0-9]")) {
                sanitary = true;
            } else {
                System.out.println("Could not parse input as value. Please try again.");
            }
        }
        return new BigDecimal(cleanMoney);
    }

    /**
     * Informs the user that the money has been successfully added to the balance.
     * @param moneyEntered the money added to the balance
     * @param balance      the total balance
     */
    @Override
    public void confirmMoney(BigDecimal moneyEntered, BigDecimal balance) {
        System.out.println("You have successfully added £" + moneyEntered.toString() + " to your balance.");
        System.out.println("Your new balance is £" + balance.toString());
    }

    /**
     * Offers the user the opportunity to buy an item from the inventory.
     *
     * @param IDs An int array if all IDs that could be bought by the user.
     * @return The ID from the array corresponding to the relevant item.
     */
    @Override
    public int offerPurchase(int[] IDs) {
        while (true) {
            System.out.println("Please input the ID of the item you wish to purchase.");
            int selected = scanner.nextInt();
            //Grab the first int from the user and check if it's one of the possible IDs, using a lambda expression.
            if(Arrays.stream(IDs).anyMatch(i -> i==selected)) {
                return selected;
            } else {
                System.out.println("ID not recognised. Please try again.");
            }
        }
    }

    /**
     * Confirm to the user that the purchase has succeeded.
     *
     * @param boughtItem the bought item
     * @param change     the change equivalent of the remaining balance.
     */
    @Override
    public void confirmPurchase(String boughtItem, Change[] change) {
        System.out.println("You have successfully purchased " + boughtItem + ".");
        System.out.println("You will also be provided with the following change:");
        //Uses StringBuilder to make a list of change from the array
        StringBuilder changeListBuilder = new StringBuilder();
        for (Change c : change) {
            changeListBuilder.append(c.getValueName()).append(" ");
        }
        String changeList = changeListBuilder.toString();
        System.out.println(
                //Adds commas in the right place to make it in to a human-readable list.
                changeList.trim()
                .replaceAll("coin ", "coin, ")
                .replaceAll("note ", "note, ")
        );
    }

    /**
     * Report a terminal exception to the user, and provide the change breakdown of the current balance.
     *
     * @param e      the exception thrown
     * @param change the change returned to the user
     */
    @Override
    public void reportError(Exception e, Change[] change){
        System.out.println(e.getMessage());
        System.out.println("You will be provided with the following change:");
        //Uses StringBuilder to make a list of change from the array
        StringBuilder changeListBuilder = new StringBuilder();
        for (Change c : change) {
            changeListBuilder.append(c.getValueName()).append(" ");
        }
        String changeList = changeListBuilder.toString();
        System.out.println(
                //Adds commas in the right place to make it in to a human-readable list.
                changeList.trim()
                        .replaceAll("coin ", "coin, ")
                        .replaceAll("note ", "note, ")
        );
    }

    @Override
    public void reportError(Exception e){
        System.out.println(e.getMessage());
    }

    public void reportAuditError() { //In more sophisticated implementations, this should probably terminate the program.
        if (!auditErrorThisSession) {
            System.out.println("There was an error when writing to the audit log.");
            System.out.println("Transactions for the remainder of this session will not be recorded.");
            auditErrorThisSession = true;
        }
    }

    /**
     *  Inform the user of the end of the program.
     */
    @Override
    public void goodbye() {
        System.out.println("Goodbye!");
    }

}
