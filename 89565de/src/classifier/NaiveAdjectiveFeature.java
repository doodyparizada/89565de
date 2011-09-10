package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveAdjectiveFeature extends Feature {
	public NaiveAdjectiveFeature() {
		this.pos=Pos.ADJECTIVE;
		this.source = NaiveSource.NAME;

	}
}
