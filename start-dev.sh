#!/bin/bash
set -e

echo "Starting PostgreSQL container in detached mode..."
docker compose up -d

echo "Starting Quarkus application in dev mode..."
mvn quarkus:dev