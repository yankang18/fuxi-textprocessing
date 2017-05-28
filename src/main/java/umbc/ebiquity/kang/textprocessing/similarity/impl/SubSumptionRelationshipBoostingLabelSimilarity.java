package umbc.ebiquity.kang.textprocessing.similarity.impl;

import java.util.List;

import uk.ac.shef.wit.simmetrics.similaritymetrics.QGramsDistance;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserQGram3Extended;
import umbc.ebiquity.kang.textprocessing.similarity.AbstractTwoLevelLabelSimilarity;
import umbc.ebiquity.kang.textprocessing.similarity.ITokenListSimilarity;
import umbc.ebiquity.kang.textprocessing.util.TextProcessingUtils;

public class SubSumptionRelationshipBoostingLabelSimilarity extends AbstractTwoLevelLabelSimilarity {
	
	private ITokenListSimilarity stemWordBasedListSimilarity = new StemWordBasedTokenListSimilarity();
	private boolean penality = true;

	private double differenceThreshold = 0.2;
	private double equalityThreshold = 0.9;
	private double penalityRate = -0.4;
	private double boostingRate = 0.8;
	
	public SubSumptionRelationshipBoostingLabelSimilarity(ITokenListSimilarity wordListSimilarity) {
		super(wordListSimilarity, new QGramsDistance(new TokeniserQGram3Extended()));
	}
	
	public SubSumptionRelationshipBoostingLabelSimilarity(ITokenListSimilarity wordListSimilarity, boolean penality) {
		super(wordListSimilarity, new QGramsDistance(new TokeniserQGram3Extended()));
		this.penality = penality;
	}
	
	@Override
	public double computeLabelSimilarity(String label1, String label2) {
		
		if (label1 == null || label2 == null)
			return 0.0;
		
		label1 = label1.trim().toLowerCase();
		label2 = label2.trim().toLowerCase();
		if (label1.equals(label2))
			return 1.0;
		
		List<String> wordList1 = TextProcessingUtils.tokenizeLabel2List(label1, true, true, false);
		List<String> wordList2 = TextProcessingUtils.tokenizeLabel2List(label2, true, true, false);
		if (wordList1.size() == 0 || wordList2.size() == 0){
			return 0.0;
		} 
		
		double similarity = super.computeTokenListSimilarity(wordList1, wordList2);

		// If similarity score is in the range of [differenceThreshold,
		// equalityThreshold), we will check whether there is a chance that we
		// can boost the similarity if one label is sub-sumption of the other
		if (similarity >= this.equalityThreshold || similarity < differenceThreshold) {
			return similarity;
		}

		double rootSimilarityFactor = this.stemWordBasedListSimilarity.computeSimilarity(wordList1, wordList2);
		if (rootSimilarityFactor > 0) {
			int labelLengthDiff = label1.length() - label2.length();
			if (labelLengthDiff > 0 && label1.endsWith(label2)
					|| labelLengthDiff < 0 && label2.endsWith(label1)) {
				/*
				 * when the concept has more words than the onto-class, boosting
				 * the similarity between the concept and the onto-class when
				 * the concept is highly likely the subclass of the onto-class
				 * (e.g., AB:B, ABC:BC, ABC:C, ABCD:BCD, ABCD:CD, etc.)
				 */
				similarity = similarity + boostingRate * (1 - similarity) * rootSimilarityFactor;
			} else if (penality) {
				double penalityFactor = penalityRate * similarity;
				similarity = similarity + similarity * penalityFactor;
			}
		} else {
			if (penality) {
				double penalityFactor = this.penalityRate;
				similarity = similarity + similarity * penalityFactor;
			}
		}
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
