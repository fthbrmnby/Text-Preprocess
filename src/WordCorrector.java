import java.util.List;

import zemberek.normalization.SingleWordSpellChecker;

/**
 * @author Fatih Barmanbay
 * Creates a dictionary from a list of words and uses that dictionary to correct words.
 */
public class WordCorrector {
	private SingleWordSpellChecker spellChecker;
	
	
	/**
	 * @param dictionary List of words to create dictionary with.
	 */
	public WordCorrector(List<String> dictionary) {
		spellChecker = new SingleWordSpellChecker(2);
		dictionary.forEach(e -> spellChecker.addWord(e));
	}
	
	/**
	 * @param word A word to correct
	 * @return If there is a match in dictionary for given word, return the matched word. 
	 * Else return "UNK"
	 */
	public String correctWord(String word) {
		List<String> result = spellChecker.getSuggestionsSorted(word);
		if(result.size() == 0) return "UNK";
		return result.get(0);
	}
}
