package umbc.ebiquity.kang.textprocessing.feature;

public class SubString extends LexicalFeature {

	public SubString(String label, Position subStringPosition) {
		super(label, subStringPosition, Type.SUBSTRING);
	}
}
