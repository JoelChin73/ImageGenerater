//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

import java.util.ArrayList;

/*
 * Use this class to store the respective parts of speech (POS)
 * and the respective word.
 */

public class Word {

	private ArrayList<String> nounSet;
	private ArrayList<String> verbSet;
	private ArrayList<String> adjectiveSet;
	private ArrayList<String> adverbSet;
	private ArrayList<String> prepositionSet;
	private ArrayList<String> pronounSet;
	private ArrayList<String> particleSet;
	private ArrayList<Integer> numericSet;
	private ArrayList<String> predeterminerSet;
	private ArrayList<String> possessiveEndingSet;
	private boolean plural = false;

	public Word() {
		nounSet = new ArrayList<String>();
		verbSet = new ArrayList<String>();
		adjectiveSet = new ArrayList<String>();
		adverbSet = new ArrayList<String>();
		prepositionSet = new ArrayList<String>();
		pronounSet = new ArrayList<String>();
		particleSet = new ArrayList<String>();
		numericSet = new ArrayList<Integer>();
		predeterminerSet = new ArrayList<String>();
		possessiveEndingSet = new ArrayList<String>();
	}

	public void add(String word, String pos) {
		if (pos.equals("NN") || pos.equals("NNS") || pos.equals("NNP") || pos.equals("NNPS")) {
			nounSet.add(word);
			if (pos.equals("NNS") || pos.equals("NNPS"))
				plural = true;
		}
		if (pos.equals("VB") || pos.equals("VBD") || pos.equals("VBG") || pos.equals("VBN") || pos.equals("VBP")
				|| pos.equals("VBZ")) {
			verbSet.add(word);
		}
		if (pos.equals("JJ") || pos.equals("JJR") || pos.equals("JJS")) {
			adjectiveSet.add(word);
		}
		if (pos.equals("RB") || pos.equals("RBR") || pos.equals("RBS")) {
			adverbSet.add(word);
		}
		if (pos.equals("PRP") || pos.equals("PRP$") || pos.equals("WP$")) {
			// does not detect plural words here, but will eventually be shown via database
			// of images
			pronounSet.add(word);
		}
		if (pos.equals("RP")) {
			particleSet.add(word);
		}
		if (pos.equals("IN") || pos.equals("TO")) {// in parts of speech tags, "to" has a separate preposition tag
			prepositionSet.add(word);
		}
		if (pos.equals("PDT")) {
			predeterminerSet.add(word);
		}
		if (pos.equals("CD")) {
			int temp = new WordToNumber().replace(word);
			Integer num = new Integer(temp);
			numericSet.add(num);
			if (temp > 1)
				plural = true;
		}
		if (pos.equals("POS")) {
			possessiveEndingSet.add(word);
		}
	}

	public void add(String labelYield) {
		String[] tag = labelYield.split("/");
		add(tag[0], tag[1]);
	}

	public ArrayList<String> getNoun() {
		return nounSet;
	}

	public ArrayList<String> getVerb() {
		return verbSet;
	}

	public ArrayList<String> getAdjective() {
		return adjectiveSet;
	}

	public ArrayList<String> getAdverb() {
		return adverbSet;
	}

	public ArrayList<String> getPreposition() {
		return prepositionSet;
	}

	public ArrayList<String> getPronoun() {
		return pronounSet;
	}

	public ArrayList<String> getParticle() {
		return particleSet;
	}

	public ArrayList<Integer> getNumeric() {
		return numericSet;
	}

	public ArrayList<String> getPossessiveEnding() {
		return possessiveEndingSet;
	}

	public boolean getPlural() {
		return plural;
	}
}
