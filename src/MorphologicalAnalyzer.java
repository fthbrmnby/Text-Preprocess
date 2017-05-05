import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Fatih Barmanbay This class does morphological analysis operations
 *         like disambiguation, POS tagging and stemming on given sentences. All
 *         of this operations done in sentenceAnalyzer method.
 */
public class MorphologicalAnalyzer {
	private TurkishSentenceAnalyzer analyzer;
	private int unkCount;
	private Set<String> unknownWords;
	private WordCorrector wordCorrector;

	/**
	 * @param analyzer
	 * @param dictionary
	 *            Path of words file. Used for creating a dictionary Constructor
	 *            of the class.
	 */
	public MorphologicalAnalyzer(TurkishSentenceAnalyzer analyzer,
			String dictionary,
			String stopWords,
			String turkishWords) {
		this.analyzer = analyzer;
		unkCount = 0;
		unknownWords = new TreeSet<>();
		wordCorrector = new WordCorrector(FileOperations.readFile(dictionary),
				new TreeSet<String>(FileOperations.readFile(stopWords)),
				new TreeSet<String>(FileOperations.readFile(turkishWords)));
	}

	/**
	 * @param words:
	 *            A sentence tokenized into words. Disambiguates, POS tags and
	 *            finds stems of words on given sentence.
	 */
	public String sentenceAnalyzer(List<String> words){
		List<JsonBuilder> wordsAndTags = new ArrayList<>();
		String rawSentence = String.join(" ", words);

		SentenceAnalysis analysis = analyzer.analyze(rawSentence);
		analyzer.disambiguate(analysis);

		for (SentenceAnalysis.Entry entry : analysis) {
			WordAnalysis wordAnalysis = entry.parses.get(0);
			String word = wordAnalysis.getSurfaceForm();
			// Check if word is already in its root form
			if(wordCorrector.isWord(wordAnalysis.getSurfaceForm())) {
				// Check if the word is a stop word
				if (!wordCorrector.isStopWord(word)){
					String posTag = wordAnalysis.getPos().toString();
					String engWord = GCTranslate.getTranslation(word);
					// Check if the translation has more than one word
					if (engWord.contains(" ")) {
						List<String> engWords = Arrays.asList(engWord.split(" "));
						engWords.forEach(e -> wordsAndTags.add(new JsonBuilder(e, posTag)));
					} else {
						wordsAndTags.add(new JsonBuilder(engWord, posTag));
					}
				}
			// Word is not in its root form. Do stemming.	
			} else {
				if (wordAnalysis.getLemma() == "UNK") {
					String unkWord = wordAnalysis.getSurfaceForm();
					String correctWord = wordCorrector.correctWord(unkWord);
					//System.out.println("Before Correction: " + unkWord + "\nAfter Correction: " + correctWord);
					
					SentenceAnalysis analyzeAgain = analyzer.analyze(correctWord);
					analyzer.disambiguate(analyzeAgain);
					WordAnalysis wa = analyzeAgain.getEntry(0).parses.get(0);
					String root = wa.getLemma().toLowerCase();
					// If root is still unknown, add it as UNK.
					if (root == "UNK") {
						String posTag = wa.dictionaryItem.primaryPos.toString();
						wordsAndTags.add(new JsonBuilder(root, posTag));
						unknownWords.add(wordAnalysis.root);
						unkCount++;
					// If word successfully corrected and it is not a stop word,
					// then translate the root of the word and add it to JSON.
					} else if (!wordCorrector.isStopWord(root)) {
						// If the word is root, then do not try to take the root of it.
						if (wordCorrector.isWord(correctWord)) {
							String engRoot = GCTranslate.getTranslation(correctWord);
							// Is there more than one word in translation? If there is,
							// than take the all the words and add them to the JSON
							if (engRoot.contains(" ")) {
								String[] translations = engRoot.split(" ");
								String posTag = wa.dictionaryItem.primaryPos.toString();
								Arrays.asList(translations).forEach(e -> 
								wordsAndTags.add(new JsonBuilder(e, posTag)));
							} else {
								String posTag = wa.dictionaryItem.primaryPos.toString();
								wordsAndTags.add(new JsonBuilder(engRoot, posTag));
							}
							
						} else {
							String engRoot = GCTranslate.getTranslation(root);
							String posTag = wa.dictionaryItem.primaryPos.toString();
							wordsAndTags.add(new JsonBuilder(engRoot, posTag));
						}
						
					} else {
						continue;
					}
				// If the word is correctly spelled, just take the root and POSTAG
				// and add it the JSON.
				} else {
					String root = wordAnalysis.getLemma().toLowerCase();
					if (!wordCorrector.isStopWord(root)) {
							String engRoot = GCTranslate.getTranslation(root);
							String posTag = wordAnalysis.dictionaryItem.primaryPos.toString();
							// Check if translation has more than one word
							if (engRoot.contains(" ")) {
								List<String> engWords = Arrays.asList(engRoot.split(" "));
								engWords.forEach(e -> wordsAndTags.add(new JsonBuilder(e, posTag)));
							} else {
								wordsAndTags.add(new JsonBuilder(engRoot, posTag));
							}
					} else {
						continue;
					}
				}
			}
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
