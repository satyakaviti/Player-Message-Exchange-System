package com.player.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Unit test for Player and CommunicationManager.
 */
public class AppTest {

    @Test
    public void testPlayerSendMessage() {
        Player player = new Player("TestPlayer");
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        String testMessage = "Test Message";
        player.sendMessage(queue, testMessage);

        assertEquals(1, player.getMessageCounter());
        assertEquals(testMessage + " (Counter: 1)", queue.poll());
    }

    @Test
    public void testPlayerReceiveMessage() {
        Player player = new Player("TestPlayer");
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String testMessage = "Test Message";

        queue.offer(testMessage);
        String receivedMessage = player.receiveMessage(queue);

        assertNotNull(receivedMessage);
        assertEquals(testMessage, receivedMessage);
    }

    @Test
    public void testCommunicationManagerInitialization() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        CommunicationManager manager = new CommunicationManager(player1, player2);
        assertNotNull(manager);
    }

    @Test
    public void testCommunicationManagerMessageExchange() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");


        CommunicationManager manager = new CommunicationManager(player1, player2);


        manager.startCommunication();


        assertEquals(10, player1.getMessageCounter());
        assertEquals(10, player2.getMessageCounter());
    }
}
