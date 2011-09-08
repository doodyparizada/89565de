package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaivePFeature extends Feature {
	public NaivePFeature() {
		this.pos=Pos.P;
		this.source=SourceFactory.getInstance().getSource("naive");

	}
}
