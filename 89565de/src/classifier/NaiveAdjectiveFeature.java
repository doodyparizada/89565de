package classifier;

import pos.Pos;
import source.SourceFactory;

public class NaiveAdjectiveFeature extends Feature {
	public NaiveAdjectiveFeature() {
		this.pos=Pos.ADJECTIVE;
		this.source=SourceFactory.getInstance().getSource("naive");

	}
}
