#!/usr/bin/env sh

set -o allexport
# shellcheck disable=SC1090
. "$(pwd)/$1"
set +o allexport

envsubst < "$2" > "$3"
