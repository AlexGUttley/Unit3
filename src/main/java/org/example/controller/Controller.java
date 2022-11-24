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
    public Controller () throws RuntimeException{
        databaseManager = new FileIO();
        ui = new CmdUserIO();
        try{
            services = new Services(databaseManager.readStock());
        } catch (Exception e) {
            logIO("Failed to read from file.");
            ui.reportError(e);
            ui.goodbye();
            throw new RuntimeException();
        }
    }

    /**
     * Core loop of the program.
     */
    public void start() {
        if(ui.welcome(services.getInventoryReadout())){
            BigDecimal moneyToAdd = ui.addMoney();
            services.addMoney(moneyToAdd);
            log();
            ui.confirmMoney(moneyToAdd, services.getMoney());
            try {
                //To allow recognition of invalid IDs, exception throwing, and unsuccessful transactions, comment out line 47 below and uncomment line 48.
                String boughtItem = services.buy(ui.offerPurchase(services.getValidIDs()));
                //String boughtItem = services.buy(ui.offerPurchase(services.getIDs()));

                //Pass the bought item and the change relevant back to the ui, where it can be displayed.
                if (boughtItem != null) {
                    log();
                    ui.confirmPurchase(boughtItem, services.calculateChange());
                    log();
                }
            } catch (Exception e){
                ui.reportError(e, services.calculateChange());
                log();
            }
            //Have the ui say goodbye.
            ui.goodbye();
            try{
                databaseManager.writeStock(services.getInventory());
            } catch (Exception ignored) {
                logIO("Failed to write to file.");
            }
        }
    }

    private void log(){ //Logs every event to the audit file. Reports any failure to the UI.
        try{
            databaseManager.writeAuditLog(services.getSummary());
        } catch (Exception ignored) {
            ui.reportAuditError();
        }
    }

    private void logIO(String summary){ //Logs read/write operations to the audit file. Reports any failure to the UI.
        try{
            databaseManager.writeAuditLog(summary);
        } catch (Exception ignored) {
            ui.reportAuditError();
        }
    }
}
