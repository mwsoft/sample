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

import java.io.IOException;
import java.io.Reader;
import java.lang.Character.UnicodeBlock;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

/**
 * CJKTokenizer is designed for Chinese, Japanese, and Korean languages.
 * <p>
 * The tokens returned are every two adjacent characters with overlap match.
 * </p>
 * <p>
 * Example: "java C1C2C3C4" will be segmented to: "java" "C1C2" "C2C3" "C3C4".
 * </p>
 * Additionally, the following is applied to Latin text (such as English):
 * <ul>
 * <li>Text is converted to lowercase.
 * <li>Numeric digits, '+', '#', and '_' are tokenized as letters.
 * <li>Full-width forms are converted to half-width forms.
 * </ul>
 * For more info on Asian language (Chinese, Japanese, and Korean) text
 * segmentation: please search <a
 * href="http://www.google.com/search?q=word+chinese+segment">google</a>
 * 
 */
public final class CJKTokenizerSaveKaomoji extends Tokenizer {

	// ~ Static fields/initializers
	// ---------------------------------------------
	/** Word token type */
	static final int WORD_TYPE = 0;

	/** Single byte token type */
	static final int SINGLE_TOKEN_TYPE = 1;

	/** Double byte token type */
	static final int DOUBLE_TOKEN_TYPE = 2;

	/** KATAKANA token type */
	static final int SYMBOL_TOKEN_TYPE = 3;

	/** Names for token types */
	static final String[] TOKEN_TYPE_NAMES = { "word", "single", "double", "symbol" };

	/** Max word length */
	private static final int MAX_WORD_LEN = 255;

	/** buffer size: */
	private static final int IO_BUFFER_SIZE = 256;

	// ~ Instance fields
	// --------------------------------------------------------

	/** word offset, used to imply which character(in ) is parsed */
	private int offset = 0;

	/** the index used only for ioBuffer */
	private int bufferIndex = 0;

	/** data length */
	private int dataLen = 0;

	/**
	 * character buffer, store the characters which are used to compose <br>
	 * the returned Token
	 */
	private final char[] buffer = new char[MAX_WORD_LEN];

	/**
	 * I/O buffer, used to store the content of the input(one of the <br>
	 * members of Tokenizer)
	 */
	private final char[] ioBuffer = new char[IO_BUFFER_SIZE];

	/** word type: single=>ASCII double=>non-ASCII word=>default */
	private int tokenType = WORD_TYPE;

	/**
	 * tag: previous character is a cached double-byte character "C1C2C3C4"
	 * ----(set the C1 isTokened) C1C2 "C2C3C4" ----(set the C2 isTokened) C1C2
	 * C2C3 "C3C4" ----(set the C3 isTokened) "C1C2 C2C3 C3C4"
	 */
	private boolean preIsTokened = false;

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	// ~ Constructors
	// -----------------------------------------------------------

	/**
	 * Construct a token stream processing the given input.
	 * 
	 * @param in
	 *            I/O reader
	 */
	public CJKTokenizerSaveKaomoji(Reader in) {
		super(in);
	}

	public CJKTokenizerSaveKaomoji(AttributeSource source, Reader in) {
		super(source, in);
	}

	public CJKTokenizerSaveKaomoji(AttributeFactory factory, Reader in) {
		super(factory, in);
	}

	// ~ Methods
	// ----------------------------------------------------------------

	/**
	 * Returns true for the next token in the stream, or false at EOS. See
	 * http:/
	 * /java.sun.com/j2se/1.3/docs/api/java/lang/Character.UnicodeBlock.html for
	 * detail.
	 * 
	 * @return false for end of stream, true otherwise
	 * 
	 * @throws java.io.IOException
	 *             - throw IOException when read error <br>
	 *             happened in the InputStream
	 * 
	 */
	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		/** how many character(s) has been stored in buffer */

		while (true) { // loop until we find a non-empty token

			int length = 0;

			/** the position used to create Token */
			int start = offset;

			while (true) { // loop until we've found a full token
				/** current character */
				char c;

				/** unicode block of current character for detail */
				Character.UnicodeBlock ub;

				offset++;

				if (bufferIndex >= dataLen) {
					dataLen = input.read(ioBuffer);
					bufferIndex = 0;
				}

				if (dataLen == -1) {
					if (length > 0) {
						if (preIsTokened == true) {
							length = 0;
							preIsTokened = false;
						} else {
							offset--;
						}

						break;
					} else {
						offset--;
						return false;
					}
				} else {
					// get current character
					c = ioBuffer[bufferIndex++];

					// get the UnicodeBlock of the current character
					ub = Character.UnicodeBlock.of(c);
				}

				// if the current character is letter or digit
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
					if (length == 0) {
						start = offset - 1;
					} else if (tokenType == DOUBLE_TOKEN_TYPE || tokenType == SYMBOL_TOKEN_TYPE) {
						offset--;
						bufferIndex--;
						if (preIsTokened == true) {
							length = 0;
							preIsTokened = false;
							break;
						} else {
							break;
						}
					}
					buffer[length++] = Character.toLowerCase(c);
					tokenType = SINGLE_TOKEN_TYPE;

					if (length == MAX_WORD_LEN) {
						break;
					}
				} else if (!Character.isLetter(c) && !Character.isWhitespace(c) && c > 32 && c != 127) {
					if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
						int i = (int) c;
						if (i >= 65281 && i <= 65374) {
							// convert certain HALFWIDTH_AND_FULLWIDTH_FORMS to
							// BASIC_LATIN
							i = i - 65248;
							c = (char) i;
						}
					}

					if (length == 0) {
						start = offset - 1;
					} else if (tokenType == DOUBLE_TOKEN_TYPE || tokenType == SINGLE_TOKEN_TYPE) {
						offset--;
						bufferIndex--;
						if (preIsTokened == true) {
							length = 0;
							preIsTokened = false;
							break;
						} else {
							break;
						}
					}
					buffer[length++] = c;
					tokenType = SYMBOL_TOKEN_TYPE;

					if (length == MAX_WORD_LEN) {
						break;
					}
				} else {
					// non-ASCII letter, e.g."C1C2C3C4"
					if (Character.isLetter(c)) {
						if (length == 0) {
							start = offset - 1;
							buffer[length++] = c;
							tokenType = DOUBLE_TOKEN_TYPE;
						} else {
							if (tokenType == SINGLE_TOKEN_TYPE || tokenType == SYMBOL_TOKEN_TYPE) {
								offset--;
								bufferIndex--;

								// return the previous ASCII characters
								break;
							} else {
								buffer[length++] = c;
								tokenType = DOUBLE_TOKEN_TYPE;

								if (length == 2) {
									offset--;
									bufferIndex--;
									preIsTokened = true;

									break;
								}
							}
						}
					} else if (length > 0) {
						if (preIsTokened == true) {
							// empty the buffer
							length = 0;
							preIsTokened = false;
						} else {
							break;
						}
					}
				}
			}

			if (length > 1) {
				termAtt.copyBuffer(buffer, 0, length);
				offsetAtt.setOffset(correctOffset(start), correctOffset(start + length));
				typeAtt.setType(TOKEN_TYPE_NAMES[tokenType]);
				return true;
			} else if (length == 1) {
				char c = buffer[0];
				boolean addIndex = Character.isLetter(c);
				if (addIndex) {
					termAtt.copyBuffer(buffer, 0, length);
					offsetAtt.setOffset(correctOffset(start), correctOffset(start + length));
					typeAtt.setType(TOKEN_TYPE_NAMES[tokenType]);
					return true;
				}
			} else if (dataLen == -1) {
				offset--;
				return false;
			}

			// Cycle back and try for the next token (don't
			// return an empty string)
		}
	}

	@Override
	public final void end() {
		// set final offset
		final int finalOffset = correctOffset(offset);
		this.offsetAtt.setOffset(finalOffset, finalOffset);
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		offset = bufferIndex = dataLen = 0;
		preIsTokened = false;
		tokenType = WORD_TYPE;
	}

	@Override
	public void reset(Reader reader) throws IOException {
		super.reset(reader);
		reset();
	}
}
