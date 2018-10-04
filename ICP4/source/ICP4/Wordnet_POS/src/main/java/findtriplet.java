import edu.stanford.nlp.simple.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class findtriplet {
    public static void main(String[] args) {
        File file = new File("data/abstracts");
        String[] abstracs = read(file).split("\n");
        int count = 0;

        //for each abstract
        for (int i = 1; i < abstracs.length; i++) {
            System.out.println("The triplet extraction of the abstract " + i+" is:");
                    // Create a document. No computation is done yet.
            Document doc = new Document(abstracs[i]);
            for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
                System.out.println(sent.openie());
                count+=sent.openie().size();
            }
            System.out.println("Count:"+count);
            count = 0;
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
