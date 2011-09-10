package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveOFeature extends Feature {
	public NaiveOFeature() {
		this.pos=Pos.O;
		this.source=NaiveSource.NAME;
	}
}
