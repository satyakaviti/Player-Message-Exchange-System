# Build the project with Maven, skipping tests
echo "Building the project with Maven (skipping tests)..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "Maven build failed."
  exit 1
fi

# Start the server (Initiator)
echo "Starting Player 1 as server..."
java -cp target/classes com.player.app.MultiProcessPlayer initiator