#!/bin/bash
cd $(dirname "${0}")/ChessProjectServer
javac -d bin -cp .:bin @<(find . -type f -name "*.java")
