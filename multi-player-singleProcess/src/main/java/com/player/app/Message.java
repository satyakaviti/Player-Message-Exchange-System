package com.player.app;

/**
 * Represents a message exchanged between players.
 * <p>
 * Each message contains content and a counter to track the number of exchanges.
 */
public class Message {
    private final String content;
    private final int counter;

    /**
     * Constructs a Message with specified content and counter.
     *
     * @param content the content of the message
     * @param counter the counter value of the message
     */
    public Message(String content, int counter) {
        this.content = content;
        this.counter = counter;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the counter value of the message.
     *
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Returns a string representation of the message.
     *
     * @return a string containing the content and counter
     */
    @Override
    public String toString() {
        return content + " [Counter: " + counter + "]";
    }
}