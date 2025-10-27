#!/usr/bin/env bash
# wait-for-it.sh
# Use this script to test if a given TCP host/port are available

set -e

HOST_PORT="$1"
shift
TIMEOUT=15

if [[ "$1" == "-t" ]]; then
  TIMEOUT="$2"
  shift 2
fi

HOST=$(echo "$HOST_PORT" | cut -d: -f1)
PORT=$(echo "$HOST_PORT" | cut -d: -f2)

echo "Waiting for $HOST:$PORT to be available for up to $TIMEOUT seconds..."

for i in $(seq "$TIMEOUT"); do
  if nc -z "$HOST" "$PORT" >/dev/null 2>&1; then
    echo "$HOST:$PORT is available"
    exec "$@"
    exit 0
  fi
  echo "Attempt $i: $HOST:$PORT not ready yet..."
  sleep 1
done

echo "Timeout after ${TIMEOUT}s waiting for $HOST:$PORT"
exit 1
