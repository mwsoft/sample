package jp.mwsoft.sample.mahout.utils;

import java.io.Reader;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.util.Version;

public class SymbolTokenizer extends CharTokenizer {

	public SymbolTokenizer(Version version, Reader input) {
		super(version, input);
	}

	protected boolean isTokenChar(int c) {
		return Character.isLetter(c) || Character.isDigit(c);
	}
}
