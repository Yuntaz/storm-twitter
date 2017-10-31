# storm-twitter

Storm topology to stream tweets to store both in HDFS (using HdfsBolt) and Hive (using HiveBolt).

## Presentation

The idea of this topology is to define a set of keywords for which we want to analyze the trend along the time. Inside the topology class, it is possible to define the keywords for which we want to collect the tweets, and also to define the frequency (tick) at which we want our analysis granularity. This is also the place where are configured the parameters regarding HDFS and Hive.

![Topology](img/storm_topology.PNG)

## Deployment

Checkout the code and build it with maven using :

    clean package

To run the topology :

    storm jar storm-twitter-0.0.1-SNAPSHOT.jar fr.pvillard.storm.topology.Topology <local|cluster> <consumer key> <consumer key secret> <access token key> <access token secret>

Create the tweet_counts Hive table corresponding to your needs :

    DROP TABLE IF EXISTS tweet_counts;
	CREATE TABLE tweet_counts(filter string, tickdate timestamp, totalcount int)
    CLUSTERED BY (filter) INTO 5 BUCKETS
    STORED AS ORC
	TBLPROPERTIES ("orc.compress"="SNAPPY", "transactional"="true");
	
    DROP TABLE IF EXISTS ext_tweet_counts;	
	CREATE EXTERNAL ext_tweet_counts(filter string, tickdate timestamp, totalcount int)
    CLUSTERED BY (filter) INTO 5 BUCKETS
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	LOCATION '/user/storm_exercise'
	
## Troubleshooting

The most common issue is not having sufficient access rights for hdfs://tmp/hive and file:///tmp/hive Set the access rights to 777 like this:

	$ sudo su - hdfs -c "hdfs dfs -chmod 777 /tmp/hive"


In case of error, extend the connection timeout of the Writer like:
	
	hiveOptions = new HiveOptions(metaStoreURI, dbName, tblName, mapper)
						.withBatchSize(100)
						.withTxnsPerBatch(2)
						.withIdleTimeout(10)
						.withCallTimeout(10000000);	
	
## Example

What we can view in the Hive view in Ambari (Hortonworks distribution) :

![Hive view](img/hive_view.PNG)
