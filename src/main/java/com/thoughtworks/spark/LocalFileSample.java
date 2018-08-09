package com.thoughtworks.spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

public class LocalFileSample {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("LocalFileSample")
                .getOrCreate();

        JavaSparkContext context = new JavaSparkContext(spark.sparkContext());

        JavaRDD<String> rdd = context.textFile("/spark_test/metrics.properties.template");

        rdd = rdd.flatMap((FlatMapFunction) o -> Arrays.asList(o.toString().split(" ")).iterator());
        JavaPairRDD<String, Integer> pairs = rdd.mapToPair(s -> new Tuple2(s, 1));

        Map<String, Integer> resultMap = pairs.reduceByKey((a, b) -> a + b).collectAsMap();

        System.out.println("result is " + resultMap.keySet());
        resultMap.forEach((key, value) -> System.out.println(key + " -> " + value));
        System.out.println("below is printed by for loop..");
        for (String key : resultMap.keySet()) {
            System.out.println(key + " => " + resultMap.get(key));
        }
        spark.stop();
    }
}
