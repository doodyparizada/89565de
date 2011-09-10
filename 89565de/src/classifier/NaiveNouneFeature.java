package classifier;

import pos.Pos;
import source.NaiveSource;
import source.SourceFactory;

public class NaiveNouneFeature extends Feature {
	public NaiveNouneFeature() {
		this.pos=Pos.NOUN;
		this.source=NaiveSource.NAME;
	}
}
