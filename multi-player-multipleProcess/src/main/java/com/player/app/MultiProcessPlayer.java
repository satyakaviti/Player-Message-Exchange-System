package com.player.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Manages player communication across separate processes using sockets.
 * Responsibilities:
 * - Handle network setup and communication
 * - Manage player lifecycle in separate processes
 */
public class MultiProcessPlayer {
    private static final int INITIATOR_PORT = 5000; // Initiator listens on this port
    private static final int MAX_MESSAGES = 10;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            // Main process: launch two player processes
            ProcessBuilder initiatorPb = new ProcessBuilder(
                    "java", "-cp", "target/classes", "com.player.app.MultiProcessPlayer", "initiator"
            );
            initiatorPb.inheritIO();
            Process initiatorProcess = initiatorPb.start();

            // Give the initiator a moment to start before launching the receiver
            try {
                Thread.sleep(1000); // Wait 1 second to ensure the initiator is ready
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            ProcessBuilder receiverPb = new ProcessBuilder(
                    "java", "-cp", "target/classes", "com.player.app.MultiProcessPlayer", "receiver"
            );
            receiverPb.inheritIO();
            Process receiverProcess = receiverPb.start();

            try {
                initiatorProcess.waitFor();
                receiverProcess.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Multi-process execution interrupted");
            }
        } else if (args[0].equals("initiator")) {
            runInitiator();
        } else if (args[0].equals("receiver")) {
            runReceiver();
        }
    }

    private static void runInitiator() throws IOException {
        Player initiator = new Player("Player 1");
        try (ServerSocket serverSocket = new ServerSocket(INITIATOR_PORT)) {
            System.out.println("Initiator waiting for Receiver to connect on port " + INITIATOR_PORT + "...");
            Socket clientSocket = serverSocket.accept(); // Wait for Receiver to connect
            System.out.println("Receiver connected to Initiator");
            initiator.setNetworkConnection(clientSocket);

            for (int i = 1; i <= MAX_MESSAGES; i++) {
                String message = "Message " + i;
                initiator.sendNetworkMessage(message);

                String received = initiator.receiveNetworkMessage();
                if (received == null) break;
            }

            initiator.closeNetworkConnection();
        }
        System.out.println("Initiator completed");
    }

    private static void runReceiver() throws IOException {
        Player receiver = new Player("Player 2");
        try (Socket socket = new Socket("localhost", INITIATOR_PORT)) {
            System.out.println("Receiver connected to Initiator on port " + INITIATOR_PORT);
            receiver.setNetworkConnection(socket);

            while (receiver.getMessageCounter() < MAX_MESSAGES) {
                String received = receiver.receiveNetworkMessage();
                if (received == null) break;

                // Extract the counter from the received message
                String counterStr = received.substring(received.lastIndexOf("Counter: ") + 9, received.lastIndexOf(")"));
                int receivedCounter = Integer.parseInt(counterStr);
                String response = received + " (Reply with Received Counter: " + receivedCounter + ")";
                receiver.sendNetworkMessage(response);
            }

            receiver.closeNetworkConnection();
        }
        System.out.println("Receiver completed");
    }
}