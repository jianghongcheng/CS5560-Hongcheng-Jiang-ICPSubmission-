import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.bouncycastle.math.ec.ScaleYPointMap;

import java.util.List;
import java.util.Properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class BasicNLP {
    public static String returnpos(String text) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


            //create lists for the types that need to be counted
            String pos_n = new String();
            int pos_n_count = 0;
            String pos_v = new String();
            int pos_v_count = 0;
            String ner_name = new String();
            int ner_name_count = 0;

            // create an empty Annotation just with the given text
            Annotation document = new Annotation(text);
            // run all Annotators on this text
            pipeline.annotate(document);
            // create coremap
            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                    String word = token.get(CoreAnnotations.TextAnnotation.class);
                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                    if(pos.indexOf("NN")!=-1){
                        pos_n +=(word+", ");
                        pos_n_count++;
                        //pos_n.add(token.toString());
                        //System.out.println(pos_n);
                    }
                    else if(pos.indexOf("VB")!=-1){
                        pos_v +=(word+", ");
                        pos_v_count++;
                        //pos_n.add(token.toString());
                        //System.out.println(pos_v);
                    }

                    // this is the NER label of the token
                    String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                    if(ne.indexOf("PERSON")!=-1){
                        ner_name +=(word+", ");
                        ner_name_count++;
                        //pos_n.add(token.toString());
                        //System.out.println(ner_name);
                    }
                }
            }
            System.out.println("Analysis of abstract: "+text);
            System.out.println("count for POS_noun is "+pos_n_count+": "+pos_n);
            System.out.println("count for POS_verb is "+pos_v_count+": "+pos_v);
            System.out.println("count for NER_name is "+ner_name_count+": "+ner_name);


        return pos_n;

    }


}
