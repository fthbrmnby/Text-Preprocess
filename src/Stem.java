import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.util.List;

public class Stem {
	TurkishSentenceAnalyzer sentenceAnalyzer;
	
	/**
	 * @param sentenceAnalyzer
	 * Constructor of the class.
	 */
	public Stem(TurkishSentenceAnalyzer sentenceAnalyzer) {
		this.sentenceAnalyzer = sentenceAnalyzer;
	}
	
	/**
	 * @param words:  a list of words
	 * Disambiguates the word and return the lemma of word.
	 */
	public String stem(List<String> words) {
		String root = "";
		SentenceAnalysis result;
		for (int i=0; i<words.size(); i++) {
			result = sentenceAnalyzer.analyze(words.get(i));
			sentenceAnalyzer.disambiguate(result);
			root += result.getParses(0).get(0).getLemma() + " ";
		}
		return root;
	}
}
