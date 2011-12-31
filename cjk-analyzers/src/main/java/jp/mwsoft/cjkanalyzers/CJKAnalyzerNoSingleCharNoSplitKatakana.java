package jp.mwsoft.cjkanalyzers;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Set;

import jp.mwsoft.cjkanalyzers.CJKAnalyzerBase.DefaultSetHolder;

/**
 * An {@link Analyzer} that tokenizes text with {@link CJKTokenizer} and filters
 * with {@link StopFilter}
 * 
 */
public final class CJKAnalyzerNoSingleCharNoSplitKatakana extends StopwordAnalyzerBase {

	public static void main(String[] args) throws Exception {
		java.io.StringReader reader = new java.io.StringReader("http://www.yahoo.co.jp/");
		Tokenizer tokenizer = new CJKTokenizerSaveKaomoji(reader);

		for (int i = 0; i < 10; i++) {
			tokenizer.incrementToken();
			System.out.println(tokenizer);
		}
	}

	// ~ Constructors
	// -----------------------------------------------------------

	/**
	 * Builds an analyzer which removes words in {@link #getDefaultStopSet()}.
	 */
	public CJKAnalyzerNoSingleCharNoSplitKatakana(Version matchVersion) {
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	/**
	 * Builds an analyzer with the given stop words
	 * 
	 * @param matchVersion
	 *            lucene compatibility version
	 * @param stopwords
	 *            a stopword set
	 */
	public CJKAnalyzerNoSingleCharNoSplitKatakana(Version matchVersion, Set<?> stopwords) {
		super(matchVersion, stopwords);
	}

	// ~ Methods
	// ----------------------------------------------------------------

	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		final Tokenizer source = new CJKTokenizerNoSingleCharNoSplitKatakana(reader);
		return new TokenStreamComponents(source, new StopFilter(matchVersion, source, stopwords));
	}
}
