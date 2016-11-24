package umbc.ebiquity.kang.textprocessing.featureextractor;

import java.util.Collection;

import umbc.ebiquity.kang.textprocessing.feature.Phrase;
import umbc.ebiquity.kang.textprocessing.feature.SubString;

public interface ILexicalFeatureExtractor {

	Collection<SubString> extractCommonSubStrings(Collection<String> LabelCollections);

	Collection<String> computeCommonPhrasesInString(Collection<String> wordSets);

	Collection<Phrase> extractCommonPhrases(Collection<String> wordSets);

	String[] normalizeLabelToArray(String label);

	String normalizeLabelToString(String label); 
 
}
