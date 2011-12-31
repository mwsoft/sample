package jp.mwsoft.cjkanalyzers;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cjk.CJKTokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;

public class CustomCJKTokenizerFactory extends BaseTokenizerFactory {

	public Tokenizer create(Reader in) {
		String cls = args.get("tokenizerClass");
		if (cls != null) {
			try {
				Object obj = Class.forName(cls).getConstructor(Reader.class).newInstance(in);
				return (Tokenizer) obj;
			} catch (Throwable e) {
				log.error("initialize " + cls, e);
			}
		}
		return new CJKTokenizer(in);
	}

}
