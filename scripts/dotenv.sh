#!/usr/bin/env sh

if [ -f "$(pwd)/$1" ]; then
  set -o allexport
  # shellcheck disable=SC1090
  . "$(pwd)/$1"
  set +o allexport
else
  echo 'Environment configuration file does not exist'
fi

envsubst < "$2" > "$3"
