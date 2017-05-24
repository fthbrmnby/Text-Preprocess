import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class TranslateWords {
	
	public static HashMap<String, String> getMap() {
		return readMap();
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String> readMap() {
		HashMap<String, String> neg;
		HashMap<String, String> pos;
		try {
	         FileInputStream fileInNeg = new FileInputStream("data/neg-dict.ser");
	         FileInputStream fileInPos = new FileInputStream("data/pos-dict.ser");
	         ObjectInputStream negHash = new ObjectInputStream(fileInNeg);
	         ObjectInputStream posHash = new ObjectInputStream(fileInPos);
	         neg = (HashMap<String, String>) negHash.readObject();
	         pos = (HashMap<String, String>) posHash.readObject();
	         negHash.close();
	         posHash.close();
	         fileInNeg.close();
	         fileInPos.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Class not found!");
	         c.printStackTrace();
	         return null;
	      }
		
		HashMap<String, String> dict = new HashMap<>();
		dict.putAll(neg);
		dict.putAll(pos);
		
		return dict;
	}
}
