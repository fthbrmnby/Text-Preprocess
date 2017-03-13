import java.util.List;

import com.google.gson.Gson;

/**
 * @author Fatih Barmanbay
 * Simple class to store data as JSON.
 */
public class JsonBuilder {
	private String word;
	private String posTag;
	
	public JsonBuilder(String word, String posTag) {
		this.word = word;
		this.posTag = posTag;
	}
	
	/**
	 * @param object 
	 * @return 
	 */
	public static String toJson(Object object) {
		Gson converter = new Gson();
		String json = converter.toJson(object);
		return json;
	}
}
