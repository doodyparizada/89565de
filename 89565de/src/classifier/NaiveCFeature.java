package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveCFeature extends Feature {
	public NaiveCFeature() {
		this.pos=Pos.C;
		this.source=NaiveSource.NAME;

	}
}
