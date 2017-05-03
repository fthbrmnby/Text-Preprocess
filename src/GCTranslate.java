import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class GCTranslate {
	
	public static String getTranslation(String word){
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		
		Translation translation =
		        translate.translate(
		            word,
		            TranslateOption.sourceLanguage("tr"),
		            TranslateOption.targetLanguage("en"));
		
		return translation.getTranslatedText().toLowerCase();
	}
}
