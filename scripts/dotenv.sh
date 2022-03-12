#!/usr/bin sh

set -o allexport
source "$(pwd)/$1"
set +o allexport

envsubst < "$2" > "$3"
