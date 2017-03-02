import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;
import zemberek.tokenizer.ZemberekLexer;
import org.antlr.v4.runtime.Token;
import turkish.Deasciifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tokenize {
	
	/**
	 * @param sentences: List of sentences
	 * @return A list of words
	 * Tokenizes sentences into words. Each element of the list is a List of String.
	 */
	public static List<List<String>> tokenizeSentences(List<String> sentences) {
		List<List<String>> tokens = new ArrayList<>();
		List<String> temp = new ArrayList<>();
		ZemberekLexer lexer = new ZemberekLexer();
		for (String sentence : sentences) {
			Iterator<Token> tokenIterator = lexer.getTokenIterator(sentence);
			while (tokenIterator.hasNext()) {
				Token token = tokenIterator.next();
				temp.add(new Deasciifier(token.getText()).convertToTurkish());
			}
			tokens.add(new ArrayList<String>(temp));
			temp.clear();
		}
		return tokens;
	}
	
	/**
	 * @param paragraphs : A list of paragraphs
	 * @return: A list of sentences
	 * Tokenizes paragraphs into sentences.
	 */
	public static List<String> tokenizeParagraphs(List<String> paragraphs) {
		SentenceBoundaryDetector detector = new SimpleSentenceBoundaryDetector();
		List<String> sentences = new ArrayList<>();
		for (String paragraph : paragraphs) {
			detector.getSentences(paragraph).forEach(e -> sentences.add(e));;
		}
		return sentences;
	}
}
