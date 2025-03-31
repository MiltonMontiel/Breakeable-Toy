#!/bin/bash

# Function to clean up processes when exiting
cleanup() {
  echo "Stopping servers..."
  # Kill Spring Boot process if it exists
  if [ -n "$SPRING_PID" ]; then
    kill -TERM "$SPRING_PID" 2>/dev/null || true
  fi
  # Kill Next.js process if it exists
  if [ -n "$NEXT_PID" ]; then
    kill -TERM "$NEXT_PID" 2>/dev/null || true
  fi
  exit 0
}

# Set up trap to catch Ctrl+C and other termination signals
trap cleanup SIGINT SIGTERM EXIT

# Start the Spring Boot server
echo "Starting Spring Boot project in 'back/'..."
cd back || exit
./mvnw spring-boot:run &

# Capture the Spring Boot process ID
SPRING_PID=$!
echo "Spring Boot started with PID: $SPRING_PID"

# Navigate to the Next.js folder
cd ../front || exit

# Start the Next.js development server
echo "Starting Next.js project in 'front/'..."
npm run dev &

# Capture the Next.js process ID
NEXT_PID=$!
echo "Next.js started with PID: $NEXT_PID"

# Wait for the processes to finish
echo "Press Ctrl+C to terminate both servers."
wait
