import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import zemberek.core.logging.Log;
import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;
import zemberek.normalization.SingleWordSpellChecker;

import com.google.gson.*;

public class Test {
	
//	TurkishSentenceAnalyzer analyzer;
//	
//	public Test(TurkishSentenceAnalyzer analyzer) {
//        this.analyzer = analyzer;
//    }
//	
//	private void posTag(String s) {
//        System.out.println("Sentence  = " + s);
//        SentenceAnalysis analysis = analyzer.analyze(s);
//        analyzer.disambiguate(analysis);
//
//
//        for (SentenceAnalysis.Entry entry : analysis) {
//            WordAnalysis wa = entry.parses.get(0);
//            System.out.println(wa.root+ ":" + wa.dictionaryItem.primaryPos.toString());
//        }
//    }
	
	String word;
	String tag;
	static List<Test[]> sentence = new ArrayList();
	
	public Test(String word, String tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public static List<Test[]> sentenceBuilder(Test[]... words){
		for (Test[] word : words){ sentence.add(word);}
		return sentence;
	}
	
   public static void main(String[] args) throws IOException{

//	   TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
//       Z3MarkovModelDisambiguator disambiguator = new Z3MarkovModelDisambiguator();
//       TurkishSentenceAnalyzer sentenceAnalyzer = new TurkishSentenceAnalyzer(
//               morphology,
//               disambiguator
//       );
//       new Test(sentenceAnalyzer)
//               .posTag("Harika bir film.");
       
//       List<String> words = new ArrayList<>();
//       words.add("Seni");
//       words.add("sabaha");
//       words.add("kadar");
//       words.add("bekledim.");
//       
//       String listString = String.join(" ", words);
//       System.out.println(listString);
       
     Test[] test1 = {new Test("Sen", "Noun"), new Test("sabah", "Noun"), new Test("beklemek", "Verb")};
     Test[] test2 = {new Test("Sen", "Noun"), new Test("akşam", "Noun"), new Test("aramak", "Verb")};
     
     Gson gson = new Gson();
     List<String> objects = new ArrayList();
     objects.add(gson.toJson(test1));
     objects.add(gson.toJson(test2));
     String finalJson = gson.toJson(objects);
     
     String pattern = "\\\"";
     Pattern r = Pattern.compile(pattern);
     Matcher m = r.matcher(finalJson);
     
     if (m.find( )) {
        finalJson = m.replaceAll("A");
      }else {
         System.out.println("NO MATCH");
      }
     
     System.out.println(finalJson);
   }
	   
}
