import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;
public class Main {
	
	public static void main(String[] args) throws IOException{
		
		//final String FILE_TO_READ_NEG = "tr_polarity.neg";
		//final String FILE_TO_WRITE_NEG = "preped_file.neg";
		final String FILE_TO_READ_POS = "tr_polarity.pos";
		final String FILE_TO_WRITE_POS = "preped_file.pos";
		
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		Z3MarkovModelDisambiguator disambiguator = new Z3MarkovModelDisambiguator();
        TurkishSentenceAnalyzer sentenceAnalyzer = new TurkishSentenceAnalyzer(
                morphology,
                disambiguator
        );
		Stem stemmer = new Stem(sentenceAnalyzer);
		
		List<String> sentences = FileOperations.readFile(FILE_TO_READ_POS);
		System.out.println(sentences.size());
		
		List<List<String>> words = Tokenize.tokenizeSentences(sentences);
		
		List<String> prepedText = new ArrayList<>();
		for (List<String> line : words) {
			prepedText.add(stemmer.stem(line));
		}
		
		FileOperations.writeFile(prepedText, FILE_TO_WRITE_POS);
	}
}