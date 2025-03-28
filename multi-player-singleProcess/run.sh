#!/bin/bash

# Default to single-process mode if no argument is provided
MODE=${1:-single}

# Build the project with Maven, skipping tests
echo "Building the project with Maven (skipping tests)..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "Maven build failed."
  exit 1
fi

# Run the application based on mode
case "$MODE" in
    "single")
        echo "Running the application in single-process mode..."
        mvn exec:java -Dexec.mainClass="com.player.app.Main"
        ;;
    *)
        echo "Invalid mode: $MODE. Use 'single'."
        exit 1
        ;;
esac