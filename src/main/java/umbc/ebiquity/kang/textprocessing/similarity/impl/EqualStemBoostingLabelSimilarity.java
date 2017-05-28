package umbc.ebiquity.kang.textprocessing.similarity.impl;

import java.util.List;

import uk.ac.shef.wit.simmetrics.similaritymetrics.QGramsDistance;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserQGram3Extended;
import umbc.ebiquity.kang.textprocessing.similarity.AbstractTwoLevelLabelSimilarity;
import umbc.ebiquity.kang.textprocessing.similarity.ITokenListSimilarity;
import umbc.ebiquity.kang.textprocessing.util.TextProcessingUtils;

public class EqualStemBoostingLabelSimilarity extends AbstractTwoLevelLabelSimilarity {

	private ITokenListSimilarity stemWordBasedListSimilarity = new StemWordBasedTokenListSimilarity();
	private boolean penality = true;

	private double differenceThreshold = 0.2;
	private double equalityThreshold = 0.9;
	private double penalityFactor = -0.15;
	
	public EqualStemBoostingLabelSimilarity(ITokenListSimilarity wordListSimilarity) {
		super(wordListSimilarity, new QGramsDistance(new TokeniserQGram3Extended()));
	}
	
	public EqualStemBoostingLabelSimilarity(ITokenListSimilarity wordListSimilarity, boolean penality) {
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
		
		List<String> wordList1 = TextProcessingUtils.tokenizeLabel2List(label1);
		List<String> wordList2 = TextProcessingUtils.tokenizeLabel2List(label2);
		if (wordList1.size() == 0 || wordList2.size() == 0){
			return 0.0;
		} 
		
		double similarity = super.computeTokenListSimilarity(wordList1, wordList2);

		// If similarity score is in the range of [differenceThreshold,
		// equalityThreshold), we will check whether there is a chance that we
		// can boost the similarity if the two labels have the same stem words
		if (similarity >= this.equalityThreshold || similarity < differenceThreshold) {
			return similarity;
		}

		double boostingFactor = this.boosting(stemWordBasedListSimilarity.computeSimilarity(wordList1, wordList2));
		if (boostingFactor == 0 && penality) {
			boostingFactor = penalityFactor;
			return similarity + similarity * boostingFactor;
		}

		similarity = similarity + (1 - similarity) * boostingFactor;
		return similarity;
	}
	
	private double boosting(double value) {
		return Math.sqrt(1 - (value - 1) * (value - 1));
	}

}
