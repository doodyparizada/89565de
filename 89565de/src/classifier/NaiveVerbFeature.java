package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveVerbFeature extends Feature {
	public NaiveVerbFeature() {
		this.pos=Pos.VERB;
		this.source=SourceFactory.getInstance().getSource("naive");
	}
}
