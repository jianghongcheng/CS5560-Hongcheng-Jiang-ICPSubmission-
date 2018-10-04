import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet

/**
  * Created by Mayanka on 26-06-2017.
  */
object posScala {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\winutils")
    val conf = new SparkConf().setAppName("WordNetSpark").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)


    //    val data=sc.textFile("data/abstracts")
    //
    //    val dd=data.flatMap(line=>{
    //      val wordnet = new RiWordNet("D:\\WordNet-3.0")
    //      val wordSet=line.split(" ")
    //      val synarr=wordSet.map(word=>{
    //        if(wordnet.exists(word))
    //          (word,1)
    //      })
    //      synarr
    //    }).cache()

    //    dd.collect().foreach(linesyn=>{
    //      println("\nWord Count:"+linesyn.length)
    //      print("Valid Words:")
    //      linesyn.foreach(wordssyn=>{
    //        if(wordssyn._2 != null)
    //          print(wordssyn._1+", ")
    //          //println(wordssyn._1+":"+wordssyn._2.mkString(","))
    //      })
    //    })


    val input=sc.textFile("data/abstracts",minPartitions = 10)

    val wc=input.flatMap(line=>{line.split(" ")}).map(word=>{
      val wordnet = new RiWordNet("D:\\WordNet-3.0")
      if (wordnet.exists(word))
      //  print("1")
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
