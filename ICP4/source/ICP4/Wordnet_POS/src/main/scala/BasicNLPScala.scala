import org.apache.spark.{SparkConf, SparkContext}
import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations.{PartOfSpeechAnnotation, SentencesAnnotation, TextAnnotation, TokensAnnotation}
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.util.CoreMap
import rita.RiWordNet

import scala.collection.JavaConverters._

object BasicNLPScala{
  def main(args: Array[String]): Unit = {

    // For Windows Users
    System.setProperty("hadoop.home.dir", "D:\\winutils")
    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val input=sc.textFile("data/abstracts",minPartitions = 10)

    val wc=input.map(line=>{BasicNLP.returnpos(line)}).cache()
    wc.collect()





  }

}