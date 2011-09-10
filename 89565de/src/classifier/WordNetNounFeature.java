package classifier;

import pos.Pos;
import source.SourceFactory;
import source.WordNetAdapter;

public class WordNetNounFeature extends Feature {
	public WordNetNounFeature() {
		this.pos=Pos.NOUN;
		this.source=WordNetAdapter.NAME;

	}
}
