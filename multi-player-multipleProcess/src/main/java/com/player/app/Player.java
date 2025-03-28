package com.player.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Represents a player who can send and receive messages.
 * <p>
 * Responsibilities:
 * - Send messages to another player or process.
 * - Receive messages from another player or process.
 * - Maintain a message counter to track the number of messages sent.
 * - Support both intra-process communication (using queues) and inter-process communication (using sockets).
 */
public class Player {
    private final String name;
    private int messageCounter;
    private PrintWriter out;
    private BufferedReader in;

    public Player(String name) {
        this.name = name;
        this.messageCounter = 0;
    }

    public void sendMessage(BlockingQueue<String> messageQueue, String message) {
        synchronized (this) {
            messageCounter++;
            String formattedMessage = message + " (Counter: " + messageCounter + ")";
            System.out.println(name + " sent: " + formattedMessage);
            messageQueue.offer(formattedMessage);
        }
    }

    public String receiveMessage(BlockingQueue<String> messageQueue) {
        try {
            String message = messageQueue.take();
            System.out.println(name + " received: " + message);
            return message;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(name + " was interrupted while waiting for a message.");
            return null;
        }
    }

    public void sendNetworkMessage(String message) {
        synchronized (this) {
            messageCounter++;
            String formattedMessage = message + " (Counter: " + messageCounter + ")";
            System.out.println(name + " sent: " + formattedMessage);
            if (out != null) {
                out.println(formattedMessage);
            }
        }
    }

    public String receiveNetworkMessage() {
        try {
            String message = in.readLine();
            if (message != null) {
                System.out.println(name + " received: " + message);
            }
            return message;
        } catch (IOException e) {
            System.err.println(name + " error receiving message: " + e.getMessage());
            return null;
        }
    }

    public int getMessageCounter() {
        return messageCounter;
    }

    public String getName() {
        return name;
    }

    public void setNetworkConnection(Socket socket) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void closeNetworkConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.err.println(name + " error closing connection: " + e.getMessage());
        }
    }
}