#!/usr/bin/env bash

set -euo pipefail

if [ $# -ne 1 ]; then
  echo 'Usage: virtualenv.sh VIRTUALENV_HOME'
  exit 1
fi

if [ -d "$1" ]; then
  if [ -x "$1/bin/python3" ]; then
      echo "Virtualenv is already set up"
      exit 0
  else
      echo "Virtualenv is corrupted"
      exit 1
  fi
else
  python3 -m venv "$1"
fi
