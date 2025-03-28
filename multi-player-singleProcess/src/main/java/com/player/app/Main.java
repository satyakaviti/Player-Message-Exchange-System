package com.player.app;

/**
 * Main class for the application.
 * <p>
 * This class is the entry point of the program, where the execution begins. It determines
 * the mode of operation (single-process or multi-process) based on command-line arguments.
 */
public class Main {
    public static void main(String[] args) {
        // Determine the mode based on the command-line arguments
        String mode = (args.length < 1) ? "single" : args[0];

        // Handle the mode using a switch case
        switch (mode.toLowerCase()) {
            case "single":
                PlayerManager.startSingleProcessMode();
                break;
            case "multi":
                System.out.println("Multi-process mode not implemented yet.");
                break;
            default:
                System.err.println("Invalid mode. Defaulting to single-process mode.");
                PlayerManager.startSingleProcessMode();
                break;
        }
    }
}