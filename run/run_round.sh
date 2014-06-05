if [ $# -ne 1 ]
then
  echo "Usage: <numround>"
  exit -1
fi

nround=$1
svd_feature config.conf num_round=$nround
svd_feature_infer config.conf pred=$nround
java -cp ../../code/py/bin evaluation.NDCG ../../test.txt pred.txt NDCG_log.txt ../../data/test.csv
