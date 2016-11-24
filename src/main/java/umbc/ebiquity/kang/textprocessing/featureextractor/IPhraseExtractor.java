package umbc.ebiquity.kang.textprocessing.featureextractor;

import java.util.Collection;

import umbc.ebiquity.kang.textprocessing.feature.Phrase;

public interface IPhraseExtractor {
	
	public Collection<String> extractPhraseStrings(String[] words, int phraseLength);

	public Collection<Phrase> extractPhrases(String[] words, int phraseLength);

}
