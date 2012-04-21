package jp.mwsoft.sample.hadoop.solrwordcount;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class StopFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "My father's name was John Kinsella. It's an Irish name.";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        //=> My father's    name    John    Kinsella    It's    an  Irish   name    
    }

    static class MyAnalyzer extends Analyzer {
        String[] STOP_WORDS = { "a", "and", "are", "as", "at", "be", "but", "by", "for", "if",
                "in", "into", "is", "it", "no", "not", "of", "on", "or", "s", "such", "t", "that",
                "the", "their", "then", "there", "these", "they", "this", "to", "was", "will",
                "with" };
        Set<?> STOP_WORDS_SET = StopFilter
                .makeStopSet(Version.LUCENE_36, Arrays.asList(STOP_WORDS));

        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, reader);
            result = new StopFilter(Version.LUCENE_36, result, STOP_WORDS_SET);
            return result;
        }
    }
}
