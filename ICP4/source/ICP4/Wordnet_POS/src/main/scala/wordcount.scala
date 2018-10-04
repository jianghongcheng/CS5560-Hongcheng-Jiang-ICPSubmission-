import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by Mayanka on 09-Sep-15.
  */
object SparkWordCount {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir", "D:\\winutils");

    //val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]").set("spark.executor.driver", "4g")

    val sc = new SparkContext(sparkConf)

    //Taking Single input
    //val input = sc.textFile("input", minPartitions = 2)

    //Taking all abstracts
    val winput = sc.wholeTextFiles(path = "inputFolder", minPartitions = 2)

    val input=sc.textFile("data/abstracts",minPartitions = 10)

    val wc = input.flatMap(line => {
      line.split(" ")// return statement I/P RDD[Array[]String] O/P RDD[String]
    }).map(word => (word, 1)// I/P RDD[String] O/P RDD[(String, Int)]
    ).cache()

    val output = wc.reduceByKey(_ + _) //Every Word will have one element I/P & O/P: RDD[(String, Int)]

    output.saveAsTextFile("output")

    val o = output.collect()

    var s: String = "Words:Count \n"
    o.foreach { case (word, count) => {

      s += word + " : " + count + "\n"

    }
    }

  }

}
