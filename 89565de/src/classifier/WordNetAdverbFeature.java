package classifier;

import pos.Pos;
import source.SourceFactory;
import source.WordNetAdapter;

public class WordNetAdverbFeature extends Feature {
	public WordNetAdverbFeature() {
		this.pos=Pos.ADVERB;
		this.source=WordNetAdapter.NAME;

	}
}
