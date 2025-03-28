# Build the project with Maven, skipping tests
echo "Building the project with Maven (skipping tests)..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "Maven build failed."
  exit 1
fi

# Start the client (Receiver)
echo "Starting Player 2 as client..."
java -cp target/classes com.player.app.MultiProcessPlayer receiver