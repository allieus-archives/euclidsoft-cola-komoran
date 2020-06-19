#!/bin/sh

function build() {
  echo "Generating from ${1} for ${2}"
  docker run --rm \
    -v `pwd`:/defs namely/protoc-all \
    -f ${1} \
    -l ${2}
}

build ./kr/re/keit/Komoran.proto java
build ./kr/re/keit/Komoran.proto python

echo "Completed."
