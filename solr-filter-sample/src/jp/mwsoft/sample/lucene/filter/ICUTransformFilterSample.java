package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.icu.ICUTransformFilter;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.ibm.icu.text.Transliterator;

public class ICUTransformFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "控えろ。お前たちが簡単にお会いできる人ではない。";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // => 控エロ   オ前  タチ  ガ   簡単  ニ   オ   会イ  デキル 人   デ   ハ   ナイ  
    }

    static class MyAnalyzer extends Analyzer {
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new JapaneseTokenizer(reader, null, true, JapaneseTokenizer.Mode.NORMAL);
            // 平仮名をカタカナに
            result = new ICUTransformFilter(result, Transliterator.getInstance("Hiragana-Katakana"));
            return result;
        }
    }
}