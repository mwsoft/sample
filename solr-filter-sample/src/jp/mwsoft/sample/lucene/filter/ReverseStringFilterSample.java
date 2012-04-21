package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.reverse.ReverseStringFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class ReverseStringFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "My father's name was John Kinsella. It's an Irish name.";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // => yM    s'rehtaf    eman    saw nhoJ    allesniK    s'tI    na  hsirI   eman    
    }

    static class MyAnalyzer extends Analyzer {
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, reader);
            result = new ReverseStringFilter(Version.LUCENE_36, result);
            return result;
        }
    }
}
