
#Generate the implicit feedback file
#data/train data/test data/projectIDMapping:data/projects.csv 
#	java -cp codes/py/bin preprocessing.genTrainAndTest data/projects.csv data/train data/test data/projectIDMapping

#train.txt test.txt:data/outcomes.csv data/train data/projectIDMapping
#	java -cp codes/py/bin preprocessing.SplitTrainByTime data/outcomes.csv data/train data/projectIDMapping train.txt test.txt

train.txt.imfb test.txt.imfb:train.txt test.txt
	python ./python/mkemptyimfb.py train.txt train.txt.imfb
	python ./python/mkemptyimfb.py test.txt test.txt.imfb

#Discretization features of instances
features/train.txt.kalen_chain_id features/test.txt.kalen_chain_id:data/trainHistory.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin avsc.Discretization_Feature data/trainHistory.csv.idmap train.txt features/train.txt.kalen_chain_id test.txt features/test.txt.kalen_chain_id 1 1 1000

features/train.txt.kalen_offer_id features/test.txt.kalen_offer_id:data/trainHistory.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin avsc.Discretization_Feature data/trainHistory.csv.idmap train.txt features/train.txt.kalen_offer_id test.txt features/test.txt.kalen_offer_id 2 1 100

features/train.txt.kalen_market_id features/test.txt.kalen_market_id:data/trainHistory.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin avsc.Discretization_Feature data/trainHistory.csv.idmap train.txt features/train.txt.kalen_market_id test.txt features/test.txt.kalen_market_id 3 1 100

features/train.txt.kalen_offerdate_id features/test.txt.kalen_offerdate_id:data/trainHistory.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin avsc.Discretization_Feature data/trainHistory.csv.idmap train.txt features/train.txt.kalen_offerdate_id test.txt features/test.txt.kalen_offerdate_id 6 0 100

#Discretization features of offers	
features/train.txt.kalen_offer_category_id features/test.txt.kalen_offer_category_id:data/offers.csv.idmap train.txt test.txt
#	java -cp codes/kalen/bin avsc.Offer_Discretization_Feature data/offers.csv.idmap train.txt features/train.txt.kalen_offer_category_id test.txt features/test.txt.kalen_offer_category_id 1 1 10000
	java -cp codes/kalen/bin avsc.Offer_Discretization_Feature data/offers.csv.idmap train.txt features/train.txt.kalen_offer_category_id test.txt features/test.txt.kalen_offer_category_id 1 0 100
	
features/train.txt.kalen_offer_company_id features/test.txt.kalen_offer_company_id:data/offers.csv.idmap train.txt test.txt
#	java -cp codes/kalen/bin avsc.Offer_Discretization_Feature data/offers.csv.idmap train.txt features/train.txt.kalen_offer_company_id test.txt features/test.txt.kalen_offer_company_id 3 1 30000
	java -cp codes/kalen/bin avsc.Offer_Discretization_Feature data/offers.csv.idmap train.txt features/train.txt.kalen_offer_company_id test.txt features/test.txt.kalen_offer_company_id 3 0 100

features/train.txt.kalen_offer_brand_id features/test.txt.kalen_offer_brand_id:data/offers.csv.idmap train.txt test.txt
#	java -cp codes/kalen/bin avsc.Offer_Discretization_Feature data/offers.csv.idmap train.txt features/train.txt.kalen_offer_brand_id test.txt features/test.txt.kalen_offer_brand_id 5 1 120000
	java -cp codes/kalen/bin avsc.Offer_Discretization_Feature data/offers.csv.idmap train.txt features/train.txt.kalen_offer_brand_id test.txt features/test.txt.kalen_offer_brand_id 5 0 100

	
#Transaction Count	
transaction = data/transactions.csv.idmap
trainHistory = data/trainHistory.csv.idmap
offer = data/offers.csv.idmap

#user_category
transaction_cnt_user_category = data/transaction_cnt/transaction.count.user-category
$(transaction_cnt_user_category): $(transaction)
	java -Xmx16000m -cp codes/kalen/bin avsc.TransactionCount $< $@ 0 3

features/train.txt.kalen_user_category_history_cnt features/test.txt.kalen_user_category_history_cnt: $(transaction_cnt_user_category) $(offer) train.txt test.txt
	java -cp codes/kalen/bin feature.user_category_history_count $(trainHistory) $(offer) $(transaction_cnt_user_category) train.txt features/train.txt.kalen_user_category_history_cnt test.txt features/test.txt.kalen_user_category_history_cnt
	
#user_company
transaction_cnt_user_company = data/transaction_cnt/transaction.count.user-company
$(transaction_cnt_user_company): $(transaction)
	java -Xmx16000m -cp codes/kalen/bin avsc.TransactionCount $< $@ 0 4

features/train.txt.kalen_user_company_history_cnt features/test.txt.kalen_user_company_history_cnt: $(transaction_cnt_user_company) $(offer) train.txt test.txt
	java -cp codes/kalen/bin feature.user_company_history_count $(trainHistory) $(offer) $(transaction_cnt_user_company) train.txt features/train.txt.kalen_user_company_history_cnt test.txt features/test.txt.kalen_user_company_history_cnt
	
#user_brand
transaction_cnt_user_brand = data/transaction_cnt/transaction.count.user-brand
$(transaction_cnt_user_brand): $(transaction)
	java -Xmx16000m -cp codes/kalen/bin avsc.TransactionCount $< $@ 0 5

features/train.txt.kalen_user_brand_history_cnt features/test.txt.kalen_user_brand_history_cnt: $(transaction_cnt_user_brand) $(offer) train.txt test.txt
	java -cp codes/kalen/bin feature.user_brand_history_count $(trainHistory) $(offer) $(transaction_cnt_user_brand) train.txt features/train.txt.kalen_user_brand_history_cnt test.txt features/test.txt.kalen_user_brand_history_cnt

#Instance Count
count_split = 3
features/train.txt.kalen_showclick_chain features/test.txt.kalen_showclick_chain:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_chain features/test.txt.kalen_showclick_chain 1 -1 $(count_split)

features/train.txt.kalen_showclick_offer features/test.txt.kalen_showclick_offer:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_offer features/test.txt.kalen_showclick_offer 2 -1 $(count_split)

features/train.txt.kalen_showclick_market features/test.txt.kalen_showclick_market:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_market features/test.txt.kalen_showclick_market 3 -1 $(count_split)

features/train.txt.kalen_showclick_category features/test.txt.kalen_showclick_category:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_category features/test.txt.kalen_showclick_category -1 1 $(count_split)

features/train.txt.kalen_showclick_quantity features/test.txt.kalen_showclick_quantity:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_quantity features/test.txt.kalen_showclick_quantity -1 2 $(count_split)

features/train.txt.kalen_showclick_company features/test.txt.kalen_showclick_company:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_company features/test.txt.kalen_showclick_company -1 3 $(count_split)

features/train.txt.kalen_showclick_offervalue features/test.txt.kalen_showclick_offervalue:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_offervalue features/test.txt.kalen_showclick_offervalue -1 4 $(count_split)

features/train.txt.kalen_showclick_brand features/test.txt.kalen_showclick_brand:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_brand features/test.txt.kalen_showclick_brand -1 5 $(count_split)
	
features/train.txt.kalen_showclick_chain_category features/test.txt.kalen_showclick_chain_category:data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt
	java -cp codes/kalen/bin feature.OfferShowClickRatio data/trainHistory.csv.idmap data/offers.csv.idmap train.txt test.txt features/train.txt.kalen_showclick_chain_category features/test.txt.kalen_showclick_chain_category 1 1 $(count_split)

#filter	
features/train.txt.kalen_showclick_offer_filter features/test.txt.kalen_showclick_offer_filter:features/train.txt.kalen_showclick_offer features/test.txt.kalen_showclick_offer
	java -cp codes/kalen/bin feature.FeatureFilterStr features/train.txt.kalen_showclick_offer features/test.txt.kalen_showclick_offer features/train.txt.kalen_showclick_offer_filter features/test.txt.kalen_showclick_offer_filter 1,2	