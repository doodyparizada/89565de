package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveCarFeature extends Feature {
	public NaiveCarFeature() {
		this.pos=Pos.CAR;
		this.source=NaiveSource.NAME;

	}
}
