//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

/*
 * This class parses the sentence to tag
 * the words with the respective parts of
 * speech tagging.
 */

public class AnnotateWord extends Word {

	private String s;
	private Annotation a;
	private Tree tree;

	public AnnotateWord(String simpleSentence) throws FileNotFoundException {
		super();
		// using properties of sentence splitting and Parts Of Speech (POS)
		Properties p = new Properties();
		// p.setProperty("annotators", "tokenize, ssplit, pos");//without tree notation
		p.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(p);

		s = simpleSentence;

		// annotate text
		a = new Annotation(s);
		pipeline.annotate(a);

		List<CoreMap> sentences = a.get(CoreAnnotations.SentencesAnnotation.class);
		if (sentences != null && !sentences.isEmpty()) {
			CoreMap sentence = sentences.get(0);

			// use Tree class to get the nodes needed
			tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
		}

		// for testing purposes
		PrintWriter out = new PrintWriter(System.out);

		if (sentences != null && !sentences.isEmpty()) {
			CoreMap sentence = sentences.get(0);
			out.println();
			for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				out.println(token.toShorterString());
			}
		}
		out.println("\nTree structure of sentence:");
		tree.pennPrint(out);
		out.println();

	}

	public AnnotateWord() {
		super();
	}

	public Tree getTree() {
		return tree;
	}

	public void storeAllWords() {
		// get POS of individual words of text
		// and set the nouns of the sentence
		for (CoreLabel token : a.get(TokensAnnotation.class)) {
			String word = token.get(LemmaAnnotation.class);// sets to inferred lemma (dictionary meaning)
			String pos = token.get(PartOfSpeechAnnotation.class);
			add(word, pos);
		}
	}

	public String lemmaWord() {
		List<CoreMap> list = a.get(SentencesAnnotation.class);
		return list.get(0).get(TokensAnnotation.class).get(0).get(LemmaAnnotation.class);
	}

}
