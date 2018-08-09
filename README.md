# SparkHelloWorld
Spark test

上传文件
scp ./build/libs/SparkHelloWorld-1.0.jar root@chong-master:/usr/hdp/current/spark2-client/my_samples

cd /usr/hdp/current/spark2-client

 ./bin/spark-submit --class com.thoughtworks.spark.LocalFileSample \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 4 \
    --queue default \
    my_samples/SparkHelloWorld*.jar \
    100