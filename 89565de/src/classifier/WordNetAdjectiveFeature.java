package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;
import source.WordNetAdapter;

public class WordNetAdjectiveFeature extends Feature {
	public WordNetAdjectiveFeature() {
		this.pos=Pos.ADJECTIVE;
		this.source=WordNetAdapter.NAME;

	}
}
