package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapanesePartOfSpeechStopFilter;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class JapanesePartOfSpeechStopFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "控えろ。お前たちが簡単にお会いできる人ではない。";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // =>     控えろ	お前	たち	簡単	お	会い	できる	人	
    }

    static class MyAnalyzer extends Analyzer {
        static final String[] STOP_PART_OF_SPEECH = { 
        	"接続詞", "助動詞", "助詞", "助詞-格助詞", "助詞-格助詞-一般", "助詞-格助詞-引用", "助詞-格助詞-連語",
        	"助詞-接続助詞", "助詞-係助詞", "助詞-副助詞", "助詞-間投助詞", "助詞-並立助詞", "助詞-終助詞",
        	"助詞-副助詞／並立助詞／終助詞", "助詞-連体化", "助詞-副詞化", "助詞-特殊"};
        static final Set<String> STOP_SET = new HashSet<String>();
        static {
            for( String partOfSpeech : STOP_PART_OF_SPEECH )
                STOP_SET.add(partOfSpeech);
        }
        
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new JapaneseTokenizer(reader, null, true, JapaneseTokenizer.Mode.NORMAL);
            result = new JapanesePartOfSpeechStopFilter(true, result, STOP_SET);
            return result;
        }
    }
}
