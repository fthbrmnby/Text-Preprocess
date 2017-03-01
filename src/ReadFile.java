import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {
	/**
	 * Name of the text file to read.
	 */
	private static String FILE_NAME = "test_file.txt";
	
	/**
	 * @return List of String.
	 * Reads specified text document line by line and adds each line to a list.
	 */
	public static List<String> readTextFile() {
		BufferedReader br = null;
		FileReader fr = null;
		
		List<String> sentences = new ArrayList<>();
		
		try {
			fr = new FileReader(FILE_NAME);
			br = new BufferedReader(fr);
			
			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null) {
				sentences.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
				if (fr != null) fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sentences;
	}
}
