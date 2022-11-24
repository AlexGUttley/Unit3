package org.example.ui;

import org.example.dto.Change;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * An interface for all User I/O for the vending machine program.
 */
public interface UserIO {

    /**
     * Run at startup, provides the initial greeting and opening state of the UI.
     *
     * @param inventory An ArrayList containing the string representation of all stock in the inventory
     * @return true if the user would like to continue with the program, or false if they would like to exit.
     */
    boolean welcome(ArrayList<String> inventory);

    /**
     * Polls the user for an amount of money to add to the balance.
     *
     * @return the money to be added, in BigDecimal format.
     */
    BigDecimal addMoney();

    /**
     * Confirm that the specified amount of money has been added to the balance.
     *
     * @param moneyEntered the money added to the balance
     * @param balance      the total balance
     */
    void confirmMoney (BigDecimal moneyEntered, BigDecimal balance);

    /**
     * Offer the user the chance to purchase any valid item from the inventory.
     *
     * @param IDs An int array if all IDs that could be bought by the user.
     * @return the int from the array corresponding to the chosen item.
     */
    int offerPurchase (int[] IDs);

    /**
     * Confirm that the purchase has succeeded, and provides them with the change equal to their remaining balance..
     *
     * @param boughtItem the bought item
     * @param change     the change
     */
    void confirmPurchase (String boughtItem, Change[] change);

    /**
     * Report a terminal error to the user, and provides them with change equal to their balance.
     *
     * @param e      the exception thrown
     * @param change the change returned to the user
     */
    void reportError (Exception e, Change[] change);

    /**
     * Report a terminal error occurring before balance has been modified, where dispensation of change is not needed.
     * @param e the exception thrown
     */
    void reportError (Exception e);

    /**
     * Report an error with the audit log to the user.
     */
    void reportAuditError ();

    /**
     * Run at the end of the program, ends all UI processes.
     */
    void goodbye();
}
