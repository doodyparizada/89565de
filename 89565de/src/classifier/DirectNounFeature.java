package classifier;

import pos.Pos;
import source.DirectAdapter;

public class DirectNounFeature extends Feature {
	public DirectNounFeature() {
		this.pos=Pos.NOUN;
		this.source = DirectAdapter.NAME;
	}
}
