package umbc.ebiquity.kang.textprocessing.similarity.impl;

import java.util.List;

import umbc.ebiquity.kang.textprocessing.similarity.ILabelSimilarity;
import umbc.ebiquity.kang.textprocessing.similarity.ITokenListSimilarity;
import umbc.ebiquity.kang.textprocessing.util.TextProcessingUtils;

public class SimpleLabelSimilarity implements ILabelSimilarity {

	private ITokenListSimilarity wordListSimilarity;

	public SimpleLabelSimilarity(ITokenListSimilarity wordListSimilarity) {
		this.wordListSimilarity = wordListSimilarity;
	}

	@Override
	public double computeLabelSimilarity(String label1, String label2) {
		List<String> list1 = TextProcessingUtils.tokenizeLabel2List(label1);
		List<String> list2 = TextProcessingUtils.tokenizeLabel2List(label2);
		return this.wordListSimilarity.computeSimilarity(list1, list2);
	}

}
