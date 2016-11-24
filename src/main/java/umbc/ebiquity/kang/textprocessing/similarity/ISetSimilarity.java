package umbc.ebiquity.kang.textprocessing.similarity;

import java.util.Set;

public interface ISetSimilarity {

	double computeSimilarity(Set<String> set1, Set<String> set2);

	void setSignificantMathcThreshold(double threshold); 
}
