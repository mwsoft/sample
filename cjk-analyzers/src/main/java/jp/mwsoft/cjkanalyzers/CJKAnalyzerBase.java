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
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Arrays;
import java.util.Set;

/**
 * An {@link Analyzer} that tokenizes text with
 * {@link CJKTokenizer} and filters with {@link StopFilter}
 * 
 */
abstract public class CJKAnalyzerBase extends StopwordAnalyzerBase {

	/**
	 * An array containing some common English words that are not usually useful
	 * for searching and some double-byte interpunctions.
	 * 
	 * @deprecated use {@link #getDefaultStopSet()} instead
	 */
	@Deprecated
	public final static String[] STOP_WORDS = CJKAnalyzer.STOP_WORDS;

	// ~ Instance fields
	// --------------------------------------------------------

	/**
	 * Returns an unmodifiable instance of the default stop-words set.
	 * 
	 * @return an unmodifiable instance of the default stop-words set.
	 */
	public static Set<?> getDefaultStopSet() {
		return CJKAnalyzer.getDefaultStopSet();
	}

	protected static class DefaultSetHolder {
		static final Set<?> DEFAULT_STOP_SET = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT,
				Arrays.asList(STOP_WORDS), false));
	}

	// ~ Constructors
	// -----------------------------------------------------------

	/**
	 * Builds an analyzer which removes words in {@link #getDefaultStopSet()}.
	 */
	public CJKAnalyzerBase(Version matchVersion) {
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
	public CJKAnalyzerBase(Version matchVersion, Set<?> stopwords) {
		super(matchVersion, stopwords);
	}

	/**
	 * Builds an analyzer which removes words in the provided array.
	 * 
	 * @param stopWords
	 *            stop word array
	 * @deprecated use {@link #CJKAnalyzer(Version, Set)} instead
	 */
	@Deprecated
	public CJKAnalyzerBase(Version matchVersion, String... stopWords) {
		super(matchVersion, StopFilter.makeStopSet(matchVersion, stopWords));
	}

	// ~ Methods
	// ----------------------------------------------------------------

	@Override
	abstract protected TokenStreamComponents createComponents(String fieldName, Reader reader);
}
