#!/usr/bin/env bash

docker run --rm -v "${HOME}/.m2:/root/.m2" -v "${PWD}/:/app/" -w /app -it maven:3-jdk-8 "@"