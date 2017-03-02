import java.io.IOException;
import java.util.List;
import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;
public class Main {
	
	public static void main(String[] args) throws IOException{
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		Z3MarkovModelDisambiguator disambiguator = new Z3MarkovModelDisambiguator();
        TurkishSentenceAnalyzer sentenceAnalyzer = new TurkishSentenceAnalyzer(
                morphology,
                disambiguator
        );
		Stem stemmer = new Stem(sentenceAnalyzer);
		
		List<String> sentences = ReadFile.readTextFile();
		
		List<List<String>> words = Tokenize.tokenizeSentences(sentences);
		
		for (List<String> word : words) {
			System.out.println(stemmer.stem(word));
		}
	}
}