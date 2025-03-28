# Player Message Exchange System

### Project Description
This Java-based multithreaded application simulates a message exchange system between two players. Players exchange up to 10 messages in both single-process mode (using threads and `BlockingQueue`) and multi-process mode (using `ProcessBuilder` for inter-process communication).



### Features
- Single-Process Mode: Two threads exchange messages with synchronization.
- Multi-Process Mode: Two separate Java processes exchange messages via `ProcessBuilder`.
- Message Counters: Tracks the number of messages exchanged.
- Thread/Process Termination: Ensures graceful completion after exchanging 10 messages.



### How to Run
#### 1. Prerequisites
- Java Version: JDK 8 or higher.
- Environment: Linux/Mac/Windows (ensure shell script compatibility for non-Windows platforms).
- Project Structure:
  
  src/main/java/com/player/app/
      - Main.java
      - Player.java
      - Message.java
      - PlayerManager.java
  


  

##### Using Shell Script


1. Make the script executable:
   bash
   chmod +x run.sh
   chmod +x run_player1.sh
   chmod +x run_player2.sh
   

2. Run the script:
   - For single-process mode:
     bash
     ./run.sh 
     
   - For multi-process mode:
     bash
     ./run_player1.sh
	 ./run_player2.sh
     



### Project Output
- Single-Process Mode Example:
  
  Initiator sent: Hello from Initiator [Counter: 1]
  Receiver received: Hello from Initiator [Counter: 1]
  Receiver sent: Hello from Initiator ACK [Counter: 2]
  Initiator received: Hello from Initiator ACK [Counter: 2]
  ... (repeats until 10 messages exchanged)
  Program completed successfully.
  

- Multi-Process Mode Example: 
  
  Process 1 sent: Hello from Process 1 [Counter: 1]
  Process 2 received: Hello from Process 1 [Counter: 1]
  Process 2 sent: Hello from Process 1 ACK [Counter: 2]
  Process 1 received: Hello from Process 1 ACK [Counter: 2]
  ... (repeats until 10 messages exchanged)
  Processes terminated successfully.
  



### Future Enhancements
1. Improved Logging: Integrate logging for better traceability.
2. Configurable Parameters: Allow runtime configuration for message limits and player names.
3. Enhanced Testing: Add test cases for multi-process communication.

