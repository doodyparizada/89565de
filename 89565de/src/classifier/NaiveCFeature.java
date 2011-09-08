package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveCFeature extends Feature {
	public NaiveCFeature() {
		this.pos=Pos.C;
		this.source=SourceFactory.getInstance().getSource("naive");

	}
}
