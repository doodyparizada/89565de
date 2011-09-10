package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveVerbFeature extends Feature {
	public NaiveVerbFeature() {
		this.pos=Pos.VERB;
		this.source=NaiveSource.NAME;
	}
}
