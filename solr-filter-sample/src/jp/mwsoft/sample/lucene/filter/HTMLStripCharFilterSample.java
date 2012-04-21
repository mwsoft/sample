package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class HTMLStripCharFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "<html><head><title>title element</title><script>script element</script></head><body>body element</body></html>";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // => title element body    element 
    }

    static class MyAnalyzer extends Analyzer {
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            reader = new HTMLStripCharFilter(CharReader.get(reader));
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, reader);
            return result;
        }
    }
}
