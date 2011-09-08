package classifier;

import pos.Pos;
import source.SourceFactory;

public class DirectVerbFeature extends Feature {
	public DirectVerbFeature() {
		this.pos=Pos.VERB;
		this.source=SourceFactory.getInstance().getSource("direct");

	}
}
