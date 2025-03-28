package com.player.app;

/**
 * Manages the initialization and configuration of players.
 * <p>
 * Responsibilities:
 * - Initialize players
 * - Start message exchange threads
 * - Ensure graceful shutdown of threads
 */
public class PlayerManager {
    /**
     * Starts the application in single-process mode.
     * <p>
     * In this mode, two players exchange messages within a single process.
     */
    public static void startSingleProcessMode() {
        try {
            Player initiator = new Player("Initiator", true);
            Player receiver = new Player("Receiver", false);

            initiator.setPartner(receiver);
            receiver.setPartner(initiator);

            Thread initiatorThread = new Thread(initiator);
            Thread receiverThread = new Thread(receiver);

            initiatorThread.start();
            receiverThread.start();

            // Wait for both threads to complete
            initiatorThread.join();
            receiverThread.join();

            System.out.println("Program completed successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Program interrupted");
        }
    }
}