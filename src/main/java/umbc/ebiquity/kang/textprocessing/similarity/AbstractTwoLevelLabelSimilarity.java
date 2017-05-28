package umbc.ebiquity.kang.textprocessing.similarity;

import java.util.List;

import uk.ac.shef.wit.simmetrics.similaritymetrics.QGramsDistance;

/**
 * This abstract class is for all classes that compute similarity between labels
 * using both character-level similarity and token-level similarity.
 * 
 * @author yankang
 *
 */
public abstract class AbstractTwoLevelLabelSimilarity implements ILabelSimilarity {

	private QGramsDistance ngramSimilarity;
	private ITokenListSimilarity tokenListSimilarity;

	/**
	 * Constructor.
	 * 
	 * @param wordListSimilarity
	 * @param ngramSimilarity
	 */
	protected AbstractTwoLevelLabelSimilarity(ITokenListSimilarity wordListSimilarity, QGramsDistance ngramSimilarity) {
		this.tokenListSimilarity = wordListSimilarity;
		this.ngramSimilarity = ngramSimilarity;
	}

	/**
	 * Computes similarity between two list of tokens. Word-level and
	 * character-level similarities will be calculated and the bigger will be
	 * return.
	 * 
	 * @param wordList1
	 *            the first list of tokens
	 * @param wordList2
	 *            the second list of tokens
	 * @return the similarity
	 */
	protected double computeTokenListSimilarity(List<String> wordList1, List<String> wordList2) {
		String label1 = concatenateListAsString(wordList1);
		String label2 = concatenateListAsString(wordList2);
		double wordLevel_sim = tokenListSimilarity.computeSimilarity(wordList1, wordList2);
		double charLevel_sim = ngramSimilarity.getSimilarity(label1, label2);
		double similarity = wordLevel_sim > charLevel_sim ? wordLevel_sim : charLevel_sim;
		return similarity;
	}
	
	
	/**
	 * Concatenates a list of tokens into a string
	 * 
	 * @param list
	 *            the list of tokens
	 * @return a string
	 */
	private String concatenateListAsString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String token : list) {
			sb.append(token);
		}
		return sb.toString().toLowerCase();
	}
}
