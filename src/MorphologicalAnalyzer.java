import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author Fatih Barmanbay
 * This class does morphological analysis operations like disambiguation, POS tagging and stemming on given sentences.
 * All of this operations done in sentenceAnalyzer method.
 */
public class MorphologicalAnalyzer {
	TurkishSentenceAnalyzer analyzer;
	private int unkCount;
	private Set<String> unknownWords;
	
	/**
	 * @param analyzer
	 * Constructor of the class.
	 */
	public MorphologicalAnalyzer(TurkishSentenceAnalyzer analyzer) {
		this.analyzer = analyzer;
		unkCount = 0;
		unknownWords = new TreeSet<>();
	}
	
	/**
	 * @param words:  A sentence tokenized into words.
	 * Disambiguates, POS tags and finds stems of words on given sentence.
	 */
	public String sentenceAnalyzer(List<String> words) { //TODO: find a way to store data as JSON
		List<JsonBuilder> wordsAndTags = new ArrayList<>();
		String rawSentence = String.join(" ", words);
		
		SentenceAnalysis analysis = analyzer.analyze(rawSentence);
		analyzer.disambiguate(analysis);
		
		for (SentenceAnalysis.Entry entry : analysis) {
            WordAnalysis wordAnalysis = entry.parses.get(0);
            if (wordAnalysis.getLemma() == "UNK") {
            	unknownWords.add(wordAnalysis.root);
            	unkCount++;
            }
            String root = wordAnalysis.getLemma().toLowerCase();
            String posTag = wordAnalysis.dictionaryItem.primaryPos.toString();
            wordsAndTags.add(new JsonBuilder(root, posTag));
            //System.out.println(wordAnalysis.root+ ":" + wordAnalysis.dictionaryItem.primaryPos.toString());
        }
		return JsonBuilder.toJson(wordsAndTags);
	}
	
	
	/**
	 * @return number of UNK tag in data set.
	 */
	public int unknownCount() {
		return unkCount;
	}
	
	/**
	 * @return number of unique words that tagged as UNK.
	 */
	public int uniqueUnknownCount() {
		return unknownWords.size();
	}
	
	/**
	 * Prints all unique words tagged as UNK.
	 */
	public void printUnknownWords() {
		unknownWords.forEach(e -> System.out.println(e));
	}
}
