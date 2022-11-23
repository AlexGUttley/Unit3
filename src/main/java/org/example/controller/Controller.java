package org.example.controller;

import org.example.dao.DataIO;
import org.example.dao.FileIO;
import org.example.service.Services;
import org.example.ui.CmdUserIO;
import org.example.ui.UserIO;

import java.math.BigDecimal;

/**
 * The controller for the vending machine application; performs core functionality.
 */
public class Controller {

    private final UserIO ui;
    private final DataIO databaseManager;
    private final Services services;

    /**
     * Instantiates a new Controller.
     */
    public Controller () {
        databaseManager = new FileIO();
        ui = new CmdUserIO();
        services = new Services(databaseManager.readStock());
    }

    /**
     * Core loop of the program.
     */
    public void start() {
        if(ui.welcome(services.getInventoryReadout())){
            BigDecimal moneyToAdd = ui.addMoney();
            services.addMoney(moneyToAdd);
            databaseManager.writeAuditLog(services.getSummary());
            ui.confirmMoney(moneyToAdd, services.getMoney());
            try {
                //To allow recognition of invalid IDs, exception throwing, and unsuccessful transactions, comment out line 31 below and uncomment line 32.
                String boughtItem = services.buy(ui.offerPurchase(services.getValidIDs()));
                //String boughtItem = services.buy(ui.offerPurchase(services.getIDs()));

                //Pass the bought item and the change relevant back to the ui, where it can be displayed.
                if (boughtItem != null) {
                    databaseManager.writeAuditLog(services.getSummary());
                    ui.confirmPurchase(boughtItem, services.calculateChange());
                    databaseManager.writeAuditLog(services.getSummary());
                }
            } catch (Exception e){
                ui.reportError(e, services.calculateChange());
                databaseManager.writeAuditLog(services.getSummary());
            }
            //Have the ui say goodbye.
            ui.goodbye();
            databaseManager.writeStock(services.getInventory());
        }

    }
}
