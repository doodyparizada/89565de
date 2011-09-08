package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveNouneFeature extends Feature {
	public NaiveNouneFeature() {
		this.pos=Pos.NOUN;
		this.source=SourceFactory.getInstance().getSource("naive");
	}
}
