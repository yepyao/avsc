#!/bin/bash
if [ $# -ne 1 ]
then
  echo "Usage: <conf name>"
  exit -1
fi
rm ./model/train.$1.svm_buffer.buffer
rm ./model/test.$1.svm_buffer.buffer

echo make feature
./T.buf/mkbuf.sh $1 Makefile

echo make libsvm-xgboost format input files
java -cp codes/yep/bin/ libsvm_interface.MakeInput $1 ./model/

round=500
echo begin to run xgboost
cd ./xgboost_run/$1/
xgboost $1.conf num_round=$round
xgboost $1.conf task=pred model_in=$round.model
#echo auc evaluation and make submission
#java -cp ../../codes/yep/bin evaluation.MakeSubmission pred.txt ../../test.txt submission.txt ../../data/projectIDMapping
cd -



