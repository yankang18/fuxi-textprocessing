package umbc.ebiquity.kang.textprocessing.similarity;

public interface ILabelSimilarity {

	/**
	 * Computes the similarity between two labels
	 * 
	 * @param label1
	 *            the first label
	 * @param label2
	 *            the second label
	 * @return the similarity score in the range of [0, 1]
	 */
	double computeLabelSimilarity(String label1, String label2);
}
