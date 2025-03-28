package com.player.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Handles communication between two players in the same JVM using threads.
 */
public class CommunicationManager {
    private final Player initiator;
    private final Player receiver;

    /**
     * Sets up the manager with two players.
     *
     * @param initiator The player who starts the communication.
     * @param receiver The player who responds to messages.
     */
    public CommunicationManager(Player initiator, Player receiver) {
        this.initiator = initiator;
        this.receiver = receiver;
    }

    /**
     * Starts the communication between the two players.
     * The initiator sends 10 messages, and the receiver responds to each one.
     */
    public void startCommunication() {
        BlockingQueue<String> initiatorQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> receiverQueue = new LinkedBlockingQueue<>();
        int maxMessages = 10;

        Thread initiatorThread = new Thread(() -> {
            for (int i = 1; i <= maxMessages; i++) {
                String message = "Message " + i;
                initiator.sendMessage(receiverQueue, message);

                initiator.receiveMessage(initiatorQueue);
            }
        });

        Thread receiverThread = new Thread(() -> {
            while (receiver.getMessageCounter() < maxMessages) {
                String receivedMessage = receiver.receiveMessage(receiverQueue);
                if (receivedMessage != null) {
                    // Extract the counter from the received message
                    String counterStr = receivedMessage.substring(receivedMessage.lastIndexOf("Counter: ") + 9, receivedMessage.lastIndexOf(")"));
                    int receivedCounter = Integer.parseInt(counterStr);
                    String response = receivedMessage + " (Reply with Received Counter: " + receivedCounter + ")";
                    receiver.sendMessage(initiatorQueue, response);
                }
            }
        });

        initiatorThread.start();
        receiverThread.start();

        try {
            initiatorThread.join();
            receiverThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Communication was interrupted.");
        }

        System.out.println("Communication completed successfully.");
    }
}