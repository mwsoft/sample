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

        String str = "しかし今日はアレだったな";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // =>     今日    は   アレ  な    
    }

    static class MyAnalyzer extends Analyzer {
        static final String[] STOP_PART_OF_SPEECH = { "接続詞", "助動詞" };
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
