import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		final String FILE_TO_READ_NEG = "data/reviews.neg";
		final String FILE_TO_READ_POS = "data/reviews.pos";
		final String DICT_NEG = "data/dict.neg";
		final String DICT_POS = "data/dict.pos";
		final String STOP_WORDS = "data/stop_words.txt";
		final String TR_WORDS = "data/turkish_words.txt";
		
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		Z3MarkovModelDisambiguator disambiguator = new Z3MarkovModelDisambiguator();
        TurkishSentenceAnalyzer sentenceAnalyzer = new TurkishSentenceAnalyzer(
                morphology,
                disambiguator
        );
		MorphologicalAnalyzer analyzer = new MorphologicalAnalyzer(sentenceAnalyzer,
				DICT_POS, STOP_WORDS, TR_WORDS);
		
		List<String> sentences = FileOperations.readFile(FILE_TO_READ_POS);
		List<List<String>> words = Tokenize.tokenizeSentences(sentences);
		
		List<String> prepedText = new ArrayList<>();
		for (List<String> line : words) {
			prepedText.add(analyzer.sentenceAnalyzer(line));
		}
//		System.out.println("Number of unknown words: " + analyzer.uniqueUnknownCount());
//		System.out.println("Unknown words are: \n");
		analyzer.printUnknownWords();
		FileOperations.writeFile(prepedText, "/home/fatih/Desktop/prep-pos.json");
	}
}