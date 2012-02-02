package jp.mwsoft.sample.kuromoji;

import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;
import org.atilika.kuromoji.Tokenizer.Mode;

public class TokenizerModeSample {

	public static void main(String[] args) {

		String parseWord = "日本経済新聞でモバゲーの記事を読んだ";
		// String parseWord = "生真面目";
		// String parseWord = "ボトムアップテスト";
		Builder builder = Tokenizer.builder();

		// ノーマルモード
		Tokenizer normal = builder.build();
		List<Token> tokensNormal = normal.tokenize(parseWord);
		disp(tokensNormal);

		// Searchモード
		builder.mode(Mode.SEARCH);
		Tokenizer search = builder.build();
		List<Token> tokensSearch = search.tokenize(parseWord);
		disp(tokensSearch);
		
		// Extendsモード
		builder.mode(Mode.EXTENDED);
		Tokenizer extended = builder.build();
		List<Token> tokensExtended = extended.tokenize(parseWord);
		disp(tokensExtended);
	}

	public static void disp(List<Token> tokens) {
		for (Token token : tokens)
			System.out.print(token.getSurfaceForm() + " | ");
		System.out.println();
	}

}
