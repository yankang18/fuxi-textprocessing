package umbc.ebiquity.kang.textprocessing.similarity;

import java.util.List;

public interface IWordListSimilarity {
	
	double computeSimilarity(List<String> wordList1, List<String> wordList2);

}
