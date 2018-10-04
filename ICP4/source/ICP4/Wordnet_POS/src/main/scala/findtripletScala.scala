import edu.stanford.nlp.simple.Document
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.JavaConversions._

object findtripletScala {

  def main(args: Array[String]) {

    // For Windows Users
    System.setProperty("hadoop.home.dir", "D:\\winutils")
    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)



    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    println("y")
    val input = sc.textFile("data/abstracts").map(line => {
      //Getting OpenIE Form of the word using lda.CoreNLP
      //val t=CoreNLP.returnTriplets(line)

    })

    //println(CoreNLP.returnTriplets("The dog has a lifespan of upto 10-12 years."))
    println(input.collect().mkString("\n"))



  }

}
