#!/bin/bash
set -e

echo "Stopping container and removing persistent data volume..."
docker compose down -v