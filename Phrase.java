//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class Phrase {

	// -----------------------------------------------------------------------------------------------------------
	// Returns all phrases in a given sentence
	// -----------------------------------------------------------------------------------------------------------
	// e.g. getNounPhrases gives all noun phrases in given sentence

	public List<List<LabeledWord>> getNounPhrases(Tree parse) {
		List<List<LabeledWord>> result = new ArrayList<>();
		TregexPattern pattern = TregexPattern.compile("@NP");
		TregexMatcher matcher = pattern.matcher(parse);
		TregexPattern check = TregexPattern.compile("@CC");
		while (matcher.find()) {
			Tree match = matcher.getMatch();

			TregexMatcher checker = check.matcher(match);
			if (checker.find()) {
				continue;// this is at a higher level of noun phrase, skip to prevent repeated noun
							// phrases
			}
			List<LabeledWord> labeledYield = match.labeledYield();
			result.add(labeledYield);
		}
		return result;
	}

	public List<List<LabeledWord>> getVerbPhrases(Tree parse) {
		List<List<LabeledWord>> result = new ArrayList<>();
		TregexPattern pattern = TregexPattern.compile("@VP");
		TregexMatcher matcher = pattern.matcher(parse);
		while (matcher.find()) {
			Tree match = matcher.getMatch();

			List<LabeledWord> labeledYield = match.labeledYield();
			result.add(labeledYield);
		}
		return result;
	}

	public List<List<LabeledWord>> getPrepositionalPhrases(Tree parse) {
		List<List<LabeledWord>> result = new ArrayList<>();
		TregexPattern pattern = TregexPattern.compile("@PP");
		TregexMatcher matcher = pattern.matcher(parse);
		while (matcher.find()) {
			Tree match = matcher.getMatch();

			List<LabeledWord> labeledYield = match.labeledYield();
			result.add(labeledYield);
		}
		return result;
	}

	public List<List<LabeledWord>> getAdjectivePhrases(Tree parse) {
		List<List<LabeledWord>> result = new ArrayList<>();
		TregexPattern pattern = TregexPattern.compile("@ADJP");
		TregexMatcher matcher = pattern.matcher(parse);
		while (matcher.find()) {
			Tree match = matcher.getMatch();

			List<LabeledWord> labeledYield = match.labeledYield();
			result.add(labeledYield);
		}
		return result;
	}

	public List<List<LabeledWord>> getAdverbPhrases(Tree parse) {
		List<List<LabeledWord>> result = new ArrayList<>();
		TregexPattern pattern = TregexPattern.compile("@ADVP");
		TregexMatcher matcher = pattern.matcher(parse);
		while (matcher.find()) {
			Tree match = matcher.getMatch();

			List<LabeledWord> labeledYield = match.labeledYield();
			result.add(labeledYield);
		}
		return result;
	}

	// -----------------------------------------------------------------------------------------------------------
	// Returns all POS in a given sentence
	// -----------------------------------------------------------------------------------------------------------
	// e.g. getNounsInPhrases gives all noun in given phrase

	AnnotateWord wordInPhrase = new AnnotateWord();
	AnnotateWord lemma;
	boolean pluralWord = false;

	public ArrayList<String> getNounsInPhrase(List<LabeledWord> nounsInP) throws FileNotFoundException {
		for (int i = 0; i < nounsInP.size(); i++) {
			wordInPhrase.add(nounsInP.get(i).toString());
		}
		pluralWord = wordInPhrase.getPlural();
		if (!wordInPhrase.getNoun().isEmpty()) {
			lemma = new AnnotateWord(wordInPhrase.getNoun().get(0).toString());
		} else
			return null;
		ArrayList<String> result = new ArrayList<String>();
		result.add(lemma.lemmaWord());
		return result;

	}

	public ArrayList<String> getPronounsInPhrase(List<LabeledWord> pronounsInP) throws FileNotFoundException {
		for (int i = 0; i < pronounsInP.size(); i++) {
			wordInPhrase.add(pronounsInP.get(i).toString());
		}
		if (!wordInPhrase.getPronoun().isEmpty()) {
			lemma = new AnnotateWord(wordInPhrase.getPronoun().get(0).toString());
		} else
			return null;
		ArrayList<String> result = new ArrayList<String>();
		result.add(lemma.lemmaWord());
		return result;
	}

	public ArrayList<String> getVerbsInPhrase(List<LabeledWord> verbsInP) {
		for (int i = 0; i < verbsInP.size(); i++) {
			wordInPhrase.add(verbsInP.get(i).toString());
		}
		return wordInPhrase.getVerb();
	}

	public ArrayList<String> getParticlesInPhrase(List<LabeledWord> partsInP) {
		for (int i = 0; i < partsInP.size(); i++) {
			wordInPhrase.add(partsInP.get(i).toString());
		}
		return wordInPhrase.getParticle();
	}

	public ArrayList<String> getAdjectivesInPhrase(List<LabeledWord> adjsInP) {
		for (int i = 0; i < adjsInP.size(); i++) {
			wordInPhrase.add(adjsInP.get(i).toString());
		}
		return wordInPhrase.getAdjective();
	}

	public ArrayList<String> getPrepositionalsInPhrase(List<LabeledWord> prepsInP) {
		for (int i = 0; i < prepsInP.size(); i++) {
			wordInPhrase.add(prepsInP.get(i).toString());
		}
		return wordInPhrase.getPreposition();
	}

	public ArrayList<String> getAdverbsInPhrase(List<LabeledWord> advsInP) {
		for (int i = 0; i < advsInP.size(); i++) {
			wordInPhrase.add(advsInP.get(i).toString());
		}
		return wordInPhrase.getAdverb();
	}

	public boolean isPlural() {
		return pluralWord;
	}
	// -----------------------------------------------------------------------------------------------------------
	// display
	// -----------------------------------------------------------------------------------------------------------

	public String print(List<LabeledWord> label) {
		Word w = new Word();
		// for(int i = 0; i < list.size(); i++) {
		//
		// List<LabeledWord> label = list.get(i);
		StringBuilder phrase = new StringBuilder();

		// print per phrase
		for (int j = 0; j < label.size(); j++) {

			// show all words individually and respective labels
			String wordWithLabel = label.get(j).toString();
			w.add(wordWithLabel);

			// add word to form phrase to be read
			String[] temp = wordWithLabel.split("/");
			for (int k = 0; k < temp.length; k++) {
				if (k % 2 == 0) {
					phrase.append(temp[k]);
					phrase.append(" ");
				}
			}
		}
		System.out.println(phrase);
		return phrase.toString();
		// }
	}

	public String list(List<LabeledWord> label) {
		Word w = new Word();
		StringBuilder phrase = new StringBuilder();

		// print per phrase
		for (int j = 0; j < label.size(); j++) {

			// show all words individually and respective labels
			String wordWithLabel = label.get(j).toString();
			w.add(wordWithLabel);

			// add word to form phrase to be read
			String[] temp = wordWithLabel.split("/");
			for (int k = 0; k < temp.length; k++) {
				if (k % 2 == 0) {
					phrase.append(temp[k]);
					phrase.append(" ");
				}
			}
		}
		return phrase.toString();
	}
}
