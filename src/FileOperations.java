import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class FileOperations {
	/**
	 * @param fileName
	 * @return List of String.
	 * Reads specified text document line by line and adds each line to a list.
	 */
	public static List<String> readFile(String fileName) {
		Path file = Paths.get(fileName);
		try {
			List<String> sentences = Files.readAllLines(file);
			return sentences;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @param text
	 * @throws IOException
	 * Writes the content of List into a text file
	 */
	public static void writeFile(List<String> text, String fileName) throws IOException{
		Path file = Paths.get(fileName);
		Files.write(file, text, Charset.forName("UTF-8"));
	}
}
