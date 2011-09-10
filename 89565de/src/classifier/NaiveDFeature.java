package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveDFeature extends Feature {
	public NaiveDFeature() {
		this.pos=Pos.D;
		this.source=NaiveSource.NAME;
	}
}
