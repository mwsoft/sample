package jp.mwsoft.sample.hadoop.solrwordcount;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ja.tokenattributes.InflectionAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.ReadingAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class SolrWordCount {

    public static void main(String[] args) throws Exception {

        JapaneseAnalyzer analyzer = new JapaneseAnalyzer(Version.LUCENE_36);

        String str = "私がウスウスと眼を覚ました時、こうした蜜蜂の唸るような音は、まだ、その弾力の深い余韻を、私の耳の穴の中にハッキリと引き残していた。";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            System.out.println("--------------------------------------------------");
            
            // term
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.println("term : " + term.toString());

            // 品詞
            PartOfSpeechAttribute partOfSpeech = stream.getAttribute(PartOfSpeechAttribute.class);
            System.out.println("partOfSpeech : " + partOfSpeech.getPartOfSpeech());

            // 読み
            ReadingAttribute reading = stream.getAttribute(ReadingAttribute.class);
            System.out.println("reading : " + reading.getReading() + " / " + reading.getPronunciation());

            // 活用
            InflectionAttribute inflection = stream.getAttribute(InflectionAttribute.class);
            System.out.println("inflection : " + inflection.getInflectionForm() + " / " + inflection.getInflectionType());
        }
    }
}
