package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveOFeature extends Feature {
	public NaiveOFeature() {
		this.pos=Pos.O;
		this.source=SourceFactory.getInstance().getSource("naive");
	}
}
