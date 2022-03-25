#!/usr/bin/env sh

set -eu

if [ $# -ne 3 ]; then
  echo 'Usage: dotenv.sh DOT_ENV_FILE IN_FILE OUT_FILE'
  echo 'If the DOT_ENV_FILE does not exist, script skips its load.'
  exit 1
fi

if [ -f "$1" ]; then
  set -o allexport
  # shellcheck disable=SC1090
  . "$1"
  set +o allexport
else
  echo 'Environment configuration file does not exist'
fi

envsubst < "$2" > "$3"
