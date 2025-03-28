package com.player.app;

/**
 * Main class to start the player communication app.
 * It decides whether to run in single-process or multi-process mode based on the input.
 */
public class PlayerMain {

    /**
     * Starts the app and picks the mode to run in.
     *
     * @param args First argument can be "single" or "multi". Defaults to "single" if not provided.
     */
    public static void main(String[] args) {
        String mode = (args.length < 1) ? "single" : args[0];

        switch (mode.toLowerCase()) {
            case "single":
                Player player1 = new Player("Player 1");
                Player player2 = new Player("Player 2");
                CommunicationManager manager = new CommunicationManager(player1, player2);
                manager.startCommunication();
                break;
            case "multi":
                try {
                    MultiProcessPlayer.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.err.println("Invalid mode. Use 'single' or 'multi'. Defaulting to single-process mode.");
                Player player1Default = new Player("Player 1");
                Player player2Default = new Player("Player 2");
                CommunicationManager managerDefault = new CommunicationManager(player1Default, player2Default);
                managerDefault.startCommunication();
                break;
        }
    }
}