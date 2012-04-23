package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class NGramTokenFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "とりあえず３区切りで。";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // => とりあ   りあえ あえず えず３ ず３区 ３区切 区切り 切りで りで。 
    }

    static class MyAnalyzer extends Analyzer {
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new WhitespaceTokenizer(Version.LUCENE_36, reader);
            // 3gramで
            result = new NGramTokenFilter(result, 3, 3);
            return result;
        }
    }
}