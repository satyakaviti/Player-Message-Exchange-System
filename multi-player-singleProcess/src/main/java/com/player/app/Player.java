package com.player.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a player in the message exchange system.
 * <p>
 * Responsibilities:
 * - Maintain player identity and state
 * - Send messages to a partner player
 * - Receive and process messages from a partner player
 * - Track message counts for termination
 */
public class Player implements Runnable {
    private final String name;
    private final BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
    private BlockingQueue<Message> outbox;
    private int sentMessages = 0;
    private int receivedMessages = 0;
    private volatile boolean running = true;
    private final boolean isInitiator;

    /**
     * Constructs a Player with a specified name and initiator status.
     *
     * @param name         the name of the player
     * @param isInitiator  true if the player initiates the conversation, false otherwise
     */
    public Player(String name, boolean isInitiator) {
        this.name = name;
        this.isInitiator = isInitiator;
    }

    /**
     * Sets the partner player for message exchange.
     *
     * @param partner the partner player
     */
    public void setPartner(Player partner) {
        this.outbox = partner.inbox;
    }

    /**
     * Sends a message to the partner's inbox.
     *
     * @param content the content of the message
     * @param counter the counter value of the message
     * @throws InterruptedException if the thread is interrupted while sending
     */
    public void sendMessage(String content, int counter) throws InterruptedException {
        if (sentMessages < 10) {
            Message message = new Message(content, counter);
            outbox.put(message);
            sentMessages++;
            System.out.println(name + " sent: " + message);
            if (isInitiator && sentMessages == 10) {
                System.out.println(name + " completed sending 10 messages");
            }
        }
    }

    /**
     * Stops the player from running.
     */
    public void stop() {
        running = false;
    }

    /**
     * Executes the player's message exchange logic.
     */
    @Override
    public void run() {
        // Initiator starts the conversation
        if (isInitiator && sentMessages == 0) {
            try {
                sendMessage("Hello from " + name, 1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        while (running && (isInitiator ? receivedMessages < 10 : true)) {
            try {
                Message received = inbox.take();
                receivedMessages++;
                System.out.println(name + " received: " + received);

                if (!isInitiator || receivedMessages < 10) {
                    String responseContent = received.getContent() + " ACK";
                    sendMessage(responseContent, received.getCounter() + 1);
                }

                if (isInitiator && receivedMessages == 10) {
                    System.out.println(name + " completed receiving 10 messages");
                    stop();
                }

                if (!isInitiator && receivedMessages >= 10) {
                    stop();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Gets the number of messages sent by the player.
     *
     * @return the number of sent messages
     */
    public int getSentMessages() {
        return sentMessages;
    }

    /**
     * Gets the number of messages received by the player.
     *
     * @return the number of received messages
     */
    public int getReceivedMessages() {
        return receivedMessages;
    }
}
