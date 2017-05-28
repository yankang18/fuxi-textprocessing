package umbc.ebiquity.kang.textprocessing.similarity.impl;

import java.util.List;

import umbc.ebiquity.kang.textprocessing.similarity.ITokenListSimilarity;

public class StemWordBasedTokenListSimilarity implements ITokenListSimilarity {

	@Override
	public double computeSimilarity(List<String> tokenList1, List<String> tokenList2) {
		if(tokenList1.size() == 0 || tokenList2.size() == 0){
			return 0.0;
		} 
		
		int wordListSize1 = tokenList1.size();
		int wordListSize2 = tokenList2.size();
		int minWordListSize = wordListSize1 < wordListSize2 ? wordListSize1 : wordListSize2;
		int count = 0;
		for (int z = 0, i = wordListSize1 - 1, j = wordListSize2 - 1; z < minWordListSize; z++, i--, j--) {
			if (tokenList1.get(i).equals(tokenList2.get(j))) {
				count++;
			} else {
				break;
			}
		}
		double aveWordListSize = 0.5 * ((double) wordListSize1 + (double) wordListSize2);
		return (double) count / aveWordListSize;
	}


}
