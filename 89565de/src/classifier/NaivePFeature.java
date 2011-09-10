package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaivePFeature extends Feature {
	public NaivePFeature() {
		this.pos=Pos.P;
		this.source=NaiveSource.NAME;

	}
}
