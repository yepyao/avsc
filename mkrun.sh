#!/bin/bash
if [ $# -ne 1 ]
then
  echo "Usage: <conf name>"
  exit -1
fi
./T.buf/mkbuf.sh $1 Makefile
cd ./run/$1/
./run.sh
cd ../../
