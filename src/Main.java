import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

public class Main {
	
	public static void main(String[] args) throws IOException{
		
		//final String FILE_TO_READ_NEG = "./text-files/tr_polarity.neg";
		//final String FILE_TO_WRITE_NEG = "./text-files/preped_file_neg.json";
		final String FILE_TO_READ_POS = "./text-files/tr_polarity.pos";
		//final String FILE_TO_WRITE_POS = "./text-files/preped_file_pos.json";
		//final String TEST_FILE_READ = "./test-files/test_sentences.txt";
		//final String TEST_FILE_WRITE = "./test-files/test_result.json"; //TODO: Not writing to this path. Find out why.
		
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		Z3MarkovModelDisambiguator disambiguator = new Z3MarkovModelDisambiguator();
        TurkishSentenceAnalyzer sentenceAnalyzer = new TurkishSentenceAnalyzer(
                morphology,
                disambiguator
        );
		MorphologicalAnalyzer analyzer = new MorphologicalAnalyzer(sentenceAnalyzer);
		
		List<String> sentences = FileOperations.readFile(FILE_TO_READ_POS);
		
		List<List<String>> words = Tokenize.tokenizeSentences(sentences);
		
		List<String> prepedText = new ArrayList<>();
		for (List<String> line : words) {
			prepedText.add(analyzer.sentenceAnalyzer(line));
		}
		
		FileOperations.writeFile(prepedText, "result.json");
	}
}