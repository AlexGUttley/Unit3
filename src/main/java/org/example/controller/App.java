package org.example.controller;

/**
 * Main class used only for initiating the vending machine program.
 */
public class App {

    /**
     * The entry point of application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        try {
            Controller main = new Controller();
            main.start();
        } catch (Exception ignored) {}
    }

}
