package jp.mwsoft.sample.phraseextract;

import java.lang.Character.UnicodeBlock;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Term {

	/** 表層文字 */
	private String surface;

	/** 品詞 */
	private String feature;

	/** train用の行文字列 */
	private String trainLine;

	/** 評価値 */
	private int value = 0;

	/** 文字数 */
	private int length;
	/** 品詞ID */
	private int featureId;
	/** 未知語 */
	private boolean unknown;

	/** 単語内の文字タイプごとのパーセンテージ */
	private Map<Integer, Float> charPercentages = new HashMap<Integer, Float>();

	public static final int OTHER = 0;
	public static final int SYMBOL = 1;
	public static final int NUMERIC = 2;
	public static final int ALPHABET = 3;
	public static final int HIRAGANA = 4;
	public static final int KATAKANA = 5;
	public static final int KANJI = 6;

	public Term(int value, String surface, String feature, boolean unknown) {
		this.value = value;
		set(surface, feature, unknown);
	}

	public Term(String surface, String feature, boolean unknown) {
		set(surface, feature, unknown);
	}

	public void set(String surface, String feature, boolean unknown) {
		this.surface = surface;
		this.feature = feature;
		this.length = surface.length();
		this.unknown = unknown;
		this.charPercentages = getCharPercentages(surface);
		this.featureId = FeatureId.getFeatureId(feature);
	}

	public String toTrainString(int idx) {
		StringBuilder builder = new StringBuilder();
		builder.append(idx + ":" + this.length + " ");
		builder.append((idx + 1) + ":" + (this.unknown ? "1" : "0") + " ");
		for (Entry<Integer, Float> entry : this.charPercentages.entrySet())
			builder.append((idx + 2 + entry.getKey()) + ":" + entry.getValue() + " ");
		builder.append((idx + 9) + this.featureId + ":" + 1);
		return builder.toString();
	}

	public String toString() {
		return this.value + "\t" + this.surface + "\t" + this.feature + "\t" + (this.unknown ? "1" : "0");
	}

	/**
	 * 文字タイプごとのパーセンテージを出す
	 * 
	 * @param surface
	 * @return
	 */
	private Map<Integer, Float> getCharPercentages(String surface) {
		// 文字タイプごとにカウント
		Map<Integer, Float> counter = new HashMap<Integer, Float>();
		for (int i = 0; i < surface.length(); i++)
			incl(counter, getCharType(surface.charAt(i)));

		// パーセンテージのセット
		float length = surface.length();
		for (Entry<Integer, Float> entry : counter.entrySet())
			counter.put(entry.getKey(), entry.getValue() / length);

		return counter;
	}

	/**
	 * 記号: 1, 平仮名: 2, カタカナ: 3, 漢字: 4, 数字: 5, アルファベット: 6
	 * 
	 * @param c
	 * @return
	 */
	private int getCharType(char c) {
		UnicodeBlock block = UnicodeBlock.of(c);
		// 記号
		if (!Character.isLetter(c))
			return SYMBOL;
		// 平仮名
		else if (block == UnicodeBlock.HIRAGANA)
			return HIRAGANA;
		// 片仮名
		else if (block == UnicodeBlock.KATAKANA)
			return KATAKANA;
		// 漢字
		else if (block.toString().startsWith("CJK"))
			return KANJI;
		// 数字
		else if (Character.isDigit(c))
			return NUMERIC;
		// アルファベット
		else if (Character.toString(c).matches("[a-zA-Z]"))
			return ALPHABET;
		// その他
		return OTHER;
	}

	private void incl(Map<Integer, Float> counter, Integer key) {
		Float f = counter.get(key);
		if (f == null)
			counter.put(key, 1.0f);
		else
			counter.put(key, f + 1.0f);
	}

	public int getValue() {
		return value;
	}

	public int getLength() {
		return length;
	}

	public int getFeatureId() {
		return featureId;
	}

	public boolean isUnknown() {
		return unknown;
	}

	public Map<Integer, Float> getCharPercentages() {
		return charPercentages;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setUnknown(boolean unknown) {
		this.unknown = unknown;
	}

	public String getTrainLine() {
		return trainLine;
	}

	public void setTrainLine(String trainLine) {
		this.trainLine = trainLine;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
