package umbc.ebiquity.kang.textprocessing.similarity;

import java.util.List;

public interface ITokenListSimilarity {

	/**
	 * Computes the similarity between two list of tokens.
	 * 
	 * @param tokenList1
	 *            the first list of tokens
	 * @param tokenList2
	 *            the second list of tokens
	 * @return a similarity score in the range of [0, 1]
	 */
	double computeSimilarity(List<String> tokenList1, List<String> tokenList2);

}
