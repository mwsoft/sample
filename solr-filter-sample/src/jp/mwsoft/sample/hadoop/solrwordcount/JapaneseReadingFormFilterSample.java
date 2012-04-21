package jp.mwsoft.sample.hadoop.solrwordcount;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseReadingFormFilter;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class JapaneseReadingFormFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "控えろ。わきまえろ。お前たちが簡単にお会いできるような人ではない。";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        // => ヒカエロ  ワキマエロ   オマエ タチ  ガ   カンタン    ニ   オ   アイ  デキル ヨウ  ナ   ヒト  デ   ハ   ナイ
        
        // 引数をresult, trueにした場合
        // hikaero  wakimaero   omae    tachi   ga  kantan  ni  o   ai  dekiru  yō  na  hito    de  ha  nai
    }

    static class MyAnalyzer extends Analyzer {
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new JapaneseTokenizer(reader, null, true, JapaneseTokenizer.Mode.NORMAL);
            result = new JapaneseReadingFormFilter(result);
            return result;
        }
    }
}
