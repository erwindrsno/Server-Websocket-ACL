#!/bin/bash

# Change directory to 'target'
cd target || { echo "Directory 'target' not found!"; exit 1; }

# Run the Java application
java -jar ws-server-1.0-SNAPSHOT-jar-with-dependencies.jar
