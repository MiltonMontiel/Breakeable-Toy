#!/bin/bash

# Start the Spring Boot server
echo "Starting Spring Boot project in 'back/'..."
cd back || exit
./mvnw spring-boot:run &

# Capture the Spring Boot process ID
SPRING_PID=$!

# Navigate to the Next.js folder
cd ../front || exit

# Start the Next.js development server
echo "Starting Next.js project in 'front/'..."
npm run dev &

# Capture the Next.js process ID
NEXT_PID=$!

# Wait for the processes to finish
echo "Press Ctrl+C to terminate both servers."
wait $SPRING_PID $NEXT_PID
