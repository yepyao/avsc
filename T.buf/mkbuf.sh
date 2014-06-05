#!/bin/bash 
#$1 for conf name , $2 for makefile
rm model/train.$1.buffer
rm model/test.$1.buffer
set -e
echo "1"
python T.buf/genMakeT.py T.buf/$1.conf $1 $2 > $1.mk
echo "2"
make -f $1.mk model/train.$1.buffer model/test.$1.buffer
rm -rf $1.mk
