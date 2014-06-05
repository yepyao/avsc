#!/bin/bash

# run the SVDFeature training 

if [ $# -ne 2 ]
then
  echo "Usage: <start> <numround>"
  exit -1
fi

start=$1 
nround=$2
rm *.model
rm log.txt
rm log
for(( i=$start; i < $nround; i ++ ))
do    
    echo $"round "$i > stats.txt
    cat stats.txt
    mv log.txt log 2>/dev/null
    cat log stats.txt > log.txt 2>/dev/null
#
# run training, use different seed to ensure different kinds of pos/neg pair in each round
    svd_feature config.conf max_round=1 num_round=$nround start_counter=$i continue=1 seed=$((i+10)) silent=1
    
    svd_feature_infer config.conf pred=$((i+1)) silent=1 test:buffer_feature=../../model/train.${PWD##*/}.buffer name_pred=pred.train.txt
    svd_feature_infer config.conf pred=$((i+1)) silent=1
#    python ../../python/evalMAE.py ../../test.txt pred.txt $((i+1)) > stats.txt   
#    java -cp ../../java/py/bin preprocess.MAP ../../test.txt pred.txt > stats.txt
#    cat stats.txt
#    mv log.txt log 2>/dev/null
#    cat log stats.txt > log.txt 2>/dev/null

#     echo "training loss: "
#    java -cp ../../java/kalen/ML/bin:../../java/kalen/kddcup2013/bin eval.GroupedMAP ../../train.txt pred.train.txt ../../train.txt.imfb ../../features/train.txt.kalen_AuthorHasAff > stats.txt
#    cat stats.txt
#    mv log.txt log 2>/dev/null
#    cat log stats.txt > log.txt 2>/dev/null

#    java -cp ../../java/kalen/ML/bin:../../java/kalen/kddcup2013/bin eval.GroupedMAP ../../test.txt pred.txt ../../test.txt.imfb ../../features/test.txt.kalen_AuthorHasAff > stats.txt
	java -cp ../../codes/kalen/bin evaluation.Evaluate pred.train.txt ../../train.txt
	java -cp ../../codes/kalen/bin evaluation.Evaluate pred.txt ../../test.txt
    
#    cat stats.txt
	
#	java -cp ../../code/yep.jar yep.main.MakeSubmission ../../test.txt pred.txt submission.txt
#	java -cp ../../code/yep.jar yep.evaluation.Evaluation ../../test.txt submission.txt 38
	
    mv log.txt log 2>/dev/null
    cat log stats.txt > log.txt 2>/dev/null


#   rm -f log stats.txt pred.txt
# check whether we want to keep the model of this round
    if [ `python ../check_model_save.py config.conf $i` -eq 0 ]
    then
        rm `printf %04d $i`.model
    fi
done

cp pred.txt ../../${PWD##*/}.pred.txt
cp pred.train.txt ../../${PWD##*/}.pred.train.txt
rm *.model
cp log.txt ../../${PWD##*/}.log.txt
