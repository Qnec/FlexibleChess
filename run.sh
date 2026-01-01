#!/bin/bash
cd $(dirname "${0}")/ChessProjectServer
#echo "$@"
java -cp .:bin main.Server "$@"
