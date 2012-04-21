package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.MappingCharFilter;
import org.apache.lucene.analysis.NormalizeCharMap;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class MappingCharFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "I am happy to join with you today";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // => title element body    element 
    }

    static class MyAnalyzer extends Analyzer {
    	
    	final static NormalizeCharMap map = new NormalizeCharMap();
    	static {
    		map.add( "a", "x");
    	}
    	
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            reader = new MappingCharFilter(map, CharReader.get(reader));
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, reader);
            return result;
        }
    }
}
