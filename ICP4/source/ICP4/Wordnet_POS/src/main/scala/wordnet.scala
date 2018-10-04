import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet

/**
  * Created by Mayanka on 26-06-2017.
  */
object wordnet {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\winutils")
    val conf = new SparkConf().setAppName("WordNetSpark").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val input=sc.textFile("data/abstracts",minPartitions = 10)

    val wc=input.flatMap(line=>{line.split(" ")}).map(word=>{
      val wordnet = new RiWordNet("D:\\WordNet-3.0")
      if (wordnet.exists(word))
        (word,1)
      else
        (word,0)

    }).cache()

    val output=wc.reduceByKey(_+_)

    output.saveAsTextFile("output")

    val o=output.collect()

    var s:String="Words:Count \n"
    o.foreach{
      case(word,count)=>{
      s+=word+" : "+count+"\n"
    }}



  }


}
