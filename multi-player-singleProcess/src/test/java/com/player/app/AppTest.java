package com.player.app;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for Player application classes.
 */
public class AppTest {

    /**
     * Test to ensure Player initiator sends and receives messages correctly.
     */
    @Test
    public void testInitiatorPlayer() throws InterruptedException {
        Player initiator = new Player("Initiator", true);
        Player receiver = new Player("Receiver", false);

        initiator.setPartner(receiver);
        receiver.setPartner(initiator);

        Thread initiatorThread = new Thread(initiator);
        Thread receiverThread = new Thread(receiver);

        initiatorThread.start();
        receiverThread.start();

        initiatorThread.join();
        receiverThread.join();

        assertEquals(10, initiator.getSentMessages());
        assertEquals(10, initiator.getReceivedMessages());
        assertEquals(10, receiver.getSentMessages());
        assertEquals(10, receiver.getReceivedMessages());
    }

    /**
     * Test to ensure the PlayerManager initializes and runs correctly in single-process mode.
     */
    @Test
    public void testPlayerManagerSingleProcessMode() {
        try {
            PlayerManager.startSingleProcessMode();
        } catch (Exception e) {
            fail("Single process mode encountered an unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test to validate the functionality of the Message class.
     */
    @Test
    public void testMessageClass() {
        Message message = new Message("Test Message", 1);
        assertEquals("Test Message", message.getContent());
        assertEquals(1, message.getCounter());
        assertEquals("Test Message [Counter: 1]", message.toString());
    }
}
