package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveDFeature extends Feature {
	public NaiveDFeature() {
		this.pos=Pos.D;
		this.source=SourceFactory.getInstance().getSource("naive");
	}
}
