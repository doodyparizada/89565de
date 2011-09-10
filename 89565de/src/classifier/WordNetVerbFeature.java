package classifier;

import pos.Pos;
import source.SourceFactory;
import source.WordNetAdapter;

public class WordNetVerbFeature extends Feature {
	public WordNetVerbFeature() {
		this.pos=Pos.VERB;
		this.source=WordNetAdapter.NAME;

	}
}
