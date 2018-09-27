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
    public static void main(String args[]) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

// read abstracts from the file
        File file = new File("data/11.txt");
        String[] abstracs = read(file).split("\n");


        //for each abstract
        for(int i=1;i<abstracs.length;i++){
            //create lists for the types that need to be counted
            String pos_n = new String();
            int pos_n_count = 0;
            String pos_v = new String();
            int pos_v_count = 0;
            String ner_name = new String();
            int ner_name_count = 0;

            // create an empty Annotation just with the given text
            Annotation document = new Annotation(abstracs[i]);
            // run all Annotators on this text
            pipeline.annotate(document);
            // create coremap
            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                    //System.out.println("\n" + token);

                    // this is the text of the token
                    String word = token.get(CoreAnnotations.TextAnnotation.class);
                    //System.out.println("Text Annotation");
                    //System.out.println(token + ":" + word);
                    // this is the POS tag of the token

                    //String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                    //System.out.println("Lemma Annotation");
                    //System.out.println(token + ":" + lemma);
                    // this is the Lemmatized tag of the token


                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    //System.out.println("POS");
                    //System.out.println(token + ":" + pos);

                    //if the token is a noun or verb
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
                    //System.out.println("NER");
                    //System.out.println(token + ":" + ne);

                    //figure out if it's a name
                    if(ne.indexOf("PERSON")!=-1){
                        ner_name +=(word+", ");
                        ner_name_count++;
                        //pos_n.add(token.toString());
                        //System.out.println(ner_name);
                    }
                }
            }
            System.out.println("Abstract"+i+":");
            System.out.println("count for POS_noun is "+pos_n_count+": "+pos_n);
            System.out.println("count for POS_verb is "+pos_v_count+": "+pos_v);
            System.out.println("count for NER_name is "+ner_name_count+": "+ner_name);
        }


    }
    public static String read(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//create a BufferedReader to read the file
            String s = null;
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

}
